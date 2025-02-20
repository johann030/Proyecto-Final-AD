package daoHibernate;

import java.util.List;

import modeloHibernate.AlumnoH;
import modeloHibernate.GrupoH;

public interface AlumnoDao {

	int insertarAlumno(AlumnoH al) throws Exception;

	int insertarGrupo(GrupoH gp) throws Exception;

	List<AlumnoH> mostrarAlumnos() throws Exception;

	int cambiarNombre(String nombre, int id) throws Exception;

	void borrarPorPK(int id) throws Exception;

	void borrarPorApellido(String apellido) throws Exception;

	void borrarAlumnosPorCurso(String curso) throws Exception;

	List<AlumnoH> mostrarAlumnosPorGrupo(int id_grupo) throws Exception;

	AlumnoH mostrarAlumnoPorPK(int nia) throws Exception;

	void cambiarGrupo(int nia, int nuevoId) throws Exception;
}