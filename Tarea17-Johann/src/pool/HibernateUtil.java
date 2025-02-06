package pool;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	static {
		try {
			// Configuracion manual del SessionFactory
			Configuration configuration = new Configuration().configure("hibernate.cfg.xml");

			configuration.addAnnotatedClass(null);
			configuration.addAnnotatedClass(null);

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