package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.admin.models.ProfileManagementAdminModel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementAdminPanel;
import com.example.inventorymanagement.client.common.models.ProfileManagementChangePassModel;
import com.example.inventorymanagement.client.common.views.ProfileManagementChangePassPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

// todo: handle action event and submit it to rmi server
public class ProfileManagementChangePassController extends Application implements Initializable, ControllerInterface {
    @FXML
    private BorderPane borderPaneProfileManagementChangePass;
    @FXML
    private ImageView personIconImage;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label changePasswordLabel;
    @FXML
    private TextField oldPasswordTextField;
    @FXML
    private TextField newPasswordTextField;
    @FXML
    private Button saveButton;
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private ProfileManagementChangePassModel profileManagementChangePassModel;
    private ProfileManagementChangePassPanel profileManagementChangePassPanel = new ProfileManagementChangePassPanel();
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public BorderPane getBorderPaneProfileManagementChangePass() {
        return borderPaneProfileManagementChangePass;
    }
    public ImageView getPersonIconImage(){
        return personIconImage;
    }

    public Label getChangePasswordLabel() {
        return changePasswordLabel;
    }
    public TextField getOldPasswordTextField(){
        return oldPasswordTextField;
    }
    public TextField getNewPasswordTextField(){
        return newPasswordTextField;
    }

    public Label getEmailLabel() {
        return emailLabel;
    }

    public Label getUsernameLabel() {
        return usernameLabel;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public void fetchAndUpdate() throws RemoteException {
    }

    public String getObjectsUsed() throws RemoteException {
        return "ProfileManagementChangePass"; // Return the name of this panel
    }

    @Override
    public void start(Stage stage) throws Exception {
        profileManagementChangePassPanel = new ProfileManagementChangePassPanel();
        profileManagementChangePassPanel.start(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize the model and panel objects
        profileManagementChangePassPanel = new ProfileManagementChangePassPanel();
        profileManagementChangePassModel = new ProfileManagementChangePassModel(registry, clientCallback);
    }
}

