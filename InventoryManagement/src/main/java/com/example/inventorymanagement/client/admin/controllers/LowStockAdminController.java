package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.views.LowStocksAdminPanel;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class LowStockAdminController extends Application {
    @FXML
    TableView lowStockTable;
    @FXML
    TableColumn productColumn;
    @FXML
    TableColumn quantityColumn;

    public TableView getLowStockTable() {
        return lowStockTable;
    }

    public TableColumn getProductColumn() {
        return productColumn;
    }

    public TableColumn getQuantityColumn() {
        return quantityColumn;
    }

    private LowStocksAdminPanel lowStocksAdminPanel = new LowStocksAdminPanel();


    @Override
    public void start(Stage stage) throws Exception {
        lowStocksAdminPanel.start(stage);
    }
}
