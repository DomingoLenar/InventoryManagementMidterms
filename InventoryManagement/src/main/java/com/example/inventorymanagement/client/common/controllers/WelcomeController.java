package com.example.inventorymanagement.client.common.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WelcomeController {
    @FXML
    private Button loginButtonWelcome;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
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
        if (mainController != null) {
            mainController.showLoginPanel();
        }
    }
}
