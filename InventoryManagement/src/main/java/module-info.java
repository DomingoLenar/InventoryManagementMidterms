module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports com.example.inventorymanagement.client.common.controller;
    opens com.example.inventorymanagement.client.common.controller to javafx.fxml;
    exports com.example.inventorymanagement.client.common.views;
    opens com.example.inventorymanagement.client.common.views to javafx.fxml;
}