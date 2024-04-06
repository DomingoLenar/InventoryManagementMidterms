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
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class ItemOrderRequestImpl extends UnicastRemoteObject implements ItemOrderRequestInterface {

    public ItemOrderRequestImpl() throws RemoteException{

    }

    @Override
    public boolean createSalesInvoice(ClientCallback clientCallback, ItemOrder salesInvoice) throws RemoteException, OutOfRoleException, NotLoggedInException {

        checkIfValidPerm(clientCallback.getUser());
        checkIfLoggedIn(clientCallback);
        boolean success = GSONProcessing.addItemOrder("sales",salesInvoice);
        callUpdate("itemorder");

        return success;

    }

    @Override
    public boolean createPurchaseOrder(ClientCallback clientCallback, ItemOrder purchaseOrder) throws RemoteException, OutOfRoleException, NotLoggedInException {

        checkIfLoggedIn(clientCallback);
        checkIfLoggedIn(clientCallback);
        boolean success = GSONProcessing.addItemOrder("purchase",purchaseOrder);
        callUpdate("itemorder");

        return success;
    }

    @Override
    public LinkedList<ItemOrder> fetchSalesInvoices(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {

        checkIfValidPerm(clientCallback.getUser());
        checkIfLoggedIn(clientCallback);

        LinkedList<ItemOrder> salesInvoices = GSONProcessing.fetchListOfItemOrder("sales");
        return salesInvoices;
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
        LinkedHashMap<Integer, Float> revenues = new LinkedHashMap<>();

        for (int i = 1; i <= 12; i++) {
            revenues.put(i, 0f); // Float value 0
        }

        String currentYear = getCurrentDate().split("-")[0];


        LinkedList<ItemOrder> orders = GSONProcessing.fetchListOfItemOrder("sales");
        orders.stream().forEach(itemOrder -> {
            LinkedList<OrderDetail> orderDetails = itemOrder.getOrderDetails();
            float revenue = (float)orderDetails.stream().mapToDouble(orderDetail -> orderDetail.getQty()*orderDetail.getUnitPrice()).sum();
            String[] orderDate = itemOrder.getDate().split("-");
            int month = Integer.parseInt(orderDate[1]);

            if(orderDate[0].equals(currentYear)){
                float iValue = revenues.get(month);
                iValue += revenue;
                revenues.put(month, iValue);
            }

        });

        return revenues;
    }

    @Override
    public LinkedHashMap<Integer, Float> fetchMonthlyCost(ClientCallback clientCallback) throws RemoteException, OutOfRoleException, NotLoggedInException {
        LinkedHashMap<Integer, Float> revenues = new LinkedHashMap<>();

        for (int i = 1; i <= 12; i++) {
            revenues.put(i, 0f); // Float value 0
        }

        String currentYear = getCurrentDate().split("-")[0];


        LinkedList<ItemOrder> orders = GSONProcessing.fetchListOfItemOrder("sales");
        orders.stream().forEach(itemOrder -> {
            LinkedList<OrderDetail> orderDetails = itemOrder.getOrderDetails();
            float cost = (float)orderDetails.stream().mapToDouble(orderDetail -> orderDetail.getQty()*Float.parseFloat(orderDetail.getBatchNo().split("_")[2])).sum();
            String[] orderDate = itemOrder.getDate().split("-");
            int month = Integer.parseInt(orderDate[1]);

            if(orderDate[0].equals(currentYear)){
                float iValue = revenues.get(month);
                iValue += cost;
                revenues.put(month, iValue);
            }

        });

        return revenues;
    }

    @Override
    public ArrayList<String> fetchSuppliers(ClientCallback clientCallback) throws RemoteException, NotLoggedInException {
        checkIfLoggedIn(clientCallback);
        return GSONProcessing.fetchListOfSuppliers();
    }

    // Checks if user invoking the request has valid permissions
    private void checkIfValidPerm(User user) throws OutOfRoleException{
        String userRole = user.getRole();
        if(!(userRole.equals("admin") || userRole.equals("sales"))){
            throw new OutOfRoleException("Insufficient Permission");
        }
    }

    private void checkIfLoggedIn(ClientCallback clientCallback) throws NotLoggedInException {
        try {
            Registry reg = LocateRegistry.getRegistry("localhost", 2018);
            UserRequestInterfaceImplementation userStub = (UserRequestInterfaceImplementation) reg.lookup("userRequest");
            if (!(userStub.isLoggedIn(clientCallback))) throw new NotLoggedInException("Not Logged In");
        } catch (AccessException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (OutOfRoleException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getCurrentDate(){
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String currentDate = localDate.format(formatter);
        return currentDate;
    }

    private void callUpdate(String panel){
        try{
            Registry reg = LocateRegistry.getRegistry("localhost",2018);
            UserRequestInterfaceImplementation userStub = (UserRequestInterfaceImplementation) reg.lookup("userRequest");
            userStub.callUpdate(panel);
        }catch(Exception e){

        }
    }

}
