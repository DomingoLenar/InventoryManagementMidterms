package com.example.inventorymanagement.client.purchaser.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.purchaser.models.StockControlPurchaserModel;
import com.example.inventorymanagement.client.purchaser.views.StockControlPurchaserPanel;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class StockControlPurchaserController extends Application implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlPurchaser;
    @FXML
    private Button lowStocksButtonPurchaser;
    @FXML
    private Button addItemButtonPurchaser;
    @FXML
    private TextField searchFieldPurchaser;
    @FXML
    private TableView stockControlPurchaserTable;

    @FXML
    public BorderPane getBorderPaneStockControlPurchaser() {
        return borderPaneStockControlPurchaser;
    }

    @FXML
    public Button getLowStocksButton() {
        return lowStocksButtonPurchaser;
    }

    @FXML
    public Button getAddItemButton() { return addItemButtonPurchaser;}

    @FXML
    public TextField getSearchFieldPurchaser() { return searchFieldPurchaser; }

    @FXML
    public TableView getStockControlPurchaserTable() { return stockControlPurchaserTable; }

    private StockControlPurchaserModel stockControlPurchaserModel;
    private StockControlPurchaserPanel stockControlPurchaserPanel = new StockControlPurchaserPanel();

    private MainController mainController;

    @Override
    public void start(Stage stage) throws Exception {
        stockControlPurchaserPanel.start(stage);
    }

    @FXML
    private void initialize() {
        addHoverEffect(lowStocksButtonPurchaser);
        addHoverEffect(addItemButtonPurchaser);

        lowStocksButtonPurchaser.setOnAction(event -> handleLowStocks());
        addItemButtonPurchaser.setOnAction(event -> handleAddItem());
    }

    @FXML
    private void handleAddItem() {
        // Handle sales invoice button action
    }

    @FXML
    private void handleLowStocks() {
        // Handle low stocks button action
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = stockControlPurchaserModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        stockControlPurchaserTable.getItems().clear();
        stockControlPurchaserTable.getItems().addAll(items);
    }

    public StockControlPurchaserController() {
        // Default constructor
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public StockControlPurchaserController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        StockControlPurchaserModel stockControlPurchaserModel = new StockControlPurchaserModel(registry, clientCallback); // use this on events of saleshistorysalesview
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
