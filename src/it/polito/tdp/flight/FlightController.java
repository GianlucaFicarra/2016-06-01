package it.polito.tdp.flight;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.flight.model.Airline;
import it.polito.tdp.flight.model.Airport;
import it.polito.tdp.flight.model.AirportDistance;
import it.polito.tdp.flight.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FlightController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Airline> boxAirline;

    @FXML
    private ComboBox<Airport> boxAirport;

    @FXML
    private TextArea txtResult;
    
    private Model model;

    public void setModel(Model model) {
		this.model=model;
		boxAirline.getItems().addAll(model.listaAirline());
		
	}
    

    @FXML
    void doServiti(ActionEvent event) {
    	txtResult.clear();
    	
    	Airline linea= boxAirline.getValue();
        if(linea==null) {
        	txtResult.appendText("Inserisci una linea");
        }
    	
        try {
        		model.creaGrafo(linea);
        		List<Airport> raggiunti = model.getRaggiunti();
        		boxAirport.getItems().addAll(raggiunti);
        		
        		txtResult.appendText("Aereoporti Raggiunti");
        		for(Airport a: raggiunti) {
        			txtResult.appendText(a.getName());
        		}
        		
        }catch (RuntimeException e) {
			e.printStackTrace();
			txtResult.appendText("Errore\n");
			return;
		}
    

    }
    
    @FXML
    void doRaggiungibili(ActionEvent event) {
	txtResult.clear();
    	
	Airline linea= boxAirline.getValue();
    if(linea==null) {
    	txtResult.appendText("Inserisci una linea");
    }
    	Airport airport= boxAirport.getValue();
        if(airport==null) {
        	txtResult.appendText("Inserisci una aereoporto");
        }
        
        List<AirportDistance> list = model.AirportsRaggiungibili(linea, airport) ;
    	
    	txtResult.clear();
    	txtResult.appendText("Distanze da "+airport.getName()+"\n");
    	for(AirportDistance ad: list)
    		txtResult.appendText(String.format("%s (%.2f km) - %d steps\n", 
    				ad.getAirport().getName(), ad.getDistance(), ad.getTratte()));
    	

    }


    @FXML
    void initialize() {
        assert boxAirline != null : "fx:id=\"boxAirline\" was not injected: check your FXML file 'Flight.fxml'.";
        assert boxAirport != null : "fx:id=\"boxAirport\" was not injected: check your FXML file 'Flight.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Flight.fxml'.";

    }

	
}
