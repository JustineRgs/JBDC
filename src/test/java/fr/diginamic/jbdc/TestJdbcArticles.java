package fr.diginamic.jbdc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import fr.diginamic.jdbc.dao.ArticleDaoJdbc;
import fr.diginamic.jdbc.dao.FournisseurDaoJdbc;
import fr.diginamic.jdbc.entites.Article;
import fr.diginamic.jdbc.entites.Fournisseur;

public class TestJdbcArticles {
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
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD)) {
			FournisseurDaoJdbc fournisseurDao = new FournisseurDaoJdbc(connection);
			ArticleDaoJdbc articleDao = new ArticleDaoJdbc(connection);

			// Insert Fournisseur
			Fournisseur fournisseur = new Fournisseur(4, "La Maison de la Peinture");
			fournisseurDao.insert(fournisseur);

			// Insert Articles
			Article article1 = new Article("Peinture blanche 1L", 12.5, fournisseur);
			Article article2 = new Article("Peinture rouge mate 1L", 15.5, fournisseur);
			Article article3 = new Article("Peinture noire laquée 1L", 17.8, fournisseur);
			Article article4 = new Article("Peinture bleue mate 1L", 15.5, fournisseur);
			articleDao.insertArticle(article1);
			articleDao.insertArticle(article2);
			articleDao.insertArticle(article3);
			articleDao.insertArticle(article4);

			// Reduction Articles
			articleDao.salePriceArticles(25);

			// Liste Articles
			System.out.println("Liste des articles :");
			List<Article> listArticles = articleDao.listArticles();
			for (Article article : listArticles) {
				System.out.println(article);
			}
			System.out.println();

			// Moyenne des prix des articles
			double average = articleDao.averagePriceArticle();
			System.out.println("Moyenne des prix des articles : " + average);
			System.out.println();

			// Supprimer tous les articles dont le nom contient "Peinture"
			articleDao.deleteArticleByName("Peinture");

			// Liste Articles
			System.out.println("Liste des articles aprés suppression des peintures : ");
			List<Article> listArticles2 = articleDao.listArticles();
			for (Article article : listArticles2) {
				System.out.println(article);
			}
			System.out.println();

			// Supprimer le fournisseur "La Maison de la Peinture"
			fournisseurDao.delete(fournisseur);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
