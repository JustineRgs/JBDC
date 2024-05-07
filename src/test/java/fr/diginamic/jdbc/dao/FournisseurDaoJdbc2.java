package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Fournisseur;

public class FournisseurDaoJdbc2 {
	private Connection connection;

	public FournisseurDaoJdbc2(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Extrait tous les fournisseurs de la base de données.
	 *
	 * @return une ArrayList contenant tous les fournisseurs extraits de la base de données
	 */
	public List<Fournisseur> extraire() {
		List<Fournisseur> fournisseurs = new ArrayList<>();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM fournisseur");
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				fournisseurs.add(new Fournisseur(id, nom));
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fournisseurs;
	}

	/**
	 * Insère un nouveau fournisseur dans la base de données.
	 *
	 * @param fournisseur : Le fournisseur à insérer dans la base de données.
	 */
	public void insert(Fournisseur fournisseur) {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO fournisseur (nom) VALUES (?)");
			statement.setString(1, fournisseur.getNom());
			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Met à jour le nom d'un fournisseur dans la base de données.
	 *
	 * @param ancienNom  Le nom actuel du fournisseur.
	 * @param nouveauNom Le nouveau nom à attribuer au fournisseur.
	 * @return Le nombre de fournisseurs mis à jour.
	 */
	public int update(String ancienNom, String nouveauNom) {
		int updateFournisseurs = 0;
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE fournisseur SET nom = ? WHERE nom = ?");
			statement.setString(1, nouveauNom);
			statement.setString(2, ancienNom);
			updateFournisseurs = statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateFournisseurs;
	}

	/**
	 * Supprime un fournisseur de la base de données.
	 *
	 * @param fournisseur : Le fournisseur à supprimer de la base de données.
	 * @return true si le fournisseur a été supprimé avec succès, sinon false.
	 */
	public boolean delete(Fournisseur fournisseur) {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM fournisseur WHERE nom = ?");
			statement.setString(1, fournisseur.getNom());
			int rowsDeleted = statement.executeUpdate();
			statement.close();
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
