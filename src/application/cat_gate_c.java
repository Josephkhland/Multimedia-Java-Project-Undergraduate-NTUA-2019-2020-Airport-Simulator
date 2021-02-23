package application;

public class cat_gate_c extends Category {

	public cat_gate_c() {
		categoryName = "Commercial Gate";
		flight_types_allowed.add(flight_type.COMMERCIAL);
		airship_types_allowed.add(airship_type.TURBOPROP);
		airship_types_allowed.add(airship_type.JET);
		special_services_provided.add(special_services.CLEANING);
		special_services_provided.add(special_services.LOAD);
		special_services_provided.add(special_services.PASSENGER_TRANSIT);
		special_services_provided.add(special_services.REFUEL);
		maxStayTime = 90; //Time in Simulation Minutes
	}
}
