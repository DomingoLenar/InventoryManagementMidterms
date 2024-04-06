package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.sales.models.StockControlSalesModel;
import com.example.inventorymanagement.client.sales.views.StockControlSalesPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class StockControlSalesController extends Application implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button createSalesInvoiceButtonSales;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView stockControlSalesTable;

    @FXML
    public BorderPane getBorderPaneStockControlSales() {
        return borderPaneStockControlSales;
    }

    @FXML
    public Button getCreateSalesInvoiceButton() {
        return createSalesInvoiceButtonSales;
    }

    @FXML
    public TextField getSearchFieldSales() {
        return searchFieldSales;
    }

    @FXML
    public TableView getStockControlSalesTable() {
        return stockControlSalesTable;
    }

    private StockControlSalesModel stockControlSalesModel;
    private StockControlSalesPanel stockControlSalesPanel = new StockControlSalesPanel();

    private MainController mainController;

    @Override
    public void start(Stage stage) throws Exception {
        stockControlSalesPanel.start(stage);
    }

    @FXML
    public void initialize() {
        if (createSalesInvoiceButtonSales != null) {
            addHoverEffect(createSalesInvoiceButtonSales);
            createSalesInvoiceButtonSales.setOnAction(event -> handleSalesInvoice());
        }
    }

    @FXML
    private void handleSalesInvoice() {
        // Handle sales invoice button action
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = stockControlSalesModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        stockControlSalesTable.getItems().clear();
        stockControlSalesTable.getItems().addAll(items);
    }

    public StockControlSalesController() {
        // Default constructor
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public StockControlSalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        StockControlSalesModel stockControlSalesModel = new StockControlSalesModel(registry, clientCallback); // use this on events of stockControlSalesView
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}