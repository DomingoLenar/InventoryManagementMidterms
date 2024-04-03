package com.example.inventorymanagement.client.purchaser.controllers;

import com.example.inventorymanagement.client.common.controllers.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class StockControlPurchaserController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlPurchaser;
    @FXML
    private Button lowStocksButtonPurchaser;
    @FXML
    private Button addItemButtonPurchaser;
    @FXML
    private TextField searchFieldPurchaser;
    @FXML
    private TableView stockControlPurchaserTable;

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getCurrentPanel() throws RemoteException {
        return "StockControlPurchaser"; // Return the name of this panel
    }

    @FXML
    public BorderPane getBorderPaneStockControlPurchaser() {
        return borderPaneStockControlPurchaser;
    }

    @FXML
    public Button getLowStocksButton() {
        return lowStocksButtonPurchaser;
    }

    @FXML
    public Button getAddItemButton() { return addItemButtonPurchaser;}

    @FXML
    public TextField getSearchFieldPurchaser() { return searchFieldPurchaser; }

    @FXML
    public TableView getStockControlPurchaserTable() { return stockControlPurchaserTable; }

    @FXML
    private void initialize() {
        addHoverEffect(lowStocksButtonPurchaser);
        addHoverEffect(addItemButtonPurchaser);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
