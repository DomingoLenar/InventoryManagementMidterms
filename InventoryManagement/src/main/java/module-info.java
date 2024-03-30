module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.rmi;

    // Controllers
    exports com.example.inventorymanagement.client.common.controllers;
    opens com.example.inventorymanagement.client.common.controllers to javafx.fxml;
    exports com.example.inventorymanagement.client.admin.controllers;
    opens com.example.inventorymanagement.client.admin.controllers to javafx.fxml;
    exports com.example.inventorymanagement.client.purchaser.controllers;
    opens com.example.inventorymanagement.client.purchaser.controllers to javafx.fxml;
    exports com.example.inventorymanagement.client.sales.controllers;
    opens com.example.inventorymanagement.client.sales.controllers to javafx.fxml;

    // Views
    exports com.example.inventorymanagement.client.common.views;
    opens com.example.inventorymanagement.client.common.views to javafx.fxml;
    exports com.example.inventorymanagement.client.admin.views;
    opens com.example.inventorymanagement.client.admin.views to javafx.fxml;
    exports com.example.inventorymanagement.client.purchaser.views;
    opens com.example.inventorymanagement.client.purchaser.views to javafx.fxml;
    exports com.example.inventorymanagement.client.sales.views;
    opens com.example.inventorymanagement.client.sales.views to javafx.fxml;

    // Models
    exports com.example.inventorymanagement.util.objects;

    // Server
    exports com.example.inventorymanagement.server.model;

    // Exceptions
    exports com.example.inventorymanagement.util.exceptions;

    // Callback
    exports com.example.inventorymanagement.util;

    // Requests
    exports com.example.inventorymanagement.util.requests;
}