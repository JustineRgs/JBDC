package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.entites.Fournisseur;

public class TestDaoJdbc {
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
		try {
			Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD);
			FournisseurDaoJdbc fournisseurDao = new FournisseurDaoJdbc(connection);

			// Insert
			Fournisseur fournisseur = new Fournisseur(4, "France de matériaux");
			fournisseurDao.insert(fournisseur);

			// Read
			List<Fournisseur> fournisseurs = fournisseurDao.extraire();
			System.out.println("Liste des fournisseurs après INSERT :");
			for (Fournisseur f : fournisseurs) {
				System.out.println(f.getId() + " - " + f.getNom());
			}
			System.out.println();

			// Update
			fournisseurDao.update("France de matériaux", "France matériaux");

			// Read
			fournisseurs = fournisseurDao.extraire();
			System.out.println("Liste des fournisseurs après UPDATE :");
			for (Fournisseur f : fournisseurs) {
				System.out.println(f.getId() + " - " + f.getNom());
			}
			System.out.println();

			// Delete
			fournisseur = new Fournisseur(1, "France matériaux");
			fournisseurDao.delete(fournisseur);

			// Read
			fournisseurs = fournisseurDao.extraire();
			System.out.println("Liste des fournisseurs après DELETE :");
			for (Fournisseur f : fournisseurs) {
				System.out.println(f.getId() + " - " + f.getNom());
			}
			System.out.println();

			// Test insert
			Fournisseur fournisseurTest = new Fournisseur(4, "L’Espace Création");
			fournisseurDao.insert(fournisseurTest);

			// Read
			fournisseurs = fournisseurDao.extraire();
			System.out.println("Liste des fournisseurs après TEST INSERT :");
			for (Fournisseur f : fournisseurs) {
				System.out.println(f.getId() + " - " + f.getNom());
			}
			System.out.println();
			System.out.println(
					"L'insertion 'L’Espace Création' fonctionne bien parce que j'ai préparé ma requète avec PreparedStatement :P");

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
