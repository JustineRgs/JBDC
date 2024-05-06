package fr.diginamic.jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;

public class TestSelect {
	private static final String DB_URL;
	private static final String DB_USER;
	private static final String DB_PWD;

	static {
		ResourceBundle dbConfig = ResourceBundle.getBundle("db");
		DB_URL = dbConfig.getString("db.url");
		DB_USER = dbConfig.getString("db.user");
		DB_PWD = dbConfig.getString("db.password");
	};

	public static void main(String[] args) {
		List<Fournisseur> fournisseurs = new ArrayList<>();
		Connection connection = null;
		Statement st = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			st = connection.createStatement();
			resultSet = st.executeQuery("SELECT * FROM FOURNISSEUR");

			while (resultSet.next()) {
				int id = resultSet.getInt("ID");
				String nom = resultSet.getString("NOM");
				Fournisseur fournisseur = new Fournisseur(id, nom);
				fournisseurs.add(fournisseur);
			}

			for (Fournisseur fournisseur : fournisseurs) {
				System.out.println("ID: " + fournisseur.getId() + ", Nom: " + fournisseur.getNom());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (st != null) {
					st.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
