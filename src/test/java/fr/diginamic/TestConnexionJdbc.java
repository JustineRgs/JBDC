package fr.diginamic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class TestConnexionJdbc {
	private static final String DB_URL;
	private static final String DB_USER;
	private static final String DB_PWD;

	static {
		ResourceBundle dbConfig = ResourceBundle.getBundle("db");
		DB_URL = dbConfig.getString("db.url");
		DB_USER = dbConfig.getString("db.user");
		DB_PWD = dbConfig.getString("db.password");
	}

	public static void main(String[] args) throws SQLException {
		try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PWD)) {
			Statement st = connection.createStatement();

			int nb = st.executeUpdate("INSERT INTO FOURNISSEUR(NOM) VALUES('DIGI')");
			System.out.println("Nmbre d'élément crée : " + nb);

			int nbUpdate = st.executeUpdate("UPDATE FOURNISSEUR SET NOM = 'DIGINAMIC' WHERE id_fournisseur = 4");
			System.out.println("Nmbre d'élément modifié : " + nbUpdate);

			int nbDelete = st.executeUpdate("DELETE FROM FOURNISSEUR WHERE NOM = 'DIGINAMIC'");
			System.out.println("Nmbre d'élément supprimé : " + nbDelete);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}