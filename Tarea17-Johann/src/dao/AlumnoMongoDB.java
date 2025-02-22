package dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

import modelo.Alumno;
import modelo.Grupo;
import pool.ConexionMongo;

public class AlumnoMongoDB implements AlumnoDao {

	private static final Logger logger = LogManager.getLogger(AlumnoMongoDB.class);

	private static AlumnoMongoDB instance;

	static {
		instance = new AlumnoMongoDB();
	}

	private AlumnoMongoDB() {
	}

	public static AlumnoMongoDB getInstance() {
		return instance;
	}

	@Override
	public int insertarAlumno(Alumno al) throws Exception {
		try {
			MongoDatabase conn = ConexionMongo.getDatabase();

			MongoCollection<Document> coleccion = conn.getCollection("alumnos");
			InsertOneResult resultado;

			ObjectId idGrupo = buscarIdGrupo(al.getId_grupo());

			if (idGrupo == null) {
				logger.error("Error: No se encontró el grupo con idGrupo " + al.getId_grupo());
				return 0;
			}

			Document alumno = new Document("nia", al.getNia()).append("nombre", al.getNombre())
					.append("apellidos", al.getApellidos()).append("genero", al.getGenero())
					.append("fechaNacimiento", al.getNacimiento().toString()).append("ciclo", al.getCiclo())
					.append("curso", al.getCurso()).append("idGrupo", idGrupo);

			resultado = coleccion.insertOne(alumno);

			if (resultado.wasAcknowledged()) {
				logger.info("Alumno insertado correctamente.");
				return 1;
			} else {
				logger.info("Alumno no insertado.");
				return 0;
			}
		} catch (Exception e) {
			logger.error("Error al insertar alumno." + e.getMessage(), e);
			return 0;
		}
	}

