package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.admin.controllers.StockControlAdminController;
import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.client.admin.views.StockControlAdminPanel;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.client.purchaser.controllers.StockControlPurchaserController;
import com.example.inventorymanagement.client.purchaser.models.StockControlPurchaserModel;
import com.example.inventorymanagement.client.purchaser.views.StockControlPurchaserPanel;
import com.example.inventorymanagement.client.sales.controllers.StockControlSalesController;
import com.example.inventorymanagement.client.sales.models.StockControlSalesModel;
import com.example.inventorymanagement.client.sales.views.StockControlSalesPanel;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

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
    private ItemOrderRequestInterface iOService;
    private ItemRequestInterface itemService;
    private Registry registry;

    public MainController(UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) throws RemoteException {
        this.userService = userService;
        this.iOService = iOService;
        this.itemService = itemService;
        this.registry = registry;
        this.clientCallback = new ClientCallbackImpl(null);

        initControllers();
    }

    private void initControllers() {
        StockControlAdminController stockControlAdminController = new StockControlAdminController(clientCallback, userService, iOService, itemService, registry);
        stockControlAdminController.setMainController(this);
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

            InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: logo.png");
            }

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

            InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

            if (inputStream != null) {
                Image image = new Image(inputStream);
                stage.getIcons().add(image);
            } else {
                System.err.println("Failed to load image: logo.png");
            }

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

    public void displayAdminMainMenu() {
        try {
            StockControlAdminController adminController = new StockControlAdminController(clientCallback, userService, iOService, itemService, registry);
            adminController.setStockControlAdminModel(new StockControlAdminModel(registry, clientCallback));
            adminController.setMainController(this); // Set the MainController instance in StockControlAdminController
            adminController.start(new Stage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayPurchaserMainMenu() {
        try {
            StockControlPurchaserController purchaserController = new StockControlPurchaserController(clientCallback, userService, iOService, itemService, registry);
            purchaserController.setStockControlPurchaserModel(new StockControlPurchaserModel(registry, clientCallback));
            purchaserController.setMainController(this); // Set the MainController instance in StockControlPurchaserController
            purchaserController.start(new Stage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void displaySalesMainMenu() {
        try {
            StockControlSalesController salesController = new StockControlSalesController(clientCallback, userService, iOService, itemService, registry);
            salesController.setStockControlSalesModel(new StockControlSalesModel(registry, clientCallback));
            salesController.setMainController(this); // Set the MainController instance in StockControlSalesController
            salesController.start(new Stage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
