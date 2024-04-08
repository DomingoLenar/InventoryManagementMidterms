package com.example.inventorymanagement.client.purchaser.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.purchaser.models.StockControlPurchaserModel;
import com.example.inventorymanagement.client.purchaser.views.StockControlPurchaserPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class StockControlPurchaserController implements ControllerInterface {
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
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, Integer> totalQtyColumn;

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

    private MainController mainController;
    public StockControlPurchaserController(){

    }

    public StockControlPurchaserController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.stockControlPurchaserModel = new StockControlPurchaserModel(registry, clientCallback);
    }
    boolean initialized = false;
    @FXML
    public void initialize() {
        addHoverEffect(lowStocksButtonPurchaser);
        addHoverEffect(addItemButtonPurchaser);

        lowStocksButtonPurchaser.setOnAction(event -> handleLowStocks());
        addItemButtonPurchaser.setOnAction(event -> handleAddItem());
        stockControlPurchaserModel = new StockControlPurchaserModel(MainController.registry, MainController.clientCallback);
        if (!initialized) { // Check if already initialized
            initialized = true; // Set the flag to true

            // Check if UI components are not null
            if (stockControlPurchaserTable != null && addItemButtonPurchaser != null & lowStocksButtonPurchaser != null) {
                addHoverEffect(addItemButtonPurchaser);
                addHoverEffect(lowStocksButtonPurchaser);
                lowStocksButtonPurchaser.setOnAction(event -> handleLowStocks());
                addItemButtonPurchaser.setOnAction(event -> handleAddItem());

                try {
                    if (stockControlPurchaserModel != null) {
                        populateTableView(stockControlPurchaserModel.fetchItems());
                    } else {
                        // Handle the case where stockControlPurchaserModel is null
                        System.out.println("Stock Control Purchaser Model is null.");
                    }
                } catch (NotLoggedInException e) {
                    // Show prompt to user not logged in
                    System.out.println("User is not logged in.");
                }
            } else {
                // Handle the case where UI components are null
                System.out.println("Error: Table or button is null. Cannot initialize.");
            }
        }
    }
    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = stockControlPurchaserModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            // Show Prompt
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        if (stockControlPurchaserTable != null && itemNameColumn != null && totalQtyColumn != null) {
            ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
            stockControlPurchaserTable.setItems(observableItems);

            // Make sure the cell value factories are set for the table columns
            itemNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
            totalQtyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalQty()).asObject());
        } else {
            System.out.println("Error: Table or columns are null. Cannot populate table.");
        }
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "items";
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    @FXML
    private void handleAddItem() {
        // Handle sales invoice button action
    }

    @FXML
    private void handleLowStocks() {
        // Handle low stocks button action
    }
}
