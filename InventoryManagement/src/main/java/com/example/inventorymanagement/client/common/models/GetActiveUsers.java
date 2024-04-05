package com.example.inventorymanagement.client.common.models;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FetchActiveUsers {

    public void process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            UserRequestInterface userRequest = (UserRequestInterface) registry.lookup("UserRequestService");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            userRequest.getActiveUser(cB);

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
