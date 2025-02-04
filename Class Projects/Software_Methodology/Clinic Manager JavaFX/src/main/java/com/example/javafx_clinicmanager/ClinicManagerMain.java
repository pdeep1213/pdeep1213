package com.example.javafx_clinicmanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The ClinicManagerMain class is an entry point for the Clinic Manager JavaFX application.
 * It initializes and displays the main application window by loading the "clinic-view.fxml" layout.
 *
 * @author Deep Patel, Manan Patel
 */


public class ClinicManagerMain extends Application {

    /**
     * This method is the main start point for JavaFX applications.
     * This method sets up the primary stage, creating a start screen.
     *
     * @param stage the primary stage for this JavaFX application
     * @throws IOException if the "clinic-view.fxml" file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClinicManagerMain.class.getResource("clinic-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 550, 570);
        stage.setTitle("Clinic Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method launches the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch();
    }
}