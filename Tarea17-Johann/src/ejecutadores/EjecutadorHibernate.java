package ejecutadores;

import controlador.Controlador;
import daoHibernate.AlumnoDao;
import daoHibernate.AlumnoHibernate;
import vistaHibernate.IVista;
import vistaHibernate.VistaConsolaH;

public class EjecutadorHibernate {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoHibernate.getInstance();
		IVista vista = new VistaConsolaH();
		new Controlador().ejecutar(modelo, vista);
	}
}