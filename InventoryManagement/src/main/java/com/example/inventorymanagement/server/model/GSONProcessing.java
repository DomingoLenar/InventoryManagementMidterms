package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.User;
import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.LinkedList;

public class GSONProcessing {
    public static synchronized boolean changePassword(String userName, String newPassword, String oldPassword) {
        try {
            String filePath = "com/example/inventorymanagement/data/users.json";
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new FileReader(filePath));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray userList = rootObject.getAsJsonArray("users");

            for (JsonElement userElement : userList) {
                JsonObject userObject = userElement.getAsJsonObject();
                String name = userObject.get("username").getAsString();
                if (name.equals(userName)) {
                    String password = userObject.get("password").getAsString();
                    if (password.equals(oldPassword)) {
                        userObject.addProperty("password", newPassword);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        FileWriter writer = new FileWriter(filePath);
                        gson.toJson(rootElement, writer);
                        writer.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized boolean changeUserRole(String userName, String newRole) {
        try {
            String filePath = "InventoryManagement/src/server/res/users.json";
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(new FileReader(filePath));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray userList = rootObject.getAsJsonArray("users");

            for (JsonElement userElement : userList) {
                JsonObject userObject = userElement.getAsJsonObject();
                String name = userObject.get("username").getAsString();
                if (name.equals(userName)) {
                    String role = userObject.get("role").getAsString();
                    if (!role.equals(newRole)) {
                        userObject.addProperty("role", newRole);
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        FileWriter writer = new FileWriter(filePath);
                        gson.toJson(rootElement, writer);
                        writer.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static synchronized ArrayList<String> fetchListOfSuppliers() {
        ArrayList<String> suppliers = new ArrayList<>();
        try {
            String filePath = "InventoryManagement/src/server/res/suppliers.json";
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


    /**
     * Method for fetching object of user
     * @param username  String of username will be used as a search key to find the object of user inside the json
     * @return  object of User or null if not found
     */
    public static synchronized User fetchUser(String username){
        try{
            String jsonFile = "com/example/inventorymanagement/data/users.json";
            JsonElement rootElement = JsonParser.parseReader(new FileReader(jsonFile));
            JsonObject jsonObject = rootElement.getAsJsonObject();
            JsonArray jsonArray = jsonObject.getAsJsonArray("users");
            for(JsonElement jsonElement: jsonArray){
                Gson gson = new Gson();

                JsonObject userObject = jsonElement.getAsJsonObject();
                if(userObject.get("username").getAsString().equals(username))
                    return gson.fromJson(jsonElement, User.class);

            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;
    }


    /**
     *   Method for fetching list of item orders (purchase or sales)
     * @param type  String value, should be either purchase or sales
     * @return  Returns LinkedList of object ItemOrder
     */
    public static synchronized LinkedList<ItemOrder> fetchListOfItemOrder(String type){
        LinkedList<ItemOrder> listOfItemOrder = new LinkedList<>();
        try{
            String jsonFile = "com/example/inventorymanagement/data/"+type+"order.json";
            JsonElement rootElement = JsonParser.parseReader(new FileReader(jsonFile));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray jsonArray = rootObject.getAsJsonArray(type+"Orders");
            for(JsonElement jsonElement: jsonArray){
                Gson gson = new Gson();
                ItemOrder itemOrder = gson.fromJson(jsonElement, ItemOrder.class);
                listOfItemOrder.addLast(itemOrder);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return listOfItemOrder;
    }

    public static synchronized LinkedList<Item> fetchListOfItems(){
        LinkedList<Item> itemList = new LinkedList<>();
        try{
            String itemJsonFile = "com/example/inventorymanagement/data/items.json";
            JsonElement rootElement = JsonParser.parseReader(new FileReader(itemJsonFile));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray itemJsonArray = rootObject.getAsJsonArray("items");
            for(JsonElement jsonElement : itemJsonArray){
                Gson gson = new Gson();
                Item item = gson.fromJson(jsonElement, Item.class);
                itemList.addLast(item);
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return itemList;
    }

    public static synchronized boolean addUser(User newUser){
        Gson gson = new Gson();
        try{
            String jsonFile = "com/example/inventorymanagement/data/users.json";
            JsonElement rootElement = JsonParser.parseReader(new FileReader(jsonFile));
            JsonObject rootObject = rootElement.getAsJsonObject();
            JsonArray userJsonArray = rootObject.getAsJsonArray("users");

            String jsonString = gson.toJson(newUser);
            JsonElement userElement = JsonParser.parseString(jsonString);
            userJsonArray.add(userElement);

            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
