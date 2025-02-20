package vistaBD;

import java.util.List;

import modelo.Alumno;
import modelo.Grupo;

public interface IVistaBD {

	int menu();

	Alumno insertarAlumno();

	Grupo insertarGrupo();

	void mostrarAlumnos(List<Alumno> alumnos);

	int pedirIdGrupo();

	int pedirNia();

	String pedirNombre();

	String pedirCurso();

	String pedirApellido();
}