package dao_hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;
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
	public int insertarAlumno(AlumnoH al) throws Exception {
		Transaction tx = null;
		try (SessionFactory sf = HibernateUtil.getSessionFactory(); Session session = sf.openSession()) {
			// Crear la transaccion de la sesion
			tx = session.beginTransaction();

			// Insertar el alumno usando persist()
			session.persist(al);

			tx.commit();
			return 1;

		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		}
	}

	@Override
	public int insertarGrupo(GrupoH gp) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AlumnoH> mostrarAlumnos() throws Exception {
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
	public List<AlumnoH> mostrarAlumnosPorGrupo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AlumnoH> mostrarAlumnoPorPK() throws Exception {
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

	public void mostrarCursos() throws Exception {
		// TODO Auto-generated method stub
	}
}