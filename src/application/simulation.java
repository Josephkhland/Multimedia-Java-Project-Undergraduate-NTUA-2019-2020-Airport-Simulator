package application;

import java.util.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * The simulation is a Class which follows the Singleton Pattern.
 * The simulation instance is used to operate the engine's clock as well as to store the 
 * information of the simulation and handle the operations of this Application.
 */
public class simulation {
	private int totalTime;
	private float earnings;
	private int scenario_id;
	private long startTime;
	private long countTime;
	int simulation_tick =5000;
	int cross_thread_command=0;
	
	//Queue<Flight> fRegistered;
	Queue<Flight> fOnHold;
	Queue<Flight> fLanding;
	List<Flight> fParked;
	List<parking> parkingSlots;
	
	Queue<String> loggedMessages;
	
	Airport airport;
	
	//Creating the Category objects for this simulation.
	cat_gate port_category_0;
	cat_gate_c port_category_1;
	cat_zone_A port_category_2;
	cat_zone_B port_category_3;
	cat_zone_C port_category_4;
	cat_general port_category_5;
	cat_long port_category_6;
	
	/**
	   * This method reads the setup_id.txt file and creates the Flight objects required based on it
	   * placing them in the fOnHold List, where the Flights that are On Hold are placed.
	   * @param id The scenario ID. Decides which setup file to select. 
	   * @return boolean if the Setup is successful 
	   * @throws IOEXCEPTION if the file is not found
	   * @see IOEXCEPTION
	   */
	private boolean setup(int id) {
	
		try {
			String filename = "./medialab/setup_" + String.valueOf(id) +".txt";
			File myScenario = new File(filename);
			Scanner myReader =new Scanner(myScenario);
			while (myReader.hasNext()) {
				String data = myReader.nextLine();
				String[] lineData = data.split(",");
				String id0 = lineData[0].trim();
				String city = lineData[1].trim();
				int fl_type = Integer.parseInt(lineData[2].trim());
				int air_type = Integer.parseInt(lineData[3].trim());
				int timeTotal = Integer.parseInt(lineData[4].trim());
				Flight nFlight = new Flight(id0,city,fl_type,air_type,timeTotal,totalTime);
				fOnHold.add(nFlight);
			}
			myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("Error: File not Found.");
				logTimestamp("Error: File not Found.");
				e.printStackTrace();
				return false;
			}
		return true;
	}
	
	/**
	   * This Method is used during initialisation of the simulation instance. it creates parking objects for the Gates
	   * based on the data stored within the Airport Instance for the simulation.
	   */
	private void initialiseParking() {
		//Setting Up Parking Slots:
		for (int i=0; i<7; i++) {
			for (int j=0; j<airport.capacity[i];j++){
				String nID = airport.idConcat[i] + String.valueOf(j);
				parking nPark = null;
				switch(i) {
				
				case 0:
					//Gate
					nPark = new parking(nID,port_category_0,parking_state.FREE,airport.cost[i]);
					break;
				case 1:
					//Commercial Gate
					nPark = new parking(nID,port_category_1,parking_state.FREE,airport.cost[i]);
					break;
				case 2:
					//Zone A
					nPark = new parking(nID,port_category_2,parking_state.FREE,airport.cost[i]);
					break;
				case 3:
					//Zone B
					nPark = new parking(nID,port_category_3,parking_state.FREE,airport.cost[i]);
					break;
				case 4:
					//Zone C
					nPark = new parking(nID,port_category_4,parking_state.FREE,airport.cost[i]);
					break;
				case 5:
					//General Parking Slot
					nPark = new parking(nID,port_category_5,parking_state.FREE,airport.cost[i]);
					break;
				case 6:
					//Long Duration
					nPark = new parking(nID,port_category_6,parking_state.FREE,airport.cost[i]);
					break;
				}
				parkingSlots.add(nPark);
				
			}
		}
	}
	
