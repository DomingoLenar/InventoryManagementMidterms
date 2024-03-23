
module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    exports com.example.inventorymanagement.client.controller;
    opens com.example.inventorymanagement.client.controller to javafx.fxml;
    exports com.example.inventorymanagement.client.controller.welcome;
    opens com.example.inventorymanagement.client.controller.welcome to javafx.fxml;
    exports com.example.inventorymanagement.client.controller.login;
    opens com.example.inventorymanagement.client.controller.login to javafx.fxml;
    exports com.example.inventorymanagement.client.controller.ProfileManagement;
    opens com.example.inventorymanagement.client.controller.ProfileManagement to javafx.fxml;
}