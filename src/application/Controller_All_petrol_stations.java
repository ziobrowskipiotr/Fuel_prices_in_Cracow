package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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

public class Controller_All_petrol_stations {
    @FXML
    private TableView<Data> tableView; // Upewnij się, że masz fx:id="tableView" w pliku FXML dla TableView
    @FXML
    private TableColumn<Data, String> nameColumn; // fx:id="nameColumn"
    @FXML
    private TableColumn<Data, String> addressColumn; // fx:id="addressColumn"
    @FXML
    private TableColumn<Data, String> brandColumn; // fx:id="brandColumn"
    @FXML
    private TableColumn<Data, String> pb95Column; // fx:id="pb95Column"
    @FXML
    private TableColumn<Data, String> pb98Column; // fx:id="pb98Column"
    @FXML
    private TableColumn<Data, String> dieselColumn; // fx:id="dieselColumn"
    @FXML
    private TableColumn<Data, String> lpgColumn; // fx:id="lpgColumn"
    
    @FXML
    private TableView<DataPb95> tableView1; // Upewnij się, że masz fx:id="tableView" w pliku FXML dla TableView
    @FXML
    private TableColumn<DataPb95, String> nameColumn1; // fx:id="nameColumn"
    @FXML
    private TableColumn<DataPb95, String> addressColumn1; // fx:id="addressColumn"
    @FXML
    private TableColumn<DataPb95, String> brandColumn1; // fx:id="brandColumn"
    @FXML
    private TableColumn<DataPb95, String> pb95Column1; // fx:id="pb95Column"
    
    @FXML
    private TableView<DataPb98> tableView2; // Upewnij się, że masz fx:id="tableView" w pliku FXML dla TableView
    @FXML
    private TableColumn<DataPb98, String> nameColumn2; // fx:id="nameColumn"
    @FXML
    private TableColumn<DataPb98, String> addressColumn2; // fx:id="addressColumn"
    @FXML
    private TableColumn<DataPb98, String> brandColumn2; // fx:id="brandColumn"
    @FXML
    private TableColumn<DataPb98, String> pb98Column2; // fx:id="pb95Column"
    
    @FXML
    private TableView<DataDiesel> tableView3; // Upewnij się, że masz fx:id="tableView" w pliku FXML dla TableView
    @FXML
    private TableColumn<DataDiesel, String> nameColumn3; // fx:id="nameColumn"
    @FXML
    private TableColumn<DataDiesel, String> addressColumn3; // fx:id="addressColumn"
    @FXML
    private TableColumn<DataDiesel, String> brandColumn3; // fx:id="brandColumn"
    @FXML
    private TableColumn<DataDiesel, String> dieselColumn3; // fx:id="pb95Column"
    
    @FXML
    private TableView<DataLpg> tableView4; // Upewnij się, że masz fx:id="tableView" w pliku FXML dla TableView
    @FXML
    private TableColumn<DataLpg, String> nameColumn4; // fx:id="nameColumn"
    @FXML
    private TableColumn<DataLpg, String> addressColumn4; // fx:id="addressColumn"
    @FXML
    private TableColumn<DataLpg, String> brandColumn4; // fx:id="brandColumn"
    @FXML
    private TableColumn<DataLpg, String> dieselColumn4; // fx:id="pb95Column"
    
    private static final Logger projectLogger = LogManager.getLogger(Controller_All_petrol_stations.class);
    private Scene scene;
    private Stage stage;
    
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
    
    public void initialize() {
        // Ustawianie sposobu wyświetlania danych dla każdej kolumny
    	nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        pb95Column.setCellValueFactory(cellData -> cellData.getValue().pb95Property());
        pb98Column.setCellValueFactory(cellData -> cellData.getValue().pb98Property());
        dieselColumn.setCellValueFactory(cellData -> cellData.getValue().dieselProperty());
        lpgColumn.setCellValueFactory(cellData -> cellData.getValue().lpgProperty());
        
        try {
        	for (ArrayList<String> row : WebScapper.all_data()) {
        		Data dataColumn = new Data(row.get(0), row.get(1), row.get(2), row.get(3),row.get(4), row.get(5), row.get(6));    // Log each row added to the TableView
        		tableView.getItems().add(dataColumn);
        	}
        }catch (Exception e) {
        	projectLogger.info("Internet connection failed, reading data from CSV.");
        	for (ArrayList<String> row : readDataFromCSV("scraped_data.csv")) {
                Data dataColumn = new Data(row.get(0), row.get(1), row.get(2), row.get(3),row.get(4), row.get(5), row.get(6));
                tableView.getItems().add(dataColumn);
        	}
        }
   }
    
    public void initializePb95() {
    	nameColumn1.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn1.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn1.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        pb95Column1.setCellValueFactory(cellData -> cellData.getValue().pb95Property());

        for (ArrayList<String> row : WebScapper.all_data_Pb95()) {
        	DataPb95 dataColumn = new DataPb95(row.get(0), row.get(1), row.get(2), row.get(3));
        	tableView1.getItems().add(dataColumn);
        }
   }
    public void initializePb98() {
    	nameColumn2.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn2.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn2.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        pb98Column2.setCellValueFactory(cellData -> cellData.getValue().pb98Property());

        for (ArrayList<String> row : WebScapper.all_data_Pb98()) {
        	DataPb98 dataColumn = new DataPb98(row.get(0), row.get(1), row.get(2), row.get(3));
        	tableView2.getItems().add(dataColumn);
        }
   }
    
    public void initializeDiesel() {
    	nameColumn3.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn3.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn3.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        dieselColumn3.setCellValueFactory(cellData -> cellData.getValue().dieselProperty());

        for (ArrayList<String> row : WebScapper.all_data_Diesel()) {
        	DataDiesel dataColumn = new DataDiesel(row.get(0), row.get(1), row.get(2), row.get(3));
        	tableView3.getItems().add(dataColumn);
        }
   }
    
    public void initializeLpg() {
    	nameColumn4.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        addressColumn4.setCellValueFactory(cellData -> cellData.getValue().addressProperty());
        brandColumn4.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
        dieselColumn4.setCellValueFactory(cellData -> cellData.getValue().lpgProperty());

        for (ArrayList<String> row : WebScapper.all_data_Diesel()) {
        	DataLpg dataColumn = new DataLpg(row.get(0), row.get(1), row.get(2), row.get(3));
        	tableView4.getItems().add(dataColumn);
        }
   }
    private ArrayList<ArrayList<String>> readDataFromCSV(String filename) {
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(new ArrayList<>(Arrays.asList(values)));
            }
        } catch (IOException e) {
            projectLogger.error("An error occurred while reading data from CSV", e);
        }
        return data;
    }

}