package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.LowStocksAdminModel;
import com.example.inventorymanagement.client.admin.views.LowStocksAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class LowStocksAdminController implements ControllerInterface {
    @FXML
    private TableView lowStockTable;
    @FXML
    private TableColumn<Item, String> productColumn;
    @FXML
    private TableColumn<Item, String> quantityColumn;
    private LowStocksAdminModel lowStocksAdminModel;
    private MainController mainController;
    boolean initialized = false;

    public TableView getLowStockTable() {
        return lowStockTable;
    }

    public TableColumn getProductColumn() {
        return productColumn;
    }

    public TableColumn getQuantityColumn() {
        return quantityColumn;
    }

    public LowStocksAdminController(){
        //default constructor
    }
    public LowStocksAdminController(Registry registry, ClientCallback clientCallback){
        this.lowStocksAdminModel = new LowStocksAdminModel(registry, clientCallback);
    }

    /**
     * Method to populate table
     * @param items
     */
    public void populateTableView(LinkedList<Item> items){
        if(lowStockTable != null && productColumn != null && quantityColumn != null){
            ObservableList<Item> observableList = FXCollections.observableArrayList(items);
            lowStockTable.setItems(observableList);

            productColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItemName()));
            quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getTotalQty()).asObject().asString());
        } else {
            System.out.println("Table or columns are null. Cannot populate");
        }
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<Item> items = lowStocksAdminModel.fetchLowStocks();
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
        lowStocksAdminModel = new LowStocksAdminModel(MainController.registry, MainController.clientCallback);
        if (!initialized){
            initialized = true;
            if (lowStockTable != null){
                try{
                    if (lowStocksAdminModel != null){
                        populateTableView(lowStocksAdminModel.fetchLowStocks());
                    } else {
                        System.out.println("Low Stocks Model is null.");
                    }
                } catch (NotLoggedInException e) {
                    System.out.println("User not logged in.");
                }
            } else {
                System.out.println("Table is null. Cannot Initialize");
            }
        }
    }
}
