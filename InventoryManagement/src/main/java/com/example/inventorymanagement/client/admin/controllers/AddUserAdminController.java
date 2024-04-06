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
import java.util.LinkedList;
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

    // Reference to the AddUserAdminModel and AddUserAdminPanel
    AddUserAdminModel addUserAdminModel = new AddUserAdminModel();
    AddUserAdminPanel addUserAdminPanel = new AddUserAdminPanel();
    // Declare clientCallback
    private ClientCallback clientCallback;
    private Registry registry;

    // Initialize clientCallback before using it
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
    public void initialize(URL location, ResourceBundle resources) {
        roleComboBox.getItems().addAll("Sales Person", "Purchaser");
        roleComboBox.setPromptText("Set Role As...");
        Font font = new Font("Share Tech Mono", 15);
        roleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        addHoverEffect(saveButton);
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    @Override
    public void start(Stage stage) throws Exception {
        addUserAdminPanel.start(stage);
    }

    // Method that will fetch data from model and insert icontroller runt into ui elements
    public void fetchAndUpdate() throws RemoteException {
    }

    // Method for getting what specific panel is currently on display
    public String getObjectsUsed() throws RemoteException {
        return "User";
    }

    @FXML
    private void handleSaveButtonClick() {
        try {
            // Get the data from the UI elements
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            // Create a new User object
            User newUser = new User(username, password, role);

            // Perform user addition operation using the addUserAdminModel instance
            boolean success = addUserAdminModel.addUser(newUser);

            if (success) {
                throw new RuntimeException("User added successfully.");
            } else {
                throw new RuntimeException("Failed to add user. Make sure to enter a 7 character password or check if the username has duplicates");
            }
        } catch (Exception e) {
            // Handle the exception here (display error message, log, etc.)
            e.printStackTrace();
        }
    }
}


