package com.example.inventorymanagement.client.admin.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class UserManagementAdminPanel extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // Load font
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        // Load FXML files
        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        FXMLLoader financesLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/userManagement/userManagementAdmin-view.fxml"));


        // Load UI elements
        BorderPane navigationBar = navigationBarLoader.load();
        BorderPane financesPanel = financesLoader.load();

        // Set up root pane
        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(financesPanel);

        // Create scene
        Scene sceneStockControlAdminPanel = new Scene(root, 1080, 650);

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
        stage.setScene(sceneStockControlAdminPanel);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

