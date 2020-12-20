package Ocean;
import java.util.ArrayList;
import java.util.Random;

import org.inria.restlet.mta.backend.PoissonPilote;
import org.inria.restlet.mta.backend.Requin;
import org.inria.restlet.mta.backend.Zone;

public class Ocean {

	/* Constantes */

	static final int TAILLE_OCEAN = 4;
	static final int NB_REQUINS = 3;
	static final int NB_MAX_SARDINES = 20;
	static final int NB_POISSONS_PILOTES = 6;
	static final int CASES_POISSONS_PILOTES = 8;

	/* Attributs */

	private Zone[][] zones = new Zone[TAILLE_OCEAN][TAILLE_OCEAN];
	private Requin[] requins = new Requin[NB_REQUINS];
	private ArrayList<PoissonPilote> poissonsPilotes[] = new ArrayList[CASES_POISSONS_PILOTES]; // a test (tableau de liste, qui sera [case00(),case01(1,1,1),...], la case00 n'as pas de poissons pilotes, la case 01 a 3 poissons pilotes)
	private int nbRequins = 0;

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
		int compteur = 0;
		while (compteur<CASES_POISSONS_PILOTES) {
			int ligne = r.nextInt(TAILLE_OCEAN);
			int colonne = r.nextInt(TAILLE_OCEAN);
			int random = r.nextInt(NB_POISSONS_PILOTES)+1;
			if(zones[ligne][colonne].getNbPoissonsPilotes() == 0) {
				poissonsPilotes[compteur] = new ArrayList<PoissonPilote>();
				for(int i=0; i < random; i++) {
					poissonsPilotes[compteur].add(new PoissonPilote(zones[ligne][colonne]));
				}
				zones[ligne][colonne].setNbPoissonsPilotes(random);
				compteur++;
			}			
		}
	}

	public synchronized void synchro() {
		notifyAll();
	}
	
	public synchronized void moveRequin(Requin requin) {
		//Le requin commence par quitter sa zone
		requin.getZone().setOccupee(false);
		requin.getZone().setRequin(null);
		//il choisi aleatoirement une case adjacente
		int ligne = requin.getZone().getLigne();
		int colonne = requin.getZone().getColonne();
		int l_arr = ligne;
		int c_arr = colonne;
		Random r = new Random();
		int move = r.nextInt(4);		
		if (move == 0) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " va en haut");
			if (l_arr == 0) {
				l_arr = TAILLE_OCEAN - 1;
			} else {
				l_arr -= 1;
			}
		} else if (move == 1) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " va en bas");
			if (l_arr == TAILLE_OCEAN - 1) {
				l_arr = 0;
			} else {
				l_arr += 1;
			}
		} else if (move == 2) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " va a gauche");
			if (c_arr == 0) {
				c_arr = TAILLE_OCEAN - 1;
			} else {
				c_arr -= 1;
			}
		} else if (move == 3) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " va a droite");
			if (c_arr == TAILLE_OCEAN - 1) {
				c_arr = 0;
			} else {
				c_arr += 1;
			}
		}
		//Si sa zone destination est occupee, il attend qu'elle se libere
		while (zones[l_arr][c_arr].isOccupee()) {
			try {
				System.out.println("La zone [" + l_arr + "][" + c_arr + "] est occupee, le requin "
						+ Thread.currentThread().getName() + " attend");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(!zones[l_arr][c_arr].isOccupee()) {System.out.println("La zone [" + l_arr + "][" + c_arr + "] s'est liberer");}
		}
		System.out.println("le requin " + Thread.currentThread().getName() + " occupe maintenant la zone [" + l_arr
				+ "][" + c_arr + "]");
		requin.setZone(zones[l_arr][c_arr]);
		requin.getZone().setOccupee(true);
		requin.getZone().setRequin(requin);
		//il notifie les autres requins qu'il a quitter sa zone et qu'il en occupe maintenant une autre
		notifyAll();
	}

	/* Point d'entree du programme */
	private static void OceanWork() {
		Ocean TP5 = new Ocean();

		for (Zone[] zs : TP5.zones) {
			for (Zone z : zs) {
				//On affiche l'etat de l'ocean après l'initialisation
				z.afficher();
			}
		}

		for (Requin r : TP5.requins) {
			r.start();
			TP5.nbRequins++;
		}

		for(ArrayList<PoissonPilote> listepp : TP5.poissonsPilotes) {
			for(PoissonPilote pp : listepp) {
				pp.start();
			}
		}

		for (Requin r : TP5.requins) {
			try {
				r.join();
				TP5.nbRequins--;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(TP5.nbRequins);

		if(TP5.nbRequins == 0) {
			for(ArrayList<PoissonPilote> listepp : TP5.poissonsPilotes) {
				for(PoissonPilote pp : listepp) {
					pp.interrupt();
				}
			}
		}
		for (Zone[] zs : TP5.zones) {
			for (Zone z : zs) {
				//On affiche l'etat de l'ocean a la fin de la simulation
				z.afficher();
			}
		}
	}

	public static void main(String[] args) {
		OceanWork();
	}

} // class Ocean