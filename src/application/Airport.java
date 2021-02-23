package application;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

/**
 * This Class is used to create the Airport Object
 * and save its information.
 */
public class Airport {
	//Initialising Variables
	//A Capacity for each Type of Parking Slot in the current Airport
	int[] capacity = new int[7]; 
	
	//Cost for parking in each Type of Parking Slot in current Airport
	int[] cost = new int [7];
	
	//Unique Tag to be attached on each  Parking Slot of each given Type in current Airport
	String[] idConcat = new String[7];
	
	/**
	   * This method is the Constructor of an Object Type Airport.
	   * It reads through an input file to fetch the information
	   * about the Airport.
	   * @param scenario_id Based on the Given integer value, a different file is selected
	   * @throws IOEXCEPTION In case there is no file found in location ./medialab/airport_scenario-id.txt
	   */
	public Airport(int scenario_id) {
		for (int i=0;i<7;i++) {
			capacity[i] = 0;
			cost[i] = 0;
			idConcat[i] = null;
		}
		try {
		String filename = "./medialab/airport_" + String.valueOf(scenario_id) +".txt";
		File myScenario = new File(filename);
		Scanner myReader =new Scanner(myScenario);
		while (myReader.hasNext()) {
			String data = myReader.nextLine();
			String[] lineData = data.split(",");
			int Type = Integer.parseInt(lineData[0]) -1;
			capacity[Type]= Integer.parseInt(lineData[1]);
			cost[Type]= Integer.parseInt(lineData[2]);
			idConcat[Type] = lineData[3].trim();
		}
		myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not Found.");
			e.printStackTrace();
		}
	}
}
