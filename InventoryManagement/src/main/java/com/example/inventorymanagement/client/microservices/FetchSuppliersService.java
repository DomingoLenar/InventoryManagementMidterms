package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.ItemOrderRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class FetchSuppliersService {

    public ArrayList<String> process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("locahost", 1099);

            ItemOrderRequestInterface IORequest = (ItemOrderRequestInterface) registry.lookup("itemOrder");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            return IORequest.fetchSuppliers(cB);

        } catch (NotBoundException | RemoteException | NotLoggedInException e) {
            throw new RuntimeException(e);
        }
    }

}
