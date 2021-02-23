package application;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class GatesController {

	@FXML
	private ListView<String> gatesList;
	
	public void updateGates(ObservableList<String> contents) {
		gatesList.setItems(contents);
	}
	
	public GatesController() {
		// TODO Auto-generated constructor stub
	}

}
