package ejecutadores;

import controlador.ControladorBD;

import dao.AlumnoBD;
import dao.AlumnoDao;
import vistaBD.IVistaBD;
import vistaBD.VistaConsolaBD;

public class EjecutadorMySQL {
	// falta hacer el modelo 3 capas, mirar practicas anteriores
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoBD.getInstance();
		IVistaBD vista = new VistaConsolaBD();
		new ControladorBD().ejecutar(modelo, vista);
	}
}