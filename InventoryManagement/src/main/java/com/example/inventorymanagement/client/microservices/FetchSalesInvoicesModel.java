package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class FetchSalesInvoicesModel {

    public LinkedList<ItemOrder> process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("itemOrder");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            return IORequest.fetchSalesInvoices(cB);


        } catch (NotLoggedInException | OutOfRoleException | RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }

}
