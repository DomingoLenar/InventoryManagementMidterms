package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.FinancesAdminModel;
import com.example.inventorymanagement.client.admin.models.ProfileManagementAdminModel;
import com.example.inventorymanagement.client.admin.views.FinancesAdminPanel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class ProfileManagementAdminController extends Application implements Initializable, ControllerInterface {
    @FXML
    private BorderPane borderPaneProfileManagement;
    @FXML
    private Label profileManagementLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label userEmailLabel;
    @FXML
    private ComboBox changeUserAccountComboBox;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Button logoutButton;
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private ProfileManagementAdminModel profileManagementAdminModel;
    private ProfileManagementAdminPanel profileManagementAdminPanel;
    public void fetchAndUpdate() throws RemoteException {
        // No implementation needed yet in this controller
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "ProfileManagementAdmin";
    }

    public BorderPane getBorderPaneProfileManagement(){
        return borderPaneProfileManagement;
    }
    public Label getProfileManagementLabel(){
        return profileManagementLabel;
    }
    public Label getUsernameLabel(){
        return usernameLabel;
    }
    public Label getUserEmailLabel(){
        return userEmailLabel;
    }
    public ComboBox getChangeUserAccountComboBox(){
        return changeUserAccountComboBox;
    }
    public Button getLogoutButton(){
        return logoutButton;
    }
    public Button getChangePasswordButton(){
        return changePasswordButton;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
    public void initialize(URL location, ResourceBundle resources) {
        changeUserAccountComboBox.setPromptText("Change Role...");
        Font font = new Font("Share Tech Mono", 15);
        changeUserAccountComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");
        addHoverEffect(logoutButton);
        addHoverEffect(changePasswordButton);
        // initialize the model and panel objects
        profileManagementAdminPanel = new ProfileManagementAdminPanel();
        profileManagementAdminModel = new ProfileManagementAdminModel(registry, clientCallback);
    }
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void start(Stage stage) throws Exception {
        profileManagementAdminPanel = new ProfileManagementAdminPanel();
        profileManagementAdminPanel.start(stage);
    }
}
