package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.sales.views.CreateSalesInvoiceSalesPanel;
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

public class CreateSalesInvoiceSalesController extends Application implements ControllerInterface, Initializable {
    @FXML
    ComboBox itemNameComboBox;
    @FXML
    TextField itemQuantityField;
    @FXML
    Label itemPriceLabel;
    private CreateSalesInvoiceSalesPanel createSalesInvoiceSalesPanel = new CreateSalesInvoiceSalesPanel();

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
        createSalesInvoiceSalesPanel.start(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
