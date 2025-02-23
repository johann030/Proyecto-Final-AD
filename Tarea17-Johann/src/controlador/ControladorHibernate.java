package controlador;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import daoHibernate.AlumnoDao;
import ficheros.Ficheros;
import ficheros.FicherosHibernate;
import modeloHibernate.AlumnoH;
import modeloHibernate.GrupoH;
import vistaHibernate.IVistaH;

public class ControladorHibernate {

	private static final Logger logger = LogManager.getLogger(ControladorHibernate.class);

	private Ficheros fichero = FicherosHibernate.getInstance();

	public ControladorHibernate() {
	}

	public void ejecutar(AlumnoDao modelo, IVistaH vista) {
		int opcion = -1;

		do {
			try {
				opcion = vista.menu();

			} catch (NumberFormatException e) {
				logger.error("Error al dar las opciones. " + e.getMessage(), e);
				continue;

			}
			switch (opcion) {
			case 1:
				insertarAlumno(modelo, vista);

				break;
			case 2:
				insertarGrupo(modelo, vista);

				break;
			case 3:
				mostrarAlumnos(modelo, vista);

				break;
			case 4:
				guardarAlumnos();

				break;
			case 5:
				recogerAlumnos();

				break;
			case 6:
				cambiarNombre(modelo, vista);

				break;
			case 7:
				borrarPorPK(modelo, vista);

				break;
			case 8:
				borrarPorApellido(modelo, vista);

				break;
			case 9:
				borrarPorCurso(modelo, vista);

				break;
			case 10:
				guardarGrupos();

				break;
			case 11:
				recogerGrupos();

				break;
			case 12:
				mostrarAlumnoGrupo(modelo, vista);

				break;
			case 13:
				mostrarAlumnoPK(modelo, vista);

				break;
			case 14:
				cambiarGrupoAlumno(modelo, vista);

				break;
			case 15:
				guardarGrupoElegido(vista);

				break;
			case 16:
				logger.info("Saliendo de la aplicacion.");

				break;
			default:
				logger.error("Opcion dada no corresponde a una operacion.");
			}

		} while (opcion != 16);
	}

