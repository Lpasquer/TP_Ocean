package org.inria.restlet.mta.backend;
import java.util.Random;

import Ocean.Ocean;

/**
 * Les objets instances de la classe Requin representent des requins nageant
 * dans l'ocean. Leur fonctionnement est le suivant : l'appel a run deplace le
 * requin de la case actuelle, choisi une case aleatoire contigue a sa case
 * actuelle, et rentre quand elle est disponible. puis il attend un certain
 * temps avant de repartir.
 */
public class Requin extends Thread {
	/**
	 * le nombre de cycle de vie du requin
	 */
	static final int NB_CYCLES = 7;
	/**
	 * le nombre de poissons pilotes que peut transporter un requin au maximum
	 */
	static final int PLACES_poissonsPilotes = 3;
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
	 * le nombre de places restantes sur le requin
	 */
	private int place;
	private boolean dispo;

	/**
	 * Construit un objet instance de Requin
	 * 
	 * @param ocean   l'ocean dans lequel evolue le requin
	 * @param zone La zone de depart du requin
	 */
	public Requin(Ocean ocean, Zone zone) {
		this.ocean = ocean;
		this.zone = zone;
		this.cycle = NB_CYCLES;
		this.place = PLACES_poissonsPilotes;
		this.dispo = false;
	}

	/**
	 * Effectue le cycle de vie du Requin
	 */
	public void run() {
		while (cycle > 0) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " de la zone [" + getZone().getLigne()
					+ "][" + getZone().getColonne() + "] se deplace, il lui reste " + cycle + " cycle(s) de vie.");
			ocean.moveRequin(this);
			mangerSardine();
			cycle -= 1;
			if(cycle != 0) {
				dispo = true;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			dispo = false;			
		}
		this.getZone().setOccupee(false);
		System.out.println("Le requin " + Thread.currentThread().getName()
				+ " est arrivé à la fin de son cycle, il disparaît de l'ocean");
		while(place < Requin.PLACES_poissonsPilotes) { //depose les poissons pilotes qui ont pu monter avant que le requin ne meurs
			this.zone.setNbPoissonsPilotes(zone.getNbPoissonsPilotes() + 1);
			place ++;
		}
		ocean.synchro(); // au cas ou un autre requin est en attente sur la zone qu'occupait ce requin avant de mourir
		Thread.currentThread().interrupt();
	}

	public void mangerSardine() {
		int nbSardines = this.getZone().getnbSardines();
		Random r = new Random();
		int faim = r.nextInt(3) + 1;
		if (nbSardines >= faim) {
			System.out.println("Le requin " + Thread.currentThread().getName() + " mange " + faim + " sardine(s)");
			this.getZone().setnbSardines(nbSardines - faim);
			System.out.println("Il reste " + getZone().getnbSardines() + " sardines dans la zone ["
					+ getZone().getLigne() + "][" + getZone().getColonne() + "]");
		}
		else {
			System.out.println("Le requin " + Thread.currentThread().getName() + " ne mange pas de sardine");
		}
	}

	public synchronized void embarquement() {
		while(this.getPlace() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(zone.getNbPoissonsPilotes() > 0) {
			this.setPlace(this.getPlace() - 1);
			this.zone.setNbPoissonsPilotes(this.zone.getNbPoissonsPilotes() - 1);
			System.out.println("Le poisson pilote "+Thread.currentThread().getName()+" est monter sur le requin "+this.getName());			
		}
	}

	public synchronized void debarquement() {
		while(this.getPlace() == Requin.PLACES_poissonsPilotes) {
			try {
				wait();
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
		this.setPlace(this.getPlace() + 1);
		this.zone.setNbPoissonsPilotes(this.zone.getNbPoissonsPilotes() + 1);
		System.out.println("Le poisson pilote "+Thread.currentThread().getName()+" est descendu du requin "+this.getName());
		notifyAll();
	}

	public Zone getZone() {
		return zone;
	}

	public void setZone(Zone zone) {
		this.zone = zone;
	}

	public int getPlace() {
		return place;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public boolean isDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}



}
