package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.common.controllers.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.rmi.RemoteException;

import javafx.fxml.FXMLLoader;

public class NavigationBarAdminController implements ControllerInterface {
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

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }
    @Override
    public String getCurrentPanel() throws RemoteException {
        return "NavigationBarAdmin"; // Return the name of this panel
    }

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
        dashboardButtonAdmin.setOnAction(event -> loadDashboardAdminPanel());
        profileButtonAdmin.setOnAction(event -> loadProfileManagementPanel());
        //salesInvoiceButtonAdmin.setOnAction(event -> loadSalesInvoicePanel);

        addHoverEffect(dashboardButtonAdmin);
        addHoverEffect(stockControlButtonAdmin);
        addHoverEffect(financesButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(userManagementButtonAdmin);
        addHoverEffect(profileButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
    }

    private void loadProfileManagementPanel() {
        try {
            BorderPane profileManagementPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagement-view.fxml"));
            mainBorderPane.setRight(profileManagementPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    private void loadDashboardAdminPanel() {
        // Load Dashboard panel
        try {
            BorderPane dashboardAdminPanel = FXMLLoader.load(getClass().getResource("/com/example/inventorymanagement/client/view/dashboard/dashboardAdmin-view.fxml"));
            mainBorderPane.setRight(dashboardAdminPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
