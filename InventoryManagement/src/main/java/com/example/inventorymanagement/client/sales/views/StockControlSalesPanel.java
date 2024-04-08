package com.example.inventorymanagement.client.sales.views;

import com.example.inventorymanagement.client.sales.controllers.StockControlSalesController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.example.inventorymanagement.client.sales.controllers.NavigationBarSalesController;

import java.io.IOException;
import java.io.InputStream;

public class StockControlSalesPanel {

    public void start(Stage stage, StockControlSalesController controller) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        // Set the controller before loading the FXML file
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlSales-view.fxml"));
        viewLoader.setController(controller);

        BorderPane stockControlPanelSales = viewLoader.load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarSales-view.fxml"));
        BorderPane navigationBarSales = loader.load();

        // Get the controller
        NavigationBarSalesController navBarSalesController = loader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        BorderPane root = new BorderPane();
        root.setLeft(navigationBarSales);
        root.setRight(stockControlPanelSales);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navBarSalesController.setMainBorderPane(root);
    }
}