package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.exceptions.AlreadyLoggedInException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

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
    @FXML
    public void OnLogin(ActionEvent actionEvent) throws IOException, UserExistenceException, AlreadyLoggedInException { // TODO: validate by server rmi
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password, null);
        mainController.setClientCallback(new ClientCallbackImpl(user));
        mainController.getUserService().login(mainController.getClientCallback());

        Node source = (Node) actionEvent.getSource();
        source.getScene().getWindow().hide();
        mainController.displayAdminMainMenu();
    }
}
