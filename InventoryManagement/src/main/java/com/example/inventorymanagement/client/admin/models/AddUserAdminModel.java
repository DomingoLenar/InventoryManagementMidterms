package com.example.inventorymanagement.client.admin.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddUserAdminModel {
    private final StringProperty role;
    private final StringProperty username;
    private final StringProperty password;

    public  AddUserAdminModel() {
        this(null, null, null);
    }

    public AddUserAdminModel (String role, String username, String password) {
        this.role = new SimpleStringProperty(role);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getRole() {
        return role.get();
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public StringProperty roleProperty() {
        return role;
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }
}
