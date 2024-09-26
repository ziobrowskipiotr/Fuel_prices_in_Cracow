package application;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.*;


public class MyController {

	private static final Logger projectLogger = LogManager.getLogger(MyController.class);
    private Scene scene;
    private Stage stage;
    @FXML
    private ImageView imageView;
    @FXML
    private BarChart<String, Number> fuelPriceChart; // Upewnij się, że masz wykres słupkowy w Twoim pliku FXML z fx:id="fuelPriceChart"
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;

    
    public void switchToAll(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("All_petrol_stations.fxml"));
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
    public void switchToPb95(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Pb95.fxml"));
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
    public void switchToPb98(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Pb98.fxml"));
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
    
    public void switchToDiesel(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Diesel.fxml"));
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
    
    public void switchToLpg(ActionEvent event) throws IOException {
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("Lpg.fxml"));
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
    @FXML
    public void initialize() {
        // Pobieranie danych
    	try {
    		FuelPriceScraper scraper = new FuelPriceScraper();
        	Map<String, Double> fuelPrices = scraper.getFuelPrices();

        	// Konfiguracja osi Y
        	double maxPrice = Collections.max(fuelPrices.values());
        	yAxis.setAutoRanging(false);
        	yAxis.setLowerBound(0);
        	yAxis.setUpperBound(maxPrice + 1);

        	// Tworzenie serii danych
        	for (Map.Entry<String, Double> entry : fuelPrices.entrySet()) {
            	XYChart.Series<String, Number> series = new XYChart.Series<>();
            	series.setName(entry.getKey());
            	XYChart.Data<String, Number> data = new XYChart.Data<>(entry.getKey(), entry.getValue());
            	series.getData().add(data);
            	fuelPriceChart.getData().add(series);
        	}
    	}catch(Exception e) {
    		projectLogger.error("Error with setting data: ", e);
    	}
    }
}
