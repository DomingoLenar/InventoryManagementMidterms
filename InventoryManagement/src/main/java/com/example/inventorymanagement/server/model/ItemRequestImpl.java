package com.example.inventorymanagement.server.model;


import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Comparator;
import java.util.LinkedList;

public class ItemRequestImpl implements ItemRequestInterface {
    @Override
    public LinkedList<Item> fetchLisOfItems(ClientCallback clientCallback) throws RemoteException, NotLoggedInException {
        checkIfLoggedIn(clientCallback);
        return GSONProcessing.fetchListOfItems();
    }

    public LinkedList<Item> fetchLowestStock(ClientCallback clientCallback) throws RemoteException, NotLoggedInException{
        checkIfLoggedIn(clientCallback);
        LinkedList<Item> items = GSONProcessing.fetchListOfItems();
        return  items.stream()
                .sorted(Comparator.comparingInt(Item::getTotalQty))
                .limit(5)
                .collect(LinkedList::new, LinkedList::add, LinkedList::addAll);

    }

    @Override
    public boolean createItemListing(ClientCallback clientCallback, Item item) throws RemoteException, NotLoggedInException, OutOfRoleException {
        checkIfLoggedIn(clientCallback);
        boolean success = GSONProcessing.addItem(item);
        callUpdate("item");
        return success;
    }

    @Override
    public boolean removeItemListing(ClientCallback clientCallback, Item item) throws RemoteException, NotLoggedInException, OutOfRoleException {
        checkIfLoggedIn(clientCallback);
        boolean success = GSONProcessing.removeItem(item.getItemName());
        callUpdate("item");
        return success;
    }

    private void checkIfLoggedIn(ClientCallback clientCallback) throws NotLoggedInException {
        try{
            Registry reg = LocateRegistry.getRegistry("localhost",2018);
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

    private void callUpdate(String panel){
        try{
            Registry reg = LocateRegistry.getRegistry("localhost",2018);
            UserRequestInterfaceImplementation userStub = (UserRequestInterfaceImplementation) reg.lookup("userRequest");
            userStub.callUpdate(panel);
        }catch(Exception e){

        }
    }
}
