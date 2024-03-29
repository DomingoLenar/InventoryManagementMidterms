package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.objects.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GSONProcessing {
    public static boolean changePassword(User toChange, String newPassword) {
        try {
            String filePath = "com/example/inventorymanagement/data/users.json";
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new FileReader(filePath));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray userList = rootObject.getAsJsonArray("users");

            for (JsonElement userElement : userList) {
                JsonObject userObject = userElement.getAsJsonObject();
                String name = userObject.get("username").getAsString();
                if (name.equals(toChange.getUsername())) {
                    String currentPassword = userObject.get("password").getAsString();
                    if (currentPassword.equals(newPassword)) {
                        throw new Exception("Your new password cannot be the same as the old password");
                    }
                        userObject.addProperty("password", newPassword);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        FileWriter writer = new FileWriter(filePath);
                        gson.toJson(rootElement, writer);
                        writer.close();
                        return true;
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeUserRole(User toChange, String newRole) {
        String filePath = "com/example/inventorymanagement/data/users.json";
        try {
            Gson gson = new Gson();
            Type userListType = new TypeToken<List<User>>(){}.getType();
            List<User> users = gson.fromJson(new FileReader(filePath), userListType);

            boolean isChanged = false;

            for (User user : users) {
                if (user.getUsername().equals(toChange.getUsername())) {
                    user.setRole(newRole);
                    isChanged = true;
                    break;
                }
            }

            if (isChanged) {
                try (FileWriter writer = new FileWriter(filePath)) {
                    Gson prettyGson = new GsonBuilder().setPrettyPrinting().create();
                    prettyGson.toJson(users, writer);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<String> fetchListOfSuppliers() {
        ArrayList<String> suppliers = new ArrayList<>();
        try {
            String filePath = "com/example/inventorymanagement/data/suppliers.json";
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new FileReader(filePath));
            JsonObject jsonObject = rootElement.getAsJsonObject();
            JsonArray suppliersArray = jsonObject.getAsJsonArray("suppliers");

            for (JsonElement supplierElement : suppliersArray) {
                JsonObject supplierObject = supplierElement.getAsJsonObject();
                String name = supplierObject.get("name").getAsString();
                suppliers.add(name);
            }
            return suppliers;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
