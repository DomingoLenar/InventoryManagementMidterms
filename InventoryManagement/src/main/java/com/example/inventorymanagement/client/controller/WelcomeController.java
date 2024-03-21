package com.example.inventorymanagement.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class WelcomeController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label titleText;
    @FXML
    private Button loginButton;

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public Label getTitleText() {
        return titleText;
    }

    public Button getLoginButton() {
        return loginButton;
    }
}
