package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.client.admin.views.StockControlAdminPanel;
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
<<<<<<<<< Temporary merge branch 1
import java.util.LinkedList;

public class StockControlAdminController extends Application implements ControllerInterface {

    @FXML
    private BorderPane borderPaneStockControlAdmin;
    @FXML
    private Button addItemButton;
    @FXML
    private Button lowStocksButton;
    @FXML
    private Button createSalesInvoiceButton;
    @FXML
    private Button addListingButton;
    @FXML
    private TextField searchFieldAdmin;
    @FXML
    private TableView<Item> stockControlAdminTable;
    @FXML
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, Integer> totalQtyColumn;

    private Registry registry;
    private ClientCallback clientCallback;
    private MainController mainController;
    private StockControlAdminModel stockControlAdminModel;
    private StockControlAdminPanel stockControlAdminPanel = new StockControlAdminPanel();
    private boolean initialized = false; // Flag to track initialization

    public StockControlAdminController() {
        // Default constructor
    }

    public StockControlAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        this.clientCallback = clientCallback;
        this.registry = registry;
    }

    public void setStockControlAdminModel(StockControlAdminModel stockControlAdminModel) {
        this.stockControlAdminModel = stockControlAdminModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Start the panel first
        stockControlAdminPanel.start(stage, this);

        // Call initialize after panel is started and model is initialized
        initialize();

        try {
            fetchAndUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        if (!initialized) { // Check if already initialized
            initialized = true; // Set the flag to true

            // Check if UI components are not null
            if (stockControlAdminTable != null && addItemButton != null & lowStocksButton != null & addListingButton != null & createSalesInvoiceButton != null) {
                addHoverEffect(addItemButton);
                addHoverEffect(lowStocksButton);
                addHoverEffect(addListingButton);
                addHoverEffect(createSalesInvoiceButton);
                lowStocksButton.setOnAction(event -> handleLowStocks());
                addItemButton.setOnAction(event -> handleAddItem());
                addListingButton.setOnAction(event -> handleAddListing());
                createSalesInvoiceButton.setOnAction(event -> handleCreateSalesInvoice());

                try {
                    if (stockControlAdminModel != null) {
                        populateTableView(stockControlAdminModel.fetchItems());
                    } else {
                        // Handle the case where stockControlAdminModel is null
                        System.out.println("Stock Control Adminr Model is null.");
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

    @FXML
    private void handleAddItem() {
        // Handle Add Item Button Action
    }

    @FXML
    private void handleLowStocks() {
        // Handle Low Stocks Button Action
    }

    @FXML
    private void handleAddListing() {
        // Handle Add Listing Button Action
    }

    @FXML
    private void handleCreateSalesInvoice() {
        // Handle Create Sales Invoice Button Action
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = stockControlAdminModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            // Show Prompt
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        if (stockControlAdminTable != null && itemNameColumn != null && totalQtyColumn != null) {
            ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
            stockControlAdminTable.setItems(observableItems);

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

    // Getters for FXML components (if needed)

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() {
        return borderPaneStockControlAdmin;
    }

    @FXML
    public Button getAddItemButton() {
        return addItemButton;
    }

    @FXML
    public Button getLowStocksButton() {
        return lowStocksButton;
    }

    @FXML
    public TextField getSearchFieldSAdmin() {
        return searchFieldAdmin;
    }

    @FXML
    public TableView<Item> getStockControlAdminTable() {
        return stockControlAdminTable;
    }

    @FXML
    public TableColumn<Item, String> getItemNameColumn() {
        return itemNameColumn;
    }

    @FXML
    public TableColumn<Item, Integer> getTotalQtyColumn() {
        return totalQtyColumn;
    }
}
