package start;

import presentation.HomeScreen;

import javax.swing.*;
import java.sql.SQLException;
import java.util.logging.Logger;


public class Start {
	protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());
	/**
	 * The main method of the application.
	 * Initializes the HomeScreen and sets up the application to exit on close.
	 *
	 * @param args Command line arguments (not used in this application)
	 * @throws SQLException If a database access error occurs
	 */
	public static void main(String[] args) throws SQLException {
		HomeScreen home =new HomeScreen("Home Screen");
		home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
