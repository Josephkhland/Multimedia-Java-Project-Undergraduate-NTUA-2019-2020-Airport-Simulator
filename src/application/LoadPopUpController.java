package application;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class LoadPopUpController {

	@FXML 
	ComboBox<String> scenario_combobox;
	
	ObservableList<String> availableScenarios=FXCollections.observableArrayList();
	
	/**
	   * Constructor of the Load Pop Up Menu
	   */
	public LoadPopUpController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	   * This is method is run to initialize the contents of the
	   * scenario_combobox.
	   */
	@FXML protected void initter() {
		filesChecker();
		scenario_combobox.setItems(availableScenarios);
	}
	
	/**
	   * This method changes the Scenario for the Simulation,
	   * based on the Scenario_id selected from the scenario_combobox.
	   * It creates an Alert when no scenario_id is selected.
	   */
	@FXML protected void scenario_load_action() {
		if(scenario_combobox.getSelectionModel().isEmpty())
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information Dialog");
			alert.setHeaderText("No Scenario Selected");
			alert.setContentText("You need to select a Scenario before you can load it.");

			alert.showAndWait();
		}
		else
		{
			int scenarioChoice = Integer.parseInt(scenario_combobox.getValue().trim());
			simulation.getInstance().changeScenario(scenarioChoice);
			Stage stage = (Stage) scenario_combobox.getScene().getWindow();
			stage.close();
		}
	}
	
	/**
	   * This method checks the directory "medialab" for all the possible Scenarios.
	   * For every pair of airport_id.txt file and setup_id.txt, it extends the contents of 
	   * the scenario_combobox.
	   */
	private void filesChecker() {
		File directoryPath = new File("medialab");
		List<String> setupfiles = new LinkedList<String>();
		List<String> airportfiles = new LinkedList<String>();
		//List all files and directories
		for (File file : directoryPath.listFiles()) {
			if (file == null) break;
			if (file.getName().startsWith("airport_")&& file.getName().endsWith(".txt")) {
				String[] temp =file.getName().split("_");
				String str = temp[1];
				String airportID = str.substring(0, str.lastIndexOf('.'));
				airportfiles.add(airportID);
			}
			if (file.getName().startsWith("setup_")&& file.getName().endsWith(".txt")) {
				String[] temp =file.getName().split("_");
				String str = temp[1];
				String setupID = str.substring(0, str.lastIndexOf('.'));
				setupfiles.add(setupID);
			}
		}
		for (String index : setupfiles) {
			if (index == null) break;
			if (airportfiles.contains(index)) {
				if (!availableScenarios.contains(index)) {
					availableScenarios.add(index);
				}
			}
		}
		
		return;
	}
	
	

}
