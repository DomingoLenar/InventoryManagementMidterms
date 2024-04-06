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

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginController {
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
    public void OnLogin(ActionEvent actionEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password, null);
        try {
            mainController.setClientCallback(new ClientCallbackImpl(user));
            User currentUser = mainController.getUserService().login(mainController.getClientCallback());
            String role = currentUser.role;

            Node source = (Node) actionEvent.getSource();
            source.getScene().getWindow().hide();

            switch (role) {
                case "admin":
                    mainController.displayAdminMainMenu();
                    break;
                case "sales":
                    mainController.displaySalesMainMenu();
                    break;
                case "purchaser":
                    mainController.displayPurchaserMainMenu();
                    break;
            }
        } catch (RemoteException remoteException) {
            throw new RuntimeException("RMI Server error");
        } catch (UserExistenceException userExistenceException) {

        } catch (AlreadyLoggedInException alreadyLoggedInException) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
