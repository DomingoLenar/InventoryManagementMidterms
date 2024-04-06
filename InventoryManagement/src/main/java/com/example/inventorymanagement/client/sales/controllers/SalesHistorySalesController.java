package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.sales.models.CreateSalesInvoiceSalesModel;
import com.example.inventorymanagement.client.sales.models.SalesHistorySalesModel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class SalesHistorySalesController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button createSalesInvoiceSalesButton;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView salesHistorySalesTable;
    private SalesHistorySalesModel salesHistorySalesModel = new SalesHistorySalesModel();
    private CreateSalesInvoiceSalesModel createSalesInvoiceSalesModel = new CreateSalesInvoiceSalesModel();

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "";
    }

    @FXML
    public BorderPane getBorderPaneStockControlSales() { return borderPaneStockControlSales;}

    @FXML
    public Button getcreateSalesInvoiceSalesButton() { return createSalesInvoiceSalesButton; }

    @FXML
    public TextField getSearchFieldSales() { return searchFieldSales; }

    @FXML
    public TableView getsalesHistorySalesTable() { return salesHistorySalesTable; }

    public SalesHistorySalesController() {
    }
    @FXML
    private void initialize() {
        addHoverEffect(createSalesInvoiceSalesButton);

//        createSalesInvoiceSalesButton.setOnAction(this::handleSalesInvoice);
    }

//    private void handleSalesInvoice (ActionEvent event){
//        createSalesInvoiceSalesModel.handleSalesInvoice;
//    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
