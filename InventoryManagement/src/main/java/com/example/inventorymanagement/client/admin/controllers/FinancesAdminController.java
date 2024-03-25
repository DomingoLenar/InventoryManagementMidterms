package com.example.inventorymanagement.client.admin.controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.fxml.FXML;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.time.LocalTime;

public class FinancesAdminController {
    @FXML
    public BorderPane borderPaneFinancesAdmin;
    @FXML
    public TextField searchBar;
    @FXML
    public Button grossRevenueBg;
    @FXML
    public Button taxDeductableBg;
    @FXML
    public Button salesWorthBg;
    @FXML
    public Button grossProfitsBg;
    @FXML
    public Label helloLabel;
    @FXML
    public Label grossRevenueLabel;
    @FXML
    public Label taxDeductableLabel;
    @FXML
    public Label salesWorthLabel;
    @FXML
    public Label grossProfitLabel;
    @FXML
    public Label grossRevenueAmount;
    @FXML
    public Label taxDeductableAmount;
    @FXML
    public Label salesWorthAmount;
    @FXML
    public Label grossProfitsAmount;
    @FXML
    public ImageView grossRevenueIcon;
    @FXML
    public ImageView taxDeductableIcon;
    @FXML
    public ImageView salesWorthIcon;
    @FXML
    public ImageView grossProfitsIcon;
    @FXML
    public StackedBarChart revenueCostChart;
    @FXML
    public PieChart productsSoldChart;
    @FXML
    public Label analyticsTrackingLabel;
    @FXML
    public Button dateBg;
    @FXML
    public Button timeBg;
    @FXML
    public Button dateWhiteBg;
    @FXML
    public Label dayLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public Circle clockFace;
    @FXML
    public Line hourHand;
    @FXML
    public Line minuteHand;
    @FXML
    public Label dateTodayLabel;

    public BorderPane getBorderPaneFinancesAdmin() {
        return borderPaneFinancesAdmin;
    }

    public TextField getSearchBar() {
        return searchBar;
    }

    public Button getGrossRevenueBg() {
        return grossRevenueBg;
    }

    public Button getTaxDeductableBg() {
        return taxDeductableBg;
    }

    public Button getSalesWorthBg() {
        return salesWorthBg;
    }

    public Button getGrossProfitsBg() {
        return grossProfitsBg;
    }

    public Label getHelloLabel() {
        return helloLabel;
    }

    public Label getGrossRevenueLabel() {
        return grossRevenueLabel;
    }

    public Label getTaxDeductableLabel() {
        return taxDeductableLabel;
    }

    public Label getSalesWorthLabel() {
        return salesWorthLabel;
    }

    public Label getGrossProfitLabel() {
        return grossProfitLabel;
    }

    public Label getGrossRevenueAmount() {
        return grossRevenueAmount;
    }

    public Label getTaxDeductableAmount() {
        return taxDeductableAmount;
    }

    public Label getSalesWorthAmount() {
        return salesWorthAmount;
    }

    public Label getGrossProfitsAmount() {
        return grossProfitsAmount;
    }

    public ImageView getGrossRevenueIcon() {
        return grossRevenueIcon;
    }

    public ImageView getTaxDeductableIcon() {
        return taxDeductableIcon;
    }

    public ImageView getSalesWorthIcon() {
        return salesWorthIcon;
    }

    public ImageView getGrossProfitsIcon() {
        return grossProfitsIcon;
    }

    public StackedBarChart getRevenueCostChart() {
        return revenueCostChart;
    }

    public PieChart getProductsSoldChart() {
        return productsSoldChart;
    }

    public Label getAnalyticsTrackingLabel() {
        return analyticsTrackingLabel;
    }

    public Button getDateBg() {
        return dateBg;
    }

    public Button getTimeBg() {
        return timeBg;
    }

    public Button getDateWhiteBg() {
        return dateWhiteBg;
    }

    public Label getDayLabel() {
        return dayLabel;
    }

    public Label getTimeLabel() {
        return timeLabel;
    }

    public Circle getClockFace() {
        return clockFace;
    }

    public Line getHourHand() {
        return hourHand;
    }

    public Line getMinuteHand() {
        return minuteHand;
    }

    public Label getDateTodayLabel() {
        return dateTodayLabel;
    }

    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: derive(#EAD7D7, -10%);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #EAD7D7;"));
    }

        public class ClockController {
            @FXML
            private Circle clockFace;
            @FXML
            private Line hourHand;
            @FXML
            private Line minuteHand;

            public void initialize() {
                updateClock();
            }

            private void updateClock() {
                Runnable updater = () -> {
                    while (true) {
                        // Get current time
                        LocalTime time = LocalTime.now();

                        // Calculate rotation angles for hour and minute
                        double minuteAngle = (time.getMinute() + time.getSecond() / 60.0) * 6;
                        double hourAngle = (time.getHour() % 12 + time.getMinute() / 60.0) * 30;

                        // Rotate the clock hands
                        rotateHand(minuteHand, minuteAngle);
                        rotateHand(hourHand, hourAngle);

                        // Sleep for 1 second before updating again
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };

                // Start a new thread for updating the clock
                Thread clockThread = new Thread(updater);
                clockThread.setDaemon(true);
                clockThread.start();
            }

            private void rotateHand(Line hand, double angle) {
                hand.setRotate(angle);
            }
        }
    }