	public void insertarAlumno(AlumnoDao modelo, IVistaH vista) {
		AlumnoH al = vista.insertarAlumno();

		try {
			int alumnoInsertado = modelo.insertarAlumno(al);
			if (alumnoInsertado == 1) {
				logger.info("El alumno ha sido insertado correctamente.");
			} else {
				logger.info("El alumno no se ha insertado.");
			}
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
	}

	public void insertarGrupo(AlumnoDao modelo, IVistaH vista) {
		GrupoH gp = vista.insertarGrupo();

		try {
			int grupoInsertado = modelo.insertarGrupo(gp);
			if (grupoInsertado == 1) {
				logger.info("El grupo ha sido insertado correctamente.");
			} else {
				logger.info("El grupo no se ha insertado.");
			}
		} catch (Exception e) {
			logger.error("Error al insertar el grupo." + e.getMessage(), e);
		}
	}

	public void mostrarAlumnos(AlumnoDao modelo, IVistaH vista) {
		List<AlumnoH> alumnos;
		try {
			alumnos = modelo.mostrarAlumnos();
			vista.mostrarAlumnos(alumnos);
			logger.info("Se han mostrado correctamente los Alumnos");
		} catch (Exception e) {
			logger.error("Error al mostrar los alumnos." + e.getMessage(), e);
		}
	}

	public void guardarAlumnos() {
		try {
			fichero.guardarTxtAlumnos();
			logger.info("Escritura he fichero txt hecha correctamente.");
		} catch (Exception e) {
			logger.error("Error al escribir en el archivo." + e.getMessage(), e);
		}
	}

	public void recogerAlumnos() {
		try {
			fichero.leerTxtAlumnos();
			logger.info("Lectura del fichero correcta.");
		} catch (Exception e) {
			logger.error("Error al leer el archivo" + e.getMessage(), e);
		}
	}

	public void cambiarNombre(AlumnoDao modelo, IVistaH vista) {
		try {
			int nia = vista.pedirNia();
			String nombre = vista.pedirNombre();
			modelo.cambiarNombre(nombre, nia);
			logger.info("Se ha cambiado correctamente el nombre del alumno.");
		} catch (Exception e) {
			logger.error("Error al cambiar el nombre." + e.getMessage(), e);
		}
	}

	public void borrarPorPK(AlumnoDao modelo, IVistaH vista) {
		try {
			int nia = vista.pedirNia();
			modelo.borrarPorPK(nia);
			logger.info("Se han borrado correctamente el alumno.");
		} catch (Exception e) {
			logger.error("Error al borrar el alumno." + e.getMessage(), e);
		}
	}

	public void borrarPorApellido(AlumnoDao modelo, IVistaH vista) {
		try {
			String apellido = vista.pedirApellido();
			modelo.borrarPorApellido(apellido);
			logger.info("Se han borrado correctamente los alumnos.");
		} catch (Exception e) {
			logger.error("Error al borrar los alumnos." + e.getMessage(), e);
		}
	}

	public void borrarPorCurso(AlumnoDao modelo, IVistaH vista) {
		try {
			String curso = vista.pedirCurso();
			modelo.borrarAlumnosPorCurso(curso);
			logger.info("Se han borrado correctamente los alumnos.");
		} catch (Exception e) {
			logger.error("Error al borrar los alumnos." + e.getMessage(), e);
		}
	}

	public void guardarGrupos() {
		try {
			fichero.guardarJSONGrupos();
			logger.info("Escritura he fichero JSON hecha correctamente.");
		} catch (Exception e) {
			logger.error("Error al leer el archivo" + e.getMessage(), e);
		}
	}

	public void recogerGrupos() {
		try {
			fichero.leerJSONGrupos();
			logger.info("Lectura del fichero correcta.");
		} catch (Exception e) {
			logger.error("Error al leer el archivo" + e.getMessage(), e);
		}
	}

	public void mostrarAlumnoGrupo(AlumnoDao modelo, IVistaH vista) {
		try {
			int idGrupo = vista.pedirIdGrupo();
			List<AlumnoH> al = modelo.mostrarAlumnosPorGrupo(idGrupo);
			vista.mostrarAlumnos(al);
			logger.info("Se han mostrado correctamente los alumnos.");
		} catch (Exception e) {
			logger.error("Error al mostrar los alumnos." + e.getMessage(), e);
		}
	}

	@SuppressWarnings("unchecked")
	public void mostrarAlumnoPK(AlumnoDao modelo, IVistaH vista) {
		int nia = vista.pedirNia();
		try {
			AlumnoH al = modelo.mostrarAlumnoPorPK(nia);
			vista.mostrarAlumnos((List<AlumnoH>) al);
			logger.info("El alumno ha sido insertado correctamente.");

		} catch (Exception e) {
			logger.error("Error al mostrar el alumno." + e.getMessage(), e);
		}
	}

	public void cambiarGrupoAlumno(AlumnoDao modelo, IVistaH vista) {
		int nia = vista.pedirNia();
		int nuevoGrupo = vista.pedirIdGrupo();
		try {
			modelo.cambiarGrupo(nia, nuevoGrupo);
			logger.info("El alumno se ha cambiado correctamente.");
		} catch (Exception e) {
			logger.error("Error al escribir en el archivo." + e.getMessage(), e);
		}

	}

	public void guardarGrupoElegido(IVistaH vista) {
		int grupo = vista.pedirIdGrupo();
		try {
			fichero.elegirGrupoJSON(grupo);
		} catch (Exception e) {
			logger.error("Error al escribir en el archivo." + e.getMessage(), e);
		}
	}
}