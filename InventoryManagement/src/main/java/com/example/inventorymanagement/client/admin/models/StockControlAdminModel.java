package com.example.inventorymanagement.client.admin.models;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StockControlAdminModel {
    @FXML
    public void handleAddItem() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/addItem-view.fxml"));

            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Item");
            stage.setScene(new Scene(root));

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
