package com.example.inventorymanagement.client.common.views;

import com.example.inventorymanagement.client.common.controllers.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainPanel extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController mainController = MainController.getInstance(); // Get instance of MainController
        mainController.setStage(primaryStage);

        mainController.showWelcome();

        primaryStage.setTitle("Stock Pilot");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
