package com.example.inventorymanagement.client.common.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void showLogin() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/login/login-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage loginStage = new Stage();  // Create a new stage for the login scene
            loginStage.setScene(scene);
            loginStage.setTitle("Stock Pilot - Login");

            // Close the main stage when login stage is shown
            stage.close();

            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
