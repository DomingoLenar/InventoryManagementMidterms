package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.views.AddItemAdminPanel;
import com.example.inventorymanagement.client.admin.views.AddListingAdminPanel;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AddListingAdminController extends Application implements Initializable, ControllerInterface {
    @FXML private TextField itemNameField;
    @FXML private Button okButton;
    private AddListingAdminPanel addItemAdminPanel = new AddListingAdminPanel();

    public TextField getItemNameField() {
        return itemNameField;
    }

    public Button getOkButton() {
        return okButton;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void start(Stage stage) throws Exception{
        addItemAdminPanel.start(stage);
    }
}
