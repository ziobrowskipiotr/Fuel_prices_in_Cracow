package application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DataPb95 {
	
	private static Logger logger = LogManager.getLogger(DataPb95.class.getName());
	
	private final StringProperty name;
	private final StringProperty address;
	private final StringProperty brand;
	private final StringProperty pb95;

	// Constructor to initialize DataColumn with data
	public DataPb95(String name, String address, String brand, String pb95) {
		this.name = new SimpleStringProperty(name);
		this.address = new SimpleStringProperty(address);
		this.brand = new SimpleStringProperty(brand);
		this.pb95 = new SimpleStringProperty(pb95);
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

	// Access methods for pb95
	public String getPb95() {
		return pb95.get();
	}

	public void setPb95(String pb95) {
		this.pb95.set(pb95);
	}

	public StringProperty pb95Property() {
		return pb95;
	}

}