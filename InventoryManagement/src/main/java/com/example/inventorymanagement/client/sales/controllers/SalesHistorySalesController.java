package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.sales.models.SalesHistorySalesModel;
import com.example.inventorymanagement.client.sales.views.SalesHistorySalesPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
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

public class SalesHistorySalesController extends Application implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button createSalesInvoiceSalesButton;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView salesHistorySalesTable;

    @FXML
    public BorderPane getBorderPaneStockControlSales() {
        return borderPaneStockControlSales;
    }

    @FXML
    public Button getcreateSalesInvoiceSalesButton() {
        return createSalesInvoiceSalesButton;
    }

    @FXML
    public TextField getSearchFieldSales() {
        return searchFieldSales;
    }

    @FXML
    public TableView getsalesHistorySalesTable() {
        return salesHistorySalesTable;
    }

    private SalesHistorySalesModel salesHistorySalesModel;
    private SalesHistorySalesPanel salesHistorySalesPanel = new SalesHistorySalesPanel();

    private MainController mainController;
    private ClientCallback clientCallback;
    private Registry registry;
    private ItemOrder salesInvoice;

    @Override
    public void start(Stage stage) throws Exception {
        salesHistorySalesPanel.start(stage);
    }

    @FXML
    private void initialize() {
        if (createSalesInvoiceSalesButton != null) {
            addHoverEffect(createSalesInvoiceSalesButton);
            createSalesInvoiceSalesButton.setOnAction(event -> handleSalesInvoice());
        }
    }

    @FXML
    private void handleSalesInvoice() {
        // Handle the event when the sales invoice button is clicked
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = salesHistorySalesModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        salesHistorySalesTable.getItems().clear();
        salesHistorySalesTable.getItems().addAll(items);
    }

    public SalesHistorySalesController() {

    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public SalesHistorySalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        SalesHistorySalesModel salesHistorySalesModel = new SalesHistorySalesModel(registry, clientCallback); // use this on events of saleshistorysalesview
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
