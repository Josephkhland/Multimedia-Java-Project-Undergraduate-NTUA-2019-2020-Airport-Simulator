package application;

public class cat_general extends Category {
	
	public cat_general() {
		categoryName = "General Parking Space";
		flight_types_allowed.add(flight_type.PASSENGER);
		flight_types_allowed.add(flight_type.COMMERCIAL);
		flight_types_allowed.add(flight_type.PRIVATE);
		airship_types_allowed.add(airship_type.TURBOPROP);
		airship_types_allowed.add(airship_type.JET);
		airship_types_allowed.add(airship_type.SINGLE_MOTOR);
		special_services_provided.add(special_services.CLEANING);
		special_services_provided.add(special_services.REFUEL);
		maxStayTime = 240; //Time in Simulation Minutes
	}

}
