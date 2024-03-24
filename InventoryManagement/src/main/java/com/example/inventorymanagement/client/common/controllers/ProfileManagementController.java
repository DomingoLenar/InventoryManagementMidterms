package com.example.inventorymanagement.client.common.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class ProfileManagementController {
    @FXML
    private BorderPane borderPaneProfileManagement;
    @FXML
    private ImageView personIconImage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label changePasswordLabel;
    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private Button saveButton;

    public BorderPane getBorderPaneProfileManagement() {
        return borderPaneProfileManagement;
    }
    public ImageView getPersonIconImage(){
        return personIconImage;
    }

    public Label getChangePasswordLabel() {
        return changePasswordLabel;
    }

    public Label getEmailLabel() {
        return emailLabel;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getSaveButton() {
        return saveButton;
    }
}
