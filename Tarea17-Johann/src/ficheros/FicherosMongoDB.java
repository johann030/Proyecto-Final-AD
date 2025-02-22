package ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import dao.AlumnoMongoDB;
import modelo.Alumno;
import modelo.Grupo;

public class FicherosMongoDB implements Ficheros {

	private static final Logger logger = LogManager.getLogger(FicherosMongoDB.class);

	private AlumnoMongoDB dao = AlumnoMongoDB.getInstance();

	private static FicherosMongoDB instance;

	static {
		instance = new FicherosMongoDB();
	}

	private FicherosMongoDB() {
	}

	public static FicherosMongoDB getInstance() {
		return instance;
	}

	@Override
	public void guardarTxtAlumnos() throws Exception {
		List<Alumno> al = dao.mostrarAlumnos();

		try (BufferedWriter bw = new BufferedWriter(new FileWriter("alumnosMongoDB.txt"))) {
			if (al.isEmpty()) {
				logger.info("No hay alumnos para guardar.");
			} else {
				for (Alumno alumno : al) {
					bw.write(String.format("%d, %s, %s, %s, %s, %s, %s, %d", alumno.getNia(), alumno.getNombre(),
							alumno.getApellidos(), alumno.getGenero(), alumno.getNacimiento(), alumno.getCiclo(),
							alumno.getCurso(), alumno.getId_grupo()));
					bw.newLine();
				}
				logger.info("Escritura he fichero txt hecha correctamente.");
			}
		}
	}

	@Override
	public void leerTxtAlumnos() throws Exception {
		try (BufferedReader br = new BufferedReader(new FileReader("alumnosMongoDB.txt"))) {
			String lineas;
			while ((lineas = br.readLine()) != null) {
				String[] alumnoData = lineas.split(",");
				int nia = Integer.parseInt(alumnoData[0].trim());
				String nombre = alumnoData[1].trim();
				String apellidos = alumnoData[2].trim();
				String genero = alumnoData[3].trim();
				LocalDate nacimiento = LocalDate.parse(alumnoData[4].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
				String ciclo = alumnoData[5].trim();
				String curso = alumnoData[6].trim();
				int id_grupo = Integer.parseInt(alumnoData[7].trim());

				dao.insertarAlumno(new Alumno(nia, nombre, apellidos, genero, nacimiento, ciclo, curso, id_grupo));
			}
		} catch (Exception e) {
			logger.error("Error al leer el archivo" + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void guardarJSONGrupos() throws Exception {
		JSONArray listaGrupos = new JSONArray();
		List<Grupo> grupos = ((AlumnoMongoDB) dao).mostrarGrupo();
		List<Alumno> alumnos = dao.mostrarAlumnos();

		try (FileWriter JSON = new FileWriter("gruposMongoDB.json")) {
			for (Grupo grupo : grupos) {
				JSONObject grupoJSON = new JSONObject();
				grupoJSON.put("id_grupo", grupo.getId_grupo());
				grupoJSON.put("nombre_grupo", grupo.getNombre());
				grupoJSON.put("aula", grupo.getAula());

				JSONArray listaAlumnos = new JSONArray();

				for (Alumno alumno : alumnos) {
					if (alumno.getId_grupo() == grupo.getId_grupo()) {
						JSONObject alumnoJSON = new JSONObject();
						alumnoJSON.put("NIA", alumno.getNia());
						alumnoJSON.put("nombre", alumno.getNombre());
						alumnoJSON.put("apellidos", alumno.getApellidos());
						alumnoJSON.put("genero", alumno.getGenero());
						alumnoJSON.put("fecha_nacimiento", alumno.getNacimiento());
						alumnoJSON.put("ciclo", alumno.getCiclo());
						alumnoJSON.put("curso", alumno.getCurso());

						listaAlumnos.add(alumnoJSON);
					}
				}
				grupoJSON.put("alumnos", listaAlumnos);
				listaGrupos.add(grupoJSON);
			}
			JSON.write(listaGrupos.toJSONString());
			JSON.flush();

			logger.info("Escritura he fichero JSON hecha correctamente.");

		} catch (Exception e) {
			logger.error("Error al escribir en el archivo." + e.getMessage(), e);
		}
	}

	@Override
	public void leerJSONGrupos() throws Exception {
		try {
			JSONParser parser = new JSONParser();
			JSONArray gruposArray = (JSONArray) parser.parse(new FileReader("gruposMongoDB.json"));

			if (gruposArray == null) {
				logger.info("El JSON esta vacio.");
				return;
			}
			for (Object grupoObj : gruposArray) {
				JSONObject grupoJSON = (JSONObject) grupoObj;

				int id_grupo = ((Long) grupoJSON.get("id_grupo")).intValue();
				String nombre_grupo = (String) grupoJSON.get("nombre_grupo");
				int aula = ((Long) grupoJSON.get("aula")).intValue();

				dao.insertarGrupo(new Grupo(id_grupo, nombre_grupo, aula));

				JSONArray alumnosArray = (JSONArray) grupoJSON.get("alumnos");
				for (Object alumnoObj : alumnosArray) {
					JSONObject alumnoJSON = (JSONObject) alumnoObj;

					int NIA = ((Long) alumnoJSON.get("NIA")).intValue();
					String nombre = (String) alumnoJSON.get("nombre");
					String apellidos = (String) alumnoJSON.get("apellidos");
					String genero = (String) alumnoJSON.get("genero");
					String fechaNacimientoStr = (String) alumnoJSON.get("fecha_nacimiento");
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDate fecha_nacimiento = LocalDate.parse(fechaNacimientoStr, formatter);
					String ciclo = (String) alumnoJSON.get("ciclo");
					String curso = (String) alumnoJSON.get("curso");
					dao.insertarAlumno(
							new Alumno(NIA, nombre, apellidos, genero, fecha_nacimiento, ciclo, curso, id_grupo));
				}
			}
			logger.info("JSON leido e insertado correctamente.");

		} catch (Exception e) {
			logger.error("Error al leer el archivo" + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void elegirGrupoJSON(int idGrupo) throws Exception {
		List<Grupo> grupos = dao.mostrarGrupo();
		List<Alumno> alumnos = dao.mostrarAlumnos();
		JSONObject grupoJSON = new JSONObject();

		try (FileWriter JSON = new FileWriter("gruposHibernateElegido.json")) {
			for (Grupo grupo : grupos) {
				grupoJSON.put("id_grupo", grupo.getId_grupo());
				grupoJSON.put("nombre_grupo", grupo.getNombre());
				grupoJSON.put("aula", grupo.getAula());

				JSONArray listaAlumnos = new JSONArray();

				for (Alumno alumno : alumnos) {
					if (alumno.getId_grupo() == grupo.getId_grupo()) {
						JSONObject alumnoJSON = new JSONObject();
						alumnoJSON.put("NIA", alumno.getNia());
						alumnoJSON.put("nombre", alumno.getNombre());
						alumnoJSON.put("apellidos", alumno.getApellidos());
						alumnoJSON.put("genero", alumno.getGenero());
						alumnoJSON.put("fecha_nacimiento", alumno.getNacimiento());
						alumnoJSON.put("ciclo", alumno.getCiclo());
						alumnoJSON.put("curso", alumno.getCurso());

						listaAlumnos.add(alumnoJSON);
					}
				}
				grupoJSON.put("alumnos", listaAlumnos);
			}
			JSON.write(grupoJSON.toJSONString());
			JSON.flush();

			logger.info("Escritura he fichero JSON hecha correctamente.");

		} catch (Exception e) {
			logger.error("Error al escribir en el archivo." + e.getMessage(), e);
		}
	}
}
