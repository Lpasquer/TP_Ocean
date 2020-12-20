package org.inria.restlet.mta.backend;

public class PoissonPilote extends Thread {

	/**
	 * le nombre de cycle de vie des poissons pilotes
	 */
	static final int NB_CYCLES = 2;
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
	public PoissonPilote(Zone zone) {
		this.zone = zone;
		this.cycle = NB_CYCLES;
	}

	/**
	 * Effectue le cycle de vie du poisson pilote
	 */
	public void run() {
		while(cycle > 0 && !isInterrupted()) {
			Requin transporteur = zone.getRequin();
			if(transporteur != null) {
				if(zone.isOccupee() && transporteur.isDispo()) { 

					transporteur.embarquement();
					try {
						sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					transporteur.debarquement();
					this.setZone(transporteur.getZone());
					cycle -= 1;
				}
			}
			
		}		
		if(isInterrupted()) {
			Thread.currentThread().interrupt();
		}
		else {
			this.getZone().setNbPoissonsPilotes(this.getZone().getNbPoissonsPilotes() - 1);
			System.out.println("Le poisson pilote " + Thread.currentThread().getName()
					+ " est arrivé à la fin de son cycle, il disparaît de l'ocean");
			Thread.currentThread().interrupt();
		}
		
	}
	
	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}



}
