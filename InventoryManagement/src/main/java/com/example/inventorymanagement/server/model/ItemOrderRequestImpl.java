package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.OrderDetail;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ItemOrderRequestImpl implements ItemOrderRequestInterface {

    public ItemOrderRequestImpl() throws RemoteException{

    }

    @Override
    public boolean createSalesInvoice(ClientCallback clientCallback, ItemOrder salesInvoice) throws RemoteException, OutOfRoleException, NotLoggedInException {

        checkIfValidPerm(clientCallback.getUser());
        checkIfLoggedIn(clientCallback);

        return false;
    }

    @Override
    public boolean createPurchaseOrder(ClientCallback clientCallback, ItemOrder purchaseOrder) throws RemoteException, OutOfRoleException, NotLoggedInException {
        return false;
    }

    @Override
    public void fetchSalesInvoices(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

    }

    @Override
    public float fetchRevenueToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

        checkIfValidPerm(clientCallback.getUser());
        checkIfLoggedIn(clientCallback);

        float revenue = 0;
        LinkedList<ItemOrder> rawSalesList = GSONProcessing.fetchListOfItemOrder("sales");

        String currentDate = getCurrentDate();

        for(ItemOrder itemOrder : rawSalesList){

            if(itemOrder.getDate().equals(currentDate)) {

                LinkedList<OrderDetail> orderDetails = itemOrder.getOrderDetails();
                float cRevenue = (float) orderDetails.stream()
                        .mapToDouble(orderDetail -> {
                            return (orderDetail.getQty() * orderDetail.getUnitPrice());
                        }).sum();
                revenue += cRevenue;
            }
        }

        return revenue;
    }

    @Override
    public float fetchCostToday(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {
        checkIfValidPerm(clientCallback.getUser());
        checkIfLoggedIn(clientCallback);

        float cost = 0;
        LinkedList<ItemOrder> rawSalesList = GSONProcessing.fetchListOfItemOrder("sales");

        String currentDate = getCurrentDate();

        for(ItemOrder itemOrder : rawSalesList){

            if(itemOrder.getDate().equals(currentDate)) {

                LinkedList<OrderDetail> orderDetails = itemOrder.getOrderDetails();
                float cCost = (float) orderDetails.stream()
                        .mapToDouble(orderDetail -> {

                            String batchNo = orderDetail.getBatchNo();
                            String[] disseminatedBatch = batchNo.split("_");
                            float unitCost = Float.parseFloat(disseminatedBatch[2]);

                            return (orderDetail.getQty()*unitCost);

                        }).sum();
                cost += cCost;
            }
        }

        return cost;
    }

    @Override
    public LinkedHashMap<Integer, Float> fetchMonthlyRevenue(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {
        return null;
    }

    @Override
    public LinkedHashMap<Integer, Float> fetchMonthlyCost(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {
        return null;
    }

    // Checks if user invoking the request has valid permissions
    private void checkIfValidPerm(User user) throws OutOfRoleException{
        String userRole = user.getRole();
        if(!(userRole.equals("admin") || userRole.equals("sales"))){
            throw new OutOfRoleException("Insufficient Permission");
        }
    }

    private void checkIfLoggedIn(ClientCallback clientCallback) throws RemoteException, NotLoggedInException {
        try {
            Registry reg = LocateRegistry.getRegistry(2018);
            UserRequestInterfaceImplementation userServant = (UserRequestInterfaceImplementation) reg.lookup("user");

            if(!(userServant.clientCallbacks.contains(clientCallback))){
                throw new NotLoggedInException("Not logged in");
            }
        }catch(NotBoundException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    private static String getCurrentDate(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = localDate.format(formatter);
        return currentDate;
    }

}
