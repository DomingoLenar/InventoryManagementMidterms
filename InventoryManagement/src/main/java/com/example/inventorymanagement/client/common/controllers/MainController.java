package com.example.inventorymanagement.client.common.controllers;

import com.example.inventorymanagement.client.admin.controllers.*;
import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.client.purchaser.controllers.NavigationBarPurchaserController;
import com.example.inventorymanagement.client.purchaser.controllers.ProfileManagementPurchaserController;
import com.example.inventorymanagement.client.purchaser.controllers.StockControlPurchaserController;
import com.example.inventorymanagement.client.admin.controllers.StockControlAdminController;
import com.example.inventorymanagement.client.sales.controllers.NavigationBarSalesController;
import com.example.inventorymanagement.client.sales.controllers.ProfileManagementSalesController;
import com.example.inventorymanagement.client.sales.controllers.SalesHistorySalesController;
import com.example.inventorymanagement.client.sales.controllers.StockControlSalesController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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

    static StockControlAdminController stockControlAdminController;
    NavigationBarAdminController navigationBarAdminController;
    static BorderPane stockControlAdminPanel;
    static SalesHistoryAdminController salesHistoryAdminController;
    static BorderPane salesHistoryAdminPanel;
    static ProfileManagementAdminController profileManagementAdminController;
    static BorderPane profileManagementAdminPanel;
    static DashboardAdminController dashboardAdminController;
    static BorderPane dashboardAdminPanel;

    static StockControlPurchaserController stockControlPurchaserController;
    NavigationBarPurchaserController navigationBarPurchaserController;
    static BorderPane stockControlPurchaserPanel;
    static ProfileManagementPurchaserController profileManagementPurchaserController;
    static BorderPane profileManagementPurchaserPanel;

    static StockControlSalesController stockControlSalesController;
    NavigationBarSalesController navigationBarSalesController;
    static BorderPane stockControlSalesPanel;
    static SalesHistorySalesController salesHistorySalesController;
    static BorderPane salesHistorySalesPanel;
    static ProfileManagementSalesController profileManagementSalesController;
    static BorderPane profileManagementSalesPanel;


    public MainController(UserRequestInterface userService, ItemOrderRequestInterface iOService, ItemRequestInterface itemService, Registry registry) throws RemoteException {
        MainController.userService = userService;
        MainController.iOService = iOService;
        MainController.itemService = itemService;
        MainController.registry = registry;
        clientCallback = new ClientCallbackImpl(null);

//        initControllers();
    }

    // for future ref.
