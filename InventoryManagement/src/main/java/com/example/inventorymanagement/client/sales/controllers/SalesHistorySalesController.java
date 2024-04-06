package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.sales.models.SalesHistorySalesModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class SalesHistorySalesController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button createSalesInvoiceSalesButton;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView salesHistorySalesTable;

    private SalesHistorySalesModel salesHistorySalesModel;

    private ClientCallback clientCallback;
    private Registry registry;
    private ItemOrder salesInvoice;

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = salesHistorySalesModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
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
        try {
            this.clientCallback = clientCallback;
            this.registry = registry;
            this.salesInvoice = salesInvoice;

            salesHistorySalesModel = new SalesHistorySalesModel(registry, clientCallback);
            clientCallback.setCurrentPanel(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addHoverEffect(createSalesInvoiceSalesButton);
        createSalesInvoiceSalesButton.setOnAction(event -> handleSalesInvoice());
    }

    private void populateTableView(LinkedList<Item> items) {
        salesHistorySalesTable.getItems().clear();
        salesHistorySalesTable.getItems().addAll(items);
    }

    @FXML
    private void handleSalesInvoice() {

    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
