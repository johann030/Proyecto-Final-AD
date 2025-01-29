package pool;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Conexion {

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource dataSource;

	static {
		config.setJdbcUrl("jdbc:mysql://localhost/johann06?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&serverTime=Europe/Madrid");
		config.setUsername("johann");
		config.setPassword("manager");
		config.addDataSourceProperty("maximumPoolSize", 1);

		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
	}

	private Conexion() {
	}

	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}