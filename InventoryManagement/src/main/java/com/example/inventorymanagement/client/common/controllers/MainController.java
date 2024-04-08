package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.admin.controllers.AddItemAdminController;
import com.example.inventorymanagement.client.admin.controllers.StockControlAdminController;
import com.example.inventorymanagement.client.admin.models.StockControlAdminModel;
import com.example.inventorymanagement.client.admin.views.StockControlAdminPanel;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.client.purchaser.controllers.NavigationBarPurchaserController;
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
    public Stage stage;
    public static ClientCallback clientCallback;
    public static UserRequestInterface userService;
    public static ItemOrderRequestInterface iOService;
    public static ItemRequestInterface itemService;
    public static Registry registry;
    StockControlAdminController stockControlAdminController;
    AddItemAdminController addItemAdminController;
    StockControlPurchaserController stockControlPurchaserController;
    NavigationBarPurchaserController navigationBarPurchaserController;

    public MainController(UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) throws RemoteException {
        MainController.userService = userService;
        MainController.iOService = iOService;
        MainController.itemService = itemService;
        MainController.registry = registry;
        clientCallback = new ClientCallbackImpl(null);

        initControllers();
    }

    public Registry getRegistry() {
        return registry;
    }

    private void initControllers() {
        stockControlAdminController= new StockControlAdminController(clientCallback, userService, iOService, itemService, registry, this);
        stockControlAdminController.setMainController(this);
        addItemAdminController= new AddItemAdminController(clientCallback, userService, iOService, itemService, registry, this);
        addItemAdminController.setMainController(this);
//        stockControlPurchaserController = new StockControlPurchaserController(clientCallback, userService, iOService, itemService, registry, this);
//        navigationBarPurchaserController = new NavigationBarPurchaserController(clientCallback, userService, iOService, itemService, registry, this);

    }

    public NavigationBarPurchaserController getNavigationBarPurchaserController() {
        return navigationBarPurchaserController;
    }

    public StockControlPurchaserController getStockControlPurchaserController() {
        return stockControlPurchaserController;
    }

    public AddItemAdminController getAddItemAdminController() {
        return addItemAdminController;
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

    public void displayAdminMainMenu() throws IOException {
        new StockControlAdminPanel().start(new Stage());
    }

    public void displayPurchaserMainMenu() throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarPurchaser-view.fxml"));
        BorderPane navigationBar = navLoader.load();
        navigationBarPurchaserController = navLoader.getController();

        // Create the stock control panel
        FXMLLoader stockPurchaserLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlPurchaser-view.fxml"));
        BorderPane stockControlPurchaserPanel = stockPurchaserLoader.load();
        stockControlPurchaserController = stockPurchaserLoader.getController();


        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(stockControlPurchaserPanel);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        // Set the main BorderPane reference in the navigation bar controller
        navigationBarPurchaserController.setMainBorderPane(root);
//        new StockControlPurchaserPanel().start(new Stage());
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
