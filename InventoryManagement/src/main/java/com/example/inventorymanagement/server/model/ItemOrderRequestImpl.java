package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;

import java.rmi.RemoteException;

public class ItemOrderRequestImpl implements ItemOrderRequestInterface {

    public ItemOrderRequestImpl() throws RemoteException{

    }

    @Override
    public void createSalesInvoice(ClientCallback clientCallback, ItemOrder salesInvoice) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void createPurchaseOrder(ClientCallback clientCallback, ItemOrder purchaseOrder) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void fetchSalesInvoices(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void fetchRevenueToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void fetchCostToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void fetchMonthlyRevenue(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public void fetchMonthlyCost(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }
}
