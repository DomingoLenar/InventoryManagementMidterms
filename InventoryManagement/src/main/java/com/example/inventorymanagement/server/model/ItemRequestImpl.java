package com.example.inventorymanagement.server.model;


import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class ItemRequestImpl implements ItemRequestInterface {
    @Override
    public LinkedList<Item> fetchLisOfItems(ClientCallback clientCallback) throws RemoteException, NotLoggedInException {
        checkIfLoggedIn(clientCallback);
        return GSONProcessing.fetchListOfItems();
    }

    @Override
    public boolean createItemListing(ClientCallback clientCallback, Item item) throws RemoteException, NotLoggedInException, OutOfRoleException {
        checkIfLoggedIn(clientCallback);
        return GSONProcessing.addItem(item);
    }

    @Override
    public boolean removeItemListing(ClientCallback clientCallback, Item item) throws RemoteException, NotLoggedInException, OutOfRoleException {
        checkIfLoggedIn(clientCallback);
        return GSONProcessing.removeItem(item.getItemName());
    }

    private void checkIfLoggedIn(ClientCallback clientCallback){
        try{
            Registry reg = LocateRegistry.getRegistry("localhost",2018);
            UserRequestInterfaceImplementation userStub = (UserRequestInterfaceImplementation) reg.lookup("userRequest");
            if (!(userStub.clientCallbacks.contains(clientCallback))) throw new NotLoggedInException("Not Logged In");
        }catch(Exception e){

        }
    }
}
