package com.example.inventorymanagement.client.common.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class LoginController {
    @FXML
    private BorderPane borderPaneLogin;
    @FXML
    private Button loginButtonLogin;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    @FXML
    public BorderPane getBorderPaneLogin() {
        return borderPaneLogin;
    }

    @FXML
    public Button getLoginButtonLogin() {
        return loginButtonLogin;
    }

    @FXML
    public TextField getUsernameField() { return usernameField; }

    @FXML
    public TextField getPasswordField() { return passwordField; }
}
