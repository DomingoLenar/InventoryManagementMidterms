package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RemoveUserService {

    public boolean process (Registry registry, ClientCallback cB , User toRemove){
        try {

            UserRequestInterface userRequest = (UserRequestInterface) registry.lookup("userRequest");

            return userRequest.removeUser(cB,toRemove);

        } catch (Exception e){
            e.printStackTrace();
            return false;
        }


    }
}
