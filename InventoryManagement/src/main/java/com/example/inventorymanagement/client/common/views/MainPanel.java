package com.example.inventorymanagement.client.common.views;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainPanel extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost");
        UserRequestInterface userService = (UserRequestInterface) registry.lookup("user");
        MainController mainController = new MainController(userService);
        mainController.setStage(primaryStage);
        mainController.loadWelcomeView();
        primaryStage.setTitle("Stock Pilot");
        primaryStage.show();
    }


    public static void main(String[] args) throws NotBoundException, RemoteException {
        launch(args);

    }
}
