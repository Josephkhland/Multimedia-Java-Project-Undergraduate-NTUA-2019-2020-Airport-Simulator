package application;

import java.io.IOException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class GuiController {
	
	int allTime;
	private simulation sim;

	//Upper Part Labels
	@FXML
	private Label up_fOnHold_label ;
	
	@FXML
	private Label up_freeParking_label ;
	
	@FXML
	private Label up_nextDepartures_label;
	
	@FXML
	private Label up_earnings_label;
	
	@FXML
	private Label up_time_label;
	
	
	//List Views
	//Bottom Terminal 
	@FXML
	private ListView<String> bottom_terminal_list;
	
	//Gates Lists
	@FXML
	private ListView<String> gatesList;
	
	@FXML
	private ListView<String> commGatesList;
	
	@FXML
	private ListView<String> zoneAGatesList;
	
	@FXML
	private ListView<String> zoneBGatesList;
	
	@FXML
	private ListView<String> zoneCGatesList;
	
	@FXML
	private ListView<String> generalGatesList;
	
	@FXML
	private ListView<String> longGatesList;
	
	//Menu Items 
	@FXML
	private MenuItem menu_start;
	
	@FXML
	private MenuItem menu_load;
	
	private Stage loadStage;
	@FXML protected void menu_load_action() {
		if (loadStage == null) {
		try {
	        FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource("Load_scenario.fxml"));
	        //LoadPopUpController loadController = fxmlLoader.getController();
	        
	        
	        Scene scene = new Scene(fxmlLoader.load());
	        loadStage = new Stage();
	        loadStage.setTitle("Load Scenario");
	        loadStage.setScene(scene);
	        loadStage.show();
	    
	        
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		}
		else if (loadStage.isShowing()) 
		{
			loadStage.toFront();
		} 
		else 
		{
			loadStage.show();
		}
	    
		
	}
	
	
	@FXML
	private MenuItem menu_exit;
	
	@FXML protected void menu_exit_action() {
		Platform.exit();
		
	}
	
	@FXML
	private MenuItem menu_gates;
	
	private Stage gatesStage;
	GatesController gateController;
	@FXML protected void menu_gates_action() {
		if (gatesStage == null) {
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(getClass().getResource("Gates.fxml"));
		        Parent root = fxmlLoader.load();
		        gateController = fxmlLoader.getController();
		        //GatesController gateController = fxmlLoader.getController();
		        gateController.updateGates(gates_menu_list);
		        
		        Scene scene = new Scene(root);
		        gatesStage = new Stage();
		        gatesStage.setTitle("Gates");
		        gatesStage.setScene(scene);
		        gatesStage.show();
		    
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			}
			else if (gatesStage.isShowing()) 
			{
				gatesStage.toFront();
			} 
			else 
			{
				gatesStage.show();
			}
	}
	
	@FXML
	private MenuItem menu_holding;
	
	private Stage holdingStage;
	GatesController holdingController;
	@FXML protected void menu_holding_action() {
		if (holdingStage == null) {
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(getClass().getResource("Gates.fxml"));
		        Parent root = fxmlLoader.load();
		        holdingController = fxmlLoader.getController();
		        //GatesController gateController = fxmlLoader.getController();
		        holdingController.updateGates(holding_menu_list);
		        
		        Scene scene = new Scene(root);
		        holdingStage = new Stage();
		        holdingStage.setTitle("Flights on Hold");
		        holdingStage.setScene(scene);
		        holdingStage.show();
		    
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			}
			else if (holdingStage.isShowing()) 
			{
				holdingStage.toFront();
			} 
			else 
			{
				holdingStage.show();
			}
	}
	
	
	@FXML 
	private MenuItem menu_delayed;
	
	private Stage delayedStage;
	GatesController delayedController;
	@FXML protected void menu_delayed_action() {
		if (delayedStage == null) {
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(getClass().getResource("Gates.fxml"));
		        Parent root = fxmlLoader.load();
		        delayedController = fxmlLoader.getController();
		        //GatesController gateController = fxmlLoader.getController();
		        delayedController.updateGates(delayed_menu_list);
		        
		        Scene scene = new Scene(root);
		        delayedStage = new Stage();
		        delayedStage.setTitle("Delayed Flights");
		        delayedStage.setScene(scene);
		        delayedStage.show();
		    
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			}
			else if (delayedStage.isShowing()) 
			{
				delayedStage.toFront();
			} 
			else 
			{
				delayedStage.show();
			}
	}
	
	@FXML 
	private MenuItem menu_departures;
	
	private Stage nDepartsStage;
	GatesController nDepartsController;
	@FXML protected void menu_departures_action(){
		if (nDepartsStage == null) {
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(getClass().getResource("Gates.fxml"));
		        Parent root = fxmlLoader.load();
		        nDepartsController = fxmlLoader.getController();
		        //GatesController gateController = fxmlLoader.getController();
		        nDepartsController.updateGates(nDeparts_menu_list);
		        
		        Scene scene = new Scene(root);
		        nDepartsStage = new Stage();
		        nDepartsStage.setTitle("Flights Departing within 10 Minutes");
		        nDepartsStage.setScene(scene);
		        nDepartsStage.show();
		    
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			}
			else if (nDepartsStage.isShowing()) 
			{
				nDepartsStage.toFront();
			} 
			else 
			{
				nDepartsStage.show();
			}
	}
	
	@FXML 
	private MenuItem menu_flights;
	
	private Stage flightStage;
	GatesController flightController; 
	@FXML protected void menu_flights_action() {
		if (nDepartsStage == null) {
			try {
		        FXMLLoader fxmlLoader = new FXMLLoader();
		        fxmlLoader.setLocation(getClass().getResource("Gates.fxml"));
		        Parent root = fxmlLoader.load();
		        flightController = fxmlLoader.getController();
		        //GatesController gateController = fxmlLoader.getController();
		        flightController.updateGates(flights_menu_list);
		        
		        Scene scene = new Scene(root);
		        flightStage = new Stage();
		        flightStage.setTitle("All Flights");
		        flightStage.setScene(scene);
		        flightStage.show();
		    
		        
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
			}
			else if (flightStage.isShowing()) 
			{
				flightStage.toFront();
			} 
			else 
			{
				flightStage.show();
			}
	}
	
	//ADD Airplane Components
	@FXML
	private TextField flightIDField;
	
	@FXML
	private TextField flightOriginField;
	
	@FXML
	private TextField departureTimeField;
	
	@FXML
	private ComboBox<String> flightTypeField;
	
	@FXML
	private ComboBox<String> airplaneTypeField;
	
	@FXML
	private RadioButton cleaningServiceButton;
	
	@FXML
	private RadioButton loadingServiceButton;
	
	@FXML
	private RadioButton passengerServiceButton;
	
	@FXML
	private RadioButton refuellingServiceButton;
	
	@FXML
	private Button addFlightButton;
	
	@FXML
	private Button genID;
	
	@FXML
	private CheckBox lockAutoscrollCheckbox;
	
	@FXML
	private MenuItem speed_mode_fast_menu;
	@FXML
	private MenuItem speed_mode_avg_menu;
	@FXML
	private MenuItem speed_mode_normal_menu;
	@FXML
	private MenuItem speed_mode_real_menu;
	
	@FXML protected void speed_mode_fast() {
		simulation.getInstance().setTick(100);
		speed_mode_fast_menu.setDisable(true);
		speed_mode_avg_menu.setDisable(false);
		speed_mode_normal_menu.setDisable(false);
		speed_mode_real_menu.setDisable(false);
	}
	
	@FXML protected void speed_mode_avg() {
		simulation.getInstance().setTick(1000);
		speed_mode_fast_menu.setDisable(false);
		speed_mode_avg_menu.setDisable(true);
		speed_mode_normal_menu.setDisable(false);
		speed_mode_real_menu.setDisable(false);
	}
	
	@FXML protected void speed_mode_normal() {
		simulation.getInstance().setTick(5000);
		speed_mode_fast_menu.setDisable(false);
		speed_mode_avg_menu.setDisable(false);
		speed_mode_normal_menu.setDisable(true);
		speed_mode_real_menu.setDisable(false);
	}
	
	@FXML protected void speed_mode_real() {
		simulation.getInstance().setTick(60000);
		speed_mode_fast_menu.setDisable(false);
		speed_mode_avg_menu.setDisable(false);
		speed_mode_normal_menu.setDisable(false);
		speed_mode_real_menu.setDisable(true);
	}
	
	
	@FXML protected void generateID(ActionEvent event) {
        flightIDField.setText(simulation.getInstance().airplaneIDGenerator());
        addFlightButton.setDisable(false);
    }
	
	@FXML protected void addFlight(ActionEvent event) {
		if(flightIDField.getText().isBlank() || !simulation.getInstance().checkAirplaneIDUniqueness(flightIDField.getText().trim())) {
			virtualConsole("ID VALUE CAN'T BE BLANK OR ID ALREADY EXISTS.");
		}
		else
		{
			if(flightOriginField.getText().isBlank()) {
				virtualConsole("ORIGIN FIELD CANNOT BE BLANK");
			}
			else if(flightTypeField.getSelectionModel().isEmpty()) {
				virtualConsole("FLIGHT TYPE CANNOT BE BLANK");
				
			}
			else if(airplaneTypeField.getSelectionModel().isEmpty())
			{
				virtualConsole("AIRPLANE TYPE CANNOT BE BLANK");
			}
			else if(departureTimeField.getText().isBlank()) {
				virtualConsole("TIME UNTIL DEPARTURE CAN'T BE BLANK");
				
			}
			else {
				String id0 = flightIDField.getText().trim();
				String city = flightOriginField.getText().trim();
				
				int fl_type;
				if (flightTypeField.getValue() == "PRIVATE") {
					fl_type =3;
				}
				else if (flightTypeField.getValue() == "PASSENGER") {
					fl_type =1;
				}
				else {
					fl_type =2;
				}
				
				int air_type;
				if (airplaneTypeField.getValue() == "JET") {
					air_type =3;
				}
				else if (airplaneTypeField.getValue()=="SINGLE MOTOR") {
					air_type =1;
				}
				else {
					air_type =2;
				}
				try {
				int timeTotal = Integer.parseInt(departureTimeField.getText().trim());
				
				if(timeTotal <0) {
					virtualConsole("NEGATIVE DEPARTURE TIME NOT ALLOWED");
					return;
				}			
				Flight nFlight = new Flight(id0,city,fl_type,air_type,timeTotal,allTime);
				
				if(cleaningServiceButton.isSelected()) {
					nFlight.addSpecialTreatment(special_services.CLEANING);
				}
				
				if(loadingServiceButton.isSelected() ) {
					nFlight.addSpecialTreatment(special_services.LOAD);
				}
				if(passengerServiceButton.isSelected()) {
					nFlight.addSpecialTreatment(special_services.PASSENGER_TRANSIT);
				}
				if(refuellingServiceButton.isSelected()) {
					nFlight.addSpecialTreatment(special_services.REFUEL);
				}
				
				simulation.getInstance().addFlight(nFlight);
				simulation.getInstance().getTotalTime();
				String cTime = up_time_label.getText();
				virtualConsole(cTime+"|Flight: "+nFlight.getID()+" is now on Hold");
				flightIDField.setText("");
				}
				catch (NumberFormatException nfe)
			    {
			      virtualConsole(" NUMBER FORMAT EXPECTED IN TIME UNTIL DEPARTURE ");
			    }
			}
		}
	}
	

	ObservableList<String> flight_type_options = FXCollections.observableArrayList("PRIVATE","PASSENGER","COMMERCIAL");
	ObservableList<String> airship_type_options = FXCollections.observableArrayList("JET","SINGLE MOTOR","TURBOPROP");
	ObservableList<String> bottom_terminal = FXCollections.observableArrayList();
	
	public GuiController() {

	}
	

	private static GuiController instance;
	public static GuiController getInstance() {
		return instance == null ? instance = new GuiController() : instance;
	}
	

	public void fetchTopUpdates(int flHold , int freeGates, int nextDepartures,float earnings, int time) {
		up_fOnHold_label.setText("Flights on Hold: "+ flHold);
		up_freeParking_label.setText("Gates: "+ freeGates);
		up_nextDepartures_label.setText("Next Departures: "+ nextDepartures);
		up_earnings_label.setText("Earnings: "+ earnings +" $");
		String hours = String.valueOf(time/60);
		int minutes = time%60;
		String minStr;
		if (minutes<10) {
			minStr = "0"+minutes;
		}
		else {
			minStr = String.valueOf(minutes);
		}
		allTime=time;
		up_time_label.setText(hours+":"+minStr);
	}
	
	public void init() {
        flightIDField.setDisable(true);
        flightTypeField.setItems(flight_type_options);
        airplaneTypeField.setItems(airship_type_options);
        bottom_terminal_list.setItems(bottom_terminal);
	}
	
	public void resetScenario() {
		bottom_terminal.clear();
		up_time_label.setText("00:00");
		allTime=0;
		simulation.getInstance().changeScenario(simulation.getInstance().getScenarioID());
		
	}
	
	public void resetGUI() {
		bottom_terminal.clear();
	}

	public void virtualConsole(String s) {
	    int index = bottom_terminal.size();
	    bottom_terminal.add(s);
	    if (s == "_EXECUTEORDER66") {
	    	bottom_terminal.clear();
	    	up_time_label.setText("00:00");
			allTime=0;
	    	return;
	    }
	    if (!lockAutoscrollCheckbox.isSelected()) {
	    	bottom_terminal_list.scrollTo(index);
	    }
	}
	
	public void updateGates(ObservableList<String> contents, int listCase) {
		switch(listCase) {
		case 0:
			gatesList.setItems(contents);
			break;
		case 1:
			commGatesList.setItems(contents); 
			break;
		case 2:
            zoneAGatesList.setItems(contents);
            break;
		case 3:
            zoneBGatesList.setItems(contents);
            break;
		case 4:
            zoneCGatesList.setItems(contents);
            break;
		case 5:
            generalGatesList.setItems(contents);
            break;
		case 6:
            longGatesList.setItems(contents);
            break;
		}
		
	}
	
	ObservableList<String> gates_menu_list =FXCollections.observableArrayList();
	ObservableList<String> flights_menu_list =FXCollections.observableArrayList();
	ObservableList<String> delayed_menu_list =FXCollections.observableArrayList();
	ObservableList<String> holding_menu_list =FXCollections.observableArrayList();
	ObservableList<String> nDeparts_menu_list =FXCollections.observableArrayList();
	public void updateMenus(ObservableList<String> gates,ObservableList<String> flights, ObservableList<String> delayed, ObservableList<String> holding, ObservableList<String> nDeparts) {
		gates_menu_list = gates;
		if (gateController!= null) {
			gateController.updateGates(gates_menu_list);
		}
		flights_menu_list = flights;
		if (flightController!= null) {
			flightController.updateGates(flights_menu_list);
		}
		delayed_menu_list = delayed;
		if (delayedController!= null) {
			delayedController.updateGates(delayed_menu_list);
		}
		holding_menu_list = holding;
		if (holdingController!= null) {
			holdingController.updateGates(holding_menu_list);
		}
		nDeparts_menu_list = nDeparts;
		if (nDepartsController!= null) {
			nDepartsController.updateGates(nDeparts_menu_list);
		}
	}
	
	
	private Main main;

	
    // connect main class to controller
    public void setMain(Main main0) {
        main = main0; 

    }

}
