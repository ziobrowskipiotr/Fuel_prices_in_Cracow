package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class FuelPriceScraper {

	private static final Logger projectLogger = LogManager.getLogger(FuelPriceScraper.class);
	
	public Map<String, Double> getFuelPrices() throws IOException {
	    Map<String, Double> prices = new HashMap<>();
	    String url = "https://mapa.nocowanie.pl/krakow/ceny_paliw/";
	    try {
	    	Connection.Response response = Jsoup.connect(url).execute();
		    if (response.statusCode() == 200) {
		        // Połączenie z URL i pobranie danych
		        Document doc = Jsoup.connect(url).timeout(300).get();
		        Elements fuelPrices = doc.select("ul.block-list li");
	
		        // Parsowanie danych
		        for (Element priceElement : fuelPrices) {
		            String fullText = priceElement.text();
		            String fuelType = priceElement.select("span.tlight.orange").text().replace(":", "").trim();
		            String priceString = fullText.split(" ")[1];
	
		            if (fuelType.equals("LPG")) {
		                priceString = priceString.substring(0, priceString.length());
		            }
	
		            double price = Double.parseDouble(priceString);
		            prices.put(fuelType, price);
		        }
	
		        // Zapis danych do pliku CSV, jeśli są jakieś dane
		        if (!prices.isEmpty()) {
		            saveDataToCSV(prices, "fuel_prices.csv");
		            projectLogger.info("Data was saved in csv file");
		        }
		    }
	    } catch (Exception e) {
	        // Obsługa braku połączenia internetowego
	    	prices = readDataFromCSV("fuel_prices.csv");
	        projectLogger.info("Internet connection failed. Reading fuel prices from CSV.");
	    }
	    return prices;
	}




    public void saveDataToCSV(Map<String, Double> data, String filename) {
        try (PrintWriter writer = new PrintWriter(new File(filename))) {
            for (Map.Entry<String, Double> entry : data.entrySet()) {
                writer.println(entry.getKey() + "," + entry.getValue());
            }
        } catch (FileNotFoundException e) {
            projectLogger.error("An error occurred while saving fuel prices to CSV", e);
        }
    }
    
    private Map<String, Double> readDataFromCSV(String filename) {
        Map<String, Double> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.put(values[0], Double.parseDouble(values[1]));
            }
        } catch (IOException e) {
            projectLogger.error("An error occurred while reading fuel prices from CSV", e);
        }
        return data;
    }
}

