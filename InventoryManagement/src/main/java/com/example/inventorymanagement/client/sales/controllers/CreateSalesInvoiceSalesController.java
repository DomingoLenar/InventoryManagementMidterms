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
    private Map<String, Item> itemMap = new HashMap<>();
    private LinkedList<OrderDetail> orderDetailsList = new LinkedList<>();
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
            LinkedList<Item> itemList = createSalesInvoiceSalesModel.fetchListOfItems();
            for (Item item : itemList) {
                itemNameComboBox.getItems().add(item.getItemName());
                itemMap.put(item.getItemName(), item); // Add item to the map
            }
        } catch (NotLoggedInException e) {
            showAlert("User not logged in.");
        }
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "item";
    }

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

        Stock firstAvailableStock = selectedItem.findFirstAvailableStock();
        if (firstAvailableStock == null) {
            showAlert("Error: No available stock for selected item.");
            return;
        }

        float unitPrice = selectedItem.findFirstAvailableStock().getPrice();
        String batchNo = firstAvailableStock.getBatchNo();
        OrderDetail newOrderDetail = new OrderDetail(selectedItem.getItemId(), quantity, unitPrice, batchNo);
        orderDetailsList.add(newOrderDetail);

        itemNameListView.getItems().add(selectedItemName);
        quantityListView.getItems().add(quantity);
        priceListView.getItems().add(unitPrice);
        itemQuantityField.setText("");
        itemNameComboBox.getSelectionModel().select(null);

        updateTotalPriceLabel();

    }
    @FXML
    private void handleClearButton() {
        if (confirmAction("Are you sure you want to clear all items in your order?")) {
            clearOrderDetails();
            itemNameComboBox.getSelectionModel().select(null);
            itemQuantityField.setText("");
        }
    }

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
     * @throws NotLoggedInException
     * @throws OutOfRoleException
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
//        private Stock getSelectedStock(Item selectedItem) {
//        if (selectedItem == null) {
//            return null;
//        }
//        LinkedList<Stock> stocks = selectedItem.getStocks();
//        if (stocks.isEmpty()) {
//            showAlert("No stock available for selected item.");
//            return null;
//        }
//        return stocks.getFirst();
//    }
//
//    /**
//     * Method to get int quantity from textfield
//     * @return int value obtained from itemQuantityField
//     */
//    private int getQuantity() {
//        if (itemQuantityField.getText().trim().isEmpty()) {
//            showAlert("Please enter quantity.");
//        }
//        try {
//            return Integer.parseInt(itemQuantityField.getText().trim());
//        } catch (NumberFormatException e) {
//            showAlert("Please enter a valid quantity.");
//            return -1;
//        }
//    }
//
    private void updateTotalPriceLabel() {
        float totalPrice = 0.0f;
        for (OrderDetail orderDetail : orderDetailsList) {
            totalPrice += orderDetail.getQty() * orderDetail.getUnitPrice();
        }
        itemPriceLabel.setText(String.format("%.2f", totalPrice));
    }

    private void clearOrderDetails() {
        orderDetailsList.clear();
        itemNameListView.getItems().clear();
        priceListView.getItems().clear();
        quantityListView.getItems().clear();
        updateTotalPriceLabel();
    }


//    private float calculateTotalPrice(float unitPrice, int quantity) {
//        return unitPrice * quantity;
//    }
//
//    private void updateItemPriceLabel(float totalPrice) {
//        itemPriceLabel.setText(String.format("%.2f", totalPrice));
//    }

    //
//    private ItemOrder createItemOrder(Item selectedItem, int quantity, float unitPrice) {
//        OrderDetail orderDetail = new OrderDetail(selectedItem.getItemId(), quantity, unitPrice, "");
//        ItemOrder itemOrder = new ItemOrder();
//        itemOrder.addOrderDetail(orderDetail);
//        return itemOrder;
//    }
//
//    private void createSalesInvoice(ItemOrder itemOrder) throws NotLoggedInException, OutOfRoleException {
//        if (createSalesInvoiceSalesModel.createSalesInvoice(itemOrder)) {
//            showSuccess("Sale invoiced successfully!");
//        } else {
//            showAlert("Failed to create sales invoice.");
//        }
//    }
//
//
    private Item lookupItem(String itemName) {
        return itemMap.get(itemName);
    }

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

    private void updateStockControlTable() {
        try {
            // Call fetchAndUpdate method of StockControlSalesController to update the table
            MainController.getStockControlSalesController().fetchAndUpdate();
        } catch (RemoteException e) {
            showAlert("Error updating stock control table: " + e.getMessage());
        }
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

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
}
