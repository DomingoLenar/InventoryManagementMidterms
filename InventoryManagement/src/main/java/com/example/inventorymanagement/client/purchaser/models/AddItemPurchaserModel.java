package com.example.inventorymanagement.client.purchaser.models;

import com.example.inventorymanagement.client.microservices.CreatePurchaseOrderService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;

import java.rmi.registry.Registry;

public class AddItemPurchaserModel {
    private Registry registry;
    private ClientCallback clientCallback;
    private CreatePurchaseOrderService createPurchaseOrderService;

    public AddItemPurchaserModel(Registry registry, ClientCallback clientCallback) {
        this.registry = registry;
        this.clientCallback = clientCallback;
        this.createPurchaseOrderService = new CreatePurchaseOrderService();
    }

    public boolean createPurchaseOrder(ItemOrder purchaseOrder) throws OutOfRoleException, NotLoggedInException {
        return CreatePurchaseOrderService.process(registry, clientCallback, purchaseOrder);
    }
}
