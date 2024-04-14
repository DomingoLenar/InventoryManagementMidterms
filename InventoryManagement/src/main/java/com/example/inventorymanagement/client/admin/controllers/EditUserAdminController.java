package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddItemAdminModel;
import com.example.inventorymanagement.client.admin.models.AddUserAdminModel;
import com.example.inventorymanagement.client.admin.models.EditUserAdminModel;
import com.example.inventorymanagement.client.admin.views.AddItemAdminPanel;
import com.example.inventorymanagement.client.admin.views.AddUserAdminPanel;
import com.example.inventorymanagement.client.admin.views.EditUserAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.ChangeUserRoleService;
import com.example.inventorymanagement.client.microservices.UpdateCallback;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

import static com.example.inventorymanagement.client.common.controllers.MainController.clientCallback;
import static com.example.inventorymanagement.client.common.controllers.MainController.registry;
import static com.example.inventorymanagement.server.model.GSONProcessing.removeUser;

public class EditUserAdminController implements ControllerInterface {

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
    private ProfileManagementChangePassAdminController profileManagementChangePassAdminController;

    private MainController mainController;

    public EditUserAdminController() {

    }

    // getter methods of FXML components
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
    public EditUserAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController){
        this.editUserAdminModel = new EditUserAdminModel(registry, clientCallback);
    }
    public EditUserAdminController(ProfileManagementChangePassAdminController profileManagementChangePassAdminController) {
        this.profileManagementChangePassAdminController = profileManagementChangePassAdminController;
    }

    boolean initialized = false;

    public void fetchAndUpdate() throws RemoteException {

    }

    public String getObjectsUsed() throws RemoteException {
        return "User";
    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void handleChangePasswordButton() {
        // Call the change password logic from ProfileManagementChangePassAdminController
        profileManagementChangePassAdminController.handleSave();
    }
    private void handleDeleteUserButton(){
        try {
            // Get the selected user from the UI or any other source
            User selectedUser = (User) usernameLabel.getUserData(); // Get the selected user

            // Call the removeUser method from the model
            boolean success = editUserAdminModel.removeUser(selectedUser);
            if (success) {
                // User deletion was successful
                showPromptMessage(true);
            } else {
                // User deletion failed
                showPromptMessage(false);
            }
        } catch (RemoteException e) {
            // Handle RemoteException
            e.printStackTrace();
            showPromptMessage(false);
        }
    }

    @FXML
    private void handleSave() {
        // Get the selected user from the UI or any other source
        User selectedUser = (User) usernameLabel.getUserData(); // Get the selected user

        // Get the new role from the combo box
        String newRole = changeRoleComboBox.getValue();

        try {
            // Call the changeUserRole method from the model
            boolean success = editUserAdminModel.changeUserRole(selectedUser, newRole);
            if (success) {
                // Role change was successful
                showPromptMessage(true);
            } else {
                // Role change failed
                showPromptMessage(false);
            }
        } catch (RemoteException e) {
            // Handle RemoteException
            e.printStackTrace();
            showPromptMessage(false);
        }
    }
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {
        changeRoleComboBox.getItems().addAll("Sales Person", "Purchaser");
        changeRoleComboBox.setPromptText("Change Role...");
        Font font = new Font("Share Tech Mono", 15);
        changeRoleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        addHoverEffect(saveButton);
        addHoverEffect(changePasswordButton);
        addHoverEffect(deleteUserButton);

        // action handlers
        saveButton.setOnAction(event -> handleSave());
        changePasswordButton.setOnAction(event -> handleChangePasswordButton());
        deleteUserButton.setOnAction(event -> handleDeleteUserButton());
        editUserAdminModel = new EditUserAdminModel(registry, clientCallback);
        if (!initialized){
            initialized = true;
            try{
                if (editUserAdminModel !=null){
                    fetchAndUpdate();
                }else {
                    System.out.println("Edit User Model is null");
                }
            }catch (RemoteException e){
                System.out.println("User is not logged in");
            }
        } else {
            System.out.println("Error: Save button is null. cannot initialize");
        }
        try {
            clientCallback.setCurrentPanel(this);
            UpdateCallback.process(clientCallback, registry);
        } catch (NotLoggedInException e){
            showAlert("User is not logged in");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

    }

    // Method to show a prompt message
    private void showPromptMessage(boolean success) {
        Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
        alert.setTitle(success ? "Success" : "Error");
        alert.setHeaderText(success ? "User details updated successfully!" : "Failed to update user details");
        alert.showAndWait();
    }

    public void editUser(User selectedUser) {
        // Set the selected user's username to the usernameLabel
        usernameLabel = new Label();

        // Set the selected user's data as user data of the usernameLabel
        usernameLabel.setUserData(selectedUser);

        // Set the selected user's current role as the default value of the changeRoleComboBox
        changeRoleComboBox = new ComboBox<>();
    }
}
