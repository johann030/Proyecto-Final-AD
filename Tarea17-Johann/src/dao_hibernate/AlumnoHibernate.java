package dao_hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;
import pool.HibernateUtil;

public class AlumnoHibernate implements AlumnoDao {

	private static final Logger logger = LogManager.getLogger(AlumnoHibernate.class);

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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			// Crear la transaccion de la sesion
			transaccion = session.beginTransaction();

			// Insertar el alumno usando persist()
			session.persist(al);

			transaccion.commit();
			return 1;

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al insertar el alumno." + e.getMessage(), e);
			}
			return 0;
		} finally {
			session.close();
		}
	}

	@Override
	public int insertarGrupo(GrupoH gp) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			session.persist(gp);

			transaccion.commit();
			return 1;

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al insertar el grupo." + e.getMessage(), e);
			}
			return 0;
		} finally {
			session.close();
		}
	}

	@Override
	public List<AlumnoH> mostrarAlumnos() throws Exception {
		List<AlumnoH> AlumnoH = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			AlumnoH = session.createSelectionQuery("FROM AlumnoH", AlumnoH.class).getResultList();

		} catch (Exception e) {
			logger.error("Error al mostrar los alumnos." + e.getMessage(), e);
		} finally {
			session.close();
		}
		return AlumnoH;
	}

	@Override
	public int cambiarNombre(String nombre, int id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			String hql = """
					UPDATE AlumnoH a
					SET a.nombre = :nombre
					WHERE a.nia = :id
					""";
			MutationQuery q = session.createMutationQuery(hql);
			q.setParameter("nombre", nombre);
			q.setParameter("id", id);
			int filas = q.executeUpdate();

			transaccion.commit();

			return filas;

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al cambiar el nombre del alumno." + e.getMessage(), e);
			}
			return 0;
		} finally {
			session.close();
		}
	}

	@Override
	public void borrarPorPK(int id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			AlumnoH al = session.get(AlumnoH.class, id);

			if (al != null) {
				session.remove(al);
			}

			transaccion.commit();

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al borrar el alumno." + e.getMessage(), e);
			}
		} finally {
			session.close();
		}
	}

	@Override
	public void borrarPorApellido(String apellido) throws Exception {
		// TODO
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			AlumnoH al = session.get(AlumnoH.class, apellido);

			if (al != null) {
				session.remove(al);
			}

			transaccion.commit();

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al borrar el alumno." + e.getMessage(), e);
			}
		} finally {
			session.close();
		}

	}

	@Override
	public void borrarAlumnosPorCurso(String curso) throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			String hql = """
					SELECT a.nia
					FROM AlumnoH a
					""";

			List<String> cursos = session.createSelectionQuery(hql, String.class).getResultList();

			System.out.println("LISTADO DE CURSOS");
			for (String Curso : cursos) {
				System.out.println(Curso);
			}
			// TODOEliminar los alumnos del curso indicado por el usuario (debes mostrarle
			// previamente los cursos existentes).
			AlumnoH al = session.get(AlumnoH.class, "");

			if (al != null) {
				session.remove(al);
			}

			transaccion.commit();

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al borrar el alumno." + e.getMessage(), e);
			}
		} finally {
			session.close();
		}
	}

	@Override
	public List<AlumnoH> mostrarAlumnosPorGrupo() throws Exception {
		// TODO Mostrar los alumnos del grupo que elija el usuario: Mostrara los datos
		// del alumno y del grupo al que pertenece
		List<AlumnoH> al = mostrarAlumnos();
		for (AlumnoH alH : al) {
			System.out.println(alH);
		}
		return null;
	}

	@Override
	public List<AlumnoH> mostrarAlumnoPorPK() throws Exception {
//		Mostrar el alumno (todos sus datos) a partir de su PK que elije el 
//		usuario. Para ello, primero se deben mostrar todos los alumnos (solo la 
//		PK y el nombre) e indicar al usuario que elija el que quiere mostrar. 
		return null;
	}

	@Override
	public int cambiarGrupo() throws Exception {
//		Cambiar de grupo al alumno que elija el usuario. Para ello, primero se 
//		deben mostrar todos los alumnos (solo la PK y el nombre) e indicar al 
//		usuario que elija la PK del alumno que quiere cambiar. Después se 
//		mostrarán los grupos, para que el usuario elija a qué grupo irá el alumno.
		return 0;
	}

	public void mostrarCursos() throws Exception {
//		Guardar el grupo que elija el usuario (con toda su información como 
//				atributos) en un fichero XML o JSON. Para cada grupo se guardará 
//				también el listado de alumnos de ese grupo. Los datos del alumno serán 
//				atributos en el XML
	}
}