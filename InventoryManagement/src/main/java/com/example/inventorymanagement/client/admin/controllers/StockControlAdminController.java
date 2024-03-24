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
    private Button lowStocksButtonAdmin;
    @FXML
    private Button salesInvoiceButtonAdmin;
    @FXML
    private Button addListingButtonAdmin;
    @FXML
    private Button addItemButtonAdmin;
    @FXML
    private TextField searchFieldAdmin;
    @FXML
    private TableView stockControlAdminTable;

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() { return borderPaneStockControlAdmin;}

    @FXML
    public Button getLowStocksButton() { return lowStocksButtonAdmin; }

    @FXML
    public Button getSalesInvoiceButton() { return salesInvoiceButtonAdmin; }

    @FXML
    public Button getAddListingButton() { return addListingButtonAdmin;}

    @FXML
    public Button getAddItemButton() { return addItemButtonAdmin;}

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getStockControlAdminTable() { return stockControlAdminTable; }
}
