package it.polito.tdp.flight.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.flight.db.FlightDAO;

public class Model {

	private List<Airline> airlines;
	private List<Airport> airports; //nodi
	private AirportIdMap airportIdMap;
	private List<Tratta> tratte; //archi
	
	private FlightDAO dao;
	SimpleDirectedWeightedGraph<Airport, DefaultWeightedEdge> grafo;
	
	private List<Airport> raggiunti; //nodi
	
	public Model() {
		dao= new FlightDAO();
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
        this.airlines =  dao.loadAllAirlines(); //carico tutte le linee disponibili
		
		this.airportIdMap = new AirportIdMap();
		this.airports= dao.getAllAirports(airportIdMap);; //vertici
		
		this.tratte = new LinkedList<>(); //archi
		
		raggiunti=new LinkedList<>();
	}
	

	public List<Airline> listaAirline() {
		return airlines;
	}

	public void creaGrafo(Airline linea) {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//creato grafo e vertici li aggiungo
		Graphs.addAllVertices(grafo, this.airports);
		
		//archi
		tratte=dao.getTratte(airportIdMap, linea);
		
		//peso
		for(Tratta t: tratte) {
			Airport source= airportIdMap.get(t.getOrigine());
			Airport destination= airportIdMap.get(t.getDestination());
		
			double distanza=0.0;
			if(source!=null && destination!=null) {
				
			if(!raggiunti.contains(source)) {
				raggiunti.add(source);
			}
			if(!raggiunti.contains(destination)) {
				raggiunti.add(destination);
			}		
				
				distanza = LatLngTool.distance(new LatLng(source.getLatitude(), source.getLongitude()),
                        new LatLng(destination.getLatitude(), destination.getLongitude()),
                        LengthUnit.KILOMETER);
			}
			
			Graphs.addEdge(grafo, source, destination, distanza);
		}
	}


	public List<Airport> getRaggiunti() {
		return raggiunti;
	}


	public List<AirportDistance> AirportsRaggiungibili(Airline linea, Airport airport) {

		List<AirportDistance> list = new ArrayList<>();

		/*for (Airport end : raggiunti) {
			ShortestPathAlgorithm<Airport,DefaultWeightedEdge> spa = new DijkstraShortestPath<Airport, DefaultWeightedEdge>(grafo);
			
			double peso = spa.getPathWeight(airport, end);
			if (peso != 0) {
				list.add(new AirportDistance(end, peso.getWeight(), peso.getEdgeList().size()));
			}
		}*/

		list.sort(new Comparator<AirportDistance>() {
			@Override
			public int compare(AirportDistance o1, AirportDistance o2) {
				return Double.compare(o1.getDistance(), o2.getDistance());
			}
		});

		return list;
	}
	
	

}
