package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WebScapper {
	
	private static final Logger projectLogger = LogManager.getLogger(WebScapper.class);
	
	public static ArrayList<ArrayList<String>> all_data() {
		projectLogger.info("Starting all_data method.");
        ArrayList<ArrayList<String>> allStationData = new ArrayList<>();
        try {
        	String url1 = "https://www.autocentrum.pl/stacje-paliw/?s=1&order=&q=krak%C3%B3w";
        	String url2 = "https://www.autocentrum.pl/stacje-paliw/strona-2/?s=1&order=&q=krak%C3%B3w";
        	String url3 = "https://www.autocentrum.pl/stacje-paliw/strona-3/?s=1&order=&q=krak%C3%B3w";
        	Connection.Response response1 = Jsoup.connect(url1).timeout(1500).execute();
        	Connection.Response response2 = Jsoup.connect(url2).timeout(1500).execute();
        	Connection.Response response3 = Jsoup.connect(url3).timeout(1500).execute();
        	
		    if (response1.statusCode() == 200 && response2.statusCode() == 200 && response3.statusCode() == 200) {
		    	ArrayList<ArrayList<String>> stationData1 = web(url1);
		    	ArrayList<ArrayList<String>> stationData2 = web(url2);
		    	ArrayList<ArrayList<String>> stationData3 = web(url3);

		    	for (ArrayList<ArrayList<String>> stationDataList : Arrays.asList(stationData1, stationData2, stationData3)) {
		    		for (ArrayList<String> stationData : stationDataList) {
		    			allStationData.add(stationData);
		    		}
		    	}
		    	projectLogger.info("The data was downloaded from the website");
		    }
        } catch (Exception e) {
	        projectLogger.error("An exception occurred while fetching data from the web or while parsing the data", e);
        }
        if(!allStationData.isEmpty()) {
        	saveDataToCSV(allStationData, "scraped_data.csv");
        	projectLogger.info("The data was saved to a csv file");
        }

        if (allStationData.isEmpty()) {
            projectLogger.info("No internet connection");
            try {
            	allStationData = loadDataFromCSV("scraped_data.csv");
            }catch(Exception e) {
            	projectLogger.warn("Error while reading data from csv file", e);
            }
        }
        projectLogger.info("Finished all_data method.");

        return allStationData;
    }


	public static ArrayList<ArrayList<String>> web(String url) throws IOException {
		projectLogger.info("Starting web method.");
	    ArrayList<ArrayList<String>> stationData = new ArrayList<>();
	    try {
	    	Connection.Response response = Jsoup.connect(url).execute();
		    if (response.statusCode() == 200) {
		        Document document = Jsoup.connect(url).timeout(300).get();
		        Elements stationItems = document.select(".station-item");
	
		        for (Element stationItem : stationItems) {
		            ArrayList<String> singleStationData = new ArrayList<>();
	
		            String name = stationItem.select(".name b").text();
		            String address = stationItem.select(".name").text().split(",")[1].trim();
		            String brand = stationItem.select(".address").text().trim();
	
		            Elements fuels = stationItem.select(".petrols-wrapper .petrol");
		            String pb95 = "";
		            String pb98 = "";
		            String diesel = "";
		            String lpg = "";
	
		            for (Element fuel : fuels) {
		                String fuelType = fuel.select(".fuel-logo").text();
		                String priceText = fuel.text();
		                String price = priceText.replaceAll("[^0-9,.]", "").replace(",", ".");
	
		                try {
		                    switch (fuelType) {
		                        case "95":
		                        case "miles 95":
		                        case "FuelSave 95":
		                        case "MILES 95":
		                            pb95 = price;
		                            if (!pb95.isEmpty() && pb95.length() > 2) {
		                                pb95 = pb95.substring(2);
		                            }
		                            break;
		                        case "V-Power Nitro+ Racing":
		                        	pb95 = price;
		                        	break;
		                        case "98":
		                        case "miles 98":
		                        case "Ultimate 98":
		                            pb98 = price;
		                            if (!pb98.isEmpty() && pb98.length() > 2) {
		                                pb98 = pb98.substring(2);
		                            }
		                            break;
		                        case "ON":
		                        case "Ekodiesel":
		                        case "EKODIESEL":
		                        case "Ultimate ON":
		                            diesel = price;
		                            break;
		                        case "LPG":
		                            lpg = price;
		                            break;
		                    }
		                    projectLogger.info("The data has been scraped");
		                } catch (Exception e) {
		                    projectLogger.error("Wystąpił błąd przy przetwarzaniu cen paliwa: " + e.getMessage());
		                }
		            }
	 
		            singleStationData.add(name);
		            singleStationData.add(address);
		            singleStationData.add(brand);
		            singleStationData.add(pb95);
		            singleStationData.add(pb98);
		            singleStationData.add(diesel);
		            singleStationData.add(lpg);
	
		            stationData.add(singleStationData);
		        }
		    }
	    } catch (IOException e) {
	        projectLogger.error("An exception occurred while fetching data from the web or while parsing the data", e);

	    }

	    return stationData;
	}
	
	public static ArrayList<ArrayList<String>> all_data_Pb95() {
		ArrayList<ArrayList<String>> allStationData = all_data();
		ArrayList<ArrayList<String>> filteredStationData = new ArrayList<>();
		try {
			for (ArrayList<String> stationData : allStationData) {
				if (stationData.size() > 4 && !stationData.get(3).isEmpty()) {
					ArrayList<String> filteredData = new ArrayList<>();
					filteredData.add(stationData.get(0)); // name
					filteredData.add(stationData.get(1)); // address
					filteredData.add(stationData.get(2)); // brand
					filteredData.add(stationData.get(3)); // pb98
					filteredStationData.add(filteredData);
				}
			}
			projectLogger.info("The data has been created");
		} catch(Exception e) {
			projectLogger.warn("The data has not been created");
		}
		return filteredStationData;
	}
	public static ArrayList<ArrayList<String>> all_data_Pb98() {
	    ArrayList<ArrayList<String>> allStationData = all_data();
	    ArrayList<ArrayList<String>> filteredStationData = new ArrayList<>();
	    try {
	    	for (ArrayList<String> stationData : allStationData) {
	    		if (stationData.size() > 4 && !stationData.get(4).isEmpty()) {
	    			ArrayList<String> filteredData = new ArrayList<>();
	    			filteredData.add(stationData.get(0)); // name
	    			filteredData.add(stationData.get(1)); // address
	    			filteredData.add(stationData.get(2)); // brand
	    			filteredData.add(stationData.get(4)); // pb98
	    			filteredStationData.add(filteredData);
	    		}
	    	}
			projectLogger.info("The data has been created");
	    } catch(Exception e){
			projectLogger.warn("The data has not been created");
	    }
	    return filteredStationData;
	}
	
	public static ArrayList<ArrayList<String>> all_data_Diesel() {
	    ArrayList<ArrayList<String>> allStationData = all_data();
	    ArrayList<ArrayList<String>> filteredStationData = new ArrayList<>();
	    try {
	    	for (ArrayList<String> stationData : allStationData) {
	    		if (stationData.size() > 5 && !stationData.get(5).isEmpty()) {
	    			ArrayList<String> filteredData = new ArrayList<>();
	    			filteredData.add(stationData.get(0)); // name
	    			filteredData.add(stationData.get(1)); // address
	    			filteredData.add(stationData.get(2)); // brand
	    			filteredData.add(stationData.get(5)); // diesel
	    			filteredStationData.add(filteredData);
	    		}
	    	}
			projectLogger.info("The data has been created");
	    }catch(Exception e) {
			projectLogger.warn("The data has not been created");
	    }
	    return filteredStationData;
	}
	
	public static ArrayList<ArrayList<String>> all_data_Lpg() {
	    ArrayList<ArrayList<String>> allStationData = all_data();
	    ArrayList<ArrayList<String>> filteredStationData = new ArrayList<>();
	    try {
	    	for (ArrayList<String> stationData : allStationData) {
	    		if (stationData.size() > 6 && !stationData.get(6).isEmpty()) {
	    			ArrayList<String> filteredData = new ArrayList<>();
	    			filteredData.add(stationData.get(0)); // name
	    			filteredData.add(stationData.get(1)); // address
	    			filteredData.add(stationData.get(2)); // brand
	    			filteredData.add(stationData.get(6)); // lpg
	    			filteredStationData.add(filteredData);
	    		}
	    	}
			projectLogger.info("The data has been created");
	    }catch(Exception e) {
			projectLogger.warn("The data has not been created");
	    }
	    return filteredStationData;
	}
	public static void saveDataToCSV(ArrayList<ArrayList<String>> data, String filename) {
	    try (PrintWriter writer = new PrintWriter(new File(filename))) {
	        StringBuilder sb = new StringBuilder();
	        for (ArrayList<String> row : data) {
	            sb.append(String.join(",", row));
	            sb.append("\n");
	        }
	        writer.write(sb.toString());
	    } catch (FileNotFoundException e) {
	        projectLogger.error("An error occurred while saving data to CSV: " + e.getMessage());
	    }
	}
	public static ArrayList<ArrayList<String>> loadDataFromCSV(String filename) {
	    ArrayList<ArrayList<String>> dataFromCSV = new ArrayList<>();
	    Path filePath = Paths.get(filename);
	    if (Files.exists(filePath)) {
	        try (Scanner scanner = new Scanner(filePath)) {
	            while (scanner.hasNextLine()) {
	                dataFromCSV.add(parseCsvLine(scanner.nextLine()));
	            }
	            projectLogger.warn("Data was read from the csv file");
  
	        } catch (IOException e) {
	            projectLogger.info("Data was not read from the csv file", e);
	        }
	    } else {
	        projectLogger.warn("File not found: " + filename);
	    }
	    return dataFromCSV;
	}

	private static ArrayList<String> parseCsvLine(String line) {
	    // Zakładając, że dane CSV są rozdzielone przecinkami
	    String[] values = line.split(",", -1);
	    return new ArrayList<>(Arrays.asList(values));
	}
}