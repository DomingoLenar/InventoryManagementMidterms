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

public class CreateSalesInvoice {

    public boolean process (User requestBy, ItemOrder salesInvoice){
        try {
            Registry registry = LocateRegistry.getRegistry("locahost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("itemOrder");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            return IORequest.createSalesInvoice(cB, salesInvoice);

        } catch (NotBoundException | RemoteException | OutOfRoleException | NotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }
}
