package it.polito.tdp.flight.model;

import java.util.HashMap;
import java.util.Map;

public class AirportIdMap {

private Map<Integer, Airport> map;
	
	public AirportIdMap() {
		map = new HashMap<>();
	}
	
	public Airport get(String airportId) {
		return map.get(airportId);
	}
	
	public Airport get(Airport airport) {
		Airport old = map.get(airport.getId());
		if (old == null) {
			map.put(airport.getId(), airport);
			return airport;
		}
		return old;
	}
	
	public void put(Airport airport, Integer airportId) {
		map.put(airportId, airport);
	}
	
}