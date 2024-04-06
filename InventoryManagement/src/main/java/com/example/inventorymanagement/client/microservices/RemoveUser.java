package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoveUser {

    public boolean process (User requestBy , User toRemove){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            UserRequestInterface userRequest = (UserRequestInterface) registry.lookup("userRequest");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

            return userRequest.removeUser(cB,requestBy,toRemove);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}
