package controlador;

import dao_hibernate.AlumnoDao;
import dao_hibernate.AlumnoHibernate;
import vista.IVista;
import vista.VistaHibernate;

public class ControladorHibernate {

	public static void main(String[] args) throws Exception {
		AlumnoDao modelo = AlumnoHibernate.getInstance();
		IVista vista = new VistaHibernate();
		new ControladorHibernate().ejecutar(modelo, vista);
	}

	public void ejecutar(AlumnoDao modelo, IVista vista) {
		vista.init();
	}
}