package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.objects.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class UserManagementAdminController implements Initializable, ControllerInterface {

    @FXML
    private BorderPane borderPaneUserManagement;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> userManagementTableView;
    @FXML
    private Button addUserButton;

    public BorderPane getBorderPaneUserManagement() {
        return borderPaneUserManagement;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getAddUserButton() {
        return addUserButton;
    }

    public TableView<User> getUserManagementTableView() {
        return userManagementTableView;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @FXML
    private void initialize() {
        addHoverEffect(addUserButton);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
