package application;

import java.util.*;

public class Flight {
	protected String id;
	protected String originCity;
	protected flight_type f_type;
	protected airship_type a_type;
	protected flight_state state;
	protected int minutesUntilPlannedDeparting;
	protected int minutesInCurrentState;
	protected List<special_services> special_treatment;
	protected int registrationTime;
	
	
	//Constructors
	/**
	   * This method is the Constructor of an Object Type Flight.
	   * These objects can be created by the user or during initialization
	   * of the simulation.
	   * @param newID This is a Unique ID given to the Flight Object.
	   * @param oCity This is the Origin/Destination of the Flight.
	   * @param flightType This defines the flight type.
	   * @param planeType This defines the airplane type.
	   * @param minutes This is the time left until the Flight is planned to depart.
	   * @param regist This is the time when the Flight is registered to the system.
	   */
	public Flight(String newID, String oCity, int flightType, int planeType, int minutes, int regist){
		id = newID;
		originCity = oCity;
		if (flightType ==1) {
			f_type = flight_type.PASSENGER;
		}
		else if (flightType ==2) {
			f_type = flight_type.COMMERCIAL;
		}
		else if (flightType ==3) {
			f_type = flight_type.PRIVATE;
		}
		
		if (planeType == 1) {
			a_type = airship_type.SINGLE_MOTOR;
		}
		else if (planeType == 2) {
			a_type = airship_type.TURBOPROP;
		}
		else if (planeType == 3) {
			a_type = airship_type.JET;
		}
		
		minutesUntilPlannedDeparting = minutes;
		minutesInCurrentState = 0;
		state = flight_state.HOLDING;
		special_treatment = new LinkedList<special_services>();
		registrationTime = regist;
	}
	
	//Getters
	/**
	   * This method gets the Flight's Unique ID.
	   * @return String
	   */
	public String getID(){
		return id;
	}
	/**
	   * This method gets the Flight's Origin/Destination.
	   * @return String
	   */
	public String getOrigin() {
		return originCity;
	}
	/**
	   * This method gets the Flight's Type.
	   * @return flight_type
	   * @see flight_type
	   */
	public flight_type getFlightType() {
		return f_type;
	}
	
	/**
	   * This method gets the Flight's Airplane Type.
	   * @return airship_type
	   * @see airship_type
	   */
	public airship_type getAirplaneType() {
		return a_type;
	}
	
	/**
	   * This method gets the Flight's state (HOLDING, LANDING, PARKED).
	   * @return flight_state
	   * @see flight_state
	   */
	public flight_state getState() {
		return state;
	}
	
	/**
	   * This method gets the Flight's Time left until Planned Departure.
	   * @return int
	   */
	public int getMinutesToDepartment() {
		return minutesUntilPlannedDeparting;
	}
	
	/**
	   * This method gets the Flight's Time that has passed within the Current State.
	   * @return int
	   */
	public int getMinutesInState() {
		return minutesInCurrentState;
	}
	
	/**
	   * This creates a String with the Flight's information, to be outputed to the
	   * Flights pop-up window.
	   * @return String "ID | Origin/Destination | Flight Type | Airplane Type | State | (Gate) | Special Services"
	   */
	public String getOutputString() {
		String first ="ID: "+id+ " | Origin/Destination: " + originCity + " | Flight Type: "+ f_type + " | Airplane Type: " + a_type +" | State: "+state;
		if (state != flight_state.HOLDING) {
			List<parking> gates = simulation.getInstance().getGates();
			for(parking gate : gates) {
				if (gate.checkForFlight(this)) {
					first = first + " | Gate: "+ gate.getID();
					break;
				}
			}
		}
		if (special_treatment.isEmpty()) first = first + " | Special Services: NONE";
		else {
			first = first + " | Special Services: ";
			if (special_treatment.contains(special_services.CLEANING)) first = first + "C";
			if (special_treatment.contains(special_services.LOAD)) first = first + "L";
			if (special_treatment.contains(special_services.REFUEL)) first = first + "R";
			if (special_treatment.contains(special_services.PASSENGER_TRANSIT)) first = first + "P";
		}
		
		return first; 
	}
	
	/**
	   * This creates a String with the Flight's information, to be outputed to the
	   * Delayed pop-up window, the Flights on Hold Pop-up Menu, the Next Departures pop up menu.
	   * The String is shorter and simpler than in method getOutputString()
	   * @return String "ID | Flight Type | Airplane Type 
	   */
	public String getOutputSimpleString() {
		return "ID: "+id+" | Flight Type: "+ f_type + " | Airplane Type: " + a_type;
	}
	
	/**
	   * Returns the Registration Time of the Flight.
	   * @return int
	   */
	public int getRegistrationTime() {
		return registrationTime;
	}
	
	//Checkers
	/**
	   * Method for checking whether a Special Service is included for the Flight.
	   * @return Boolean True if the service is contained.
	   */
	public boolean checkSpecialServiceRequirement(special_services service) {
		return special_treatment.contains(service);
	}
	
	//Setters
	/**
	   * This adds a Special Service to the Flight.
	   * @param service Service to be added.
	   * @see special_services
	   */
	public void addSpecialTreatment(special_services service) {
		if (!special_treatment.contains(service)) {
			special_treatment.add(service);
		}
	}
	
	/**
	   * Setter Method for the Time Left until Departure of the Flight.
	   * @param time The new Time left until departure.
	   */
	public void setMinutesToDepartment(int time) {
		minutesUntilPlannedDeparting = time;
	}
	
	/**
	   * This Method reduces the Time Left until Departure by 1 Minute.
	   */
	public void progressMinutes() {
		minutesUntilPlannedDeparting = minutesUntilPlannedDeparting-1;
	}
	
	/**
	   * This method sets the State of the Flight
	   * @param sta The flight_state to set to the Flight.
	   */
	public void setState(flight_state sta) {
		state = sta;
	}
	
	/**
	   * Sets the Minutes spent in the Current State for the Flight
	   * @param min 
	   */
	public void setMinutesInState(int min) {
		minutesInCurrentState = min;
	}
	
	
	
}
