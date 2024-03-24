package com.example.inventorymanagement.client.common.controller;

import com.example.inventorymanagement.client.common.views.WelcomePanel;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainController mainController = MainController.getInstance(); // Get instance of MainController
        mainController.setStage(stage);

        // Show the welcome panel
        WelcomePanel welcomePanel = new WelcomePanel();
        welcomePanel.start(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
