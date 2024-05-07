package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Fournisseur;

public class FournisseurDaoJdbc {
	private Connection connection;

	public FournisseurDaoJdbc(Connection connection) {
		this.connection = connection;
	}

	/**
	 * Extrait tous les fournisseurs de la base de données.
	 *
	 * @return une ArrayList contenant tous les fournisseurs extraits de la base de
	 *         données
	 */
	public List<Fournisseur> extraire() {
		List<Fournisseur> fournisseurs = new ArrayList<>();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM fournisseur");
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String nom = resultSet.getString("nom");
				fournisseurs.add(new Fournisseur(id, nom));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return fournisseurs;
	}

	/**
	 * Insère un nouveau fournisseur dans la base de données.
	 *
	 * @param fournisseur : Le fournisseur à insérer dans la base de données.
	 */
	public void insert(Fournisseur fournisseur) {
		Statement statement = null;
		try {
			statement = connection.createStatement();
			statement.executeUpdate("INSERT INTO fournisseur (nom) VALUES ('" + fournisseur.getNom() + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Statement statement = null;
		try {
			statement = connection.createStatement();
			updateFournisseurs = statement
					.executeUpdate("UPDATE fournisseur SET nom = '" + nouveauNom + "' WHERE nom = '" + ancienNom + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
		Statement statement = null;
		try {
			statement = connection.createStatement();
			int rowsDeleted = statement
					.executeUpdate("DELETE FROM fournisseur WHERE nom = '" + fournisseur.getNom() + "'");
			return rowsDeleted > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
