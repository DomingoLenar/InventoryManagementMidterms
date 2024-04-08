package com.example.inventorymanagement.client.admin.views;

import com.example.inventorymanagement.client.admin.controllers.ProfileManagementChangePassAdminController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;

public class ProfileManagementChangePassAdminPanel {
    public void start(Stage stage, ProfileManagementChangePassAdminController controller) throws IOException {

        Font.loadFont(getClass().getResourceAsStream("/fonts/ShareTechMono-Regular.ttf"),20);

        FXMLLoader fxmlLoader = new FXMLLoader(ProfileManagementChangePassAdminPanel.class.getResource("/com/example/inventorymanagement/client/view/profileManagement/profileManagementChangePass-view.fxml"));
        InputStream inputStream = getClass().getResourceAsStream("/icons/logo.png");

        fxmlLoader.setController(controller);

        if (inputStream != null) {
            Image image = new Image(inputStream);
            stage.getIcons().add(image);
        } else {
            System.err.println("Failed to load image: logo.png");
        }

        Scene sceneprofileManagement = new Scene(fxmlLoader.load(), 850, 500);
        stage.setTitle("Stock Pilot");
        stage.setScene(sceneprofileManagement);
        stage.show();
        stage.setResizable(false);
    }
}

