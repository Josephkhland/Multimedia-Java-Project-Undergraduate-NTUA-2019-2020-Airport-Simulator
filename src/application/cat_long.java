package application;

public class cat_long extends Category {
	
	public cat_long() {
		categoryName = "Long Duration";
		flight_types_allowed.add(flight_type.COMMERCIAL);
		flight_types_allowed.add(flight_type.PRIVATE);
		airship_types_allowed.add(airship_type.TURBOPROP);
		airship_types_allowed.add(airship_type.JET);
		airship_types_allowed.add(airship_type.SINGLE_MOTOR);
		special_services_provided.add(special_services.CLEANING);
		special_services_provided.add(special_services.LOAD);
		special_services_provided.add(special_services.PASSENGER_TRANSIT);
		special_services_provided.add(special_services.REFUEL);
		maxStayTime = 600; //Time in Simulation Minutes
	}

}
