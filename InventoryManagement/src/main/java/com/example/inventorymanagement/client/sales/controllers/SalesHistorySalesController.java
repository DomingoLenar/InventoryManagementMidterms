package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.sales.models.SalesHistorySalesModel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.UpdateCallback;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.OrderDetail;
import com.example.inventorymanagement.util.objects.Stock;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class SalesHistorySalesController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneSalesHistorySales;
    @FXML
    private Button createSalesInvoiceSalesButton;
    @FXML
    private TextField searchFieldSales;
    @FXML
    private TableView salesHistorySalesTable;
    @FXML
    private TableColumn<ItemOrder, String> productColumn;
    @FXML
    private TableColumn<ItemOrder, Integer> quantityColumn;
    @FXML
    private TableColumn<ItemOrder, Float> priceColumn;
    @FXML
    private TableColumn<ItemOrder, Float> costColumn;
    @FXML
    private TableColumn<ItemOrder, String> supplierColumn;
    @FXML
    private TableColumn<ItemOrder, String> dateColumn;

    @FXML
    public BorderPane getBorderPaneSalesHistorySales() {
        return borderPaneSalesHistorySales;
    }

    @FXML
    public Button getcreateSalesInvoiceSalesSalesButton() {
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

    public TableColumn getProductColumn() {
        return productColumn;
    }

    public TableColumn getQuantityColumn() {
        return quantityColumn;
    }

    public TableColumn getPriceColumn() {
        return priceColumn;
    }

    public TableColumn getCostColumn() {
        return costColumn;
    }

    public TableColumn getSupplierColumn() {
        return supplierColumn;
    }

    public TableColumn getDateColumn() {
        return dateColumn;
    }

    private MainController mainController;
    private SalesHistorySalesModel salesHistorySalesModel;
    public SalesHistorySalesController() {

    }

    public SalesHistorySalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.salesHistorySalesModel = new SalesHistorySalesModel(registry, clientCallback);
    }

    boolean initialized = false;

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<ItemOrder> itemOrders = salesHistorySalesModel.fetchItems();
            populateTableView(itemOrders);
        } catch (NotLoggedInException e) {
            showAlert("Error occurred while fetching items: " + e.getMessage());
        }
    }

    private void populateTableView(LinkedList<ItemOrder> itemOrders) {
        ObservableList<ItemOrder> observableItems = FXCollections.observableArrayList(itemOrders);
        salesHistorySalesTable.setItems(observableItems);

        productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getOrderDetails().get(0).getItemId())));
        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOrderDetails().get(0).getQty()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getOrderDetails().get(0).getUnitPrice()).asObject());
        costColumn.setCellValueFactory(cellData -> {
            ItemOrder order = cellData.getValue();
            float totalCost = order.getOrderDetails().stream()
                    .map(detail -> detail.getQty() * detail.getUnitPrice())
                    .reduce(0.0f, Float::sum);
            return new SimpleFloatProperty(totalCost).asObject();
        });
        supplierColumn.setCellValueFactory(cellData -> {
            OrderDetail orderDetail = cellData.getValue().getOrderDetails().get(0);
            String batchNo = orderDetail.getBatchNo();
            Stock stock = findStockByBatchNo(batchNo);
            return new SimpleStringProperty(stock != null ? stock.getSupplier() : "Supplier Not Found");
        });
        dateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate()));
    }

    private Stock findStockByBatchNo(String batchNo) {
        try {
            LinkedList<ItemOrder> itemOrders = salesHistorySalesModel.fetchItems();
            for (ItemOrder itemOrder : itemOrders) {
                for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
                    if (orderDetail.getBatchNo().equals(batchNo)) {
                        return findStockByBatchNoInItemOrder(batchNo, itemOrder);
                    }
                }
            }
        } catch (NotLoggedInException e) {
            showAlert("User is not logged in");
        }
        return null;
    }

    private Stock findStockByBatchNoInItemOrder(String batchNo, ItemOrder itemOrder) {
        try {
            for (OrderDetail orderDetail : itemOrder.getOrderDetails()) {
                if (orderDetail.getBatchNo().equals(batchNo)) {
                    // Assuming the batch number uniquely identifies a stock
                    return new Stock(batchNo, orderDetail.getQty(), orderDetail.getUnitPrice(), 0, "", ""); // Create a temporary stock with available information
                }
            }
        } catch (Exception e) {
            // Handle any other exceptions
            showAlert("Error finding stock by batch number in item order: " + e.getMessage());
        }
        return null; // Return null if stock with batchNo is not found or if an exception occurs
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "ItemOrder";
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleSalesInvoice() {
        // Handle the event when the sales invoice button is clicked
    }

    @FXML
    public void initialize() { // initialize components -> better approach is to initialize just the components and let nav___bar buttons handle the population of data/realtime
        addHoverEffect(createSalesInvoiceSalesButton);

        createSalesInvoiceSalesButton.setOnAction(event -> handleSalesInvoice());
        salesHistorySalesModel = new SalesHistorySalesModel(MainController.registry, MainController.clientCallback);
        if (!initialized) { // Check if already initialized
            initialized = true; // Set the flag to true

            // Check if UI components are not null
            if (salesHistorySalesTable != null && createSalesInvoiceSalesButton != null) {
                addHoverEffect(createSalesInvoiceSalesButton);
                createSalesInvoiceSalesButton.setOnAction(event -> handleSalesInvoice());

                try {
                    if (salesHistorySalesModel != null) {
                        populateTableView(salesHistorySalesModel.fetchItems());
                    } else {
                        // Handle the case where salesHistorySalesModel is null
                        System.out.println("Sales History Sales Model is null.");
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
        try {
            MainController.clientCallback.setCurrentPanel(this);
            UpdateCallback.process(MainController.clientCallback, MainController.registry);
        } catch (NotLoggedInException e){
            showAlert("User is not logged in");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
