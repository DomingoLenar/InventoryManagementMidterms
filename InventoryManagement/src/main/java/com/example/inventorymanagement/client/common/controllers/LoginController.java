package com.example.inventorymanagement.client.common.controllers;

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

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        addHoverEffect(loginButtonLogin);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#FFFFFF, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #FFFFFF;"));
    }
}
