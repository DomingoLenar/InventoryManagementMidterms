package com.example.inventorymanagement.client.admin.controllers;

import com.example.inventorymanagement.client.admin.models.DashboardAdminModel;
import com.example.inventorymanagement.client.admin.models.FinancesAdminModel;
import com.example.inventorymanagement.client.admin.views.DashboardAdminPanel;
import com.example.inventorymanagement.client.admin.views.FinancesAdminPanel;
import com.example.inventorymanagement.client.common.controllers.MainController;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.ControllerInterface;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;


import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class FinancesAdminController extends Application implements Initializable, ControllerInterface {
    @FXML
    private BorderPane borderPaneFinancesAdmin;
    @FXML
    private TextField searchBar;
    @FXML
    private Button grossRevenueBg;
    @FXML
    private Button taxDeductableBg;
    @FXML
    private Button salesWorthBg;
    @FXML
    private Button grossProfitsBg;
    @FXML
    private Label helloLabel;
    @FXML
    private Label grossRevenueLabel;
    @FXML
    private Label taxDeductableLabel;
    @FXML
    private Label salesWorthLabel;
    @FXML
    private Label grossProfitLabel;
    @FXML
    private Label grossRevenueAmount;
    @FXML
    private Label taxDeductableAmount;
    @FXML
    private Label salesWorthAmount;
    @FXML
    private Label grossProfitsAmount;
    @FXML
    private ImageView grossRevenueIcon;
    @FXML
    private ImageView taxDeductableIcon;
    @FXML
    private ImageView salesWorthIcon;
    @FXML
    private ImageView grossProfitsIcon;
    @FXML
    private StackedBarChart revenueCostChart;
    @FXML
    private PieChart productsSoldChart;
    @FXML
    private Label analyticsTrackingLabel;
    @FXML
    private Button dateBg;
    @FXML
    private Button timeBg;
    @FXML
    private Button dateWhiteBg;
    @FXML
    private Label dayLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Circle clockFace;
    @FXML
    private Line hourHand;
    @FXML
    private Line minuteHand;
    @FXML
    private Label dateTodayLabel;
    private MainController mainController;

    private ClientCallback clientCallback;
    private Registry registry;
    private FinancesAdminPanel financesAdminPanel;
    private FinancesAdminModel financesAdminModel;

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
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void fetchAndUpdate() throws RemoteException {
    }

    @Override
    public String getObjectsUsed() throws RemoteException {
        return "FinancesAdmin";
    }

    public void initialize(URL location, ResourceBundle resources) {
        // Set the current date
        LocalDate currentDate = LocalDate.now();
        dateTodayLabel.setText(currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        // Set the current day
        String currentDay = currentDate.getDayOfWeek().toString();
        dayLabel.setText(currentDay);

        // Update time label every second
        updateTimeLabel();

        // initialize the model and panel objects
        financesAdminPanel = new FinancesAdminPanel();
        financesAdminModel = new FinancesAdminModel(registry, clientCallback);

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

    @Override
    public void start(Stage stage) throws Exception {
        financesAdminPanel = new FinancesAdminPanel();
        financesAdminPanel.start(stage);
    }
}


