package com.example.inventorymanagement.client.purchaser.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.purchaser.models.LowStocksPurchaserModel;
import com.example.inventorymanagement.client.purchaser.models.StockControlPurchaserModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class LowStocksPurchaserController implements ControllerInterface {
    //Declarations
    @FXML
    private TextField searchField;
    @FXML
    private TableView lowStocksTable;
    @FXML
    private TableColumn productColumn;
    @FXML
    private TableColumn quantityLeftColumn;
    private LowStocksPurchaserModel lowStocksPurchaserModel;
    private MainController mainController;
    boolean initialized=false;

    //Getters
    public TextField getSearchField() {
        return searchField;
    }
    public TableView getLowStocksTable() {
        return lowStocksTable;
    }
    public TableColumn getProductColumn() {
        return productColumn;
    }
    public TableColumn getQuantityLeftColumn() {
        return quantityLeftColumn;
    }

    public LowStocksPurchaserController(){
        //default constructor
    }
    public LowStocksPurchaserController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController){
        this.lowStocksPurchaserModel = new LowStocksPurchaserModel(registry, clientCallback);
    }

    /**
     * For table population
     * @param items to be populated in the table
     */
    private void populateTableView(LinkedList<Item> items) {
        if (lowStocksTable != null && productColumn != null && quantityLeftColumn != null) {
            ObservableList<Item> observableList = FXCollections.observableArrayList(items);
            lowStocksTable.setItems(observableList);
        } else {
            System.out.println("Error: Table and/or Columns are null. Populate canceled");
        }
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = lowStocksPurchaserModel.fetchLowStocks();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "Item";
    }

    /**
     * Initialize UI Components
     */
    @FXML
    public void initialize(){
        lowStocksPurchaserModel = new LowStocksPurchaserModel(MainController.registry, MainController.clientCallback);
        if (!initialized){
            initialized = true;
            if (lowStocksTable != null){
                try{
                    if (lowStocksPurchaserModel != null){
                        populateTableView(lowStocksPurchaserModel.fetchLowStocks());
                    } else {
                        System.out.println("Low Stocks Model is null.");
                    }
                } catch (NotLoggedInException e) {
                    System.out.println("User not logged in.");
                }
            } else {
                System.out.println("Low Stocks Table is null");
            }
        }
    }
}
