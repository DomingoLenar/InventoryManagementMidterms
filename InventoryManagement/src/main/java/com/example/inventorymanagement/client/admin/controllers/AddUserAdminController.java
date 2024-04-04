
package com.example.inventorymanagement.client.admin.controllers;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserAdminController implements Initializable{
        @FXML
        private ComboBox<String> roleComboBox;

        @FXML
        private TextField usernameField;

        @FXML
        private TextField passwordField;
        public ComboBox<String> getRoleComboBox() {
            return roleComboBox;
        }
        public TextField getUsernameField(){
            return usernameField;
        }
        public TextField getPasswordField(){
            return passwordField;
        }

        @Override
        public void initialize(URL location, ResourceBundle resources) {
            roleComboBox.getItems().addAll("Sales Person", "Purchaser");
            roleComboBox.setPromptText("Set Role As...");
            Font font = new Font("Share Tech Mono", 15);
            roleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
        }


