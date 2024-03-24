package com.example.inventorymanagement.client.sales.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class StockControlSalesController {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button salesInvoiceButtonSales;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView stockControlSalesTable;

    @FXML
    public BorderPane getBorderPaneStockControlSales() { return borderPaneStockControlSales;}

    @FXML
    public Button getSalesInvoiceButton() { return salesInvoiceButtonSales; }

    @FXML
    public TextField getSearchFieldSales() { return searchFieldSales; }

    @FXML
    public TableView getStockControlSalesTable() { return stockControlSalesTable; }

    @FXML
    private void initialize() {
        addHoverEffect(salesInvoiceButtonSales);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
