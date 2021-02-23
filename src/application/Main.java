package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.util.Queue;
import java.io.IOException;
import javafx.fxml.FXMLLoader;

/**
* This Application simulates the Traffic of an Airport, managing Incoming and Outgoing Flights.
* It was made specifically for Multimedia Techonology Course's Lab
* 
* Full JavaDoc Documentation is created for parking Class.
*
* @author  Iosif-Georgios Panagiotopoulos el16063 (Josephkhland)
* @version 1.0
* @since   2020-02-24 
*/
public class Main extends Application {
	
	
	private Stage primaryStage;
	private simulation sim;
	int exit_status =0;

	
	//To pass in the Gui Controller
	int FLIGHTS_ON_HOLD = 0;
	int FREE_SLOTS_NUM =0;
	int NEXT_DEPARTURES =0;
	float EARNINGS =0;
	int TOTAL_TIME =0;
	
	ObservableList<String> gatesList = FXCollections.observableArrayList();
	ObservableList<String> commGatesList = FXCollections.observableArrayList();
	ObservableList<String> zoneAGatesList = FXCollections.observableArrayList();
	ObservableList<String> zoneBGatesList = FXCollections.observableArrayList();
	ObservableList<String> zoneCGatesList = FXCollections.observableArrayList();
	ObservableList<String> generalGatesList = FXCollections.observableArrayList();
	ObservableList<String> longGatesList = FXCollections.observableArrayList();
	
	ObservableList<String> fullGatesList = FXCollections.observableArrayList();
	ObservableList<String> flightsList = FXCollections.observableArrayList();
	ObservableList<String> delayedList = FXCollections.observableArrayList();
	ObservableList<String> holdingList = FXCollections.observableArrayList();
	ObservableList<String> nDepartsList = FXCollections.observableArrayList();

	/**
	   * This method Starts the Stage upon which the FXML GUI is Loaded and the Window is created.
	   * @param primaryStage The Primary Stage of this Application.
	   */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; // connect primary stage
        mainWindow();
        
        
    }

    /**
	   * With this Method the Main Window is Created.
	   * FXML is Loaded, the Scene is created and the Primary Stage is shown.
	   * In addition a secondary Thread is Created for running the Simulation
	   * @throws IOEXCEPTION In case the GUI.fxml is missing from the Repository.
	   */
    // main window
    public void mainWindow() {
        try {
        	//Opening Simulation
        	sim = new simulation(0);
            // view
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("GUI.fxml"));
            Parent root =  loader.load();
            
            // controller
            GuiController mainWindowController = loader.getController();
            mainWindowController.setMain(this);
            mainWindowController.init();

            // scene on stage
            Scene scene = new Scene(root);
            primaryStage.setTitle("Medialab Airport");
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setOnCloseRequest(e -> Platform.exit());
            Thread thread = new Thread(){
                public void run(){
                	
                	Runnable updater = new Runnable() {

                		/**
                		   * This run method is used to update the FXML Form .
                		   * When the updater is called, the GUI Controller fetches any updated information
                		   * upon Lists and variables of the Simulation
                		   * In addition, logged messages of the simulation are transfered to the FXML form
                		   * to be printed upon the VirtualConsole.
                		   */
                        @Override
                        public void run() {
                            mainWindowController.fetchTopUpdates(FLIGHTS_ON_HOLD,FREE_SLOTS_NUM,NEXT_DEPARTURES,EARNINGS,TOTAL_TIME);
                            gatesList = simulation.getInstance().getParkingPerCategory("Gate");
                            commGatesList = simulation.getInstance().getParkingPerCategory("Commercial Gate");
                            zoneAGatesList = simulation.getInstance().getParkingPerCategory("Zone A");
                            zoneBGatesList = simulation.getInstance().getParkingPerCategory("Zone B");
                            zoneCGatesList = simulation.getInstance().getParkingPerCategory("Zone C");
                            generalGatesList = simulation.getInstance().getParkingPerCategory("General Parking Space");
                            longGatesList = simulation.getInstance().getParkingPerCategory("Long Duration");
                            fullGatesList = simulation.getInstance().getFullParking();
                            flightsList = simulation.getInstance().getAllFlights();
                        	delayedList = simulation.getInstance().getAllDelays();
                        	holdingList = simulation.getInstance().getAllHolding();
                        	nDepartsList = simulation.getInstance().getAllNextDepartures();
                        	mainWindowController.updateGates(gatesList,0);
                        	mainWindowController.updateGates(commGatesList,1);
                        	mainWindowController.updateGates(zoneAGatesList,2);
                			mainWindowController.updateGates(zoneBGatesList,3);
                			mainWindowController.updateGates(zoneCGatesList,4);
                			mainWindowController.updateGates(generalGatesList,5);
                			mainWindowController.updateGates(longGatesList,6);
                			mainWindowController.updateMenus(fullGatesList,flightsList,delayedList,holdingList,nDepartsList);
                			Queue<String> messages = simulation.getInstance().getMessagesLog();
                			while(!messages.isEmpty()) {
                				mainWindowController.virtualConsole(messages.poll());
                			}
                			simulation.getInstance().clearMessagesLog();
                        			

                            	
                            
                        }
                    };
                    Platform.runLater(updater);
                    while (exit_status == 0) {
                        if (simulation.getInstance().stopwatch()) {
                        	Platform.runLater(updater);
                        }
                        FLIGHTS_ON_HOLD =simulation.getInstance().getFlightsOnHold();
                        FREE_SLOTS_NUM = simulation.getInstance().getFreeSlotsNum();
                        NEXT_DEPARTURES = simulation.getInstance().findNextDepartures();
                        EARNINGS = simulation.getInstance().getEarnings();
                        TOTAL_TIME = simulation.getInstance().getTotalTime();
                    }
                }
              };
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
	   * This method runs when the Window is closed.
	   * Through this window, the exit_status parameter is altered, which allows shutting down the Simulation process,
	   * running on a separate thread at the same time as the Window.
	   */
    @Override
    public void stop() {
        System.out.println("Stop");
        exit_status = 1;
    }
    

    
    public static void main(String[] args) {
        launch(args);
        
    }
    
  


}
