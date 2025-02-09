package dao_hibernate;

import java.util.List;

import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;

public interface AlumnoDao {

	int insertarAlumno(AlumnoH al) throws Exception;

	int insertarGrupo(GrupoH gp) throws Exception;

	List<AlumnoH> mostrarAlumnos() throws Exception;

	int cambiarNombre(String nombre, int id) throws Exception;

	void borrarPorPK(int id) throws Exception;

	void borrarPorApellido(String apellido) throws Exception;

	void borrarAlumnosPorCurso(String curso) throws Exception;

	List<AlumnoH> mostrarAlumnosPorGrupo() throws Exception;

	List<AlumnoH> mostrarAlumnoPorPK() throws Exception;

	int cambiarGrupo() throws Exception;
}