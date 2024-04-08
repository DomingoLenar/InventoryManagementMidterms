package com.example.inventorymanagement.client.purchaser.views;

import com.example.inventorymanagement.client.purchaser.controllers.StockControlPurchaserController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.example.inventorymanagement.client.purchaser.controllers.NavigationBarPurchaserController;

import java.io.IOException;
import java.io.InputStream;

public class StockControlPurchaserPanel {

    public void start(Stage stage, StockControlPurchaserController controller) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        // Set the controller before loading the FXML file
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlPurchaser-view.fxml"));
        viewLoader.setController(controller);

        BorderPane stockControlPanelPurchaser = viewLoader.load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarPurchaser-view.fxml"));
        BorderPane navigationBarPurchaser = loader.load();

        // Get the controller
        NavigationBarPurchaserController navBarPurchaserController = loader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        BorderPane root = new BorderPane();
        root.setLeft(navigationBarPurchaser);
        root.setRight(stockControlPanelPurchaser);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navBarPurchaserController.setMainBorderPane(root);
    }
}