package com.example.inventorymanagement.client.admin.views;

import com.example.inventorymanagement.client.admin.controllers.DashboardAdminController;
import com.example.inventorymanagement.client.admin.controllers.FinancesAdminController;
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

public class FinancesAdminPanel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //load font
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        //load the FXML files
        FXMLLoader navigationBarLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        FXMLLoader financesLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/finances/financesAdmin-view.fxml"));
        financesLoader.setController(new FinancesAdminController());

        // load UI elements
        BorderPane navigationBar = navigationBarLoader.load();
        BorderPane financesAdminPanel = financesLoader.load();

        // Get the controller
        FinancesAdminController controller = financesLoader.getController();

        //Set up root pane
        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(financesAdminPanel);

        //Create Scene
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
