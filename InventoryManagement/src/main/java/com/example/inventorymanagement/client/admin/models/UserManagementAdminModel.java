package com.example.inventorymanagement.client.admin.models;

import com.example.inventorymanagement.client.microservices.FetchListOfUsersService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.User;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class UserManagementAdminModel {
    private Registry registry;
    private ClientCallback clientCallback;

    public UserManagementAdminModel(Registry registry, ClientCallback clientCallback) {
        this.registry = registry;
        this.clientCallback = clientCallback;
    }

    public LinkedList<User> fetchListOfUsers() throws NotLoggedInException, OutOfRoleException{
        try {
            return FetchListOfUsersService.process(clientCallback, registry);
        } catch (NotLoggedInException | OutOfRoleException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }
        return null;
    }
}
