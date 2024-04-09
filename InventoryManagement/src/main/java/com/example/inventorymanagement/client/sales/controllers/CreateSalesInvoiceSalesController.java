package com.example.inventorymanagement.client.sales.controllers;

import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.sales.models.CreateSalesInvoiceSalesModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.OrderDetail;
import com.example.inventorymanagement.util.objects.Stock;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class CreateSalesInvoiceSalesController implements ControllerInterface {
    @FXML
    private ComboBox<Item> itemNameComboBox;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private Button okButton;

    boolean initialized = false;

    private CreateSalesInvoiceSalesModel createSalesInvoiceSalesModel;
    private MainController mainController;

    public ComboBox getItemNameComboBox() {
        return itemNameComboBox;
    }

    public TextField getItemQuantityField() {
        return itemQuantityField;
    }

    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }

    public CreateSalesInvoiceSalesController() {
        // Default Constructor
    }

    public CreateSalesInvoiceSalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.createSalesInvoiceSalesModel = new CreateSalesInvoiceSalesModel(registry, clientCallback);
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

        // Fetch and update data from the model
        try {
            // Fetch list of items and populate the ComboBox
            LinkedList<Item> itemList = createSalesInvoiceSalesModel.fetchListOfItems();
            itemNameComboBox.getItems().addAll(itemList);
        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        }
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "Item";
    }

    @FXML
    private void handleOkButton(ActionEvent actionEvent) throws NotLoggedInException, OutOfRoleException {
        try {
            validateAndProcessSalesInvoice();
        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        } catch (OutOfRoleException e) {
            showAlert("User does not have required permission.");
        } catch (Exception e) {
            showAlert("Error processing sales invoice: " + e.getMessage());
        }
    }

    private void validateAndProcessSalesInvoice() throws NotLoggedInException, OutOfRoleException {
        Item selectedItem = getSelectedItem();
        Stock selectedStock = getSelectedStock(selectedItem);
        if (selectedStock == null) {
            return; // showAlert method will already be called inside getSelectedStock method
        }

        int quantity = getQuantity();
        if (quantity <= 0) {
            showAlert("Quantity must be greater than zero.");
            return;
        }

        float totalPrice = calculateTotalPrice(selectedStock.getPrice(), quantity);
        updateItemPriceLabel(totalPrice);
        ItemOrder itemOrder = createItemOrder(selectedItem, quantity, selectedStock.getPrice());
        createSalesInvoice(itemOrder);
    }

    private Item getSelectedItem() {
        Item selectedItem = itemNameComboBox.getValue();
        if (selectedItem == null) {
            showAlert("Please select an item.");
        }
        return selectedItem;
    }

    private Stock getSelectedStock(Item selectedItem) {
        if (selectedItem == null) {
            return null;
        }
        LinkedList<Stock> stocks = selectedItem.getStocks();
        if (stocks.isEmpty()) {
            showAlert("No stock available for selected item.");
            return null;
        }
        return stocks.getFirst();
    }

    private int getQuantity() {
        if (itemQuantityField.getText().trim().isEmpty()) {
            showAlert("Please enter quantity.");
            return -1;
        }
        try {
            return Integer.parseInt(itemQuantityField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Please enter a valid quantity.");
            return -1;
        }
    }

    private float calculateTotalPrice(float unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    private void updateItemPriceLabel(float totalPrice) {
        itemPriceLabel.setText(String.format("%.2f", totalPrice));
    }

    private ItemOrder createItemOrder(Item selectedItem, int quantity, float unitPrice) {
        OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), quantity, unitPrice, "");
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.addOrderDetail(orderDetail);
        return itemOrder;
    }

    private void createSalesInvoice(ItemOrder itemOrder) throws NotLoggedInException, OutOfRoleException {
        if (createSalesInvoiceSalesModel.createSalesInvoice(itemOrder)) {
            showAlert("Sales invoice created successfully.");
        } else {
            showAlert("Failed to create sales invoice.");
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

    @FXML
    public void initialize() {
        if (!initialized) {
            addHoverEffect(okButton);
            initialized = true; // Set initialized to true after initialization

            if (okButton != null && itemNameComboBox != null && itemQuantityField != null && itemPriceLabel != null) {
                addHoverEffect(okButton);
                try {
                    // Fetch list of items and populate the ComboBox
                    LinkedList<Item> itemList = createSalesInvoiceSalesModel.fetchListOfItems();
                    itemNameComboBox.getItems().addAll(itemList);
                } catch (NotLoggedInException e) {
                    showAlert("User not logged in.");
                }
            } else {
                System.out.println("Button or Field is null. Cannot continue");
                return; // Exit the method if UI components are not properly initialized
            }

            okButton.setOnAction(actionEvent -> {
                try {
                    if (createSalesInvoiceSalesModel != null) {
                        handleOkButton(actionEvent);
                    } else {
                        System.out.println("CreateSalesInvoiceSalesModel is null");
                    }
                } catch (NotLoggedInException e) {
                    showAlert("User not logged in.");
                } catch (OutOfRoleException e) {
                    showAlert("User does not have required permission.");
                }
            });
        }
    }
}
