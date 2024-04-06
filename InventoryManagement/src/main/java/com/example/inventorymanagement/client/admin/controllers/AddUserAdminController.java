package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.AddUserAdminModel;
import com.example.inventorymanagement.client.admin.views.AddUserAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.client.sales.models.StockControlSalesModel;
import com.example.inventorymanagement.client.sales.views.StockControlSalesPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ResourceBundle;

public class AddUserAdminController extends Application implements Initializable, ControllerInterface {

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button saveButton;

    private AddUserAdminModel addUserAdminModel;
    private AddUserAdminPanel addUserAdminPanel = new AddUserAdminPanel();
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    public void start(Stage stage) throws Exception {
        addUserAdminPanel.start(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Create a list of choices
        ObservableList<String> choices = FXCollections.observableArrayList(
                "Sales", "Purchaser"
        );

        // Set the choices to the ComboBox
        roleComboBox.setItems(choices);

        // Set font style for ComboBox
        Font font = new Font("Share Tech Mono", 15);
        roleComboBox.setStyle("-fx-font-family: '" + font.getFamily() + "'; -fx-font-size: " + font.getSize() + "px;");

        // Add hover effect to the save button
        addHoverEffect(saveButton);

        // Initialize the model and panel objects
        addUserAdminModel = new AddUserAdminModel(registry, clientCallback);
        addUserAdminPanel = new AddUserAdminPanel();
    }


    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }



    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }

    public ComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getPasswordField() {
        return passwordField;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "User";
    }


    @FXML
    private void handleSaveButtonClick() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();
            User newUser = new User(username, password, role);
            boolean success = addUserAdminModel.addUserService(newUser);
            if (success) {
                throw new RuntimeException("User added successfully.");
            } else {
                throw new RuntimeException("Failed to add user. Make sure to enter a 7 character password or check if the username has duplicates");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
