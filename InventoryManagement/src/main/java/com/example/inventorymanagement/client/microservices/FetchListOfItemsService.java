package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.Item;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class FetchListOfItemsService {


    public LinkedList<Item> process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ItemRequestInterface ItemRequest = (ItemRequestInterface) registry.lookup("item");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            return ItemRequest.fetchLisOfItems(cB);

        } catch (NotBoundException | RemoteException | NotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }
}

