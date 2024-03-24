package com.example.inventorymanagement.client.sales.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class NavigationBarSalesController {
    @FXML
    private BorderPane borderPaneNavigationBarSales;
    @FXML
    private Button stockControlButtonSales;
    @FXML
    private Button salesInvoiceButtonSales;
    @FXML
    private Button financesButtonSales;
    @FXML
    private Button profileButtonSales;

    @FXML
    public BorderPane getBorderPaneNavigationBarSales() { return borderPaneNavigationBarSales;}

    @FXML
    public Button getStockControlButtonSales() { return stockControlButtonSales; }

    @FXML
    public Button getSalesInvoiceButtonSales() { return salesInvoiceButtonSales; }

    @FXML
    public Button getFinancesButtonSales() { return financesButtonSales; }

    @FXML
    public Button getProfileButtonSales() { return profileButtonSales; }

    @FXML
    private void initialize() {
        addHoverEffect(stockControlButtonSales);
        addHoverEffect(salesInvoiceButtonSales);
        addHoverEffect(financesButtonSales);
        addHoverEffect(profileButtonSales);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #967373;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
