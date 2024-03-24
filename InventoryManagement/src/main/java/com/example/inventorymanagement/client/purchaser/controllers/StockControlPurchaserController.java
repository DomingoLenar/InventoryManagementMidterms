package com.example.inventorymanagement.client.purchaser.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class StockControlPurchaserController {
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
}
