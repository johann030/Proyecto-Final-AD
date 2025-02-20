package controlador;

import dao_hibernate.AlumnoDao;
import dao_hibernate.AlumnoHibernate;
import ficheros.Ficheros;
import ficheros.FicherosHibernate;
import vista.IVista;
import vista.VistaHibernate;

public class ControladorHibernate {
	private AlumnoHibernate dao;

	private FicherosHibernate fh;

	private VistaHibernate vh;

	// TODO
	public void ejecutar(AlumnoDao modelo, IVista vista, Ficheros fichero) {
		vista.init();
	}
}