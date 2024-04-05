package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.common.controllers.ControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class EditUserAdminController implements Initializable, ControllerInterface {

    @FXML
    private BorderPane borderPaneEditUser;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private ComboBox<String> changeRoleComboBox;
    private Button saveButton;

    public BorderPane getBorderPaneEditUser() {
        return borderPaneEditUser;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    public Button getDeleteUserButton() {
        return deleteUserButton;
    }

    public ComboBox<String> getChangeRoleComboBox() {
        return changeRoleComboBox;
    }
    public Button getSaveButton(){
        return saveButton;
    }
    @Override
    public void fetchAndUpdate() throws RemoteException {
    }
    @Override
    public String getCurrentPanel() throws RemoteException {
        return "EditUserAdmin"; // Return the name of this panel
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeRoleComboBox.getItems().addAll("Sales Person", "Purchaser");
        changeRoleComboBox.setPromptText("Change Role...");
        Font font = new Font("Share Tech Mono", 15);
        changeRoleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
    }
    @FXML
    private void initialize() {
        addHoverEffect(saveButton);
    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}