	/**
	   * This method determines the initial State of the Airport and sorts the Flights added
	   * from the setup file.
	   * The Flights can be either On Hold or Parked.
	   */
	private void setInitialState() {
		int Limit = fOnHold.size();
		for(int i=0; i<Limit; i++) {
			for(int j=0; j<parkingSlots.size(); j++) {
				if (!parkingSlots.get(j).checkState()) {
					continue;
				}
				if (parkingSlots.get(j).checkForFlight(fOnHold.peek())) {
					Flight plane = fOnHold.poll();
					plane.setState(flight_state.PARKED);
					parkingSlots.get(j).landFlight(plane);
					fParked.add(plane);
					logTimestamp("Flight "+plane.getID()+ " is stationed on gate "+ parkingSlots.get(j).getID());
					break;
				}
			}
		}
	}
	
	/**
	   * This method is in every tick of the simulation. It determines if any of the Flights
	   * can proceed to landing within a particular Gate.
	   * In addition it associates Flights with their Gate and
	   * Sets the Flight state to LANDING.
	   */
	public void checkForLanding() {
		for(int j=0; j<parkingSlots.size(); j++) {
			if (!parkingSlots.get(j).checkState()) {
				continue;
			}
			if (parkingSlots.get(j).checkForFlight(fOnHold.peek())) {
				Flight plane = fOnHold.poll();
				plane.setMinutesInState(0);
				plane.setState(flight_state.LANDING);
				parkingSlots.get(j).landFlight(plane);
				fLanding.add(plane);
				logTimestamp("Flight "+plane.getID()+ " proceeding to land on gate "+parkingSlots.get(j).getID());
				break;
			}
		}
	}
	
	/**
	   * This Method checks whether any Flights have completed their landing, switching their 
	   * state to Parked.
	   */
	private void parkAfterLand() {
		parkingSlots.forEach((temp)->{
			if (fLanding.peek() == null) {
				return;
			}
			if (temp.getFlight()!=null && temp.getFlight().getID() == fLanding.peek().getID()) {
				temp.getFlight().setMinutesInState(0);
				temp.getFlight().setState(flight_state.PARKED);
			}
		});
		Flight airuberdo = fLanding.poll();
		airuberdo.setMinutesInState(0);
		airuberdo.setState(flight_state.PARKED);
		fParked.add(airuberdo);
		logTimestamp("Flight "+airuberdo.getID()+ " succesfully landed");
	}
	
	/**
	   * This Method handles Flights within the LANDING state, determining when 
	   * parkAfterLand() should be executed for each Flight.
	   */
	private void landingTransition() {
		int Limit = fLanding.size();
		for(int i=0; i<Limit; i++){
			int county = fLanding.peek().getMinutesInState();
			if (fLanding.peek().getAirplaneType() == airship_type.SINGLE_MOTOR
					&& county ==6) {
				
				parkAfterLand();
				continue;
				
			}
			else if (fLanding.peek().getAirplaneType() == airship_type.TURBOPROP
					&& county == 4) {
				
				parkAfterLand();
				continue;
			}
			else if (fLanding.peek().getAirplaneType() == airship_type.JET
					 && county ==2) {
				parkAfterLand();
				continue;
			}
			else {
				Flight airberdu = fLanding.poll();
				airberdu.setMinutesInState(county+1);
				fLanding.add(airberdu); //Add the Airplane again at the end of the Landing Queue to be Checked in the next iteration.
				continue;
			}
		}
	}

	/**
	   * This method updates the Time until Departure time for every Flight in the System.
	   */
	private void updateAirplanesTimers() {
		fOnHold.forEach((flt)->{
			int nTime = flt.getMinutesToDepartment() -1;
			flt.setMinutesToDepartment(nTime);
		});
		fLanding.forEach((flt)->{
			int nTime = flt.getMinutesToDepartment() -1;
			flt.setMinutesToDepartment(nTime);
		});
		fParked.forEach((flt)->{
			int nTime = flt.getMinutesToDepartment() -1;
			flt.setMinutesToDepartment(nTime);
		});
	}
	
	// 0.001*percentage% Chance of Departing each minute
	/**
	   * This Method determines whether a given number is below a random number in the range from 0 to 100000
	   * It is used to determine whether a Flight should be departing.
	   * @param percentage The higher this number, the more chances the Flight to depart within this Minute.
	   * @return boolean True if the random number is less than the number given
	   */
	private boolean determiningSuccess(int percentage) {
		if (getRandom(0,100000) < percentage) {
			return true; 
		}
		else return false;
	}
	
