package com.example.inventorymanagement.client.purchaser.views;

import com.example.inventorymanagement.client.admin.controllers.NavigationBarAdminController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;

public class AddItemPurchaserPanel {
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"),20);

        FXMLLoader fxmlLoader = new FXMLLoader(AddItemPurchaserPanel.class.getResource("/com/example/inventorymanagement/client/view/stockControl/addItem-view.fxml"));
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
}
