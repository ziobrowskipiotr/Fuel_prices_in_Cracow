package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataLpg {
	
	private static Logger logger = LogManager.getLogger(DataLpg.class.getName());
	
	private final StringProperty name;
	private final StringProperty address;
	private final StringProperty brand;
	private final StringProperty lpg;

	// Constructor to initialize DataColumn with data
	public DataLpg(String name, String address, String brand, String lpg) {
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.brand = new SimpleStringProperty(brand);
		this.lpg = new SimpleStringProperty(lpg);
		// Logging the creation of a new DataColumn instance
		logger.debug("Created DataColumn: {}", this);
	}

	// Access methods for name
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	// Access methods for address
	public String getAddress() {
		return address.get();
	}

	public void setAddress(String address) {
		this.address.set(address);
	}

	public StringProperty addressProperty() {
		return address;
	}

	// Access methods for brand
	public String getBrand() {
		return brand.get();
	}

	public void setBrand(String brand) {
		this.brand.set(brand);
	}

	public StringProperty brandProperty() {
		return brand;
	}

	// Access methods for lpg
	public String getLpg() {
		return lpg.get();
	}

	public void setLpg(String lpg) {
		this.lpg.set(lpg);
	}

	public StringProperty lpgProperty() {
		return lpg;
	}
}