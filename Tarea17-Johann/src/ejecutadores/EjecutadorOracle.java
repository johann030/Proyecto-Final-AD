package ejecutadores;

import controlador.ControladorHibernate;
import daoHibernate.AlumnoDao;
import daoHibernate.AlumnoOracle;
import vistaHibernate.IVistaH;
import vistaHibernate.VistaConsolaHibernate;

public class EjecutadorOracle {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoOracle.getInstance();
		IVistaH vista = new VistaConsolaHibernate();
		new ControladorHibernate().ejecutar(modelo, vista);
	}
}