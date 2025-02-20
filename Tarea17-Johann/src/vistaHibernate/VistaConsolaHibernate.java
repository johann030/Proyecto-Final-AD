package vistaHibernate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import modeloHibernate.AlumnoH;
import modeloHibernate.GrupoH;

public class VistaConsolaHibernate implements IVistaH {

	private static final Logger logger = LogManager.getLogger(VistaConsolaHibernate.class);

	private reader reader;

	public VistaConsolaHibernate() {
		reader = new reader();
	}

	@Override
	public int menu() {
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

		int opcion = reader.nextInt();

		return opcion;
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

	@Override
	public AlumnoH insertarAlumno() {
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
			logger.info("El alumno se inserto correctamente.");
			return new AlumnoH(nia, nombre, apellidos, genero, nacimiento, ciclo, curso, id_grupo);
		} catch (Exception e) {
			logger.error("Error al insertar el alumno." + e.getMessage(), e);
		}
		System.out.println("");
		return null;
	}

	@Override
	public GrupoH insertarGrupo() {
		System.out.println("\nINSERTAR GRUPO");
		System.out.println("----------------");
		System.out.print("Introduzca el id: ");
		int id = reader.nextInt();
		System.out.print("Introduzca el nombre: ");
		String nombre = reader.nextLine();
		System.out.print("Introduzca el aula: ");
		int aula = reader.nextInt();

		try {
			logger.info("El grupo se inserto correctamente.");
			return new GrupoH(id, nombre, aula);
		} catch (Exception e) {
			logger.error("Error al borrar los alumnos." + e.getMessage(), e);
		}
		System.out.println("");
		return null;
	}

	@Override
	public void mostrarAlumnos(List<AlumnoH> alumnos) {
		System.out.println("\nLISTA DE ALUMNOS");
		System.out.println("----------------");
		try {
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

	@Override
	public int pedirNia() {
		System.out.println("Introduzca el nia del alumno: ");
		int nia = reader.nextInt();

		return nia;
	}

	@Override
	public int pedirIdGrupo() {
		System.out.println("Introduzca el id del grupo: ");
		int idGrupo = reader.nextInt();

		return idGrupo;
	}

	@Override
	public String pedirNombre() {
		System.out.println("Introduzca el nombre: ");
		String nombre = reader.nextLine();
		return nombre;

	}

	@Override
	public String pedirApellido() {
		System.out.println("Introduzca el apellido: ");
		String apellido = reader.nextLine();
		return apellido;

	}

	@Override
	public String pedirCurso() {
		System.out.println("Introduzca el curso: ");
		String curso = reader.nextLine();
		return curso;
	}
}