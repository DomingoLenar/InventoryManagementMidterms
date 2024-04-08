package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.admin.models.ProfileManagementChangePassAdminModel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementChangePassAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.sales.models.ProfileManagementChangePassSalesModel;
import com.example.inventorymanagement.client.sales.views.ProfileManagementChangePassSalesPanel;
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
public class ProfileManagementChangePassSalesController extends Application implements Initializable, ControllerInterface {
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
    private ProfileManagementChangePassSalesModel profileManagementChangePassSalesModel;
    private ProfileManagementChangePassSalesPanel profileManagementChangePassSalesPanel = new ProfileManagementChangePassSalesPanel();
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
        profileManagementChangePassSalesPanel = new ProfileManagementChangePassSalesPanel();
        profileManagementChangePassSalesPanel.start(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize the model and panel objects
        profileManagementChangePassSalesPanel = new ProfileManagementChangePassSalesPanel();
        profileManagementChangePassSalesModel = new ProfileManagementChangePassSalesModel(registry, clientCallback);
    }
}


