package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class NavigationBarSalesController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneNavigationBarSales;
    @FXML
    private Button stockControlButtonSales;
    @FXML
    private Button salesHistoryButtonSales;
    @FXML
    private Button profileButtonSales;

    // Reference to the main BorderPane
    private BorderPane mainPane;
    private MainController mainController;
    public NavigationBarSalesController() {

    }
    public NavigationBarSalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.mainController = mainController;
    }

    // Setter for main BorderPane
    public void setMainPane(BorderPane mainPane) {
        this.mainPane = mainPane;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @FXML
    private void initialize() {
        // Handle button clicks
        stockControlButtonSales.setOnAction(event -> loadStockControlPanel());
        salesHistoryButtonSales.setOnAction(event -> loadSalesHistoryPanel());
        profileButtonSales.setOnAction(event -> loadProfileManagementPanel());

        addHoverEffect(stockControlButtonSales);
        addHoverEffect(salesHistoryButtonSales);
        addHoverEffect(profileButtonSales);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void loadStockControlPanel() {
        try {
            MainController.getStockControlSalesController().fetchAndUpdate(); // triggered when btn stock control is click
            mainPane.setRight(MainController.getStockControlSalesPanel()); // get the refresh components of stock control of sales
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSalesHistoryPanel() {
        // TODO: Add Getters
    }

    private void loadProfileManagementPanel() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagement-view.fxml"));
            BorderPane profileManagementPane = loader.load();
            mainPane.setRight(profileManagementPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public BorderPane getBorderPaneNavigationBarSales() { return borderPaneNavigationBarSales;}

    @FXML
    public Button getStockControlButtonSales() { return stockControlButtonSales; }

    @FXML
    public Button getSalesHistoryButtonSales() { return salesHistoryButtonSales;}

    @FXML
    public Button getProfileButtonSales() { return profileButtonSales; }
}
