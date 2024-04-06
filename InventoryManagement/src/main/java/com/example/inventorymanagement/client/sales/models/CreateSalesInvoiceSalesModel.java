package com.example.inventorymanagement.client.sales.models;

import com.example.inventorymanagement.client.microservices.CreateSalesInvoiceService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;

import java.rmi.registry.Registry;

public class CreateSalesInvoiceSalesModel {

    private Registry registry;
    private ClientCallback clientCallback;
    private ItemOrder salesInvoice;

    public CreateSalesInvoiceSalesModel(Registry registry, ClientCallback clientCallback, ItemOrder salesInvoice) {
        this.registry = registry;
        this.clientCallback = clientCallback;
        this.salesInvoice = salesInvoice;
    }

    public boolean createSalesInvoice() throws OutOfRoleException, NotLoggedInException {
        return CreateSalesInvoiceService.process(registry, clientCallback, salesInvoice);
    }
}
