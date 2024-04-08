package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.ProfileManagementAdminModel;
import com.example.inventorymanagement.client.admin.models.ProfileManagementChangePassAdminModel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.ChangeUserRoleService;
import com.example.inventorymanagement.client.microservices.RemoveUserService;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.client.purchaser.models.StockControlPurchaserModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

import static com.example.inventorymanagement.client.common.controllers.MainController.clientCallback;
import static com.example.inventorymanagement.client.common.controllers.MainController.registry;

public class ProfileManagementAdminController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneProfileManagement;
    @FXML
    private Label profileManagementLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private ComboBox<String> changeUserAccountComboBox;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button logoutButton;
    private ListView<User> userListView;

    // Getters for FXML components

    @FXML
    public BorderPane getBorderPaneProfileManagement() {
        return borderPaneProfileManagement;
    }

    @FXML
    public Label getProfileManagementLabel() {
        return profileManagementLabel;
    }

    @FXML
    public Label getUsernameLabel() {
        return usernameLabel;
    }

    @FXML
    public ComboBox<String> getChangeUserAccountComboBox() {
        return changeUserAccountComboBox;
    }

    @FXML
    public Button getChangePasswordButton() {
        return changePasswordButton;
    }

    @FXML
    public Button getLogoutButton() {
        return logoutButton;
    }

    private ProfileManagementAdminModel profileManagementAdminModel;

    private MainController mainController;

    public ProfileManagementAdminController() {
        //Default constructor
    }

    public ProfileManagementAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.profileManagementAdminModel = new ProfileManagementAdminModel(registry, clientCallback);
    }

    boolean initialized = false;

    public void fetchAndUpdate() throws RemoteException {
        try {
            // Fetch user data from the model
            updateUsernameLabel();
            LinkedList<User> userList = profileManagementAdminModel.fetchListOfUsers(); // Fetch list of users from the model
            if (userList != null && !userList.isEmpty()) {
                // Assuming you want to display the first user in the list
                User user = userList.getFirst();

                // Update UI components with user data
                if (user != null) {
                    usernameLabel.setText(user.getUsername());
                    // You can update other UI components as needed
                } else {
                    System.out.println("User data not available.");
                }
            } else {
                // Handle the case where user list is empty or null
                System.out.println("User list is empty or null.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void changeUserRole() {
        String newRole = changeUserAccountComboBox.getValue();
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        try {
            boolean success = profileManagementAdminModel.changeUserRole(selectedUser, newRole);
            if (success) {
                // Handle successful role change
                showAlert(Alert.AlertType.INFORMATION, "Role Change", "Role changed successfully.");
            } else {
                // Handle unsuccessful role change
                showAlert(Alert.AlertType.ERROR, "Role Change Error", "Failed to change role.");
            }
        } catch (UserExistenceException | OutOfRoleException | NotLoggedInException e) {
            // Handle specific exceptions
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    @FXML
    private void removeUser() {
        User selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a user to remove.");
            return;
        }

        try {
            boolean success = profileManagementAdminModel.removeUser(selectedUser);
            if (success) {
                // Handle successful user removal
                showAlert(Alert.AlertType.INFORMATION, "User Removal", "User removed successfully.");
            } else {
                // Handle unsuccessful user removal
                showAlert(Alert.AlertType.ERROR, "User Removal Error", "Failed to remove user.");
            }
        } catch (UserExistenceException | OutOfRoleException | NotLoggedInException e) {
            // Handle specific exceptions
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }

    // Helper method to display alerts
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    public String getObjectsUsed() throws RemoteException {
        return "user";
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    // Action handlers
    @FXML
    private void handleChangePassword() {
        ProfileManagementChangePassAdminController pManagementCPAC = new ProfileManagementChangePassAdminController(clientCallback, registry);
        pManagementCPAC.setProfileManagementChangePassAdminModel(new ProfileManagementChangePassAdminModel(registry, clientCallback));
        try {
            pManagementCPAC.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleLogout() {
        // Handle logout action
    }

    @FXML
    public void updateUsernameLabel() {
        try {
            usernameLabel.setText(clientCallback.getUser().getUsername());
        } catch (RemoteException e) {
            //Prompt user unable to fetch User object
        }
    }
    // Initialization method

    @FXML
    public void initialize() {
        // Combo box choices
        changeUserAccountComboBox.setPromptText("Change User Account");
        changeUserAccountComboBox.getItems().addAll("Sales", "Purchaser");

        //sout initialize
        System.out.println("initialize");
        addHoverEffect(changePasswordButton);
        addHoverEffect(logoutButton);

        // Add action handlers
        changePasswordButton.setOnAction(event -> handleChangePassword());
        logoutButton.setOnAction(event -> handleLogout());
        profileManagementAdminModel = new ProfileManagementAdminModel(registry, clientCallback);
        if (!initialized) { // Check if already initialized
            initialized = true; // Set the flag to true

            // Check if UI components are not null
            if (changeUserAccountComboBox != null && changePasswordButton != null && logoutButton != null) {
                addHoverEffect(changePasswordButton);
                addHoverEffect(logoutButton);
                changePasswordButton.setOnAction(event -> handleChangePassword());
                logoutButton.setOnAction(event -> handleLogout());

                try {
                    if (profileManagementAdminModel != null) {
                        fetchAndUpdate();
                    } else {
                        // Handle the case where profileManagementAdminModel is null
                        System.out.println("Profile Management Admin Model is null.");
                    }
                } catch (RemoteException e) {
                    // Show prompt to user not logged in
                    System.out.println("User is not logged in.");
                }
            } else {
                // Handle the case where UI components are null
                System.out.println("Error: ComboBox or Button is null. Cannot initialize.");
            }
        }
    }
}


