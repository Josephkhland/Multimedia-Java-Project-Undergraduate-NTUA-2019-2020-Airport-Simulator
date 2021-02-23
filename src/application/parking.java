package application;


public class parking {
	//Unique ID
	protected String id;
	
	//State of the Parking Slot
	protected parking_state state;
	
	//Category of the Parking Slot
	//Need to make a Category Class Object here of the appropriate subclass.
	protected Category category;
	
	//Max Time - That an airplane is allowed to stay on the Parking slot.
	protected int time;
	
	// Usage Cost -
	protected int usageCost;
	
	//Flight Entity - The airplane that is parked within.
	private Flight airplane_entity;
	
	//CONSTRUCTOR
	/**
	   * This method is the Constructor of an Object Type Parking.
	   * These objects are used for making Gates for the Airport.
	   * @param givenID This is the ID to be given to the instance.
	   * @param cat This is the Category of the Gate.
	   * @param st This is the current state of the Gate.
	   * @param cost This is the usage cost for a Flight Sorted in this object.
	   */
	public parking(String givenID, Category cat, parking_state st, int cost) {
		id = givenID;
		category = cat;
		state = st;
		time = category.getMaxStayTime();
		usageCost = cost;
		airplane_entity=null;
	
	}
	
	//Functions
	// Checks whether the Parking Slot is available for landing.
	
	/**
	   * This method checks the State of the Instance.
	   * @return boolean This returns True if the Instance has FREE state.
	   */
	public boolean checkState() {
		if (state == parking_state.FREE)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
	/**
	   * This method checks whether a given flight is eligible to
	   * dock within this Gate instance.
	   * @param f This is the Flight Instance that is checked.
	   * @return boolean If this Gate Instance can accomodate the Flight it returns True. False if it can't or if the Flight is null.
	   */
	public boolean checkForFlight(Flight f) {
		if (f == null) return false;
		if ( checkState()
				&&category.checkFlightType(f.getFlightType())
				&& category.checkAirshipType(f.getAirplaneType())
				&& category.checkSpecialService(f.special_treatment) 
				&& time >= f.getMinutesToDepartment()) {
			return true;
		}
		return false;
	}
	
	
	/**
	   * This is used to associate a Flight with this Gate Instance.
	   * In addition it alters the state of this Gate Instance to OCCUPIED.
	   * @param f The Flight Object to be associated with this Gate Instance.
	   */
	public void landFlight(Flight f) {
		airplane_entity = f;
		state = parking_state.OCCUPIED;
	}
	
	/**
	   * This is used to remove any Flight associated with this Gate Instance.
	   * In addition it alters the state of this Gate Instance to Free.
	   * @return int Returns the Usage Cost of this Gate Instance.
	   */
	public int removeFlight() {
		airplane_entity = null;
		state = parking_state.FREE;
		return usageCost;
	}
	
	//Setters
	/**
	   * This Function is used to Set the State of this Gate Instance.
	   * @param newState The state to be given in this Gate Instance's state variable.
	   */
	public void setState(parking_state newState) {
		state = newState;
	}
	
	
	
	//Getters
	/**
	   * Getter Function for returning the usage cost of this Gate Instance.
	   * @return int Returns the base usage cost for this Gate.
	   */
	public int getCost() {
		return usageCost;
	}
	
	/**
	   * Getter Function for returning the Maximum Stay time in this Gate.
	   * @return int Returns the Maximum Stay time for this Gate.
	   */
	public int getMaxStayTime() {
		return time;
	}
	
	
	/**
	   * Getter Function for returning the unique ID of this Gate Instance.
	   * @return String The unique ID of this Gate Instance.
	   */
	public String getID(){
		return id;
	}
	
	/**
	   * Getter Function for returning the Category of this Gate.
	   * @return Category Returns the Category object of this Gate.
	   */
	public Category getCategory() {
		return category;
	}
	
	/**
	   * Getter Function for returning the Category Name of this Gate's Category.
	   * @return String Returns Category Name.
	   */
	public String getCategoryName() {
		return category.getCategoryName();
	}
	
	/**
	   * Getter Function for returning the Flight Object associated with this Gate.
	   * @return Flight Returns Flight Object or null
	   */
	public Flight getFlight() {
		return airplane_entity;
	}
	
	/**
	   * Function for Creating concating String with the Gate's Information
	   * String created through here are within Lists in the Mainwindow.
	   * @return String Returns a String of the Following Format "gateID: state | flightID: flightOrigin |Delayed by/Departing in flightTimeToDepartment Minutes".
	   */
	public String getOutputString() {
		String stateos;
		if(checkState()) {
			stateos = "FREE";
		}
		else
		{
			if (airplane_entity.getMinutesToDepartment()<0) {
				stateos = "OCCUPIED | " + airplane_entity.getID() + ": "+airplane_entity.getOrigin()+ "| Delayed by "+ String.valueOf(-(airplane_entity.getMinutesToDepartment()))+" Minutes";
			}
			else {
				stateos = "OCCUPIED | " + airplane_entity.getID() + ": "+airplane_entity.getOrigin()+ "| Departing in "+ String.valueOf(airplane_entity.getMinutesToDepartment())+ " Minutes";
		
			}
		}
		return id +": " + stateos; 
	}
	
	

}
