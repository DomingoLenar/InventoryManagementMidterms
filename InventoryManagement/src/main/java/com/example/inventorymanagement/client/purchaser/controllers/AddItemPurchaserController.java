package com.example.inventorymanagement.client.purchaser.controllers;

import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceAdminModel;
import com.example.inventorymanagement.client.purchaser.models.AddItemPurchaserModel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.UpdateCallback;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.OrderDetail;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.LinkedList;

public class AddItemPurchaserController implements ControllerInterface {
    @FXML
    private ComboBox<Item> itemNameComboBox;
    @FXML
    private ComboBox<String> supplierComboBox;
    @FXML
    private TextField itemPriceField;
    @FXML
    private TextField quantityField;
    @FXML
    private Button okButton;

    private MainController mainController;
    private AddItemPurchaserModel addItemPurchaserModel;
    boolean initialized = false;

    public AddItemPurchaserModel getAddItemPurchaserModel() {
        return addItemPurchaserModel;
    }

    public ComboBox<Item> getItemNameComboBox() {
        return itemNameComboBox;
    }

    public ComboBox<String> getSupplierComboBox() { return supplierComboBox; }

    public TextField getItemPriceField() {
        return itemPriceField;
    }

    public Button getOkButton() { return okButton; }

    public TextField getQuantityField() { return quantityField; }

    public AddItemPurchaserController() {
        // Default Constructor
    }

    public AddItemPurchaserController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController){
        this.addItemPurchaserModel = new AddItemPurchaserModel(registry, clientCallback);
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        try {
            // Fetch list of items and populate the ComboBox
            LinkedList<Item> itemList = addItemPurchaserModel.fetchListOfItems();
            itemNameComboBox.getItems().addAll(itemList);
        } catch (NotLoggedInException e) {
            showAlert("Error occurred while fetching items: " + e.getMessage());
        }
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "item";
    }

    @FXML
    private void handleOkButton(ActionEvent actionEvent) throws NotLoggedInException, OutOfRoleException {
        try {
            if (okButton != null && itemNameComboBox != null && supplierComboBox != null) {
                Item selectedItem = itemNameComboBox.getValue();
                String selectedSupplier = supplierComboBox.getValue();
                float unitPrice = Float.parseFloat(itemPriceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());

                if (selectedItem != null && selectedSupplier != null && quantity > 0 && unitPrice > 0) {
                    // Generate batchNo
                    String currentDate = java.time.LocalDate.now().toString();
                    String batchNo = selectedSupplier + "_" + currentDate + "_" + unitPrice;

                    // Create ItemOrder
                    ItemOrder itemOrder = new ItemOrder();
                    itemOrder.addOrderDetail(new OrderDetail(selectedItem.getItemId(), quantity, unitPrice, batchNo));

                    // Call createPurchaseOrder method from the model
                    boolean orderCreated = addItemPurchaserModel.createPurchaseOrder(itemOrder);
                    if (orderCreated) {
                        showAlert("Item order created successfully.");
                    } else {
                        showAlert("Failed to create item order.");
                    }
                } else {
                    showAlert("Please select an item, supplier, and provide valid quantity and price.");
                }
            } else {
                System.out.println("Button or Field is null. Cannot continue");
            }
        } catch (NumberFormatException e) {
            showAlert("Please provide valid quantity and price.");
        } catch (NotLoggedInException | OutOfRoleException e) {
            showAlert("Error occurred: " + e.getMessage());
        }
    }

    private void fetchSuppliersAndUpdate() throws RemoteException {
        try {
            // Fetch list of suppliers
            ArrayList<String> suppliers = addItemPurchaserModel.fetchSuppliers();

            // Update UI with suppliers data
            Platform.runLater(() -> {
                // Clear existing items in the ComboBox
                supplierComboBox.getItems().clear();

                // Add fetched suppliers to the ComboBox
                supplierComboBox.getItems().addAll(suppliers);
            });

        } catch (NotLoggedInException e) {
            showAlert("Error occurred while fetching suppliers: " + e.getMessage());
        }
    }

    @FXML
    private void handleAddItem() {
        // TODO: Logic
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

    @FXML
    public void initialize() {

        addItemPurchaserModel = new AddItemPurchaserModel(MainController.registry, MainController.clientCallback);

        if (!initialized) {
            addHoverEffect(okButton);
            initialized = true; // Set initialized to true after initialization

            if (okButton != null && itemNameComboBox != null && supplierComboBox != null && itemPriceField != null && quantityField != null) {
                addHoverEffect(okButton);
                try {
                    fetchAndUpdate(); // Fetch and update items list on initialization
                    fetchSuppliersAndUpdate(); // Fetch and update suppliers list on initialization
                } catch (RemoteException e) {
                    showAlert("Error occurred while initializing: " + e.getMessage());
                }
            } else {
                System.out.println("Button or Field is null. Cannot continue");
                return; // Exit the method if UI components are not properly initialized
            }

            okButton.setOnAction(actionEvent -> {
                try {
                    if (addItemPurchaserModel != null) {
                        handleOkButton(actionEvent);
                    } else {
                        System.out.println("CreateSalesInvoicePurchaserModel is null");
                    }
                } catch (NotLoggedInException e) {
                    showAlert("User not logged in.");
                } catch (OutOfRoleException e) {
                    showAlert("User does not have required permission.");
                }
            });
        }
        try {
            MainController.clientCallback.setCurrentPanel(this);
            UpdateCallback.process(MainController.clientCallback, MainController.registry);
        } catch (NotLoggedInException e){
            showAlert("User is not logged in");
        } catch (RemoteException e) {
            System.out.println(e.getMessage());
        }
    }
}
