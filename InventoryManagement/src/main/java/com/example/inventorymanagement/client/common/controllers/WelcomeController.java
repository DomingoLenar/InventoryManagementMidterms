package com.example.inventorymanagement.client.common.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class WelcomeController {
    @FXML
    private BorderPane borderPaneWelcome;
    @FXML
    private Button loginButtonWelcome;

    public BorderPane getBorderPaneWelcome() {
        return borderPaneWelcome;
    }

    public Button getLoginButtonWelcome() {
        return loginButtonWelcome;
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        source.getScene().getWindow().hide();
        MainController.getInstance().showLogin();
    }
}