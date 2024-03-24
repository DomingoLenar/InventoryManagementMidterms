package com.example.inventorymanagement.client.admin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class StockControlAdminController {
    @FXML
    private BorderPane borderPaneStockControlAdmin;
    @FXML
    private Button lowStocksButton;
    @FXML
    private Button salesInvoiceButton;
    @FXML
    private Button addListingButton;
    @FXML
    private Button addItemButton;
    @FXML
    private TextField searchFieldAdmin;
    @FXML
    private TableView stockControlAdminTable;

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() {
        return borderPaneStockControlAdmin;
    }

    @FXML
    public Button getLowStocksButton() {
        return lowStocksButton;
    }

    @FXML
    public Button getSalesInvoiceButton() { return salesInvoiceButton; }

    @FXML
    public Button getAddListingButton() { return addListingButton;}

    @FXML
    public Button getAddItemButton() { return addItemButton;}

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getStockControlAdminTable() { return stockControlAdminTable; }
}
