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

public class StockControlSalesController extends Application implements ControllerInterface {

    @FXML
    private BorderPane borderPaneStockControlSales;
    @FXML
    private Button createSalesInvoiceButton;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView<Item> stockControlSalesTable;
    @FXML
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, Integer> totalQtyColumn;

    private Registry registry;
    private ClientCallback clientCallback;
    private MainController mainController;
    private StockControlSalesModel stockControlSalesModel;
    private StockControlSalesPanel stockControlSalesPanel = new StockControlSalesPanel();
    private boolean initialized = false; // Flag to track initialization

    public StockControlSalesController() {
        // Default constructor
    }

    public StockControlSalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        this.clientCallback = clientCallback;
        this.registry = registry;
    }

    public void setStockControlSalesModel(StockControlSalesModel stockControlSalesModel) {
        this.stockControlSalesModel = stockControlSalesModel;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Start the panel first
        stockControlSalesPanel.start(stage, this);

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
            if (stockControlSalesTable != null && createSalesInvoiceButton != null) {
                addHoverEffect(createSalesInvoiceButton);
                createSalesInvoiceButton.setOnAction(event -> handleSalesInvoice());

                try {
                    if (stockControlSalesModel != null) {
                        populateTableView(stockControlSalesModel.fetchItems());
                    } else {
                        // Handle the case where stockControlSalesModel is null
                        System.out.println("Stock Control Sales Model is null.");
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
    private void handleSalesInvoice() {
        // Handle sales invoice button action
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = stockControlSalesModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            // Show Prompt
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        if (stockControlSalesTable != null && itemNameColumn != null && totalQtyColumn != null) {
            ObservableList<Item> observableItems = FXCollections.observableArrayList(items);
            stockControlSalesTable.setItems(observableItems);

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
    public BorderPane getBorderPaneStockControlSales() {
        return borderPaneStockControlSales;
    }

    @FXML
    public Button getCreateSalesInvoiceButton() {
        return createSalesInvoiceButton;
    }

    @FXML
    public TextField getSearchFieldSales() {
        return searchFieldSales;
    }

    @FXML
    public TableView<Item> getStockControlSalesTable() {
        return stockControlSalesTable;
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
