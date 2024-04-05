package com.example.inventorymanagement.client.purchaser.models;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.server.model.ItemOrderRequestImpl;
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

public class CreatePurchaseOrderModel {
    
    public boolean process (User requestBy, ItemOrder purchaseOrder){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("user");

            ClientCallback cB = new ClientCallbackImpl(requestBy);
            
            return IORequest.createPurchaseOrder(cB, purchaseOrder);
            
            
        } catch (NotLoggedInException | OutOfRoleException | RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