	/**
	   * Based on a timeVariable, increase the chances of a flight departing.
	   * @param timeVariable Usually the Flight's time left until its planned departure time.
	   * @return boolean True if the Flight should depart.
	   */
	private boolean calculateDepart(int timeVariable) {
		if (timeVariable>=50) {
			//0.001% per minute to depart in that time
			return determiningSuccess(1);
		}
		else if (timeVariable < 50 && timeVariable >= 25) {
			//0.005% per minute to depart in that time
			return determiningSuccess(5);
		}
		else if (timeVariable < 25 && timeVariable >=10) {
			//1% per Minute to depart in that time
			return determiningSuccess(100);
		}
		else if (timeVariable <10 && timeVariable >= -10) {
			//4.5% per minute to depart in that time
			return determiningSuccess(1000);
		}
		else if (timeVariable <-10 && timeVariable >= -25) {
			//5.5% per minute to depart in that time
			return determiningSuccess(5500);
		}
		else if (timeVariable <-25) {
			//10% per minute to depart in that time.
			return determiningSuccess(10000);
		}
		return false;
	}
	
	/**
	   * This method is used to decide which Flights should depart from the Airport.
	   * For the Flights Departing, the discounts and extra costs are calculated alongside the
	   * earnings
	   */
	private void updateDeparts() {
		if (fParked.isEmpty()) {
			return ;
		}
		fParked.forEach((flt)->{
			int time_to_official_Departing = flt.getMinutesToDepartment();
			int priceModifier= 0;
			int discountModifier =0;
			int stayCost=0;
			if (calculateDepart(time_to_official_Departing)) {
				for(int j=0; j<flt.special_treatment.size(); j++) {
					if (flt.special_treatment.get(j) == special_services.REFUEL) {
						priceModifier += 25;
					}
					else if (flt.special_treatment.get(j) == special_services.CLEANING) {
						priceModifier += 2;
					}
					else if (flt.special_treatment.get(j) == special_services.PASSENGER_TRANSIT) {
						priceModifier += 2;
					}
					else if (flt.special_treatment.get(j) == special_services.LOAD) {
						priceModifier += 5;
					}
				}
				if (time_to_official_Departing >= 25) {
					discountModifier += 20;
				}
				else if (time_to_official_Departing >= 10 &&
						 time_to_official_Departing <= 20) {
					discountModifier +=10;
				}
				else if (time_to_official_Departing <0) {
					//Every Delayed Flight pays Twice the fee for staying thus +100%
					priceModifier +=100;
				}
				
				String port ="???";
				for (int i =0; i<parkingSlots.size(); i++) {
					if (parkingSlots.get(i).getFlight()!= null && parkingSlots.get(i).getFlight().getID() == flt.getID()) {
						stayCost = parkingSlots.get(i).removeFlight();
						port =parkingSlots.get(i).getID();
						break;
					}
				}
				for (int i =0; i<parkingSlots.size(); i++) {
					if (fParked.get(i)!= null && fParked.get(i).getID() == flt.getID()) {
						fParked.set(i, null);
						break;
					}
				}
				//Calculating Gains:
				float gains = (float)stayCost*(1+ (float)priceModifier/100)*(1-(float)discountModifier/100);
				earnings += gains;
				logTimestamp("Flight: "+flt.getID()+" Departed from gate " +port+ " paying "+gains+"$");
	
			}
			
		});
		fParked.removeAll(Collections.singleton(null));
		
	}
	
	/**
	   * This method is used to adjust the Simulation Ticks time
	   * @param i The new Simulation tick to be used.
	   */
	public void setTick(int i) {
		simulation_tick = i;
	}
	
	/**
	   * For a Given Category name, this Method generates an ObservableList<String> object to
	   * be supplied to the GUI Controller.
	   * This Method is used once for each Category to provide String information about each
	   * Gate.
	   * @param cat0 String for the Input Category Name
	   * @return ObservableList<String> These are used in the Main Window.
	   */
	public ObservableList<String> getParkingPerCategory(String cat0){
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for (parking item : parkingSlots) {
		    if (item.getCategoryName()==cat0){
		    	objectas.add(item.getOutputString());
		    }
		}
		return objectas;
		
	}
	
