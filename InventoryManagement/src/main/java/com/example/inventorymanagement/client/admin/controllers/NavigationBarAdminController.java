package com.example.inventorymanagement.client.admin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import javafx.fxml.FXMLLoader;

public class NavigationBarAdminController {
    @FXML
    private BorderPane borderPaneNavigationBarAdmin;
    @FXML
    private Button dashboardButtonAdmin;
    @FXML
    private Button stockControlButtonAdmin;
    @FXML
    private Button financesButtonAdmin;
    @FXML
    private Button salesInvoiceButtonAdmin;
    @FXML
    private Button userManagementButtonAdmin;
    @FXML
    private Button profileButtonAdmin;

    @FXML
    public BorderPane getBorderPaneNavigationBarAdmin() { return borderPaneNavigationBarAdmin;}

    @FXML
    public Button getDashboardButtonAdmin() { return dashboardButtonAdmin; }

    @FXML
    public Button getStockControlButtonAdmin() { return stockControlButtonAdmin; }

    @FXML
    public Button getFinancesButtonAdmin() { return financesButtonAdmin;}

    @FXML
    public Button getSalesInvoiceButtonAdmin() { return salesInvoiceButtonAdmin;}

    @FXML
    public Button getUserManagementButtonAdmin() { return userManagementButtonAdmin; }

    @FXML
    public Button getProfileButtonAdmin() { return profileButtonAdmin; }

    // Reference to the main BorderPane
    private BorderPane mainBorderPane;

    // Setter for main BorderPane
    public void setMainBorderPane(BorderPane mainBorderPane) {
        this.mainBorderPane = mainBorderPane;
    }

    @FXML
    private void initialize() {
        // Handle button clicks
        stockControlButtonAdmin.setOnAction(event -> loadStockControlPanel());
        financesButtonAdmin.setOnAction(event -> loadFinancesPanel());
        userManagementButtonAdmin.setOnAction(event -> loadUserManagementPanel());


        addHoverEffect(dashboardButtonAdmin);
        addHoverEffect(stockControlButtonAdmin);
        addHoverEffect(financesButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(userManagementButtonAdmin);
        addHoverEffect(profileButtonAdmin);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void loadStockControlPanel() {
        // Load Stock Control panel
        try {
            BorderPane stockControlPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlAdmin-view.fxml"));
            mainBorderPane.setRight(stockControlPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFinancesPanel() {
        // Load Finances panel
        try {
            BorderPane financesPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/finances/financesAdmin-view.fxml"));
            mainBorderPane.setRight(financesPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUserManagementPanel() {
        // Load User Management panel
        try {
            BorderPane financesPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/userManagement/userManagementAdmin-view.fxml"));
            mainBorderPane.setRight(financesPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
