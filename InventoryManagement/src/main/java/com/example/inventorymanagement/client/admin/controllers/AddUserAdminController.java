package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddUserAdminModel;
import com.example.inventorymanagement.client.admin.views.AddUserAdminPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.objects.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AddUserAdminController extends Application implements Initializable, ControllerInterface {

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button saveButton;

    private AddUserAdminModel addUserAdminModel;
    private AddUserAdminPanel addUserAdminPanel;
    private ClientCallback clientCallback;
    private Registry registry;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.getItems().addAll("Sales Person", "Purchaser");
        roleComboBox.setPromptText("Set Role As...");
        Font font = new Font("Share Tech Mono", 15);
        roleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        addHoverEffect(saveButton);
        addUserAdminModel = new AddUserAdminModel(registry, clientCallback);
        addUserAdminPanel = new AddUserAdminPanel();
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        addUserAdminPanel.start(stage);
    }

    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "User";
    }

    @FXML
    private void handleSaveButtonClick() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();
            User newUser = new User(username, password, role);
            boolean success = addUserAdminModel.addUser(newUser);
            if (success) {
                throw new RuntimeException("User added successfully.");
            } else {
                throw new RuntimeException("Failed to add user. Make sure to enter a 7 character password or check if the username has duplicates");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
