package application;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.apache.logging.log4j.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.Connection;
import java.io.*;

public class Main extends Application {

    private static Logger projectLogger = LogManager.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) {
        projectLogger.info("Application is starting");
        try {
            // Uruchomienie pobierania obrazów w osobnym wątku
            Task<Void> fetchImagesTask = new Task<Void>() {
                @Override
                protected Void call() throws Exception {
                    fetchImages();
                    return null;
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                }

                @Override
                protected void failed() {
                    super.failed();
                }
            };

            Thread thread = new Thread(fetchImagesTask);
            thread.setDaemon(true); // Ustawienie jako wątek demoniczny
            thread.start();

            Parent root = FXMLLoader.load(getClass().getResource("Main5.fxml"));
            Scene scene = new Scene(root, 1792, 1024);
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setMinWidth(600);
            primaryStage.setMinHeight(343);
            primaryStage.setMaxWidth(1792);
            primaryStage.setMaxHeight(1024);
            primaryStage.show();

        } catch(Exception e) {
            e.printStackTrace();
            projectLogger.warn("Error starting the application: ", e);
        }
        projectLogger.info("The application has terminated");
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void fetchImages() {
        String url = "https://www.e-petrol.pl/notowania/rynek-krajowy/ceny-stacje-paliw";
        String savePath = "/home/piotr/eclipse-workspace/Object_Oriented_Programming_Project/src/download_images/dwn";
        try {
            Connection.Response response = Jsoup.connect(url).execute();
            projectLogger.info("Connecting to the URL...");

            if (response.statusCode() == 200) {
                projectLogger.info("Connected to the URL");
                Document document = response.parse();
                Elements imgElements = document.select("img");

                for (int i = 2; i < imgElements.size(); i++) {
                    String imgUrl = imgElements.get(i).absUrl("src");
                    downloadImage(imgUrl, savePath + "image" + i + ".png");
                }
            }
        } catch (IOException e) {
            projectLogger.error("Error fetching images: ", e);
        }
    }

    private static void downloadImage(String imgUrl, String savePath) throws IOException {
        try (OutputStream out = new FileOutputStream(savePath)) {
            out.write(Jsoup.connect(imgUrl).ignoreContentType(true).execute().bodyAsBytes());
            projectLogger.info("Image saved at: " + savePath);
        }
        catch (Exception e) {
            projectLogger.error("Error downloading images: ", e);
        }
    }
}