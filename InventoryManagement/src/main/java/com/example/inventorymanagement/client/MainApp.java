package com.example.inventorymanagement.client;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.stage.Stage;

import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 2018);
        UserRequestInterface userService = (UserRequestInterface) registry.lookup("userRequest");
        ItemOrderRequestInterface iOService = (ItemOrderRequestInterface) registry.lookup("itemOrder");
        ItemRequestInterface itemService = (ItemRequestInterface) registry.lookup("item");
        MainController mainController = new MainController(userService, iOService, itemService, registry);
        mainController.setStage(primaryStage);
        mainController.loadWelcomeView();
        primaryStage.setTitle("Stock Pilot");
        primaryStage.show();
    }


    public static void main(String[] args) throws NotBoundException, RemoteException {
        launch(args);

    }
}
