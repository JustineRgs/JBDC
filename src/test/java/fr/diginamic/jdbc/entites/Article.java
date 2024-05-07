package fr.diginamic.jdbc.entites;

public class Article {
    private int id;
    private String designation;
    private double prix;
    private Fournisseur fournisseur;
    
	/**
	 * @param id
	 * @param designation
	 * @param prix
	 * @param fournisseur
	 */
	public Article(String designation, double prix, Fournisseur fournisseur) {
		super();
		this.designation = designation;
		this.prix = prix;
		this.fournisseur = fournisseur;
	}
	
	@Override
	public String toString() {
	    return  "Article : " + designation + "," + " Prix : " + prix + ", Fournisseur : " + fournisseur;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the designation
	 */
	public String getDesignation() {
		return designation;
	}
	
	/**
	 * @param designation the designation to set
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	/**
	 * @return the prix
	 */
	public double getPrix() {
		return prix;
	}
	
	/**
	 * @param prix the prix to set
	 */
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	/**
	 * @return the fournisseur
	 */
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	
	/**
	 * @param fournisseur the fournisseur to set
	 */
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
    
    
}
