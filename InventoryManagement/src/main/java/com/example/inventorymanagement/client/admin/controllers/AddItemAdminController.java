package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddItemAdminModel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AddItemAdminController implements Initializable, ControllerInterface {
    @FXML private TextField itemNameField;
    @FXML private TextField itemPriceField;
    @FXML private TextField quantityField;
    private MainController mainController;
    private ClientCallback clientCallback;
    private UserRequestInterface userService;
    private ItemOrderRequestInterface iOService;
    private ItemRequestInterface itemService;
    private Registry registry;
    private AddItemAdminModel addItemAdminModel;

    public AddItemAdminModel getAddItemAdminModel() {
        return addItemAdminModel;
    }

    public AddItemAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {

    }
    public AddItemAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) {
        this.userService = userService;
        this.iOService = iOService;
        this.itemService = itemService;
        this.registry = registry;
        this.clientCallback = null;
        addItemAdminModel = new AddItemAdminModel(registry, clientCallback);
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public TextField getItemNameField() {
        return itemNameField;
    }

    public TextField getItemPriceField() {
        return itemPriceField;
    }

    public TextField getQuantityField() {
        return quantityField;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }
}
