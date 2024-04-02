package com.example.inventorymanagement.util.requests;

import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ItemOrderRequestInterface {

    public void createSalesInvoice(ClientCallback clientCallback, ItemOrder salesInvoice) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void createPurchaseOrder(ClientCallback clientCallback, ItemOrder purchaseOrder) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void fetchSalesInvoices(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void fetchRevenueToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void fetchCostToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void fetchMonthlyRevenue(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException;

    public void fetchMonthlyCost(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException;

}
