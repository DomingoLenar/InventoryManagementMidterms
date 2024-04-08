package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.ProfileManagementAdminModel;
import com.example.inventorymanagement.client.admin.models.ProfileManagementChangePassAdminModel;
import com.example.inventorymanagement.client.admin.views.ProfileManagementChangePassAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;
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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

// todo: handle action event and submit it to rmi server
public class ProfileManagementChangePassAdminController extends Application implements Initializable, ControllerInterface {
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
    private ProfileManagementChangePassAdminModel profileManagementChangePassAdminModel;
    private ProfileManagementChangePassAdminPanel profileManagementChangePassAdminPanel = new ProfileManagementChangePassAdminPanel();

    public ProfileManagementChangePassAdminController(){

    }

    public ProfileManagementChangePassAdminController(ClientCallback callback, Registry registry){
        this.clientCallback = callback;
        this.registry = registry;
    }

    public void setProfileManagementChangePassAdminModel(ProfileManagementChangePassAdminModel model){
        this.profileManagementChangePassAdminModel = model;
    }
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
        return "user"; // Return the name of this panel
    }

    @Override
    public void start(Stage stage) throws Exception {
        populateTestVariables();
        profileManagementChangePassAdminPanel = new ProfileManagementChangePassAdminPanel();
        profileManagementChangePassAdminPanel.start(stage, this);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize the model and panel objects
        profileManagementChangePassAdminPanel = new ProfileManagementChangePassAdminPanel();
        profileManagementChangePassAdminModel = new ProfileManagementChangePassAdminModel(registry, clientCallback);

        saveButton.setOnAction(actionEvent -> {
            handleSave();
        });

        try {
            usernameLabel.setText(clientCallback.getUser().getUsername());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleSave(){
        try{
            User currentUser = clientCallback.getUser();
            if(currentUser.getPassword().equals(oldPasswordTextField.getText())){
                try {
                    System.out.println(profileManagementChangePassAdminModel.changePassword(currentUser, newPasswordTextField.getText()));
                }catch(OutOfRoleException e){
                    //Prompt user with the message of e.getMessage();
                }catch (NotLoggedInException e){
                    //Refer to outofroleexception
                }catch (UserExistenceException e){
                    //Refer to outofroleexception
                }
            }
        }catch (RemoteException e){
            //Prompt error fetching current session user
        }
    }

    public void populateTestVariables(){
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 2018);
            ClientCallbackImpl callback = new ClientCallbackImpl(new User("testadmin", "admintest", "admin"));
            this.registry = reg;
            this.clientCallback = callback;
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}

