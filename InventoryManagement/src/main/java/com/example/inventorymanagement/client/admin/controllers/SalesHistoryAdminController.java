package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceAdminModel;
import com.example.inventorymanagement.client.admin.models.SalesHistoryAdminModel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.rmi.RemoteException;

public class SalesHistoryAdminController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlAdmin;
    @FXML
    private Button createSalesInvoiceAdminButton;
    @FXML
    private TextField searchFieldAdmin;
    @FXML
    private TableView stockControlAdminTable;
    private SalesHistoryAdminModel salesHistoryAdminModel = new SalesHistoryAdminModel();
    private CreateSalesInvoiceAdminModel createSalesInvoiceAdminModel = new CreateSalesInvoiceAdminModel();

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "";
    }

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() { return borderPaneStockControlAdmin;}

    @FXML
    public Button getcreateSalesInvoiceAdminButton() { return createSalesInvoiceAdminButton; }

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getStockControlAdminTable() { return stockControlAdminTable; }

    public SalesHistoryAdminController() {
    }
    @FXML
    private void initialize() {
        addHoverEffect(createSalesInvoiceAdminButton);

    //    createSalesInvoiceAdminButton.setOnAction(this::handleSalesInvoice);
    }

    //private void handleSalesInvoice (ActionEvent event){
    //    createSalesInvoiceAdminModel.handleSalesInvoice();
    //}
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
