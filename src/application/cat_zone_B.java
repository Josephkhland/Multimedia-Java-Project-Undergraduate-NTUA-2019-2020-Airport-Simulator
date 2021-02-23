package application;

public class cat_zone_B extends Category {

	public cat_zone_B() {
		categoryName = "Zone B";
		flight_types_allowed.add(flight_type.PASSENGER);
		flight_types_allowed.add(flight_type.COMMERCIAL);
		flight_types_allowed.add(flight_type.PRIVATE);
		airship_types_allowed.add(airship_type.TURBOPROP);
		airship_types_allowed.add(airship_type.JET);
		special_services_provided.add(special_services.CLEANING);
		special_services_provided.add(special_services.LOAD);
		special_services_provided.add(special_services.PASSENGER_TRANSIT);
		special_services_provided.add(special_services.REFUEL);
		maxStayTime = 120; //Time in Simulation Minutes
	}
}
