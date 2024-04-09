package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddListingAdminModel;
import com.example.inventorymanagement.client.admin.views.AddItemAdminPanel;
import com.example.inventorymanagement.client.admin.views.AddListingAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AddListingAdminController implements ControllerInterface {
    @FXML
    private TextField itemNameField;
    @FXML
    private Button okButton;
    private AddListingAdminModel addListingAdminModel;
    private MainController mainController;
    boolean initialized = false;

    public TextField getItemNameField() {
        return itemNameField;
    }

    public Button getOkButton() {
        return okButton;
    }
    public AddListingAdminController (){
        //default constructor
    }
    public AddListingAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController){
        this.addListingAdminModel = new AddListingAdminModel(registry, clientCallback);
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        okButton.setOnAction(actionEvent -> {
            try {
                handleOkButton(actionEvent);
            } catch (NotLoggedInException e) {
                showAlert("User not logged in.");
            } catch (OutOfRoleException e) {
                showAlert("User does not have required permission.");
            }
        });
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "Item";
    }
    private void handleOkButton(ActionEvent actionEvent) throws NotLoggedInException, OutOfRoleException {
        if (okButton != null && itemNameField != null) {
            String itemName = itemNameField.getText();
            Item newitem = new Item();
            newitem.setItemName((itemName));
            addListingAdminModel.addListing(newitem);
        } else {
            System.out.println("Button or Field is null. Cannot continue");
        }
    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void initialize() {
        addHoverEffect(okButton);
        okButton.setOnAction(actionEvent -> {
            try {
                handleOkButton(actionEvent);
            } catch (NotLoggedInException e) {
                showAlert("User not logged in.");
            } catch (OutOfRoleException e) {
                showAlert("User does not have required permission");
            }
        });

        addListingAdminModel = new AddListingAdminModel(MainController.registry, MainController.clientCallback);
        if (!initialized){
            initialized = true;
            if (okButton != null && itemNameField != null){
                addHoverEffect(okButton);
                okButton.setOnAction(actionEvent -> {
                    try {
                        if (addListingAdminModel != null){
                            handleOkButton(actionEvent);
                        }else {
                            System.out.println("AddListingAdminModel is null");
                        }
                    } catch (NotLoggedInException e) {
                        showAlert("User not Logged In");
                    } catch (OutOfRoleException e) {
                        showAlert("User does not have required permission.");
                    }
                });
            }
        }
    }
}
