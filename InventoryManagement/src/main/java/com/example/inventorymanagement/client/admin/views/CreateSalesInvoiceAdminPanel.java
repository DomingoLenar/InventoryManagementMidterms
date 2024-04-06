package com.example.inventorymanagement.client.admin.views;

import com.example.inventorymanagement.client.admin.controllers.NavigationBarAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class CreateSalesInvoiceAdminPanel extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"),20);

        FXMLLoader fxmlLoader = new FXMLLoader(CreateSalesInvoiceAdminPanel.class.getResource("/com/example/inventorymanagement/client/view/stockControl/createSalesInvoice-view.fxml"));
        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        Scene sceneLogin = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Stock Pilot");
        stage.setScene(sceneLogin);
        stage.show();

        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}
