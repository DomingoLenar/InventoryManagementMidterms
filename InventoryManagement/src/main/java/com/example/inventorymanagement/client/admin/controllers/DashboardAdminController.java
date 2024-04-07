package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.DashboardAdminModel;
import com.example.inventorymanagement.client.admin.views.AddItemAdminPanel;
import com.example.inventorymanagement.client.admin.views.DashboardAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.stage.Stage;


public class DashboardAdminController extends Application implements Initializable, ControllerInterface {
    @FXML
    private BorderPane borderPaneAdminDashboard;
    @FXML
    private TextField searchButton;
    @FXML
    private Label usersActiveLabel;
    @FXML
    private StackedBarChart monthRevChart;
    @FXML
    private Button dateButton;
    @FXML
    private Label dateLabel;
    @FXML
    private Label dayLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label lowStockProductsLabel;
    @FXML
    private Label todayTransactionsLabel;
    @FXML
    private Label topProductsLabel;
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private DashboardAdminModel dashboardAdminModel;
    private DashboardAdminPanel dashboardAdminPanel;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }


    public BorderPane getBorderPaneAdminDashboard() {
        return borderPaneAdminDashboard;
    }

    public TextField getSearchButton() {
        return searchButton;
    }

    public Label getUsersActiveLabel() {
        return usersActiveLabel;
    }

    public Label getTodayTransactionsLabel() {
        return todayTransactionsLabel;
    }

    public Label getTopProductsLabel() {
        return topProductsLabel;
    }

    public Label getLowStockProductsLabel() {
        return lowStockProductsLabel;
    }

    public Button getDateButton(){
        return dateButton;
    }

    public Label getDayLabel() {
        return dayLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }

    public StackedBarChart getMonthRevChart() {
        return monthRevChart;
    }
    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "Dasboard";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        String formattedDate = currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yy"));
        dateLabel.setText(formattedDate);

        // Set the current day
        String currentDay = currentDate.getDayOfWeek().toString();
        dayLabel.setText(currentDay);

        // Update time label every second
        updateTimeLabel();

        // Initialize the model and panel objects
        dashboardAdminPanel = new DashboardAdminPanel();
        dashboardAdminModel = new DashboardAdminModel(registry, clientCallback);
    }


    // Method to update the time label
    private void updateTimeLabel() {
        Thread updateTimeThread = new Thread(() -> {
            while (true) {
                LocalDateTime currentTime = LocalDateTime.now();
                String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.forLanguageTag("fil-PH")));
                System.out.println("Formatted Time: " + formattedTime);

                Platform.runLater(() -> {
                    timeLabel.setText(formattedTime);
                    System.out.println("Time updated: " + formattedTime); // Debug print statement
                });

                try {
                    // Sleep for 1 second
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        dashboardAdminPanel = new DashboardAdminPanel();
        dashboardAdminPanel.start(stage);
    }
}

