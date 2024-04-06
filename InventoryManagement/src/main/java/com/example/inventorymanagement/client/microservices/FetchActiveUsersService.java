package com.example.inventorymanagement.client.microservices;

import com.example.inventorymanagement.client.model.ClientCallbackImpl;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedList;

public class FetchActiveUsersService {

    public LinkedList<User> process (User requestBy){
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            UserRequestInterface userRequest = (UserRequestInterface) registry.lookup("userRequest");

            ClientCallback cB = new ClientCallbackImpl(requestBy);

          return userRequest.getActiveUser(cB);

        } catch (Exception e){
            e.printStackTrace();
            return new LinkedList<>();
        }
    }
}
