package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import modelo.Alumno;
import modelo.Grupo;
import pool.HibernateUtil;

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
		Transaction tx = null;
		try (SessionFactory sf = HibernateUtil.getSessionFactory(); Session session = sf.openSession()) {
			// Crear la transaccion de la sesion
			tx = session.beginTransaction();

			// Insercion
			String insert = """
					INSERT INTO Alumnos()
					SELECT
					""";
			Query consulta = null;

		} catch (Exception e) {

		}
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

	@Override
	public List<Alumno> mostrarAlumnosPorGrupo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Alumno> mostrarAlumnoPorPK() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int cambiarGrupo() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void elegirGrupoJSON() throws Exception {
		// TODO Auto-generated method stub

	}

}