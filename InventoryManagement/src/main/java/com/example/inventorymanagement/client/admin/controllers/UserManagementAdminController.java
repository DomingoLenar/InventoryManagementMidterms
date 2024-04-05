package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.common.controllers.ControllerInterface;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

import java.rmi.RemoteException;

public class UserManagementAdminController implements ControllerInterface {

    @FXML
    private BorderPane borderPaneUserManagement;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView userManagementTableView;
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
    public TableView getUserManagementTableView(){
        return userManagementTableView;
    }
    @Override
    public void fetchAndUpdate() throws RemoteException {
    }
    @Override
    public String getCurrentPanel() throws RemoteException {
        return "UserManagementAdmin"; // Return the name of this panel
    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
