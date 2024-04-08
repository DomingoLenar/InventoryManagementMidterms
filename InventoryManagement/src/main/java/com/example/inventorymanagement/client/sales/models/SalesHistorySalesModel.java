package com.example.inventorymanagement.client.sales.models;

import com.example.inventorymanagement.client.microservices.FetchListOfItemsService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;

import java.rmi.registry.Registry;
import java.util.LinkedList;

public class SalesHistorySalesModel {
    private FetchListOfItemsService fetchLisOfItems;
    private Registry registry;
    private ClientCallback callback;


    public SalesHistorySalesModel(Registry registry, ClientCallback clientCallback) {
        this.fetchLisOfItems = new FetchListOfItemsService();
        this.registry = registry;
        this.callback = clientCallback;

    }
    public LinkedList<Item> fetchItems () throws NotLoggedInException {
        try {
            // Fetch items using FetchListOfItemsService
            return fetchLisOfItems.process(registry, callback);
        } catch (RuntimeException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return new LinkedList<>(); // Or throw an exception
        }
    }

}

