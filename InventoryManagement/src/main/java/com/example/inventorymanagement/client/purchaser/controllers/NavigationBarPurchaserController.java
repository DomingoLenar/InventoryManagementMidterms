package com.example.inventorymanagement.client.purchaser.controllers;

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

public class NavigationBarPurchaserController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneNavigationBarPurchaser;
    @FXML
    private Button stockControlButtonPurchaser;
    @FXML
    private Button profileButtonPurchaser;

    // Reference to the main BorderPane
    private BorderPane mainBorderPane;
    private MainController mainController;
    public NavigationBarPurchaserController() {

    }
    public NavigationBarPurchaserController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.mainController = mainController;
    }


    // Setter for main BorderPane
    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
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
    public BorderPane getBorderPaneNavigationBarPurchaser() { return borderPaneNavigationBarPurchaser;}

    @FXML
    public Button getStockControlButtonPurchaser() { return stockControlButtonPurchaser; }

    @FXML
    public Button getProfileButtonPurchaser() { return profileButtonPurchaser; }

    @FXML
    private void initialize() {
        // Handle button clicks
        stockControlButtonPurchaser.setOnAction(event -> loadStockControlPanel());
        profileButtonPurchaser.setOnAction(event -> loadProfileManagementPanel());

        addHoverEffect(stockControlButtonPurchaser);
        addHoverEffect(profileButtonPurchaser);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void loadStockControlPanel() {
        try {
            mainController.getStockControlPurchaserController().fetchAndUpdate();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadProfileManagementPanel() {
        try {
            BorderPane profileManagementPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagement-view.fxml"));
            mainBorderPane.setRight(profileManagementPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
