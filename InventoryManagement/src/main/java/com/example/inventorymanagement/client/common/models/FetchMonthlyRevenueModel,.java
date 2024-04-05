package com.example.inventorymanagement.client.common.models;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FetchMonthlyRevenueModel {

    public void process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("user");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            IORequest.fetchMonthlyRevenue(cB);


        } catch (NotLoggedInException | OutOfRoleException | RemoteException | NotBoundException e) {
            throw new RuntimeException(e);
        }

    }
}
