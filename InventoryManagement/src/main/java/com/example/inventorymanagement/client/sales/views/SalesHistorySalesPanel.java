package com.example.inventorymanagement.client.sales.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.example.inventorymanagement.client.admin.controllers.NavigationBarAdminController;

import java.io.IOException;
import java.io.InputStream;

public class SalesHistorySalesPanel {

    public void start(Stage stage) throws IOException {

        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        BorderPane navigationBar = loader.load();

        // Get the controller
        NavigationBarAdminController navBarPurchaserController = loader.getController();

        // Create the stock control panel
        BorderPane salesHistoryPurchaserPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/salesHistory/salesHistorySales-view.fxml"));

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(salesHistoryPurchaserPanel);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navBarPurchaserController.setMainBorderPane(root);
    }
}