//    private void initControllers() {
//        stockControlAdminController = new StockControlAdminController(clientCallback, userService, iOService, itemService, registry, this);
//        stockControlAdminController.setMainController(this);
//        addItemAdminController = new AddItemAdminController(clientCallback, userService, iOService, itemService, registry, this);
//        addItemAdminController.setMainController(this);
////        stockControlPurchaserController = new StockControlPurchaserController(clientCallback, userService, iOService, itemService, registry, this);
////        navigationBarPurchaserController = new NavigationBarPurchaserController(clientCallback, userService, iOService, itemService, registry, this);
//
//    }

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

    public static BorderPane getStockControlPurchaserPanel() {return stockControlPurchaserPanel;}

    public static BorderPane getStockControlAdminPanel() {return stockControlAdminPanel;}

    public static BorderPane getStockControlSalesPanel() {return stockControlSalesPanel;}

    public static BorderPane getSalesHistorySalesPanel() {return salesHistorySalesPanel;}

    public static BorderPane getSalesHistoryAdminPanel() {return salesHistoryAdminPanel;}

    public static BorderPane getProfileManagementSalesPanel() {return profileManagementSalesPanel;}

    public static BorderPane getProfileManagementPurchaserPanel() {return profileManagementPurchaserPanel;}

    public static BorderPane getProfileManagementAdminPanel() { return profileManagementAdminPanel;}
    public static BorderPane getDashboardAdminPanel(){ return dashboardAdminPanel;}

    public void displayAdminMainMenu() throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarAdmin-view.fxml"));
        BorderPane navigationBar = navLoader.load();
        navigationBarAdminController = navLoader.getController();

        FXMLLoader stockAdminLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlAdmin-view.fxml"));
        stockControlAdminPanel = stockAdminLoader.load();
        stockControlAdminController = stockAdminLoader.getController();

        FXMLLoader salesHistoryLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/salesHistory/salesHistoryAdmin-view.fxml"));
        salesHistoryAdminPanel = salesHistoryLoader.load();
        salesHistoryAdminController = salesHistoryLoader.getController();

        FXMLLoader profileManagementLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagementAdmin-view.fxml"));
        profileManagementAdminPanel = profileManagementLoader.load();
        profileManagementAdminController = profileManagementLoader.getController();

        FXMLLoader dashboardLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/dashboard/dashboardAdmin-view.fxml"));
        dashboardAdminPanel = dashboardLoader.load();
        dashboardAdminController = dashboardLoader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        VBox rightComponents = new VBox();
        rightComponents.getChildren().addAll(stockControlAdminPanel, salesHistoryAdminPanel, profileManagementAdminPanel, dashboardAdminPanel);

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(rightComponents);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        navigationBarAdminController.setMainPane(root);
    }

    public void displayPurchaserMainMenu() throws Exception {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarPurchaser-view.fxml"));
        BorderPane navigationBar = navLoader.load();
        navigationBarPurchaserController = navLoader.getController();

        FXMLLoader stockPurchaserLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlPurchaser-view.fxml"));
        stockControlPurchaserPanel = stockPurchaserLoader.load();
        stockControlPurchaserController = stockPurchaserLoader.getController();

        FXMLLoader profileManagementLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagementPurchaser-view.fxml"));
        profileManagementPurchaserPanel = profileManagementLoader.load();
        profileManagementPurchaserController = profileManagementLoader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        VBox rightComponents = new VBox();
        rightComponents.getChildren().addAll(stockControlPurchaserPanel, profileManagementPurchaserPanel);

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(rightComponents);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        navigationBarPurchaserController.setMainPane(root);
    }

    public void displaySalesMainMenu() throws IOException {
        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"), 20);

        FXMLLoader navLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/navigationBar/navigationBarSales-view.fxml"));
        BorderPane navigationBar = navLoader.load();
        navigationBarSalesController = navLoader.getController();

        FXMLLoader stockSalesLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/stockControlSales-view.fxml"));
        stockControlSalesPanel = stockSalesLoader.load();
        stockControlSalesController = stockSalesLoader.getController();

        FXMLLoader salesHistoryLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/salesHistory/salesHistorySales-view.fxml"));
        salesHistorySalesPanel = salesHistoryLoader.load();
        salesHistorySalesController = salesHistoryLoader.getController();

        FXMLLoader profileManagementLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagementSales-view.fxml"));
        profileManagementSalesPanel = profileManagementLoader.load();
        profileManagementSalesController = profileManagementLoader.getController();

        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");
        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        VBox rightComponents = new VBox();
        rightComponents.getChildren().addAll(stockControlSalesPanel, salesHistorySalesPanel, profileManagementSalesPanel);

        BorderPane root = new BorderPane();
        root.setLeft(navigationBar);
        root.setRight(rightComponents);

        Scene scene = new Scene(root, 1080, 650);
        stage.setScene(scene);
        stage.setTitle("Stock Pilot");
        stage.setResizable(false);
        stage.show();

        navigationBarSalesController.setMainPane(root);
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {

    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "user";
    }

    public NavigationBarAdminController getNavigationBarAdminController() {
        return navigationBarAdminController;
    }

    public NavigationBarPurchaserController getNavigationBarPurchaserController() { return navigationBarPurchaserController;}

    public NavigationBarSalesController getNavigationBarSalesController() {
        return navigationBarSalesController;
    }

    public static StockControlPurchaserController getStockControlPurchaserController() { return stockControlPurchaserController;}

    public static StockControlAdminController getStockControlAdminController() {
        return stockControlAdminController;
    }
    public static DashboardAdminController getDashboardAdminController(){
        return dashboardAdminController;
    }

    public static StockControlSalesController getStockControlSalesController() {
        return stockControlSalesController;
    }

    public static SalesHistorySalesController getSalesHistorySalesController() { return salesHistorySalesController;}

    public static SalesHistoryAdminController getSalesHistoryAdminController() { return salesHistoryAdminController;}

    public static ProfileManagementSalesController getProfileManagementSalesController() { return profileManagementSalesController;}

    public static ProfileManagementPurchaserController getProfileManagementPurchaserController() { return profileManagementPurchaserController;}

    public static ProfileManagementAdminController getProfileManagementAdminController() { return profileManagementAdminController;}


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
    public Registry getRegistry() {
        return registry;
    }
}
