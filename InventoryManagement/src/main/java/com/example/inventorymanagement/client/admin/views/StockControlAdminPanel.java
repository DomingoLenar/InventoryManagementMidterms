package com.example.inventorymanagement.client.admin.views;

import com.example.inventorymanagement.client.admin.controllers.StockControlAdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.example.inventorymanagement.client.admin.controllers.NavigationBarAdminController;

import java.io.IOException;
import java.io.InputStream;

public class StockControlAdminPanel {

    public void start(Stage stage, StockControlAdminController controller) throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        // Set the controller before loading the FXML file
        FXMLLoader viewLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlAdmin-view.fxml"));
        viewLoader.setController(controller);

        BorderPane stockControlPanelAdmin = viewLoader.load();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        BorderPane navigationBarAdmin = loader.load();

        // Get the controller
        NavigationBarAdminController navBarAdminController = loader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        BorderPane root = new BorderPane();
        root.setLeft(navigationBarAdmin);
        root.setRight(stockControlPanelAdmin);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navBarAdminController.setMainBorderPane(root);
    }
}