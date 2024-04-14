package com.example.inventorymanagement.client.sales.controllers;

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
import javafx.scene.layout.Region;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

public class CreateSalesInvoiceSalesController implements ControllerInterface {
    /**
     * FXML Elements
     */
    @FXML
    private ComboBox<String> itemNameComboBox;
    @FXML
    private TextField itemQuantityField;
    @FXML
    private Label itemPriceLabel;
    @FXML
    private ListView<String> itemNameListView;
    @FXML
    private ListView<Float> priceListView;
    @FXML
    private ListView<Integer> quantityListView;
    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button okButton;

    // Other variables
    private Map<String, Item> itemMap = new HashMap<>();
    private LinkedList<OrderDetail> orderDetailsList = new LinkedList<>();
    private boolean initialized = false;
    private CreateSalesInvoiceSalesModel createSalesInvoiceSalesModel;
    private MainController mainController;

    /**
     * Default Constructor
     */
    public CreateSalesInvoiceSalesController() {
        // Default Constructor
    }

    /**
     * Parameterized Constructor
     *
     * @param clientCallback The client callback.
     * @param userService    The user service.
     * @param iOService      The item order service.
     * @param itemService    The item service.
     * @param registry       The RMI registry.
     * @param mainController The main controller.
     */
    public CreateSalesInvoiceSalesController(ClientCallback clientCallback, UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry, MainController mainController) {
        this.createSalesInvoiceSalesModel = new CreateSalesInvoiceSalesModel(registry, clientCallback);
    }

    /**
     * Getters
     */
    public ComboBox getItemNameComboBox() {
        return itemNameComboBox;
    }
    public TextField getItemQuantityField() {
        return itemQuantityField;
    }
    public Label getItemPriceLabel() {
        return itemPriceLabel;
    }
    public ListView<String> getItemNameListView() {
        return itemNameListView;
    }
    public ListView<Float> getPriceListView() {
        return priceListView;
    }
    public ListView<Integer> getQuantityListView() {
        return quantityListView;
    }
    public Button getAddButton() {
        return addButton;
    }
    public Button getClearButton() {
        return clearButton;
    }
    public Button getOkButton() {
        return okButton;
    }

    //Interface implementation

    /**
     * Fetches data from the model and updates the UI components accordingly.
     * This method is called to refresh the UI with the latest data.
     *
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
            LinkedList<Item> itemList = createSalesInvoiceSalesModel.fetchListOfItems();
            for (Item item : itemList) {
                itemNameComboBox.getItems().add(item.getItemName());
                itemMap.put(item.getItemName(), item); // Add item to the map
            }
        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        }
    }

    /**
     * Retrieves a string representation of the objects used by this controller.
     * This method is typically used for logging or debugging purposes.
     *
     * @return A string representation of the objects used by this controller.
     * @throws RemoteException If there is a problem with the remote communication.
     */
    @Override
    public String getObjectsUsed() throws RemoteException {
        return "item";
    }

    //UI Event Handlers

