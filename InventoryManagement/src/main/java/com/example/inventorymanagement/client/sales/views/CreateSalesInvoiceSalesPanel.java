package com.example.inventorymanagement.client.sales.views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;

public class CreateSalesInvoiceSalesPanel{
    public void start(Stage stage) throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"),20);

        FXMLLoader fxmlLoader = new FXMLLoader(CreateSalesInvoiceSalesPanel.class.getResource("/com/example/inventorymanagement/client/view/stockControl/createSalesInvoice-view.fxml"));
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
