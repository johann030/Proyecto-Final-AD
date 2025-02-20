package ejecutadores;

import controlador.ControladorHibernate;
import dao_hibernate.AlumnoDao;
import dao_hibernate.AlumnoHibernate;
import ficheros.Ficheros;
import ficheros.FicherosHibernate;
import vista.IVista;
import vista.VistaHibernate;

public class EjecutadorHibernate {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoHibernate.getInstance();
		IVista vista = new VistaHibernate();
		Ficheros fichero = FicherosHibernate.getInstance();
		new ControladorHibernate().ejecutar(modelo, vista, fichero);
	}
}