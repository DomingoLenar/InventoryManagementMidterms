package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.util.ControllerInterface;
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

public class ProfileManagementAdminController implements Initializable, ControllerInterface {
    @FXML
    private BorderPane borderPaneProfileManagement;
    @FXML
    private Label profileManagementLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ComboBox changeUserAccountComboBox;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button logoutButton;
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed yet in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    public BorderPane getBorderPaneProfileManagement(){
        return borderPaneProfileManagement;
    }
    public Label getProfileManagementLabel(){
        return profileManagementLabel;
    }
    public Label getUsernameLabel(){
        return usernameLabel;
    }
    public Label getUserEmailLabel(){
        return userEmailLabel;
    }
    public ComboBox getChangeUserAccountComboBox(){
        return changeUserAccountComboBox;
    }
    public Button getLogoutButton(){
        return logoutButton;
    }
    public Button getChangePasswordButton(){
        return changePasswordButton;
    }
    private void initialize() {
        addHoverEffect(logoutButton);
        addHoverEffect(changePasswordButton);

    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    public void initialize(URL location, ResourceBundle resources) {
        changeUserAccountComboBox.setPromptText("Change Role...");
        Font font = new Font("Share Tech Mono", 15);
        changeUserAccountComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
    }
}
