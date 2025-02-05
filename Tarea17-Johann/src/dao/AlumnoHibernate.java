package dao;

import java.util.List;

import modelo.Alumno;
import modelo.Grupo;

public class AlumnoHibernate implements AlumnoDao {

	private static AlumnoHibernate instance;

	static {
		instance = new AlumnoHibernate();
	}

	private AlumnoHibernate() {
	}

	public static AlumnoHibernate getInstance() {
		return instance;
	}

	@Override
	public int insertarAlumno(Alumno al) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertarGrupo(Grupo gp) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Alumno> mostrarAlumnos() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardarTxtAlumnos() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void leerTxtAlumnos() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public int cambiarNombre(String nombre, int id) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void borrarPorPK(int id) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarPorApellido(String apellido) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrarAlumnosPorCurso(String curso) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void guardarJSONGrupos() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void leerJSONGrupos() throws Exception {
		// TODO Auto-generated method stub

	}

}
