package com.example.inventorymanagement.client.purchaser.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class NavigationBarPurchaserController {
    @FXML
    private BorderPane borderPaneNavigationBarPurchaser;
    @FXML
    private Button stockControlButtonPurchaser;
    @FXML
    private Button profileButtonPurchaser;

    // Reference to the main BorderPane
    private BorderPane mainBorderPane;

    // Setter for main BorderPane
    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @FXML
    public BorderPane getBorderPaneNavigationBarPurchaser() { return borderPaneNavigationBarPurchaser;}

    @FXML
    public Button getStockControlButtonPurchaser() { return stockControlButtonPurchaser; }

    @FXML
    public Button getProfileButtonPurchaser() { return profileButtonPurchaser; }

    @FXML
    private void initialize() {
        // Handle button clicks
        stockControlButtonPurchaser.setOnAction(event -> loadStockControlPanel());

        addHoverEffect(stockControlButtonPurchaser);
        addHoverEffect(profileButtonPurchaser);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void loadStockControlPanel() {
        // Load Stock Control panel
        try {
            BorderPane stockControlPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlPurchaser-view.fxml"));
            mainBorderPane.setRight(stockControlPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
