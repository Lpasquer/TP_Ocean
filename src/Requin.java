import java.util.Random;

/**
 * Les objets instances de la classe Requin representent des requins nageant dans l'ocean.
 * Leur fonctionnement est le suivant : l'appel a run deplace le requin de la case actuelle,
 * choisi une case aleatoire contigue a sa case actuelle, et rentre quand elle est disponible.
 * puis il attend un certain temps avant de repartir.
 */
public class Requin extends Thread {
	/**
	 * le nombre de cycle de vie du requin
	 */
	static final int NB_CYCLES = 5;
	/**
	 * L'ocean dans lequel se trouve le requin
	 */
	private Ocean ocean;
	/**
	 * La zone de depart du requin
	 */
	private Zone zone;
	/**
	 * le nombre de cycle restant au requin
	 */
	private int cycle;

	/**
     * Construit un objet instance de Requin
     * @param depart La zone de depart du requin
     * @param suivante La zone suivante du requin
     */
	public Requin(Ocean ocean, Zone zone) {
		this.ocean = ocean;
		this.zone = zone;
		this.cycle = NB_CYCLES;
	}
	
	/**
	 * Effectue l'itineraire du Requin
	 */
	public void run() {
		while(cycle > 0) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" de la zone ["+getZone().getLigne()+"]["+getZone().getColonne()+"] se deplace, il lui reste "+cycle+" cycle(s) de vie.");
			ocean.moveRequin(this);
			mangerSardine();
			try { Thread.sleep(1000);}
			catch(InterruptedException e) {}	
			cycle -= 1;
		}
		this.getZone().setOccupee(false);
		System.out.println("Le requin "+Thread.currentThread().getName()+" est arrivé à la fin de son cycle, il disparaît de l'ocean");
		
	}
	public void mangerSardine() {
		int nbSardines = this.getZone().getnbSardines();
		Random r = new Random();
		int faim = r.nextInt(3) + 1;
		if(nbSardines > faim ) {
			System.out.println("Le requin "+Thread.currentThread().getName()+" mange "+faim+" sardine(s)");
			this.getZone().setnbSardines(nbSardines - faim);
			System.out.println("Il reste "+getZone().getnbSardines()+" sardines dans la zone ["+getZone().getLigne()+"]["+getZone().getColonne()+"]");			
		}
	}
		
	public Zone getZone() {
		return zone;
	}
	public void setZone(Zone zone) {
		this.zone = zone;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
