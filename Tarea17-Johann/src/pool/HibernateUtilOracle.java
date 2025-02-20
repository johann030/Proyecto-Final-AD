package pool;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import modelo_hibernate.AlumnoH;
import modelo_hibernate.GrupoH;

public class HibernateUtilOracle {

	private static SessionFactory sessionFactory;

	static {
		try {
			// Configuracion manual del SessionFactory
			Configuration configuration = new Configuration().configure("Oraclehibernate.cfg.xml");

			configuration.addAnnotatedClass(AlumnoH.class);
			configuration.addAnnotatedClass(GrupoH.class);

			StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();

			sessionFactory = configuration.buildSessionFactory(registry);

		} catch (Exception ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	// Cierra la conexion al terminar el programa
	public static void shutdown() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}
}