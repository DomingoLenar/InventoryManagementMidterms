package com.example.inventorymanagement.client.admin.models;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.io.IOException;

public class CreateSalesInvoiceModel {
    /**
     * Handle Action Event when clicking createSalesInvoiceButton
     */
    @FXML
    public void handleSalesInvoice() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/inventorymanagement/client/view/stockControl/createSalesInvoice-view.fxml"));
            Parent root = fxmlLoader.load();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Sales Invoice");

            DialogPane dialogPane = new DialogPane();
            dialogPane.setContent(root);
            dialog.setDialogPane(dialogPane);

            dialog.initStyle(StageStyle.UNDECORATED);

            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
            okButton.setOnAction(event -> {
                //TODO: Logic from CreateSalesInvoicesModel for invoicing items using GSONProcessing
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(event -> dialog.close());

            dialog.getDialogPane().getChildren().addAll(okButton, cancelButton);

            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