	/**
	   * This Method generates an ObservableList<String> object, including 
	   * String with Information about Every Gate of the Airport.
	   * @return ObservableList<String> These are used in the Gates Pop-up Window.
	   */
	public ObservableList<String> getFullParking(){
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for (parking item : parkingSlots) {
		    objectas.add(item.getCategoryName()+" "+item.getOutputString());
		}
		return objectas;
		
	}
	
	/**
	   * This Method generates an ObservableList<String> object, including 
	   * String with Information about Every Flight Registered to the System.
	   * @return ObservableList<String> These are used in the Flights Pop-up Window.
	   */
	public ObservableList<String> getAllFlights(){
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for (Flight flt :fOnHold) {
			String first = flt.getOutputString()+" | Departure Time: ";
			int departureTimeMinutes = totalTime +flt.getMinutesToDepartment();
			objectas.add(first+ timeFormat(departureTimeMinutes));
		}
		for (Flight flt :fLanding) {
			String first = flt.getOutputString()+" | Departure Time: ";
			int departureTimeMinutes = totalTime +flt.getMinutesToDepartment();
			objectas.add(first+ timeFormat(departureTimeMinutes));
		}
		for (Flight flt :fParked) {
			String first = flt.getOutputString()+" | Departure Time: ";
			int departureTimeMinutes = totalTime +flt.getMinutesToDepartment();
			objectas.add(first+ timeFormat(departureTimeMinutes));
		}
		return objectas;
	}
	
	/**
	   * This Method generates an ObservableList<String> object, including 
	   * String with Information about delayed Flight registered on the System.
	   * @return ObservableList<String> These are used in the Delayed Pop-up Window.
	   */
	public ObservableList<String> getAllDelays(){
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for (parking park : parkingSlots) {
			if(park.getFlight()!=null) {
				int depTime = park.getFlight().getMinutesToDepartment();
				if(depTime<0) {
					Flight flt = park.getFlight();
					String first = flt.getOutputSimpleString()+" | Departure Time: ";
					int departureTimeMinutes = totalTime +flt.getMinutesToDepartment();
					objectas.add(first+ timeFormat(departureTimeMinutes));
					
				}
			}
		}
		return objectas;
	}
	
	/**
	   * This Method generates an ObservableList<String> object, including 
	   * String with Information about Every Flight currently On Hold.
	   * @return ObservableList<String> These are used in the Holding Pop-up Window.
	   */
	public ObservableList<String> getAllHolding(){
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for (Flight flt :fOnHold) {
			String first = flt.getOutputSimpleString()+" | Departure Time: ";
			int departureTimeMinutes = flt.getRegistrationTime();
			objectas.add(first+ timeFormat(departureTimeMinutes));
		}
		return objectas;
	}
	
	/**
	   * This Method generates an ObservableList<String> object, including 
	   * String with Information about Every Flight with a Planned Departure within 10 Minutes.
	   * @return ObservableList<String> These are used in the nDeparts Pop-up Window.
	   */
	public ObservableList<String> getAllNextDepartures() {
		ObservableList<String> objectas = FXCollections.observableArrayList();
		for(Flight flt : fParked) {
			if (flt.minutesUntilPlannedDeparting <= 10 && flt.minutesUntilPlannedDeparting >0) {
				String first = flt.getOutputSimpleString();
				objectas.add(first);
			}
		}
		return objectas;
	}
	
	/**
	   * This Method turns an integer into a time format of HH:MM
	   * @param timeInMinutes The time to be converted to HH:MM
	   * @return String HH:MM -> Hours:Minutes
	   */
	private String timeFormat(int timeInMinutes) {
		String hours = String.valueOf(timeInMinutes/60);
		int minutes = timeInMinutes%60;
		String minStr;
		if (minutes<10) {
			minStr = "0"+minutes;
		}
		else {
			minStr = String.valueOf(minutes);
		}
		return hours+":"+minStr ;
	}
	
	/**
	   * This Method gets all the parking Objects (Gates) of the Airport.
	   * @return List<parking> Returns all Gates of the Airport
	   */
	public List<parking> getGates(){
		return parkingSlots;
	}
	
	/**
	   * This Method is used to add a new Flight to the System.
	   */
	public void addFlight(Flight flubber) {
		fOnHold.add(flubber);
	}
	
