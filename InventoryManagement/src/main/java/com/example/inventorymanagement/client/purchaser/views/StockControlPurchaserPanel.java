package com.example.inventorymanagement.client.purchaser.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class StockControlPurchaserPanel extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"),20);

        FXMLLoader navigationBarLoader = new FXMLLoader(StockControlPurchaserPanel.class.getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarPurchaser-view.fxml"));
        FXMLLoader stockControlLoader = new FXMLLoader(StockControlPurchaserPanel.class.getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlPurchaser-view.fxml"));

        BorderPane navigationBar = navigationBarLoader.load();
        BorderPane stockControlPanel = stockControlLoader.load();

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(stockControlPanel);

        Scene sceneStockControlPurchaserPanel = new Scene(root, 1080, 650);

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");
        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        stage.setTitle("Stock Pilot");
        stage.setScene(sceneStockControlPurchaserPanel);
        stage.show();

        stage.setResizable(false);
    }

    public static void main(String[] args) {
        launch();
    }
}
