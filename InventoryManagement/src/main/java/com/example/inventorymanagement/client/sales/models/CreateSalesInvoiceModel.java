package com.example.inventorymanagement.client.sales.models;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.ItemOrder;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreateSalesInvoiceModel {

    public void process (User requestBy, ItemOrder salesInvoice){
        try {
            Registry registry = LocateRegistry.getRegistry("locahost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("user");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            IORequest.createSalesInvoice(cB, salesInvoice);

        } catch (NotBoundException | RemoteException | OutOfRoleException | NotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }
}
