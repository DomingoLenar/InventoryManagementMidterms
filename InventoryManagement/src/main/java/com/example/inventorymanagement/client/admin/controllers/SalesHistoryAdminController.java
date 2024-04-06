package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceAdminModel;
import com.example.inventorymanagement.client.admin.models.SalesHistoryAdminModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class SalesHistoryAdminController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlAdmin;
    @FXML
    private Button createSalesInvoiceAdminButton;
    @FXML
    private TextField searchFieldAdmin;
    @FXML

    private TableView salesHistoryAdminTable;

    private SalesHistoryAdminModel salesHistoryAdminModel;

    private ClientCallback clientCallback;
    private Registry registry;
    private ItemOrder salesInvoice;

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = salesHistoryAdminModel.fetchItems();
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
    public BorderPane getBorderPaneStockControlAdmin() { return borderPaneStockControlAdmin;}

    @FXML
    public Button getcreateSalesInvoiceAdminButton() { return createSalesInvoiceAdminButton; }

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getSalesHistoryAdminTable() { return salesHistoryAdminTable; }

    public SalesHistoryAdminController(ClientCallback clientCallback, Registry registry, ItemOrder salesInvoice) {
        try {
        this.clientCallback = clientCallback;
        this.registry = registry;
        this.salesInvoice = salesInvoice;

            salesHistoryAdminModel = new SalesHistoryAdminModel(registry, clientCallback);
            clientCallback.setCurrentPanel(this);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        addHoverEffect(createSalesInvoiceAdminButton);
        createSalesInvoiceAdminButton.setOnAction(event -> handleSalesInvoice());
    }

    private void populateTableView(LinkedList<Item> items) {
        salesHistoryAdminTable.getItems().clear();
        salesHistoryAdminTable.getItems().addAll(items);
    }

    @FXML
    private void handleSalesInvoice() {

    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
