import java.util.Random;

public class Ocean {

	/* Constantes */

	static final int TAILLE_OCEAN = 4;
	static final int NB_REQUINS = 3;
	static final int NB_MAX_SARDINES = 10;

	/* Attributs */

	private Zone[][] zones = new Zone[TAILLE_OCEAN][TAILLE_OCEAN];
	private Requin[] requins = new Requin[NB_REQUINS];
	private PoissonPilote poissonsPilotes = null;
	
	/* Constructeur du systeme d'emprunt */
	Ocean() {
		Random r = new Random();
		
		/* Instanciation des zones */
		for(int i =0; i < TAILLE_OCEAN; i++) {
			for(int j =0; j< TAILLE_OCEAN; j++) {
				int nbSardines = r.nextInt(NB_MAX_SARDINES);
				zones[i][j] = new Zone(i,j,nbSardines);
			}
		}
		
		/*répartition des requins */
		int cpt = 0;
		while(cpt < NB_REQUINS) {
			int ligne = r.nextInt(TAILLE_OCEAN);
			int colonne = r.nextInt(TAILLE_OCEAN);
			if(!zones[ligne][colonne].isOccupee()) { //si la zone n'a pas déjà un requin
				requins[cpt] = new Requin(zones[ligne][colonne]);
				cpt++;
			}
		}
		

	}

	/* Point d'entree du programme */

	public static void main(String[] args) {

		//TODO
	}


} // class Ocean