package org.inria.restlet.mta.backend;

/**
 * Les objets instances de la classe Zone representent une zone de l'ocean. Il
 * n'est pas possible que deux requins soient dans une zone en meme temps.
 */
public class Zone {
	/**
	 * Le numero de la ligne a laquelle appartient la zone
	 */
	private int ligne;
	/**
	 * Le numero de la colonne a laquelle appartient la zone
	 */
	private int colonne;
	/**
	 * Nombre de sardines dans la zone
	 */
	private int nbSardines;
	/**
	 * Est vrai si un requin se trouve dans la zone
	 */
	private boolean occupee;
	/**
	 * Nombre de poissons pilotes dans la zone
	 */
	private int nbPoissonsPilotes;
	
	private Requin requin;

	/**
	 * Cree un nouvel objet instance de Zone
	 * 
	 * @param ligne
	 * @param colonne
	 * @param nombreSardines
	 */
	public Zone(int ligne, int colonne, int nombreSardines) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nbSardines = nombreSardines;
		this.occupee = false;
		this.requin = null;
	}

	/**
	 * Affiche l'etat de la zone
	 */
	public void afficher() {
		if (!occupee) {
			System.out.println("La zone ["+ligne+"]["+colonne+"] est libre, elle contient "+nbSardines+" sardine(s) et "+nbPoissonsPilotes+" poisson(s) pilote.");
		} else {
			System.out.println("La zone ["+ligne+"]["+colonne+"] est occupee, elle contient "+nbSardines+" sardine(s) et "+nbPoissonsPilotes+" poisson(s) pilote.");
		}
	}

	public int getNbPoissonsPilotes() {
		return nbPoissonsPilotes;
	}

	public void setNbPoissonsPilotes(int nbPoissonsPilotes) {
		this.nbPoissonsPilotes = nbPoissonsPilotes;
	}

	public int getnbSardines() {
		return nbSardines;
	}

	public void setnbSardines(int nbSardines) {
		this.nbSardines = nbSardines;
	}

	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getColonne() {
		return colonne;
	}

	public void setColonne(int colonne) {
		this.colonne = colonne;
	}

	public boolean isOccupee() {
		return occupee;
	}

	public void setOccupee(boolean occupee) {
		this.occupee = occupee;
	}

	public Requin getRequin() {
		return requin;
	}

	public void setRequin(Requin requin) {
		this.requin = requin;
	}

	/**
	 * Fonction pour tester toute les methodes
	 */
	public static void principale() {
		Zone principale = new Zone(0, 0, 6);
		Zone p2 = new Zone(0, 1, 12);
		p2.setOccupee(true);
		principale.afficher();
		p2.afficher();
	}

	/**
	 * Methode d'auto-test pour la classe Zone
	 * 
	 * @param args Non utilise
	 */
	static public void main(String[] args) {
		principale();
	}

}
