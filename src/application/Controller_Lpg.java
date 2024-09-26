package application;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class Controller_Lpg {
    
    @FXML
    private TableView<DataLpg> tableView1;
    @FXML
    private TableColumn<DataLpg, String> nameColumn1;
    @FXML
    private TableColumn<DataLpg, String> addressColumn1;
    @FXML
    private TableColumn<DataLpg, String> brandColumn1;
    @FXML
    private TableColumn<DataLpg, String> lpgColumn1;
   
    private static final Logger projectLogger = LogManager.getLogger(Controller_Pb95.class);
    private Scene scene;
    private Stage stage;
    
    public void initialize() {
    	nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn1.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn1.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        lpgColumn1.setCellValueFactory(cellData -> cellData.getValue().lpgProperty());

        for (ArrayList<String> row : WebScapper.all_data_Lpg()) {
        	DataLpg dataColumn = new DataLpg(row.get(0), row.get(1), row.get(2), row.get(3));
        	tableView1.getItems().add(dataColumn);
        }
   }
    
    public void switchToMain1(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Main5.fxml"));
    		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    		scene = new Scene(root, 1792, 1024);
    		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
    		stage.setScene(scene);
    		stage.show();
    		projectLogger.info("Successful scene change");
    	} catch (Exception e) {
    		projectLogger.warn("An error occured: ", e);
    	}
    }
}