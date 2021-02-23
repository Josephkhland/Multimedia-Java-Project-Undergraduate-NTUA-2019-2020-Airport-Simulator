package application;

import java.util.*;

public class Category {
	
	//Name of the Category
	protected String categoryName;
			
	//Flight Types
	List<flight_type> flight_types_allowed = new ArrayList<flight_type>();
			
	//Airship Types
	List<airship_type> airship_types_allowed = new ArrayList<airship_type>();
			
	//Special Services
	List<special_services> special_services_provided = new ArrayList<special_services>();
	
	//Max Stay Time - Time will be measured in Simulation Minutes in this Variable.
	protected int maxStayTime;
	
	
	//Getters
	public String getCategoryName() {
		return categoryName;
	}
	
	public int getMaxStayTime() {
		return maxStayTime;
	}
	
	
	//Checkers
	public boolean checkFlightType(flight_type f_type) {
		return flight_types_allowed.contains(f_type);
	}
	
	public boolean checkAirshipType(airship_type a_type) {
		return airship_types_allowed.contains(a_type);
	}
	
	public boolean checkSpecialService(List<special_services> s_prov) {
		return special_services_provided.containsAll(s_prov);
	}
	
	
}
