package com.example.inventorymanagement.client.admin.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

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

    @FXML
    private void initialize() {
        addHoverEffect(financesButtonAdmin);
        addHoverEffect(dashboardButtonAdmin);
        addHoverEffect(stockControlButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(userManagementButtonAdmin);
        addHoverEffect(profileButtonAdmin);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
