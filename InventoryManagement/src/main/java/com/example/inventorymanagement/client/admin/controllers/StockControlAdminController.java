package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddItemAdminModel;
import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceModel;
import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;
import java.util.concurrent.ConcurrentMap;

public class StockControlAdminController implements ControllerInterface {
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
    private StockControlAdminModel stockControlAdminModel = new StockControlAdminModel();
    private AddItemAdminModel addItemAdminModel = new AddItemAdminModel();
    private CreateSalesInvoiceModel salesInvoiceModel = new CreateSalesInvoiceModel();

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() { return borderPaneStockControlAdmin;}

    @FXML
    public Button getLowStocksButton() { return lowStocksButtonAdmin; }

    @FXML
    public Button getsalesInvoiceButtonAdmin() { return salesInvoiceButtonAdmin; }

    @FXML
    public Button getAddListingButton() { return addListingButtonAdmin;}

    @FXML
    public Button getAddItemButton() { return addItemButtonAdmin;}

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getStockControlAdminTable() { return stockControlAdminTable; }

    public StockControlAdminController() {
    }
    @FXML
    private void initialize() {
        addHoverEffect(lowStocksButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(addItemButtonAdmin);
        addHoverEffect(addListingButtonAdmin);

        addItemButtonAdmin.setOnAction(this::handleAddItem);
        salesInvoiceButtonAdmin.setOnAction(this::handleSalesInvoice);
    }

    private void handleAddItem (ActionEvent event){
        addItemAdminModel.handleAddItem();
    }
    private void handleSalesInvoice (ActionEvent event){
        salesInvoiceModel.handleSalesInvoice();
    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
