package ejecutadores;

import controlador.ControladorBD;
import dao.AlumnoBD;
import dao.AlumnoDao;
import vista.IVista;
import vista.VistaConsola;

public class EjecutadorMySQL {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoBD.getInstance();
		IVista vista = new VistaConsola();
		new ControladorBD().ejecutar(modelo, vista);
	}
}