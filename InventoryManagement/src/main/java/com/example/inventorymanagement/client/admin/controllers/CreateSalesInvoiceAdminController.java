package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.views.CreateSalesInvoiceAdminPanel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class CreateSalesInvoiceAdminController extends Application implements ControllerInterface, Initializable {
    @FXML
    ComboBox itemNameComboBox;
    @FXML
    TextField itemQuantityField;
    @FXML
    Label itemPriceLabel;
    private CreateSalesInvoiceAdminPanel createSalesInvoiceAdminPanel = new CreateSalesInvoiceAdminPanel();

    public ComboBox getItemNameComboBox() {
        return itemNameComboBox;
    }

    public TextField getItemQuantityField() {
        return itemQuantityField;
    }

    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @Override
    public void start(Stage stage) throws Exception {
        createSalesInvoiceAdminPanel.start(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
