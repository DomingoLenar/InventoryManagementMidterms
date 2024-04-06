package com.example.inventorymanagement.client.sales.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.example.inventorymanagement.client.sales.controllers.NavigationBarSalesController;

import java.io.IOException;

public class StockControlSalesPanel {

    public void start(Stage stage) throws IOException {

        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarSales-view.fxml"));
        BorderPane navigationBarSales = loader.load();

        // Get the controller
        NavigationBarSalesController navBarSalesController = loader.getController();

        // Create the stock control panel
        BorderPane stockControlPanelSales = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlSales-view.fxml"));

        BorderPane root = new BorderPane();
        root.setLeft(navigationBarSales);
        root.setRight(stockControlPanelSales);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navBarSalesController.setMainBorderPane(root);
    }
}
