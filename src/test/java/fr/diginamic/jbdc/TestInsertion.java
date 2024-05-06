package fr.diginamic.jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TestInsertion {
	private static final String DB_URL;
	private static final String DB_USER;
	private static final String DB_PWD;

	static {
		ResourceBundle dbConfig = ResourceBundle.getBundle("db");
		DB_URL = dbConfig.getString("db.url");
		DB_USER = dbConfig.getString("db.user");
		DB_PWD = dbConfig.getString("db.password");
	};

	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		Statement st = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			st = connection.createStatement();
			int newFournisseur = st.executeUpdate("INSERT INTO FOURNISSEUR(NOM) VALUES('La Maison de la Peinture')");
			System.out.println("Nombre d'éléments créés : " + newFournisseur);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (st != null) {
				st.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}
