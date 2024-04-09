package com.example.inventorymanagement.client.sales.models;

import com.example.inventorymanagement.client.microservices.FetchSalesInvoicesService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;

import java.rmi.registry.Registry;
import java.util.LinkedList;

public class SalesHistorySalesModel {
    private FetchSalesInvoicesService fetchSalesInvoicesService;
    private Registry registry;
    private ClientCallback callback;


    public SalesHistorySalesModel(Registry registry, ClientCallback clientCallback) {
        this.fetchSalesInvoicesService = new FetchSalesInvoicesService();
        this.registry = registry;
        this.callback = clientCallback;
    }
    public LinkedList<ItemOrder> fetchItems () throws NotLoggedInException {
        try {
            // Fetch items using FetchListOfItemsService
            return fetchSalesInvoicesService.process(registry, callback);
        } catch (RuntimeException | OutOfRoleException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return new LinkedList<>(); // Or throw an exception
        }
    }
}

