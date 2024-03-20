module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.inventorymanagement to javafx.fxml;
    exports com.example.inventorymanagement;
    exports com.example.inventorymanagement.client.controller;
    opens com.example.inventorymanagement.client.controller to javafx.fxml;
}