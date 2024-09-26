# Fuel Prices in Cracow Application

This JavaFX application displays information about fuel prices at gas stations in Cracow, Poland. It fetches the latest price data through web scraping from selected websites. The application provides a user interface with tables and charts to present current fuel prices efficiently.

## Features

- **Real-time fuel prices:** Display prices for different types of fuel such as Pb95, Pb98, Diesel, and LPG from various gas stations in Cracow.
- **Web scraping:** Utilizes Jsoup library to scrape real-time data from web pages.
- **Data visualization:** Shows fuel prices in a tabular format and visualizes average prices on bar charts for easy comparison.
- **Multi-view interface:** Allows users to switch between different fuel types to view specific price information.
- **Background data fetching:** Implements JavaFX Task for non-blocking UI operations while fetching data in the background.
- **Error handling:** Includes error handling for network issues or data parsing errors.

## Requirements

- Java 11 or newer
- JavaFX SDK compatible with your Java version
- Maven (optional, for managing dependencies and building the project)
- Jsoup library for web scraping
- Log4j for logging

## Setup

1. **JavaFX Configuration:** Make sure you have JavaFX configured in your IDE or on your machine if you're running from the command line.
2. **Dependencies:** Include the Jsoup and Log4j libraries in your project. If you are using Maven, add them as dependencies in your `pom.xml`.
3. **Environment Variables:** Set the `PATH_TO_FX` environment variable to the path of the lib directory of the JavaFX SDK.

## Running the Application

- **IDE:** Import the project as a Maven project, configure the JavaFX SDK, and run the `Main` class.
- **Maven:** Use the command `mvn clean javafx:run` in the root directory of the project.

## Project Structure

- `src/main/java/application` - Contains the JavaFX application classes.
- `src/main/resources` - Contains FXML and CSS files for the UI, and image resources.
- `pom.xml` - Maven configuration file with project dependencies.

## Known Issues

- Web scraping is dependent on the structure of the target website. Any changes in the web page layout may require adjustments in the scraping logic.
- The application is designed for specific websites and may not work if the URLs or the HTML structure of these sites are changed.

## Future Improvements

- Implement dynamic web scraping to handle website structure changes.
- Add more data sources for fuel prices.
- Enhance the UI for a better user experience.
- Introduce more advanced data visualization techniques.

## License

This project is licensed under the MIT License - see the LICENSE file for details.
