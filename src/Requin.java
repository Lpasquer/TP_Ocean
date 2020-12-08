import java.util.Random;

/**
 * Les objets instances de la classe Requin representent des requins nageant dans l'ocean.
 * Leur fonctionnement est le suivant : l'appel a run deplace le requin de la case actuelle,
 * choisi une case aleatoire contigue a sa case actuelle, et rentre quand elle est disponible.
 * puis il attend un certain temps avant de repartir.
 */
public class Requin extends Thread {
	
	static final int NB_CYCLES = 5;
	/**
	 * La zone de depart du requin
	 */
	private Zone actuelle;
	/**
	 * le nombre de cycle de vie du requin
	 */
	private int cycle;
	/**
     * Construit un objet instance de Requin
     * @param depart La zone de depart du requin
     * @param suivante La zone suivante du requin
     */
	public Requin(Zone actuelle) {
		this.actuelle = actuelle;
		this.cycle = NB_CYCLES;
	}
	
	/**
	 * Effectue l'itineraire du Requin
	 */
	public void run() {
		while(cycle > 0) {
			actuelle.Depart();
			
			actuelle.Arrivee();
			try { Thread.sleep(1000);}
			catch(InterruptedException e) {}
		}
	}
	
	public void changeDeZone() {
		int ligne = this.actuelle.getLigne();
		int colonne = this.actuelle.getColonne();
		int random = Math.random()>.5?1:-1;
		boolean gaucheDroite = Math.random()>.5;
		if(gaucheDroite) {
			ligne += random;
		}
		else {
			colonne += random;
		}		
		
		/* on test si on n'est pas sorti de l'ocean */
		if(ligne == -1) {
			actuelle.setLigne(ligne + Ocean.TAILLE_OCEAN);
		}
		else if(ligne == Ocean.TAILLE_OCEAN) {
			actuelle.setLigne(0);
		}
		else {
			actuelle.setLigne(ligne);
		}
		if(colonne == -1) {
			actuelle.setColonne(colonne + Ocean.TAILLE_OCEAN);
		}
		else if(colonne == Ocean.TAILLE_OCEAN) {
			actuelle.setColonne(0);
		}
		else {
			actuelle.setColonne(colonne);
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
