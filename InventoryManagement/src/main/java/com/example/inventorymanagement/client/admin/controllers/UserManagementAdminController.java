package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.DashboardAdminModel;
import com.example.inventorymanagement.client.admin.models.UserManagementAdminModel;
import com.example.inventorymanagement.client.admin.views.DashboardAdminPanel;
import com.example.inventorymanagement.client.admin.views.UserManagementAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.objects.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class UserManagementAdminController extends Application implements Initializable, ControllerInterface {

    @FXML
    private BorderPane borderPaneUserManagement;
    @FXML
    private TextField searchTextField;
    @FXML
    private TableView<User> userManagementTableView;
    @FXML
    private Button addUserButton;
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private UserManagementAdminModel userManagementAdminModel;
    private UserManagementAdminPanel userManagementAdminPanel;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


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

    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "UserManagementAdmin";
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addHoverEffect(addUserButton);
        // initialize the panel and model objects
        userManagementAdminPanel = new UserManagementAdminPanel();
        userManagementAdminModel = new UserManagementAdminModel(registry, clientCallback);
    }

    @Override
    public void start(Stage stage) throws Exception {
        userManagementAdminPanel = new UserManagementAdminPanel();
        userManagementAdminPanel.start(stage);
    }
}
