package fr.diginamic.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.diginamic.jdbc.entites.Article;
import fr.diginamic.jdbc.entites.Fournisseur;

public class ArticleDaoJdbc {
	private Connection connection;

	public ArticleDaoJdbc(Connection connection) {
		this.connection = connection;
	}

	public void insertArticle(Article article) {
		String query = "INSERT INTO article (DESIGNATION, PRIX, ID_FOU) VALUES (?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, article.getDesignation());
			statement.setDouble(2, article.getPrix());
			statement.setInt(3, article.getFournisseur().getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Article> listArticles() {
		List<Article> articles = new ArrayList<>();
		String sql = "SELECT * FROM article";

		try (PreparedStatement statement = connection.prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery()) {

			while (resultSet.next()) {
				String designation = resultSet.getString("DESIGNATION");
				double prix = resultSet.getDouble("PRIX");
				int fournisseurId = resultSet.getInt("ID_FOU");

				Fournisseur fournisseur = getFournisseurById(fournisseurId);

				Article article = new Article(designation, prix, fournisseur);
				articles.add(article);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return articles;
	}

	private Fournisseur getFournisseurById(int fournisseurId) {
		String query = "SELECT * FROM fournisseur WHERE id = ?";
		Fournisseur fournisseur = null;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, fournisseurId);
			try (ResultSet resultSet = statement.executeQuery()) {
				if (resultSet.next()) {
					int id = resultSet.getInt("ID");
					String nom = resultSet.getString("NOM");
					fournisseur = new Fournisseur(id, nom);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return fournisseur;
	}

	public void salePriceArticles(double pourcentage) {
		String query = "UPDATE article SET prix = prix * (1 - ? / 100) WHERE DESIGNATION LIKE '%mate%'";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setDouble(1, pourcentage);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Extraire la moyenne des prix des articles
	public double averagePriceArticle() {
		String query = "SELECT AVG(PRIX) AS moyenne_prix FROM article";
		double moyenne = 0;

		try (PreparedStatement statement = connection.prepareStatement(query);
				ResultSet resultSet = statement.executeQuery()) {

			if (resultSet.next()) {
				moyenne = resultSet.getDouble("moyenne_prix");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return moyenne;
	}

	// Supprimer tous les articles dont le nom contient "Peinture"
	public void deleteArticleByName(String nom) {
		String query = "DELETE FROM article WHERE DESIGNATION LIKE ?";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, "%" + nom + "%");
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
