package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.admin.views.StockControlAdminPanel;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.client.purchaser.views.StockControlPurchaserPanel;
import com.example.inventorymanagement.client.sales.views.StockControlSalesPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Purpose:
 * - Loads .fxml and display the view/scene/activity
 * - Main entry point of communication on the server
 * - Temporarily display main menu for each client might modify in such a way...
 */
public class MainController implements ControllerInterface {
    private Stage stage;
    private ClientCallback clientCallback;
    private UserRequestInterface userService;

    public MainController(UserRequestInterface userService) throws RemoteException {
        this.userService = userService;
        this.clientCallback = new ClientCallbackImpl(null);
    }

    public ClientCallback getClientCallback() {
        return clientCallback;
    }

    public void setClientCallback(ClientCallback clientCallback) {
        this.clientCallback = clientCallback;
    }

    public UserRequestInterface getUserService() {
        return userService;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void loadWelcomeView() { // load welcome/index view
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/welcome/welcome-view.fxml"));
            BorderPane welcomePane = fxmlLoader.load();

            Scene scene = new Scene(welcomePane);
            stage.setScene(scene);

            WelcomeController welcomeController = fxmlLoader.getController();
            welcomeController.setMainController(this); // Pass MainController instance to WelcomeController
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadLoginView() { // load login view
        try {
            Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/login/login-view.fxml"));
            BorderPane loginPane = fxmlLoader.load();

            Scene scene = new Scene(loginPane);

            // Retrieve the current stage
            if (stage == null) {
                throw new IllegalStateException("Stage is not set. Please set the stage before calling showLoginPanel.");
            }
            stage.setScene(scene);

            LoginController loginController = fxmlLoader.getController();
            loginController.setMainController(this);
            loginController.initialize();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayAdminMainMenu() throws IOException {
        new StockControlAdminPanel().start(new Stage());
    }

    public void displayPurchaserMainMenu() throws IOException {
        new StockControlPurchaserPanel().start(new Stage());
    }

    public void displaySalesMainMenu() throws IOException{
        new StockControlSalesPanel().start(new Stage());
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
        //invalid
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "user";
    }
}
