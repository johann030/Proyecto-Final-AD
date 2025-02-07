package vista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

import dao_hibernate.AlumnoHibernate;
import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;

public class VistaHibernate implements IVista {

	private reader reader;

	private AlumnoHibernate dao;

	public VistaHibernate() {
		reader = new reader();
		dao = AlumnoHibernate.getInstance();
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

				break;
			case 13:

				break;
			case 14:

				break;
			case 15:

				break;
			case 16:
				System.out.println("\nSaliendo del programa...\n");

				break;
			default:
				System.err.println("\nLa opcion dada no corresponde a una operacion valida.");
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

		double nextDouble() {
			return Double.parseDouble(next());
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
		String nombre = reader.nextLine();
		System.out.print("Introduzca los apellidos: ");
		String apellidos = reader.nextLine();
		System.out.print("Introduzca el genero: ");
		String genero = reader.nextLine();
		System.out.print("Introduzca la fecha de nacimiento (dd/MM/aaaa): ");
		LocalDate nacimiento = reader.nextLocalDate();
		System.out.print("Introduzca el ciclo: ");
		String ciclo = reader.nextLine();
		System.out.print("Introduzca el curso: ");
		String curso = reader.nextLine();
		System.out.print("Introduzca el codigo del grupo: ");
		int id_grupo = reader.nextInt();

		try {
			dao.insertarAlumno(new AlumnoH(nia, nombre, apellidos, genero, nacimiento, ciclo, curso, id_grupo));
			System.out.println("Nuevo alumno insertado correctamente.");
		} catch (Exception e) {
			System.err.println("Error insertando el nuevo alumno.");
			e.printStackTrace();
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
			System.out.println("Nuevo grupo insertado correctamente.");
		} catch (Exception e) {
			System.err.println("Error insertando el nuevo grupo.");
			e.printStackTrace();
		}
		System.out.println("");
	}

	public void mostrarAlumnos() {
		System.out.println("\nLISTA DE ALUMNOS");
		System.out.println("----------------");
		try {
			List<AlumnoH> alumnos = dao.mostrarAlumnos();

			if (alumnos.isEmpty()) {
				System.out.println("No hay alumnos registrados.");
			} else {
				alumnos.forEach(System.out::println);
			}
		} catch (Exception e) {
			System.err.println("Error al mostrar los alumnos.");
			e.printStackTrace();
		}
		System.out.println("");
	}

	public void guardarAlumnos() {
		System.out.println("\nGUARDAR EN FICHERO(TXT)");
		System.out.println("-----------------------");
		try {
			dao.guardarTxtAlumnos();
			System.out.println("Escritura hecha correctamente en el fichero.");
		} catch (Exception e) {
			System.err.println("Error al guardar los alumnos en el fichero.");
			e.printStackTrace();
		}
		System.out.println("");
	}

	public void recogerAlumnos() {
		System.out.println("\nLEER UN FICHERO(TXT)");
		System.out.println("-----------------------");
		try {
			dao.leerTxtAlumnos();
			System.out.println("Lectura del fichero correcta, guardada en la base de datos.");
		} catch (Exception e) {
			System.err.println("Error al guardar los alumnos en la base de datos.");
			e.printStackTrace();
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
			System.out.println("El nombre ha sido actualizado correctamente.");
		} catch (Exception e) {
			System.err.println("Error al cambiar el nombre.");
			e.printStackTrace();
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
			System.out.println("Se ha borrado al alumno correctamente.");
		} catch (Exception e) {
			System.err.println("Error al borrar el alumno.");
			e.printStackTrace();
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
			System.out.println("Se ha borrado al alumno correctamente.");
		} catch (Exception e) {
			System.err.println("Error al borrar el alumno.");
			e.printStackTrace();
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
			System.out.println("Se han borrado los alumnos correctamente.");
		} catch (Exception e) {
			System.err.println("Error al borrar el alumno.");
			e.printStackTrace();
		}
		System.out.println("");
	}

	public void guardarGrupos() {
		try {
			dao.guardarJSONGrupos();
			System.out.println("Escritura hecha correctamente en el fichero.");
		} catch (Exception e) {
			System.err.println("Error al guardar los grupos en el fichero.");
			e.printStackTrace();
		}
		System.out.println("");
	}

	public void recogerGrupos() {
		try {
			dao.leerJSONGrupos();
			System.out.println("Lectura del fichero correcta, guardada en la base de datos.");
		} catch (Exception e) {
			System.err.println("Error al guardar los grupos en la base de datos.");
			e.printStackTrace();
		}
		System.out.println("");
	}
}