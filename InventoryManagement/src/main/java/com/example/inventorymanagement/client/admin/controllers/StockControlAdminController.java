package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddItemAdminModel;
import com.example.inventorymanagement.client.admin.models.CreateSalesInvoiceAdminModel;
import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class StockControlAdminController implements ControllerInterface {
    @FXML
    private BorderPane borderPaneStockControlAdmin;
    @FXML
    private Button lowStocksButtonAdmin;
    @FXML
    private Button salesInvoiceButtonAdmin;
    @FXML
    private Button addListingButtonAdmin;
    @FXML
    private Button addItemButtonAdmin;
    @FXML
    private TextField searchFieldAdmin;
    @FXML
    private TableView stockControlAdminTable;
    private StockControlAdminModel stockControlAdminModel;

    private ClientCallback clientCallback;
    private Registry registry;
    private StockControlAdminPanel stockControlAdminPanel;
    public StockControlAdminController(){
        stockControlAdminPanel = new StockControlAdminPanel();
        try {
            stockControlAdminPanel.start(new Stage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return null;
    }

    @FXML
    public BorderPane getBorderPaneStockControlAdmin() { return borderPaneStockControlAdmin;}

    @FXML
    public Button getLowStocksButton() { return lowStocksButtonAdmin; }

    @FXML
    public Button getsalesInvoiceButtonAdmin() { return salesInvoiceButtonAdmin; }

    @FXML
    public Button getAddListingButton() { return addListingButtonAdmin;}

    @FXML
    public Button getAddItemButton() { return addItemButtonAdmin;}

    @FXML
    public TextField getSearchFieldAdmin() { return searchFieldAdmin; }

    @FXML
    public TableView getStockControlAdminTable() { return stockControlAdminTable; }

    public StockControlAdminController(ClientCallback clientCallback, Registry registry) {
        this.clientCallback = clientCallback;
        this.registry = registry;

        stockControlAdminModel = new StockControlAdminModel(registry,clientCallback);

    }
    @FXML
    private void initialize() {
        addHoverEffect(lowStocksButtonAdmin);
        addHoverEffect(salesInvoiceButtonAdmin);
        addHoverEffect(addItemButtonAdmin);
        addHoverEffect(addListingButtonAdmin);

        try {
            addItemButtonAdmin.setOnAction(event -> handleAddItem());
            LinkedList<Item> items = stockControlAdminModel.fetchItems();
            populateTableView(items);
        } catch (NotLoggedInException e) {
            e.printStackTrace();
        }
    }

    private void populateTableView(LinkedList<Item> items) {
        stockControlAdminTable.getItems().clear();
        stockControlAdminTable.getItems().addAll(items);
    }
    /**
     * Handle Action Event when clicking AddItemButton
     */
    @FXML
    public void handleAddItem() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/addItem-view.fxml"));
            Parent root = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add Item");

            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(root);
            dialog.setDialogPane(dialogPane);

            dialog.initStyle(StageStyle.UNDECORATED);

            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
            okButton.setOnAction(event -> {
                //TODO: Logic from AddItemModel for adding items using GSONProcessing
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(event -> dialog.close());

            dialog.getDialogPane().getChildren().addAll(okButton, cancelButton);

            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    private void handleSalesInvoice (ActionEvent event) {

    }

    private void handleAddListing (ActionEvent event) {

    }

    private void handleLowStocks (ActionEvent event) {

    }
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }
}
