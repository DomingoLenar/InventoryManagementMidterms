package com.example.inventorymanagement.client.admin.views;

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

public class FinancesAdminPanel {
    public void start(Stage stage) throws IOException {
        // load font
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        BorderPane navigationBar = loader.load();

        // Get the controller
        NavigationBarAdminController navBarAdminController = loader.getController();

        // Create the finances panel
        BorderPane financesAdminPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/finances/financesAdmin-view.fxml"));

        //Set up root pane
        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(financesAdminPanel);

        //Create Scene
        Scene sceneFinancesAdminPanel = new Scene(root, 1080, 650);

        // Load icon
        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");
        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        // Set stage properties
        stage.setTitle("Stock Pilot");
        stage.setScene(sceneFinancesAdminPanel);
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        //navBarAdminController.setMainBorderPane(root);
    }
}
