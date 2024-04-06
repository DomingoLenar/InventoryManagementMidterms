package com.example.inventorymanagement.client.admin.views;

import com.example.inventorymanagement.client.admin.controllers.DashboardAdminController;
import com.example.inventorymanagement.client.admin.controllers.NavigationBarAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardAdminPanel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load font
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);
        System.out.println("Starting DashboardAdminPanel...");


        // Navigation bar loader
        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        BorderPane navigationBar = navLoader.load();
        NavigationBarAdminController navController = navLoader.getController();

        // Dashboard loader
        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/dashboard/dashboardAdmin-view.fxml"));
        dashboardLoader.setController(new DashboardAdminController()); // Set the controller
        BorderPane dashboard = dashboardLoader.load();

        // Set the main BorderPane reference in the navigation bar controller
        navController.setMainBorderPane(dashboard);

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(dashboard);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
