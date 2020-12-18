import java.util.Random;

public class Ocean {

	/* Constantes */

	static final int TAILLE_OCEAN = 4;
	static final int NB_REQUINS = 5;
	static final int NB_MAX_SARDINES = 15;

	/* Attributs */

	private Zone[][] zones = new Zone[TAILLE_OCEAN][TAILLE_OCEAN];
	private Requin[] requins = new Requin[NB_REQUINS];
	private PoissonPilote poissonsPilotes = null;
	
	/* Constructeur de l'Ocean */
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
				requins[cpt] = new Requin(this, zones[ligne][colonne]);
				zones[ligne][colonne].setOccupee(true);
				cpt++;
			}
		}
		/*repartition des poissons pilotes*/
		//TODO
	}

	public synchronized void moveRequin(Requin requin) {
		int ligne = requin.getZone().getLigne();
		int colonne = requin.getZone().getColonne();
		
		int l_arr = ligne;
		int c_arr = colonne;
		Random r = new Random();
		int move = r.nextInt(4);
		if(move == 0) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" va en haut");
			if(l_arr == 0) {
				l_arr = TAILLE_OCEAN - 1;
			}
			else {
				l_arr -= 1;
			}
		}
		else if(move == 1) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" va en bas");
			if(l_arr == TAILLE_OCEAN - 1) {
				l_arr = 0;
			}
			else {
				l_arr += 1;
			}
		}
		else if(move == 2) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" va a gauche");
			if(c_arr == 0) {
				c_arr = TAILLE_OCEAN - 1;
			}
			else {
				c_arr -= 1;
			}
		}
		else if(move == 3) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" va a droite");
			if(c_arr == TAILLE_OCEAN - 1) {
				c_arr = 0;
			}
			else {
				c_arr += 1;
			}
		}
		requin.getZone().setOccupee(false);
		while(zones[l_arr][c_arr].isOccupee()) {
			try {
				System.out.println("La zone ["+l_arr+"]["+c_arr+"] est occupee, le requin "+Thread.currentThread().getName()+" attend");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("La zone ["+l_arr+"]["+c_arr+"] s'est liberer");
		}
		System.out.println("le requin "+Thread.currentThread().getName()+" occupe maintenant la zone ["+l_arr+"]["+c_arr+"]");
		requin.setZone(zones[l_arr][c_arr]);
		requin.getZone().setOccupee(true);
		
		notifyAll();
	}
	

	/* Point d'entree du programme */
	private static void OceanWork(){
		Ocean TP5 = new Ocean();
		for(Requin r : TP5.requins) {
			r.start();
		}
		for(Requin r : TP5.requins) {
			try {
				r.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for(Zone[] zs : TP5.zones) {
			for(Zone z : zs) {
				z.afficher();
			}
		}
		
	}
	
	public static void main(String[] args) {
		OceanWork();
	}


} // class Ocean