	/**
	   * This method is used to reset the Lists of the Simulation, 
	   * during initialisation or after the Scenario has changed.
	   */
	private void resetLists() {
		fOnHold = new LinkedList<Flight>();
		fLanding = new LinkedList<Flight>();
		fParked = new LinkedList<Flight>();
		parkingSlots = new LinkedList<parking>();
		loggedMessages = new LinkedList<String>();
	}
	
	/**
	   * This Method re-initialises the Simulation's parameters 
	   * based on a given scenario.
	   * @param scenario The new scenario to change to.
	   */
	public void changeScenario(int scenario) {
		earnings = 0;
		scenario_id = scenario;
		
		startTime = System.currentTimeMillis(); 
		totalTime = 0;
		
		System.out.println(loggedMessages);
		port_category_0 = new cat_gate();
		port_category_1 = new cat_gate_c();
		port_category_2 = new cat_zone_A();
		port_category_3 = new cat_zone_B();
		port_category_4 = new cat_zone_C();
		port_category_5 = new cat_general();
		port_category_6 = new cat_long();
		resetLists();
		loggedMessages.add("_EXECUTEORDER66");
		airport= new Airport(scenario_id);
		setup(scenario_id);
		initialiseParking();
		setInitialState();
		
	}
	
	/**
	   * This is the Public Constructor of the simulation.
	   * It is used to Initialize it within the Main
	   * @param scenario The scenario to start with.
	   */
	public simulation(int scenario) {
		earnings = 0;
		scenario_id = scenario;
		
		startTime = System.currentTimeMillis(); 
		totalTime = 0;
		port_category_0 = new cat_gate();
		port_category_1 = new cat_gate_c();
		port_category_2 = new cat_zone_A();
		port_category_3 = new cat_zone_B();
		port_category_4 = new cat_zone_C();
		port_category_5 = new cat_general();
		port_category_6 = new cat_long();
		resetLists();
		
		airport= new Airport(scenario_id);
		setup(scenario_id);
		initialiseParking();
		setInitialState();
		
		instance = this;
	}
	
	/**
	   * The Private Constructor of the simulation.
	   * It is used for creating the Singleton Pattern,
	   * and allowing access of the simulation from every Class
	   * of the Application, without needing to create a new object.
	   */
	private simulation() {
		
	}
	private static simulation instance;
	
	/**
	   * This method allows accessing the simulation from every
	   * class of the application, without creating unwanted copies of the instance.
	   */
	public static simulation getInstance() {
		return instance == null ? instance = new simulation() : instance;
	}
	
	//Getters
	/**
	   * Getter for the Earnings.
	   * @return float The total earnings of the current simulation.
	   */
	public float getEarnings() {
		return earnings;
	}
	
	/**
	   * Getter for the Total Time.
	   * The total time the simulation has been running in simulation ticks.
	   * @return int
	   */
	public int getTotalTime() {
		return totalTime;
	}
	
	/**
	   * Getter for the Scenario ID.
	   * @return int
	   */
	public int getScenarioID() {
		return scenario_id;
	}
	
	/**
	   * Getter Free Number of Gates
	   * @return int
	   */
	public int getFreeSlotsNum() {
		int counter =0;
		for (parking item : parkingSlots) {
		    if (item.checkState()){
		    	counter++;
		    }
		}
		return counter;
	}
	
	/**
	   * Getter total number of Flights on Hold.
	   * @return int
	   */
	public int getFlightsOnHold() {
		return fOnHold.size();
	}
	
	//Update Function for triggering "Turn" operations at the end of each Simulation tick.
	/**
	   * Update Function triggered at the end of each Simulation Tick
	   */
	private void update() {
		//Increase Timer 
		totalTime++;
		
		//Calculate whether any Airplanes get to complete their landing.
		landingTransition();
		
		//Reduce Timer within each Airplane.
		updateAirplanesTimers();
		
		//Determine if any of the Parked Airplanes departs.
		updateDeparts();
		
		//For any Free Parking Slot Check whether there is an airplane that needs landing
		checkForLanding();
		
	}
	
