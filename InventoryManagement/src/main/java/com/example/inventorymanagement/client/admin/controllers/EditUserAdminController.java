package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddItemAdminModel;
import com.example.inventorymanagement.client.admin.models.AddUserAdminModel;
import com.example.inventorymanagement.client.admin.models.EditUserAdminModel;
import com.example.inventorymanagement.client.admin.views.AddItemAdminPanel;
import com.example.inventorymanagement.client.admin.views.AddUserAdminPanel;
import com.example.inventorymanagement.client.admin.views.EditUserAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class EditUserAdminController extends Application implements Initializable, ControllerInterface {

    @FXML
    private BorderPane borderPaneEditUser;
    @FXML
    private Label usernameLabel;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private ComboBox<String> changeRoleComboBox;
    @FXML
    private Button saveButton;
    private EditUserAdminModel editUserAdminModel;
    private EditUserAdminPanel editUserAdminPanel = new EditUserAdminPanel();
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    public void start(Stage stage) throws Exception {
        editUserAdminPanel.start(stage);
    }

    // Constructor to initialize clientCallback and registry
    public EditUserAdminController(ClientCallback clientCallback, Registry registry) {
        this.clientCallback = clientCallback;
        this.registry = registry;
    }

    public BorderPane getBorderPaneEditUser() {
        return borderPaneEditUser;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    public Button getDeleteUserButton() {
        return deleteUserButton;
    }

    public ComboBox<String> getChangeRoleComboBox() {
        return changeRoleComboBox;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        // Implement fetchAndUpdate method
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "EditUser";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeRoleComboBox.getItems().addAll("Sales Person", "Purchaser");
        changeRoleComboBox.setPromptText("Change Role...");
        Font font = new Font("Share Tech Mono", 15);
        changeRoleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        addHoverEffect(saveButton);
        addHoverEffect(changePasswordButton);
        addHoverEffect(deleteUserButton);
        editUserAdminModel = new EditUserAdminModel(registry, clientCallback);
        editUserAdminPanel = new EditUserAdminPanel();
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }


    // Method to show a prompt message
    private void showPromptMessage(boolean success) {
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Success" : "Error");
        alert.setHeaderText(success ? "User details updated successfully!" : "Failed to update user details");
        alert.showAndWait();
    }
    public EditUserAdminController() {
    }

}
