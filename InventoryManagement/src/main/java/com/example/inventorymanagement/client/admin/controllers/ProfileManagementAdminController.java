package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.ProfileManagementAdminModel;
import com.example.inventorymanagement.client.admin.models.ProfileManagementChangePassAdminModel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.ChangeUserRoleService;
import com.example.inventorymanagement.client.microservices.RemoveUserService;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.objects.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class ProfileManagementAdminController extends Application implements ControllerInterface {
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
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private ProfileManagementAdminModel profileManagementAdminModel;
    private ProfileManagementAdminPanel profileManagementAdminPanel;
    private ChangeUserRoleService changeUserRoleService;
    private RemoveUserService removeUserService;
    private Stage primaryStage;


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }
    public ProfileManagementAdminController() {
        // Default Constructor
    }

    public ProfileManagementAdminController(ClientCallback clientCallback, Registry registry) {
        this.changeUserRoleService = new ChangeUserRoleService();
        this.removeUserService = new RemoveUserService();
        this.clientCallback = clientCallback;
        this.registry = registry;
    }


    public void setProfileManagementAdminModel(ProfileManagementAdminModel profileManagementAdminModel) {
        this.profileManagementAdminModel = profileManagementAdminModel;
    }

    @Override
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


    @Override
    public String getObjectsUsed() throws RemoteException {
        return "ProfileManagementAdmin";
    }

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

    // Initialization method

    @FXML
    public void initialize() {
        changeUserAccountComboBox.setPromptText("Change User Account");
        changeUserAccountComboBox.getItems().addAll("Sales", "Purchaser");

        // Add action handlers
        changePasswordButton.setOnAction(event -> handleChangePassword());
        logoutButton.setOnAction(event -> handleLogout());

        // initialize the model and panel objects
        profileManagementAdminModel = new ProfileManagementAdminModel(registry, clientCallback);
        profileManagementAdminPanel = new ProfileManagementAdminPanel();
    }
    public void start(Stage stage) throws Exception {
        populateTestVariables();
        profileManagementAdminPanel = new ProfileManagementAdminPanel();
        profileManagementAdminPanel.start(stage, this);
        updateUsernameLabel();

        // Create an instance of NavigationBarAdminController
        NavigationBarAdminController navBarController = new NavigationBarAdminController();

    }


    public static void main(String[] args) {
        launch(args);
    }
    // Action handlers

    private void handleChangePassword() {
        ProfileManagementChangePassAdminController pManagementCPAC = new ProfileManagementChangePassAdminController(clientCallback, registry);
        pManagementCPAC.setProfileManagementChangePassAdminModel(new ProfileManagementChangePassAdminModel(registry,clientCallback));
        try {
            pManagementCPAC.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleLogout() {
        // Handle logout action
    }

    public void updateUsernameLabel(){
        try {
            usernameLabel.setText(clientCallback.getUser().getUsername());
        }catch(RemoteException e){
            //Prompt user unable to fetch User object
        }
    }

    public void populateTestVariables() throws RemoteException {
        Registry testReg = LocateRegistry.getRegistry("localhost", 2018);
        this.registry = testReg;
        ClientCallbackImpl callback = new ClientCallbackImpl(new User("testadmin","admintest","admin"));
        this.clientCallback = callback;
    }
}
