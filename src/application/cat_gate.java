package application;

public class cat_gate extends Category {
	
	public cat_gate() {
		categoryName = "Gate";
		flight_types_allowed.add(flight_type.PASSENGER);
		airship_types_allowed.add(airship_type.TURBOPROP);
		airship_types_allowed.add(airship_type.JET);
		special_services_provided.add(special_services.CLEANING);
		special_services_provided.add(special_services.LOAD);
		special_services_provided.add(special_services.PASSENGER_TRANSIT);
		special_services_provided.add(special_services.REFUEL);
		maxStayTime = 45; //Time in Simulation Minutes
	}

}
