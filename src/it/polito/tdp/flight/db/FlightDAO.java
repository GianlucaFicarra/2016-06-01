package it.polito.tdp.flight.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportIdMap;
import it.polito.tdp.flight.model.Tratta;

public class FlightDAO {

	//vertici
	public List<Airport> getAllAirports(AirportIdMap airportIdMap) {
		
		String sql = "SELECT * FROM airport" ;
		
		List<Airport> list = new ArrayList<>() ;
		
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(airportIdMap.get( new Airport(
						res.getInt("Airport_ID"),
						res.getString("name"),
						res.getString("city"),
						res.getString("country"),
						res.getString("IATA_FAA"),
						res.getString("ICAO"),
						res.getDouble("Latitude"),
						res.getDouble("Longitude"),
						res.getFloat("timezone"),
						res.getString("dst"),
						res.getString("tz")))) ;
			}
			
			conn.close();
			
			return list ;
		} catch (SQLException e) {

			e.printStackTrace();
			return null ;
		}
	}
	
	//tendina
	public List<Airline> loadAllAirlines() {
			
			String sql = "SELECT * FROM airline ORDER BY name" ;
			
			List<Airline> list = new ArrayList<>() ;
			
			try {
				Connection conn = DBConnect.getConnection() ;

				PreparedStatement st = conn.prepareStatement(sql) ;
				
				ResultSet res = st.executeQuery() ;
				
				while(res.next()) {
					list.add( new Airline(
							res.getInt("Airline_ID"),
							res.getString("name"),
							res.getString("alias"),
							res.getString("iata"),
							res.getString("icao"),
							res.getString("callsign"),
							res.getString("country"),
							res.getString("active"))) ;
				}
				
				conn.close();
				
				return list ;
			} catch (SQLException e) {

				e.printStackTrace();
				return null ;
			}
		}
	
	//archi
		public List<Tratta> getTratte(AirportIdMap map, Airline linea) {
		
			String sql = "SELECT DISTINCT Source_Airport_id, Destination_Airport_id " + 
					"FROM route AS r " + 
					"WHERE r.Airline_ID=410 " + 
					"AND r.Source_airport_ID> r.Destination_airport_ID ";
			
			List<Tratta> list = new ArrayList<>();
			
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				st.setInt(1, linea.getId());
				ResultSet rs = st.executeQuery();

				
				while (rs.next()) {
					
					Airport origine=  map.get(rs.getString("origin_airport_id"));
					Airport destinazione= map.get(rs.getString("destination_airport_id"));
					
					if(origine!= null || destinazione!= null) {
						
					Tratta tratta= new Tratta( origine, destinazione);
					list.add(tratta); //alla lista inserisco oggetto già presente o appena aggiunta nell'idmap
					}
					
					
				}
				conn.close();
				return list;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
	
}
