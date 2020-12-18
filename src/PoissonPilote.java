import java.util.Random;

public class PoissonPilote extends Thread {

	/**
	 * le nombre de cycle de vie des poissons pilotes
	 */
	static final int NB_CYCLES = 5;
	/**
	 * L'ocean dans lequel se trouve les poissons pilotes
	 */
	private Ocean ocean;
	/**
	 * La zone de depart des poissons pilotes
	 */
	private Zone zone;
	/**
	 * le nombre de cycle restant aux poissons pilotes
	 */
	private int cycle;

	/**
	 * Construit un objet instance de PoissonPilote
	 * 
	 * @param depart   La zone de depart des poissons pilotes
	 * @param suivante La zone suivante des poissons pilotes
	 */
	public PoissonPilote(Ocean ocean, Zone zone) {
		this.ocean = ocean;
		this.zone = zone;
		this.cycle = NB_CYCLES;
	}

}
