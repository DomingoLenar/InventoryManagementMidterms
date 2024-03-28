package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;

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
    private void initialize() {
        addHoverEffect(loginButtonWelcome);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#FFFFFF, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #FFFFFF;"));
    }

    @FXML
    private void handleLoginButtonAction(ActionEvent event) {
        Node source = (Node) event.getSource();
        source.getScene().getWindow().hide();
        MainController.getInstance().showLogin();
    }
}