	public ObjectId buscarIdGrupo(int idGrupo) {
		try {
			MongoCollection<Document> grupos = ConexionMongo.getDatabase().getCollection("grupos");

			Document grupo = grupos.find(Filters.eq("idGrupo", idGrupo)).first();

			if (grupo != null) {
				return grupo.getObjectId("_id");
			}
			return null;

		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public int insertarGrupo(Grupo gp) throws Exception {
		try {
			MongoDatabase conn = ConexionMongo.getDatabase();

			MongoCollection<Document> coleccion = conn.getCollection("grupos");
			InsertOneResult resultado;

			Document grupo = new Document("idGrupo", gp.getId_grupo()).append("nombre", gp.getNombre()).append("aula",
					gp.getAula());

			resultado = coleccion.insertOne(grupo);

			if (resultado.wasAcknowledged()) {
				logger.info("Grupo insertado correctamente.");
				return 1;
			} else {
				logger.info("Grupo no insertado.");
				return 0;
			}
		} catch (Exception e) {
			logger.error("Error al insertar grupo." + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public List<Alumno> mostrarAlumnos() throws Exception {
		try {
			MongoCollection<Document> alumnos = ConexionMongo.getDatabase().getCollection("alumnos");

			Alumno al;
			List<Alumno> listaAlumnos = new ArrayList<>();

			for (Document doc : alumnos.find()) {
				al = new Alumno();
				al.setNia(doc.getInteger("nia"));
				al.setNombre(doc.getString("nombre"));
				al.setApellidos(doc.getString("apellidos"));
				al.setGenero(doc.getString("genero"));
				al.setNacimiento(LocalDate.parse(doc.getString("fechaNacimiento")));
				al.setCiclo(doc.getString("ciclo"));
				al.setCurso(doc.getString("curso"));
				al.setId_grupo(doc.getInteger("idGrupo"));

				listaAlumnos.add(al);
			}
			logger.info("Lista de alumnos creada correctamente.");
			return listaAlumnos;
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return null;
		}
	}

	public List<Grupo> mostrarGrupo() throws Exception {
		try {
			MongoCollection<Document> alumnos = ConexionMongo.getDatabase().getCollection("grupos");

			Grupo gp;
			List<Grupo> listaGrupos = new ArrayList<>();

			for (Document doc : alumnos.find()) {
				gp = new Grupo();
				gp.setId_grupo(doc.getInteger("idGrupo"));
				gp.setNombre(doc.getString("nombre"));
				gp.setAula(doc.getInteger("aula"));

				listaGrupos.add(gp);
			}
			logger.info("Lista de grupos creada correctamente.");
			return listaGrupos;
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public int cambiarNombre(String nombre, int id) throws Exception {
		try {
			MongoCollection<Document> cambioNombre = ConexionMongo.getDatabase().getCollection("alumnos");

			Bson filtro = Filters.eq("nia", id);
			if (cambioNombre.countDocuments(filtro) > 0) {
				cambioNombre.updateOne(filtro, Updates.set("nombre", nombre));
				logger.info("Nombre del alumno cambiado correctamente.");
				return 1;
			} else {
				logger.info("No se ha cambiado correctamente el nombre.");
				return 0;
			}
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return 0;
		}
	}

	@Override
	public void borrarPorPK(int id) throws Exception {
		try {
			MongoCollection<Document> borrarPorPk = ConexionMongo.getDatabase().getCollection("alumnos");
			Bson filtro = Filters.eq("nia", id);

			if (borrarPorPk.countDocuments(filtro) > 0) {
				borrarPorPk.deleteOne(filtro);
				logger.info("Alumno borrado correctamente.");
			} else {
				logger.info("No se encontro un alumno con ese nia.");
			}
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
		}
	}

	@Override
	public void borrarPorApellido(String apellido) throws Exception {
		try {
			MongoCollection<Document> borrarPorPk = ConexionMongo.getDatabase().getCollection("alumnos");
			Bson filtro = Filters.regex("apellidos", apellido, "i");

			if (borrarPorPk.countDocuments(filtro) > 0) {
				borrarPorPk.deleteMany(filtro);
				logger.info("Alumno borrado correctamente.");
			} else {
				logger.info("No se encontro un alumno que tenga ese apellido.");
			}
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
		}
	}

	@Override
	public void borrarAlumnosPorCurso(String curso) throws Exception {
		try {
			MongoCollection<Document> borrarPorPk = ConexionMongo.getDatabase().getCollection("alumnos");
			Bson filtro = Filters.eq("curso", curso);

			if (borrarPorPk.countDocuments(filtro) > 0) {
				borrarPorPk.deleteMany(filtro);
				logger.info("Alumno borrado correctamente.");
			} else {
				logger.info("No se encontro un alumno que tenga ese apellido.");
			}
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
		}
	}

	@Override
	public List<Alumno> mostrarAlumnosPorGrupo(int id_grupo) throws Exception {
		List<Alumno> listaAlumnos = mostrarAlumnos();
		List<Grupo> listaGrupos = mostrarGrupo();
		List<Alumno> alumnosFiltrados = new ArrayList<>();

		try {
			Grupo grupoSeleccionado = listaGrupos.stream().filter(g -> g.getId_grupo() == id_grupo).findFirst()
					.orElse(null);

			if (grupoSeleccionado == null) {
				logger.info("El grupo no existe.");
				return alumnosFiltrados;
			}

			for (Alumno al : listaAlumnos) {
				if (al.getId_grupo() == id_grupo) {
					alumnosFiltrados.add(al);
				}
			}

			if (alumnosFiltrados.isEmpty()) {
				logger.info("No hay alumnos en es grupo.");
			}
			return alumnosFiltrados;

		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Alumno mostrarAlumnoPorPK(int nia) throws Exception {
		List<Alumno> listaAlumnos = mostrarAlumnos();
		try {

			if (listaAlumnos.isEmpty()) {
				logger.info("No hay alumnos registrados.");
				return null;
			}

			listaAlumnos.forEach(al -> System.out.println("NIA: " + al.getNia() + " - Nombre: " + al.getNombre()));

			Alumno alumnoSeleccionado = listaAlumnos.stream().filter(al -> al.getNia() == nia).findFirst().orElse(null);

			if (alumnoSeleccionado == null) {
				logger.info("No se encontró un alumno con el alumno.");
				return null;
			}
			logger.info("Se mostró correctamente el alumno.");

			return alumnoSeleccionado;
		} catch (Exception e) {
			logger.error("Error en la base de datos." + e.getMessage(), e);
			return null;
		}
	}

	@Override
	public void cambiarGrupo(int nia, int nuevoId) throws Exception {
		List<Alumno> listaAlumnos = mostrarAlumnos();
		List<Grupo> listaGrupos = mostrarGrupo();

		if (listaAlumnos.isEmpty()) {
			logger.info("No hay alumnos registrados.");
			return;
		}
		if (listaGrupos.isEmpty()) {
			logger.info("No hay grupos registrados.");
			return;
		}

		System.out.println("\nLista de Alumnos:");
		System.out.println("-----------------");
		for (Alumno alumno : listaAlumnos) {
			System.out.println("NIA: " + alumno.getNia() + " - Nombre: " + alumno.getNombre());
		}

		Alumno alumnoSeleccionado = null;
		for (Alumno alumno : listaAlumnos) {
			if (alumno.getNia() == nia) {
				alumnoSeleccionado = alumno;
				break;
			}
		}
		if (alumnoSeleccionado == null) {
			logger.info("No se encontró un alumno con el NIA: " + nia);
			return;
		}

		System.out.println("\nLista de Grupos:");
		System.out.println("----------------");
		for (Grupo grupo : listaGrupos) {
			System.out.println("ID Grupo: " + grupo.getId_grupo() + " - Aula: " + grupo.getAula());
		}

		boolean grupoExiste = false;
		for (Grupo grupo : listaGrupos) {
			if (grupo.getId_grupo() == nuevoId) {
				grupoExiste = true;
				break;
			}
		}
		if (!grupoExiste) {
			logger.info("No se encontró un grupo con ese id.");
			return;
		}

		try {
			MongoCollection<Document> alumnosCollection = ConexionMongo.getDatabase().getCollection("alumnos");
			Bson filtro = Filters.eq("nia", nia);
			Bson actualizacion = Updates.set("idGrupo", nuevoId);
			UpdateResult resultado = alumnosCollection.updateOne(filtro, actualizacion);

			if (resultado.getModifiedCount() > 0) {
				logger.info("Alumno cambiado de grupo correctamente.");
			} else {
				logger.info("No se pudo cambiar el grupo del alumno.");
			}
		} catch (Exception e) {
			logger.error("Error en la base de datos: " + e.getMessage(), e);
		}
	}

	@Override
	public void cerrarConexion() throws Exception {
		ConexionMongo.cerrarConexion();
	}
}