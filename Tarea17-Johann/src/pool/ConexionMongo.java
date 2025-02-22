package pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

	private static MongoClient clienteMongo;
	private static MongoDatabase baseDatos;
	private static final Logger logger = LogManager.getLogger(ConexionMongo.class);

	static {
		String uri = "mongodb+srv://johann06:manager@cluster0.6zir8.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0";
		// TODO esto se cambia por la conexion en localhost

		clienteMongo = MongoClients.create(uri);
//		clienteMongo = MongoClients.create("mongodb://localhost:27017");
		baseDatos = clienteMongo.getDatabase("johann06");
	}

	private ConexionMongo() {
	}

	public static MongoDatabase getDatabase() throws Exception {
		return baseDatos;
	}

	public static void cerrarConexion() {
		if (clienteMongo != null) {
			clienteMongo.close();
			logger.info("Conexion cerrarda.");
		}
	}
}