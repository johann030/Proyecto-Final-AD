package ejecutadores;

import dao.AlumnoMongoDB;
import vistaBD.IVistaBD;
import vistaBD.VistaConsolaBD;
import controlador.ControladorMongoDB;
import dao.AlumnoDao;

public class EjecutadorMongoDB {
	public static void main(String[] args) {
		AlumnoDao modelo = AlumnoMongoDB.getInstance();
		IVistaBD vista = new VistaConsolaBD();
		new ControladorMongoDB().ejecutar(modelo, vista);
	}
}