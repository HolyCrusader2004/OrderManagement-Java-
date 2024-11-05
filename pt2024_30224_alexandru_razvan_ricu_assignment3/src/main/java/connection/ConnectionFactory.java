package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * The ConnectionFactory class provides methods to manage database connections.
 * It handles the creation of connections, closing connections, statements, and result sets.
 */
public class ConnectionFactory {

	private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String DBURL = "jdbc:mysql://localhost:3306/warehousedb";
	private static final String USER = "root";
	private static final String PASS = "Razvan04022004";

	private static ConnectionFactory singleInstance = new ConnectionFactory();
	/**
	 * Private constructor to prevent external instantiation.
	 * Loads the database driver.
	 */
	private ConnectionFactory() {
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Creates a new database connection.
	 *
	 * @return A connection to the database.
	 */
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(DBURL, USER, PASS);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, "An error occurred while trying to connect to the database");
			e.printStackTrace();
		}
		return connection;
	}
	/**
	 * Retrieves a database connection.
	 *
	 * @return A connection to the database.
	 */
	public static Connection getConnection() {
		return singleInstance.createConnection();
	}
	/**
	 * Closes the given database connection.
	 *
	 * @param connection The connection to be closed.
	 */
	public static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the connection");
			}
		}
	}
	/**
	 * Closes the given SQL statement.
	 *
	 * @param statement The SQL statement to be closed.
	 */
	public static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the statement");
			}
		}
	}
	/**
	 * Closes the given result set.
	 *
	 * @param resultSet The result set to be closed.
	 */
	public static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				LOGGER.log(Level.WARNING, "An error occurred while trying to close the ResultSet");
			}
		}
	}
}