    /**
     * Handles the action when the Add button is clicked.
     * This method retrieves the selected item from the combo box,
     * validates the quantity input, checks for available stock,
     * and adds the item to the order details list.
     * It also updates the UI components to reflect the changes.
     *
     * @throws RemoteException if a remote exception occurs
     */
    @FXML
    private void handleAddButton() throws RemoteException {
        String selectedItemName = itemNameComboBox.getValue();
        if (selectedItemName == null) {
            showAlert("Please select an item.");
            return;
        }
        Item selectedItem = getSelectedItem();
        if (selectedItem == null) {
            showAlert("Error: Item not found.");
            return;
        }

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

        int totalAvailableQuantity = selectedItem.getTotalQty();
        if (quantity > totalAvailableQuantity) {
            showAlert("Insufficient stock for selected item. Only " + totalAvailableQuantity + " available.");
            return;
        }

        int remainingQuantity = quantity;
        LinkedList<Stock> remainingStocks = selectedItem.getStocks();

        while (remainingQuantity > 0 && !remainingStocks.isEmpty()) {
            Stock currentStock = remainingStocks.pollFirst();
            int currentStockQty = currentStock.getQty();

            if (currentStockQty >= remainingQuantity) {
                OrderDetail newOrderDetail = new OrderDetail(selectedItem.getItemId(), remainingQuantity, currentStock.getPrice(), currentStock.getBatchNo());
                orderDetailsList.add(newOrderDetail);

                itemNameListView.getItems().add(selectedItemName);
                quantityListView.getItems().add(remainingQuantity);
                priceListView.getItems().add(currentStock.getPrice());
                itemQuantityField.setText("");
                itemNameComboBox.getSelectionModel().select(null);

                updateTotalPriceLabel();
                remainingQuantity = 0;
            } else {
                remainingQuantity -= currentStockQty;
                currentStock.setQty(0);

                OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), currentStockQty, currentStock.getPrice(), currentStock.getBatchNo());
                orderDetailsList.add(orderDetail);

                itemNameListView.getItems().add(selectedItemName);
                quantityListView.getItems().add(currentStockQty);
                priceListView.getItems().add(currentStock.getPrice());
            }
        }

        if (remainingQuantity > 0) {
            showAlert("An error occurred while processing the request. Please try again.");
        }
    }

    /**
     * Handles the action when the Clear button is clicked.
     * This method prompts the user for confirmation before clearing all items in the order.
     * If the user confirms, it clears the order details and resets the combo box and quantity field.
     */
    @FXML
    private void handleClearButton() {
        if (confirmAction("Are you sure you want to clear all items in your order?")) {
            clearOrderDetails();
            itemNameComboBox.getSelectionModel().select(null);
            itemQuantityField.setText("");
        }
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
            Stage stage = (Stage) okButton.getScene().getWindow();
            stage.close();

        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        } catch (OutOfRoleException e) {
            showAlert("User does not have required permission.");
        } catch (Exception e) {
            showAlert("Error processing sales invoice: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Event handler for the Create Sales Invoice button click event.
     *
     * @param event The ActionEvent representing the Create Sales Invoice button click event.
     */
    @FXML
    private void handleCreateSalesInvoice(ActionEvent event) throws RemoteException {
        try {
            String selectedItemName = itemNameComboBox.getValue();
            if (selectedItemName == null) {
                showAlert("Please select an item.");
                return;
            }
            // Retrieve the actual Item object using the selected name (replace with your implementation
            Item selectedItem = lookupItem(selectedItemName);
            if (selectedItem == null) {
                showAlert("Error: Item not found.");
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

            Stock firstAvailableStock = selectedItem.findFirstAvailableStock();
            if (firstAvailableStock == null) {
                showAlert("Error: No available stock for selected item.");
                return;
            }

            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), quantity, firstAvailableStock.getPrice(), firstAvailableStock.getBatchNo());
            LinkedList<OrderDetail> orderDetailsList = new LinkedList<>();
            orderDetailsList.add(orderDetail);

            ItemOrder itemOrder = new ItemOrder(0, MainController.clientCallback.getUser().getUsername(), formattedDate, orderDetailsList);


            // Create sales invoice using the model
            boolean success = createSalesInvoiceSalesModel.createSalesInvoice(itemOrder);
            if (success) {
                showSuccess("Sale invoiced successfully");

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

        createSalesInvoiceSalesModel = new CreateSalesInvoiceSalesModel(MainController.registry, MainController.clientCallback);

        if (!initialized) {
            addHoverEffect(addButton);
            addHoverEffect(clearButton);
            addHoverEffect(okButton);

            initialized = true;

            if (addButton != null && clearButton != null && okButton != null && itemNameComboBox != null && itemQuantityField != null && itemPriceLabel != null && itemNameListView != null) {
                addHoverEffect(okButton);
                try {
                    LinkedList<Item> itemList = createSalesInvoiceSalesModel.fetchListOfItems();
                    for (Item item : itemList) {
                        itemNameComboBox.getItems().add(item.getItemName());
                        itemMap.put(item.getItemName(), item);
                    }
                } catch (NotLoggedInException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Button or Field is null. Cannot continue");
                return;
            }
            addButton.setOnAction(actionEvent -> {
                try {
                    handleAddButton();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });

            clearButton.setOnAction(actionEvent -> {handleClearButton();});

            okButton.setOnAction(actionEvent -> {
                try {
                    handleOkButton(actionEvent);
                } catch (NotLoggedInException e) {
                    showAlert("User not logged in.");
                } catch (OutOfRoleException e) {
                    showAlert("User does not meet required permissions");
                }
            });
            try {
                MainController.clientCallback.setCurrentPanel(this);
                UpdateCallback.process(MainController.clientCallback, MainController.registry);
            } catch (NotLoggedInException e) {
                showAlert("User is not logged in");
            } catch (RemoteException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Helper Methods

    /**
     * Get selected Item from String
     *
     * @return selectedItem object of Item from String input
     */
    private Item getSelectedItem() {
        String selectedItemName = itemNameComboBox.getValue();
        if (selectedItemName == null) {
            showAlert("Please select an item.");
            return null;
        }

        Item selectedItem = itemMap.get(selectedItemName);
        if (selectedItem == null) {
            showAlert("Error: Item not found.");
            return null;
        }
        return selectedItem;
    }

    /**
     * Updates the total price label based on the current order details.
     * Calculates the total price by summing the price of each item in the order.
     * Updates the itemPriceLabel with the calculated total price.
     */
    private void updateTotalPriceLabel() {
        float totalPrice = 0.0f;
        for (OrderDetail orderDetail : orderDetailsList) {
            totalPrice += orderDetail.getQty() * orderDetail.getUnitPrice();
        }
        itemPriceLabel.setText(String.format("%.2f", totalPrice));
    }

    /**
     * Clears all the order details.
     * Removes all items from the orderDetailsList, itemNameListView, priceListView, and quantityListView.
     * Updates the total price label to reflect the cleared order.
     */
    private void clearOrderDetails() {
        orderDetailsList.clear();
        itemNameListView.getItems().clear();
        priceListView.getItems().clear();
        quantityListView.getItems().clear();
        updateTotalPriceLabel();
    }

    /**
     * Looks up an item in the item map by its name.
     *
     * @param itemName The name of the item to look up.
     * @return The item associated with the given name, or null if not found.
     */
    private Item lookupItem(String itemName) {
        return itemMap.get(itemName);
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
     * Displays a success message in an alert dialog.
     *
     * @param message The message to be displayed in the alert.
     */
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an alert with the given message.
     *
     * @param message The message to be displayed in the alert.
     */
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a confirmation dialog with the specified message.
     *
     * @param message The message to be displayed in the confirmation dialog.
     * @return {@code true} if the user confirms the action, {@code false} otherwise.
     */
    private boolean confirmAction(String message) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setHeaderText("Confirmation Required");
        confirmation.setContentText(message);
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.YES);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmation.getButtonTypes().setAll(okButton, cancelButton);
        Optional<ButtonType> result = confirmation.showAndWait();
        return result.orElse(ButtonType.CANCEL) == okButton;
    }

    /**
     * Validates the user input and processes the creation of the sales invoice.
     *
     * @throws NotLoggedInException If the user is not logged in.
     * @throws OutOfRoleException   If the user does not have the required permission.
     */
    private void validateAndProcessSalesInvoice() throws NotLoggedInException, OutOfRoleException {
        try {
            String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            ItemOrder itemOrder = new ItemOrder(0, MainController.clientCallback.getUser().getUsername(), formattedDate, orderDetailsList);

            boolean success = createSalesInvoiceSalesModel.createSalesInvoice(itemOrder);
            if (success) {
                showSuccess("Sale invoiced successfully");
                updateStockControlTable();
            } else {
                showAlert("Failed to create sales invoice.");
            }
        } catch (NotLoggedInException | OutOfRoleException e) {
            showAlert("Error: " + e.getMessage());
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
