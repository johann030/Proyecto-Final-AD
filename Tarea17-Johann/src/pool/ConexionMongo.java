package pool;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {

	private static MongoClient clienteMongo;
	private static MongoDatabase baseDatos;

	static {
		try {
			clienteMongo = MongoClients.create("mongodb://localhost:27017");
			baseDatos = clienteMongo.getDatabase("johann06");
		} catch (Exception e) {
			System.err.println("Error al conectar con MongoDB: " + e.getMessage());
		}
	}

	private ConexionMongo() {
	}

	public static MongoDatabase getDatabase() throws Exception {
		return baseDatos;
	}
}