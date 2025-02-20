package controlador;

import dao.AlumnoDao;
import dao.AlumnoBD;
import vista.IVista;
import vista.VistaConsola;

public class ControladorBD {
	public void ejecutar(AlumnoDao modelo, IVista vista) {
		vista.init();
	}
}