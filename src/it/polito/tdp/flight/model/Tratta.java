package it.polito.tdp.flight.model;

public class Tratta {

	private Airport origine;
	private Airport destination;
	public Tratta(Airport origine, Airport destination) {
		super();
		this.origine = origine;
		this.destination = destination;
	}
	public Airport getOrigine() {
		return origine;
	}
	public void setOrigine(Airport origine) {
		this.origine = origine;
	}
	public Airport getDestination() {
		return destination;
	}
	public void setDestination(Airport destination) {
		this.destination = destination;
	}
	
	
}
