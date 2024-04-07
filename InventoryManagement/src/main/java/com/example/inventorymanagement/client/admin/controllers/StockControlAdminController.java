package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;

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
    private MainController mainController;
    private ClientCallback clientCallback;
    private Registry registry;
    public StockControlAdminController() {

    }
    public StockControlAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        StockControlAdminModel stockControlAdminModel = new StockControlAdminModel(registry, clientCallback); // use this on events of stockcontroladminview
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController; // or this main...getSERVICES....actions()
    }

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

    public StockControlAdminController(ClientCallback clientCallback, Registry registry) {
        this.clientCallback = clientCallback;
        this.registry = registry;

       // stockControlAdminModel = new StockControlAdminModel(registry,clientCallback);

    }
    @FXML
    private void initialize() {
        addHoverEffect(lowStocksButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(addItemButtonAdmin);
        addHoverEffect(addListingButtonAdmin);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }
}
