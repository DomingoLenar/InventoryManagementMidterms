package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceAdminModel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.microservices.UpdateCallback;
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

public class CreateSalesInvoiceAdminController implements ControllerInterface {

    /**
     * FXML Controller Variables
     */
    @FXML
    private ComboBox<Item> itemNameComboBox;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private Button okButton;

    /**
     * Controller Variables
     */
    private CreateSalesInvoiceAdminModel createSalesInvoiceAdminModel;
    private MainController mainController;
    boolean initialized = false;

    /**
     * Getters
     */
    public ComboBox getItemNameComboBox() { return itemNameComboBox;}
    public TextField getItemQuantityField() {
        return itemQuantityField;
    }
    public Label getItemPriceLabel() { return itemPriceLabel;}

    /**
     * Default Constructor of CreateSalesInvoiceAdminController
     */
    public CreateSalesInvoiceAdminController() {
        // Default Constructor
    }

    /**
     * Constructor with dependencies.
     *
     * @param clientCallback The client callback.
     * @param userService     The user service.
     * @param iOService       The item order service.
     * @param itemService     The item service.
     * @param registry        The RMI registry.
     * @param mainController  The main controller.
     */
    public CreateSalesInvoiceAdminController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.createSalesInvoiceAdminModel = new CreateSalesInvoiceAdminModel(registry, clientCallback);
    }

    /**
     * Validates the user input and processes the creation of the sales invoice.
     *
     * @throws NotLoggedInException If the user is not logged in.
     * @throws OutOfRoleException   If the user does not have the required permission.
     */
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

    /**
     * Retrieves the selected item from the ComboBox.
     *
     * @return The selected item.
     */
    private Item getSelectedItem() {
        Item selectedItem = itemNameComboBox.getValue();
        if (selectedItem == null) {
            showAlert("Please select an item.");
        }
        return selectedItem;
    }

    /**
     * Retrieves the stock associated with the selected item.
     *
     * @param selectedItem The selected item.
     * @return The stock associated with the selected item.
     */
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

    /**
     * Retrieves the quantity entered by the user.
     *
     * @return The quantity entered by the user.
     */
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

    /**
     * Calculates the total price based on the unit price and quantity.
     *
     * @param unitPrice The unit price of the item.
     * @param quantity  The quantity of the item.
     * @return The total price.
     */
    private float calculateTotalPrice(float unitPrice, int quantity) {
        return unitPrice * quantity;
    }

    /**
     * Updates the item price label with the calculated total price.
     *
     * @param totalPrice The total price to be displayed.
     */
    private void updateItemPriceLabel(float totalPrice) {
        itemPriceLabel.setText(String.format("%.2f", totalPrice));
    }

    /**
     * Creates an ItemOrder object based on the selected item, quantity, and unit price.
     *
     * @param selectedItem The selected item.
     * @param quantity      The quantity of the item.
     * @param unitPrice     The unit price of the item.
     * @return The created ItemOrder object.
     */
    private ItemOrder createItemOrder(Item selectedItem, int quantity, float unitPrice) {
        OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), quantity, unitPrice, "");
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.addOrderDetail(orderDetail);
        return itemOrder;
    }

    /**
     * Creates a sales invoice using the provided ItemOrder.
     *
     * @param itemOrder The ItemOrder representing the sales invoice.
     * @throws NotLoggedInException If the user is not logged in.
     * @throws OutOfRoleException   If the user does not have the required permission.
     */
    private void createSalesInvoice(ItemOrder itemOrder) throws NotLoggedInException, OutOfRoleException {
        if (createSalesInvoiceAdminModel.createSalesInvoice(itemOrder)) {
            showAlert("Sales invoice created successfully.");
        } else {
            showAlert("Failed to create sales invoice.");
        }
    }

    /**
     * Updates the stock control table.
     */
    private void updateStockControlTable() {
        try {
            // Call fetchAndUpdate method of StockControlSalesController to update the table
            MainController.getStockControlSalesController().fetchAndUpdate();
        } catch (RemoteException e) {
            showAlert("Error updating stock control table: " + e.getMessage());
        }
    }

    /**
     * Adds hover effect to the specified button.
     *
     * @param button The button to which hover effect needs to be added.
     */
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    /**
     * Displays an alert with the given message.
     *
     * @param message The message to be displayed in the alert.
     */
    private void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Event handler for the Ok button click event.
     *
     * @param actionEvent The ActionEvent representing the Ok button click event.
     * @throws NotLoggedInException If the user is not logged in.
     * @throws OutOfRoleException   If the user does not have the required permission.
     */
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

    /**
     * Event handler for the Create Sales Invoice button click event.
     *
     * @param event The ActionEvent representing the Create Sales Invoice button click event.
     */
    @FXML
    private void handleCreateSalesInvoice(ActionEvent event) {
        try {
            // Retrieve selected item from the ComboBox
            Item selectedItem = itemNameComboBox.getValue();
            if (selectedItem == null) {
                showAlert("Please select an item.");
                return;
            }

            // Retrieve quantity from the TextField
            int quantity;
            try {
                quantity = Integer.parseInt(itemQuantityField.getText().trim());
                if (quantity <= 0) {
                    showAlert("Quantity must be greater than zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Please enter a valid quantity.");
                return;
            }

            // Create ItemOrder
            OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), quantity, selectedItem.getStocks().get(0).getPrice(), "");
            ItemOrder itemOrder = new ItemOrder();
            itemOrder.addOrderDetail(orderDetail);

            // Create sales invoice using the model
            boolean success = createSalesInvoiceAdminModel.createSalesInvoice(itemOrder);
            if (success) {
                showAlert("Sales invoice created successfully.");

                // Update stock control table
                updateStockControlTable();
            } else {
                showAlert("Failed to create sales invoice.");
            }
        } catch (NotLoggedInException | OutOfRoleException e) {
            showAlert("Error: " + e.getMessage());
        }
    }

    /**
     * Initializes the controller.
     */
    @FXML
    public void initialize() {

        createSalesInvoiceAdminModel = new CreateSalesInvoiceAdminModel(MainController.registry, MainController.clientCallback);

        if (!initialized) {
            addHoverEffect(okButton);
            initialized = true; // Set initialized to true after initialization

            if (okButton != null && itemNameComboBox != null && itemQuantityField != null && itemPriceLabel != null) {
                addHoverEffect(okButton);
                try {
                    // Fetch list of items and populate the ComboBox
                    LinkedList<Item> itemList = createSalesInvoiceAdminModel.fetchListOfItems();
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
                    if (createSalesInvoiceAdminModel != null) {
                        handleOkButton(actionEvent);
                    } else {
                        System.out.println("CreateSalesInvoiceAdminModel is null");
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

    /**
     * Fetches data from the model and updates the UI components accordingly.
     * This method is called to refresh the UI with the latest data.
     * @throws RemoteException If there is a problem with the remote communication.
     */
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
            LinkedList<Item> itemList = createSalesInvoiceAdminModel.fetchListOfItems();
            itemNameComboBox.getItems().addAll(itemList);
        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        }
    }

    /**
     * Retrieves a string representation of the objects used by this controller.
     * This method is typically used for logging or debugging purposes.
     * @return A string representation of the objects used by this controller.
     * @throws RemoteException If there is a problem with the remote communication.
     */
    @Override
    public String getObjectsUsed() throws RemoteException {
        return "item";
    }
}
