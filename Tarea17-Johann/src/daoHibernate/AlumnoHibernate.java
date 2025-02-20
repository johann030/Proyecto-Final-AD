package daoHibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;

import modeloHibernate.AlumnoH;
import modeloHibernate.GrupoH;
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
			logger.info("Alumno insertado correctamente.");
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
			logger.info("Grupo insertado correctamente.");
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
			logger.info("Lista de alumnos mostrada.");

		} catch (Exception e) {
			logger.error("Error al mostrar los alumnos." + e.getMessage(), e);
		} finally {
			session.close();
		}
		return AlumnoH;
	}

	public List<GrupoH> conseguirGrupos() throws Exception {
		List<GrupoH> GrupoH = new ArrayList<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			GrupoH = session.createSelectionQuery("FROM GrupoH", GrupoH.class).getResultList();
			logger.info("Lista de alumnos mostrada.");

		} catch (Exception e) {
			logger.error("Error al mostrar los alumnos." + e.getMessage(), e);
		}
		return GrupoH;
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

			logger.info("Nombre cambiado correctamente.");

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

			logger.info("Alumno borrado correctamente.");

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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaccion = null;
		try {
			transaccion = session.beginTransaction();

			String hql = """
					DELETE FROM AlumnoH
					WHERE apellidos = :apellido
					""";

			int filas = session.createMutationQuery(hql).setParameter("apellido", apellido).executeUpdate();

			transaccion.commit();

			if (filas > 0) {
				logger.info("Alumno borrado correctamente.");
			} else {
				logger.info("No se encontro alumno con ese apellido.");
			}

		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al borrar el alumno." + e.getMessage(), e);
			}
		} finally {
			session.close();
		}
	}

	public List<String> obtenerCursos() throws Exception {
		List<String> cursos = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			cursos = session.createSelectionQuery("SELECT a.curso FROM AlumnoH a GROUP BY a.curso", String.class)
					.getResultList();
		} catch (Exception e) {
			logger.error("Error al obtener la lista de cursos." + e.getMessage(), e);
		} finally {
			session.close();
		}
		return cursos;
	}

	@Override
	public void borrarAlumnosPorCurso(String curso) throws Exception {
		Transaction transaccion = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			transaccion = session.beginTransaction();

			String hql = """
					DELETE FROM AlumnoH
					WHERE curso = :curso
					""";

			int filas = session.createMutationQuery(hql).setParameter("curso", curso).executeUpdate();

			transaccion.commit();

			if (filas > 0) {
				logger.info("Alumno borrado correctamente.");
			} else {
				logger.info("No se encontro alumno con ese grupo.");
			}

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
	public List<AlumnoH> mostrarAlumnosPorGrupo(int id_grupo) throws Exception {
		List<AlumnoH> al = new ArrayList<AlumnoH>();
		List<GrupoH> gp = conseguirGrupos();

		boolean grupoExiste = gp.stream().anyMatch(g -> g.getId_grupo() == id_grupo);

		if (!grupoExiste) {
			logger.info("El grupo no existe.");
			return al;
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			String hql = """
					FROM AlumnoH
					WHERE id_grupo = :idGrupo
					""";
			al = session.createSelectionQuery(hql, AlumnoH.class).setParameter("idGrupo", id_grupo).getResultList();

			logger.info("Alumnos mostrados correctamente.");
		} catch (Exception e) {
			logger.error("No hay alumnos en el grupo." + e.getMessage(), e);
		} finally {
			session.close();
		}
		return al;
	}

	@Override
	public AlumnoH mostrarAlumnoPorPK(int nia) throws Exception {
		List<AlumnoH> alumnos = mostrarAlumnos();

		alumnos.forEach(alumno -> String.format("%d, %s", alumno.getNia(), alumno.getNombre()));
		try {

		} catch (Exception e) {
			logger.error("Error al mostrar el alumno." + e.getMessage(), e);
		}
		return alumnos.stream().filter(alumno -> alumno.getNia() == nia).findFirst().orElse(null);
	}

	@Override
	public void cambiarGrupo(int nia, int nuevoId) throws Exception {
		Transaction transaccion = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			transaccion = session.beginTransaction();

			AlumnoH alumno = session.get(AlumnoH.class, nia);

			if (alumno != null) {
				alumno.setId_grupo(nuevoId);
				session.merge(alumno);

				transaccion.commit();
				logger.info("Alumno cambiado de grupo correctamente.");
			} else {
				logger.info("No se encontró un alumno con ese NIA.");
			}
		} catch (Exception e) {
			if (transaccion != null) {
				transaccion.rollback();
				logger.error("Error al cambiar de grupo" + e.getMessage(), e);
			}
		} finally {
			session.close();
		}
	}
}