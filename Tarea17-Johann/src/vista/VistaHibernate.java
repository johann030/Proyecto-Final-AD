package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import dao_hibernate.AlumnoHibernate;
import ficheros.FicherosHibernate;
import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;

public class VistaHibernate implements IVista {

	private static final Logger logger = LogManager.getLogger(VistaHibernate.class);

	private reader reader;

	private AlumnoHibernate dao;

	private FicherosHibernate fh;

	public VistaHibernate() {
		reader = new reader();
		dao = AlumnoHibernate.getInstance();
		fh = new FicherosHibernate();
	}

	public void init() {

		int opcion;

		do {
			menu();

			opcion = reader.nextInt();

			switch (opcion) {
			case 1:
				insertarAlumno();

				break;
			case 2:
				insertarGrupo();

				break;
			case 3:
				mostrarAlumnos();

				break;
			case 4:
				guardarAlumnos();

				break;
			case 5:
				recogerAlumnos();

				break;
			case 6:
				cambiarNombre();

				break;
			case 7:
				borrarPorPK();

				break;
			case 8:
				borrarPorApellido();

				break;
			case 9:
				borrarPorCurso();

				break;
			case 10:
				guardarGrupos();

				break;
			case 11:
				recogerGrupos();

				break;
			case 12:
				mostrarAlumnoGrupo();

				break;
			case 13:
				mostrarAlumnoPK();

				break;
			case 14:
				cambiarGrupoAlumno();

				break;
			case 15:
				guardarGrupoElegido();

				break;
			case 16:
				logger.info("Saliendo de la aplicacion.");

				break;
			default:
				logger.error("Opcion dada no corresponde a una operacion.");
			}

		} while (opcion != 16);
	}

	public void menu() {
		System.out.println("GESTION DE ALUMNOS");
		System.out.println("------------------");
		System.out.println("1: Insertar alumnos.");
		System.out.println("2: Insertar grupos.");
		System.out.println("3: Mostrar alumnos.");
		System.out.println("4: Guardar los alumnos en un fichero.");
		System.out.println("5: Leer los alumnos de un fichero.");
		System.out.println("6: Modificar el nombre de un alumno por su PK.");
		System.out.println("7: Eliminar un alumno por su PK.");
		System.out.println("8: Eliminar un alumno por su apellido.");
		System.out.println("9: Eliminar los alumnos por su curso.");
		System.out.println("10: Guardar los grupos en un fichero.");
		System.out.println("11: Leer los grupos de un fichero.");
		System.out.println("12: Mostrar los alumnos del grupo elegido.");
		System.out.println("13: Mostrar alumno por su PK.");
		System.out.println("14: Cambiar el grupo de un alumno.");
		System.out.println("15: Guardar el grupo que elija el usuario.");
		System.out.println("16: Salir.");
		System.out.println("------------------");
		System.out.print("¿Que opcion elige? ");
	}

	static class reader {

		BufferedReader br;
		StringTokenizer st;

		public reader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String next() {

			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException ex) {
					System.err.println("Error de lectura.");
					ex.printStackTrace();
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(next());
		}

