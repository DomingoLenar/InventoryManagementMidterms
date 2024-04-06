package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.AlreadyLoggedInException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class LoginController implements ControllerInterface {
    @FXML
    private Button loginButtonLogin;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private MainController mainController;
    private ClientCallback clientCallback;

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
            clientCallback = new ClientCallbackImpl(user);
            clientCallback.setCurrentPanel(this);
            mainController.setClientCallback(clientCallback);
            mainController.getUserService().login(mainController.getClientCallback());
            User currentUser = mainController.getClientCallback().getUser();
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
        // todo: handle the exception using javafx components
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        //
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "user";
    }
}
