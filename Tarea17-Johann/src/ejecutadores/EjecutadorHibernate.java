package ejecutadores;

import controlador.ControladorHibernate;
import daoHibernate.AlumnoDao;
import daoHibernate.AlumnoHibernate;
import vistaHibernate.IVistaH;
import vistaHibernate.VistaConsolaHibernate;

public class EjecutadorHibernate {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoHibernate.getInstance();
		IVistaH vista = new VistaConsolaHibernate();
		new ControladorHibernate().ejecutar(modelo, vista);
	}
}