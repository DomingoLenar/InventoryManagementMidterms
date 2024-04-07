package com.example.inventorymanagement.client.admin.models;

import com.example.inventorymanagement.client.microservices.*;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;

import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class FinancesAdminModel {
    private FetchLowestStockService fetchLowestStockService;
    private FetchMonthlyCostService fetchMonthlyCostService;
    private FetchMonthlyRevenueService fetchMonthlyRevenueService;
    private FetchTransTodayService fetchTransTodayService;
    private Registry registry;
    private ClientCallback clientCallback;

    public FinancesAdminModel(Registry registry, ClientCallback clientCallback) {
        this.registry = registry;
        this.clientCallback = clientCallback;
        this.fetchLowestStockService = new FetchLowestStockService();
        this.fetchMonthlyCostService = new FetchMonthlyCostService();
        this.fetchMonthlyRevenueService = new FetchMonthlyRevenueService();
        this.fetchTransTodayService = new FetchTransTodayService();
    }

    public LinkedList<Item> fetchLowestStockItems() throws NotLoggedInException {
        return fetchLowestStockService.process(registry, clientCallback);
    }

    public LinkedHashMap<Integer, Float> fetchMonthlyCost() throws NotLoggedInException, OutOfRoleException {
        return fetchMonthlyCostService.process(registry, clientCallback);
    }

    public LinkedHashMap<Integer, Float> fetchMonthlyRevenue() throws NotLoggedInException, OutOfRoleException {
        return fetchMonthlyRevenueService.process(registry, clientCallback);
    }

    public LinkedList<ItemOrder> fetchTransactionsToday() throws NotLoggedInException {
        return fetchTransTodayService.process(registry, clientCallback);
    }
}
