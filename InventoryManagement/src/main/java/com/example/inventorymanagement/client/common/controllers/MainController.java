package com.example.inventorymanagement.client.common.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private static MainController instance;
    private Stage stage;

    private MainController() {
        // Private constructor to prevent instantiation from outside
    }

    public static MainController getInstance() {
        if (instance == null) {
            instance = new MainController();
        }
        return instance;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void showWelcome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/welcome/welcome-view.fxml"));
            BorderPane welcomePane = fxmlLoader.load();

            Scene scene = new Scene(welcomePane);
            stage.setScene(scene);
            stage.setTitle("Stock Pilot");

            WelcomeController welcomeController = fxmlLoader.getController();
            welcomeController.setMainController(this); // Pass MainController instance to WelcomeController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showLoginPanel() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/login/login-view.fxml"));
            BorderPane loginPane = fxmlLoader.load();

            Scene scene = new Scene(loginPane);

            // Retrieve the current stage
            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling showLoginPanel.");
            }
            stage.setScene(scene);
            stage.setTitle("Stock Pilot");

            LoginController loginController = fxmlLoader.getController();
            loginController.setMainController(this);
            loginController.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
