
module com.example.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports com.example.inventorymanagement.client.common.controllers;
    opens com.example.inventorymanagement.client.common.controllers to javafx.fxml;
    exports com.example.inventorymanagement.client.admin.controllers;
    opens com.example.inventorymanagement.client.admin.controllers to javafx.fxml;
    exports com.example.inventorymanagement.client.common.views;
    opens com.example.inventorymanagement.client.common.views to javafx.fxml;
    exports com.example.inventorymanagement.client.admin.views;
    opens com.example.inventorymanagement.client.admin.views to javafx.fxml;
}