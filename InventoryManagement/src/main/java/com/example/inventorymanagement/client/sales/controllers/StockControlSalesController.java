package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.sales.models.StockControlSalesModel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class StockControlSalesController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button salesInvoiceButtonSales;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView stockControlSalesTable;
    private StockControlSalesModel model = new StockControlSalesModel();

    public StockControlSalesController(){
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @FXML
    public BorderPane getBorderPaneStockControlSales() { return borderPaneStockControlSales;}

    @FXML
    public Button getSalesInvoiceButton() { return salesInvoiceButtonSales; }

    @FXML
    public TextField getSearchFieldSales() { return searchFieldSales; }

    @FXML
    public TableView getStockControlSalesTable() { return stockControlSalesTable; }

    @FXML
    public void initialize() {
        addHoverEffect(salesInvoiceButtonSales);

        salesInvoiceButtonSales.setOnAction(this::handleSalesInvoice);
    }

    public void handleSalesInvoice (ActionEvent event){
           model.handleSalesInvoice();
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