		LocalDate nextLocalDate() {
			return LocalDate.parse(next(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		}

		String nextLine() {
			String str = "";
			try {
				if (st.hasMoreElements()) {
					str = st.nextToken("\n");
				} else {
					str = br.readLine();
				}
			} catch (IOException e) {
				System.err.println("Error de lectura.");
				e.printStackTrace();
			}
			return str;
		}
	}

	public void insertarAlumno() {
		System.out.println("\nINSERTAR ALUMNO");
		System.out.println("----------------");
		System.out.print("Introduzca el nia: ");
		int nia = reader.nextInt();
		System.out.print("Introduzca el nombre: ");
		String nombre = reader.nextLine().toUpperCase();
		System.out.print("Introduzca los apellidos: ");
		String apellidos = reader.nextLine().toUpperCase();
		System.out.print("Introduzca el genero: ");
		char g = reader.nextLine().charAt(0);
		String genero = String.valueOf(g).toUpperCase();
		System.out.print("Introduzca la fecha de nacimiento (dd/MM/aaaa): ");
		LocalDate nacimiento = reader.nextLocalDate();
		System.out.print("Introduzca el ciclo: ");
		String ciclo = reader.nextLine().toUpperCase();
		System.out.print("Introduzca el curso: ");
		String curso = reader.nextLine().toUpperCase();
		System.out.print("Introduzca el codigo del grupo: ");
		int id_grupo = reader.nextInt();

		try {
			dao.insertarAlumno(new AlumnoH(nia, nombre, apellidos, genero, nacimiento, ciclo, curso, id_grupo));
			logger.info("El alumno se inserto correctamente.");
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void insertarGrupo() {
		System.out.println("\nINSERTAR GRUPO");
		System.out.println("----------------");
		System.out.print("Introduzca el id: ");
		int id = reader.nextInt();
		System.out.print("Introduzca el nombre: ");
		String nombre = reader.nextLine();
		System.out.print("Introduzca el aula: ");
		int aula = reader.nextInt();

		try {
			dao.insertarGrupo(new GrupoH(id, nombre, aula));
			logger.info("El grupo se inserto correctamente.");
		} catch (Exception e) {
			logger.error("Error al borrar los alumnos." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void mostrarAlumnos() {
		System.out.println("\nLISTA DE ALUMNOS");
		System.out.println("----------------");
		try {
			List<AlumnoH> alumnos = dao.mostrarAlumnos();

			if (alumnos.isEmpty()) {
				logger.info("El alumno esta vacio.");
			} else {
				alumnos.forEach(System.out::println);
				logger.info("Se mostro las lista de los alumnos");
			}
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void guardarAlumnos() {
		try {
			fh.guardarTxtAlumnos();
			logger.info("Se ha guardado correctamente en el fichero.");
		} catch (Exception e) {
			logger.error("Error al guardar en el fichero." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void recogerAlumnos() {
		try {
			fh.leerTxtAlumnos();
			logger.info("Se ha leido correctamente el fichero y guardado en la base de datos.");
		} catch (Exception e) {
			logger.error("Error al guardar en el fichero." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void cambiarNombre() {
		System.out.println("\nCAMBIO DE NOMBRE");
		System.out.println("-----------------------");
		System.out.print("Introduzca el nia del alumno: ");
		int id = reader.nextInt();
		System.out.print("Introduzca el nuevo nombre: ");
		String nombre = reader.nextLine();
		try {
			dao.cambiarNombre(nombre, id);
			logger.info("El nombre se ha actualizado.");
		} catch (Exception e) {
			logger.error("Error al actualizar el nombre." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void borrarPorPK() {
		System.out.println("\nBORRAR POR NIA");
		System.out.println("-----------------------");
		System.out.print("Introduzca el nia del alumno: ");
		int id = reader.nextInt();
		try {
			dao.borrarPorPK(id);
			logger.info("Se ha borrado el alumno correctamente.");
		} catch (Exception e) {
			logger.error("Error al borrar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void borrarPorApellido() {
		System.out.println("\nBORRAR POR APELLIDO");
		System.out.println("-----------------------");
		System.out.print("Introduzca el nia del alumno: ");
		String apellido = reader.nextLine();
		try {
			dao.borrarPorApellido(apellido);
			logger.info("Se ha borrado el alumno correctamente.");
		} catch (Exception e) {
			logger.error("Error al borrar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void borrarPorCurso() {
		try {
			System.out.println("\nLISTA DE CURSOS");
			System.out.println("-----------------------");
			dao.mostrarCursos();
			System.out.println("\nBORRAR POR CURSO");
			System.out.println("-----------------------");
			System.out.print("Introduzca el nia del alumno: ");
			String curso = reader.nextLine();
			dao.borrarAlumnosPorCurso(curso);
			logger.info("Se han borrado los alumnos correctamente.");
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void guardarGrupos() {
		try {
			fh.guardarJSONGrupos();
			logger.info("Se ha guardado correctamente en el fichero");
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void recogerGrupos() {
		try {
			fh.leerJSONGrupos();
			logger.info("Se ha leido correctamente el fichero y guardado en la base de datos.");
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
	}

	public void mostrarAlumnoGrupo() {
		try {
			dao.mostrarAlumnosPorGrupo();
			logger.info("Mostrado los alumnos correctamente.");
		} catch (Exception e) {
			logger.error("Error al recuperar el alumno." + e.getMessage(), e);
		}
	}

	public void mostrarAlumnoPK() {
		try {
			dao.mostrarAlumnoPorPK();
			logger.info("Mostrado el alumno por la PK.");
		} catch (Exception e) {
			logger.error("Error al recuperar el alumno." + e.getMessage(), e);
		}
	}

	public void cambiarGrupoAlumno() {
		try {
			dao.cambiarGrupo();
			logger.info("Alumno cambiado de grupo correctamente.");
		} catch (Exception e) {
			logger.error("Error al recuperar el alumno." + e.getMessage(), e);
		}
	}

	public void guardarGrupoElegido() {
		try {
			dao.mostrarCursos();
			logger.info("Creado JSON de correctamente.");
		} catch (Exception e) {
			logger.error("Error al recuperar el alumno." + e.getMessage(), e);
		}
	}
}