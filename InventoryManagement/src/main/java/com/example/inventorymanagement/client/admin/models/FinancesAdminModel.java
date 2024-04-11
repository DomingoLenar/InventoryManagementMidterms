package com.example.inventorymanagement.client.admin.models;

import com.example.inventorymanagement.client.microservices.*;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;

import java.rmi.registry.Registry;
import java.util.LinkedHashMap;

public class FinancesAdminModel {
    private FetchMonthlyCostService fetchMonthlyCostService;
    private FetchMonthlyRevenueService fetchMonthlyRevenueService;
    private FetchCostTodayService fetchCostTodayService;
    private FetchRevenueTodayService fetchRevenueTodayService;
    private Registry registry;
    private ClientCallback clientCallback;

    public FinancesAdminModel(Registry registry, ClientCallback clientCallback) {
        this.registry = registry;
        this.clientCallback = clientCallback;
        this.fetchMonthlyCostService = new FetchMonthlyCostService();
        this.fetchMonthlyRevenueService = new FetchMonthlyRevenueService();
        this.fetchCostTodayService = new FetchCostTodayService();
        this.fetchRevenueTodayService = new FetchRevenueTodayService();
    }

    public LinkedHashMap<Integer, Float> fetchMonthlyCost() throws NotLoggedInException, OutOfRoleException {
        return fetchMonthlyCostService.process(registry, clientCallback);
    }

    public LinkedHashMap<Integer, Float> fetchMonthlyRevenue() throws NotLoggedInException, OutOfRoleException {
        return fetchMonthlyRevenueService.process(registry, clientCallback);
    }

    public float fetchCostToday() throws NotLoggedInException, OutOfRoleException {
        return fetchCostTodayService.process(registry, clientCallback);
    }

    public float fetchRevenueToday() throws NotLoggedInException, OutOfRoleException {
        return fetchRevenueTodayService.process(registry, clientCallback);
    }
    public float computeGrossRevenue(LinkedHashMap<Integer, Float> monthlyRevenue) {
        float totalRevenue = 0;
        for (float revenue : monthlyRevenue.values()) {
            totalRevenue += revenue;
        }
        return totalRevenue;
    }

    public float computeTaxDeductible(float grossRevenue, float grossCost) {
        // Assuming tax-deductible is 30% of the gross revenue minus total monthly cost
        return 0.3f * (grossRevenue - grossCost);
    }

    public float computeStockWorth(float grossRevenue, float taxDeductible) {
        // Assuming stock worth is 70% of the gross revenue minus tax-deductible
        return 0.7f * (grossRevenue - taxDeductible);
    }

    public float computeGrossProfit(float grossRevenue, float grossCost) {
        return grossRevenue - grossCost;
    }

    public float computeTaxDeductible(float grossRevenue, LinkedHashMap<Integer, Float> monthlyCost) {
        // Calculate total monthly cost
        float totalMonthlyCost = 0;
        for (float cost : monthlyCost.values()) {
            totalMonthlyCost += cost;
        }

        // Assuming tax-deductible is 30% of the gross revenue minus total monthly cost
        return 0.3f * (grossRevenue - totalMonthlyCost);
    }

    public float computeGrossProfit(float grossRevenue, LinkedHashMap<Integer, Float> grossCost) {
        // Calculate total gross cost
        float totalGrossCost = 0;
        for (float cost : grossCost.values()) {
            totalGrossCost += cost;
        }

        // Gross profit is the difference between gross revenue and total gross cost
        return grossRevenue - totalGrossCost;
    }

}
