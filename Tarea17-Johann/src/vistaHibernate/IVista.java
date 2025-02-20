package vistaHibernate;

import java.util.List;

import modeloHibernate.AlumnoH;
import modeloHibernate.GrupoH;

public interface IVista {

	int menu();

	AlumnoH insertarAlumno();

	GrupoH insertarGrupo();

	void mostrarAlumnos(List<AlumnoH> alumnos);

	int pedirIdGrupo();

	int pedirNia();

	String pedirNombre();

	String pedirCurso();

	String pedirApellido();
}