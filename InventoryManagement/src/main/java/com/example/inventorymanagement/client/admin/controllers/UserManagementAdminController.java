package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.DashboardAdminModel;
import com.example.inventorymanagement.client.admin.models.UserManagementAdminModel;
import com.example.inventorymanagement.client.admin.views.DashboardAdminPanel;
import com.example.inventorymanagement.client.admin.views.UserManagementAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.UpdateCallback;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class UserManagementAdminController implements ControllerInterface {

    @FXML
    private BorderPane borderPaneUserManagement;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> userManagementTableView;
    @FXML
    private Button addUserButton;
    @FXML
    private TableColumn<User, String> nameTableColumn;
    @FXML
    private TableColumn<User, String> roleTableColumn;
    private MainController mainController;

    private UserManagementAdminModel userManagementAdminModel;
    private AddUserAdminController addUserAdminController;
    private EditUserAdminController editUserAdminController;


    public BorderPane getBorderPaneUserManagement() {
        return borderPaneUserManagement;
    }

    public TextField getSearchTextField() {
        return searchTextField;
    }

    public Button getAddUserButton() {
        return addUserButton;
    }

    public TableView<User> getUserManagementTableView() {
        return userManagementTableView;
    }


    public void setMainController(MainController mainController){
        this.mainController = mainController;
    }

    public UserManagementAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController){
        this.userManagementAdminModel = new UserManagementAdminModel(registry,clientCallback);
        this.mainController = mainController;
    }
    boolean initialized = false;

    public UserManagementAdminController() {
        // Default constructor
    }

    public void fetchAndUpdate() throws RemoteException {
        try {
            LinkedList<User> users = userManagementAdminModel.fetchListOfUsers();
            populateTableView(users);
        } catch (Exception e) {
            showAlert("Error occured while fetching users: " + e.getMessage());
        }
    }

    private void populateTableView(LinkedList<User> users) {
        if (userManagementTableView != null && nameTableColumn != null && roleTableColumn != null) {
            ObservableList<User> observableList = FXCollections.observableArrayList(users);
            userManagementTableView.setItems(observableList); // Set items to populate the TableView

            nameTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            roleTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        } else {
            System.out.println("Error: Table or columns are null. Cannot populate table");
        }
    }


    @Override
    public String getObjectsUsed() throws RemoteException {
        return "user";
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleSave() {
        if (mainController != null) {
            mainController.openAddUserAdminPanel();
        } else {
            System.out.println("MainController is not set.");
        }
    }

    private void handleRowClick() {
        if (mainController !=null){
            mainController.openEditUserAdminPanel();
        } else {
            System.out.println("Main Controller is not set.");
        }
        User selectedUser = userManagementTableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Call the editUser method of the editUserAdminController class

            editUserAdminController.editUser(selectedUser);
        }
    }

    @FXML
    public void initialize() {
        addHoverEffect(addUserButton);
        // initialize the panel and model objects

        addUserButton.setOnAction(event -> handleSave());
        // Add listener to handle row selection events
        userManagementTableView.setOnMouseClicked(event -> handleRowClick());
        userManagementAdminModel = new UserManagementAdminModel(MainController.registry, MainController.clientCallback);
        if (!initialized) {
            initialized = true;

            // check ui components if not null
            if (userManagementTableView != null & addUserButton != null) {
                addHoverEffect(addUserButton);
                addUserButton.setOnAction(event -> handleSave());

                try {
                    if (userManagementAdminModel != null) {
                        populateTableView(userManagementAdminModel.fetchListOfUsers());
                    } else {
                        System.out.println("User Management Admin Model is null");
                    }
                } catch (NotLoggedInException e) {
                    showAlert("User is not logged in.");
                }catch (OutOfRoleException e){
                    showAlert("User is not allowed to access data.");
                }
            } else {
                System.out.println("Error: Table or button is null. Cannot initialize.");
            }
        }
        try {
            MainController.clientCallback.setCurrentPanel(this);
            UpdateCallback.process(MainController.clientCallback, MainController.registry);
        } catch (NotLoggedInException e) {
            showAlert("User is not logged in");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }

        // Initialize editUserAdminController here
        editUserAdminController = new EditUserAdminController();
    }
}

