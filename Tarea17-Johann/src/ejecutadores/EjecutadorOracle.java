package ejecutadores;

import controlador.Controlador;
import daoHibernate.AlumnoDao;
import daoHibernate.AlumnoOracle;
import vistaHibernate.IVista;
import vistaHibernate.VistaConsolaH;

public class EjecutadorOracle {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoOracle.getInstance();
		IVista vista = new VistaConsolaH();
		new Controlador().ejecutar(modelo, vista);
	}
}