	/**
	   * This method allows setting the cross_thread_command variable.
	   * This variable is used for tweaking the simulation's execution
	   * @param c The cross_thread_command variable
	   */
	public void setCrossThreadCommand(int c) {
		cross_thread_command = c;
		//c = 1 to Complete Tick. 
		//c = 0 to Prevent Tick.
	}
	
	//stopwatch For triggering Update Function every 5 seconds.
	//By setting simulation_tick_in_ms we could increase/decrease the simulation speed.
	/**
	   * This method is the engine of the Simulation. It determines how much time passes IRL
	   * defining when a new Update should occur.
	   * @return boolean True if it's time for an update. False if it's not time yet.
	   */
	public boolean stopwatch() {
		countTime = System.currentTimeMillis();
		long timeElapsed = countTime -startTime;
		if (timeElapsed >= simulation_tick ||cross_thread_command ==1) {
			update();
			//Reseting counters to currentTime to initiate counting again.
			startTime = System.currentTimeMillis();
			countTime = System.currentTimeMillis();
			instance = this;
			return true;
		}
		instance = this;
		return false;
	}
	
	/**
	   * This method counts how many Flights depart within 10 minutes
	   * @return int The number of Flights departing within 10 minutes.
	   */
	public int findNextDepartures() {
		int counter =0;
		for(Flight fl : fParked) {
			if (fl.minutesUntilPlannedDeparting <= 10 && fl.minutesUntilPlannedDeparting >0) {
				counter++;
			}
		}
		return counter;
		
	}
	
	/**
	   * This method adds a new String message within LoggedMessages Queue<String>
	   * With every update, the Logged Messages are transferred to the Main Window,
	   * where they are logged within a Virtual Console.
	   * @param s

	   */
	private void logTimestamp(String s) {
		String hours = String.valueOf(totalTime/60);
		int minutes = totalTime%60;
		String minStr;
		if (minutes<10) {
			minStr = "0"+minutes;
		}
		else {
			minStr = String.valueOf(minutes);
		}
		String timestamp = hours+":"+minStr;
		String logMessage = timestamp+" |"+s;
		loggedMessages.add(logMessage);
	}
	/**
	   * Getter Method for the loggedMessages
	   * @return Queue<String>
	   */
	public Queue<String> getMessagesLog(){
		return loggedMessages;
	}
	
	/**
	   *This Method Clears the Logged Messages.
	   */
	public void clearMessagesLog(){
		loggedMessages.clear();
	}
	
	//ID Generator for Airplane
	/**
	   * This method checks whether a given String exists as an
	   * ID within one of the registered Flights currently in the system.
	   * @param aid The String to be checked
	   * @return boolean True if the String is unique.
	   */
	public boolean checkAirplaneIDUniqueness(String aid) {
		try {
		fOnHold.forEach((flt)->{
			if (flt.getID() == aid) {
				throw new ArithmeticException("418");
			}
		});
		
		fLanding.forEach((flt)->{
			if (flt.getID() == aid) {
				throw new ArithmeticException("418");
			}
		});
		
		fParked.forEach((flt)->{
			if (flt.getID() == aid) {
				throw new ArithmeticException("418");
			}
		});
		
		}
		catch (ArithmeticException e) {
			if (e.getMessage() == "418")
			{
				return false;
			}
		}
		return true;

	}
	/**
	   * This method generates a Unique ID to be used for creating a new
	   * Flight.
	   * @return String
	   */
	public String airplaneIDGenerator() {
		String pompaLinesNewName = String.valueOf(getRandomLetter()) + String.valueOf(getRandom(0,200));
		while (!checkAirplaneIDUniqueness(pompaLinesNewName)) {
			pompaLinesNewName = String.valueOf(getRandomLetter()) + String.valueOf(getRandom(0,200));
		}
		return pompaLinesNewName;
	}
	
	
	
	//Random Number Generation between min and max value
	/**
	   * This method generates a random number between the numbers
	   * min and max.
	   * @param min Lowest value possible
	   * @param max Highest value possible
	   * @return int Random Number between min and max.
	   */
	private static int getRandom(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	//Random Letter Generation 
	/**
	   * This method generates a random small-case character.
	   * @return char
	   */
	private static char getRandomLetter() {
		Random r = new Random();
		char c = (char)(r.nextInt(26) + 'a');
		return c;
		
	}
	
	
	
}
