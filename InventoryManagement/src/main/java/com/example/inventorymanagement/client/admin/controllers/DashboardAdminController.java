package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.util.ControllerInterface;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class DashboardAdminController implements Initializable, ControllerInterface {
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
        return null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        dateLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Set the current day
        String currentDay = currentDate.getDayOfWeek().toString();
        dayLabel.setText(currentDay);

        // Update time label every second
        updateTimeLabel();
    }

    // Method to update the time label
    private void updateTimeLabel() {
        Thread updateTimeThread = new Thread(() -> {
            while (true) {
                LocalDateTime currentTime = LocalDateTime.now();
                String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a", Locale.forLanguageTag("fil-PH")));
                Platform.runLater(() -> timeLabel.setText(formattedTime));

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

}

