package com.example.inventorymanagement.client.admin.models;

import com.example.inventorymanagement.client.microservices.FetchActiveUsersService;
import com.example.inventorymanagement.client.microservices.FetchMonthlyRevenueService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.User;

import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class DashboardAdminModel {

    private Registry registry;
    private ClientCallback clientCallback;

    /**
     * Constructor
     * @param registry          Object of registry that is used to access the registry of the server
     * @param clientCallback    Passed param should be of ClientCallbackImpl NOT ClientCallbackInterface
     */
    public DashboardAdminModel(Registry registry, ClientCallback clientCallback ){
        this.registry = registry;
        this.clientCallback = clientCallback;
    }

    public LinkedList<User> fetchActiveUsers() throws OutOfRoleException, NotLoggedInException {
        return FetchActiveUsersService.process(registry, clientCallback);
    }

    public LinkedHashMap<Integer, Float> fetchMonthlyRevenue() throws NotLoggedInException, OutOfRoleException{
        return FetchMonthlyRevenueService.process(registry, clientCallback);
    }


}
