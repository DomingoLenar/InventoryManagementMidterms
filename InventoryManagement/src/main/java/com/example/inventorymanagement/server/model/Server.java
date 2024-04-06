package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args){
        try {
            UserRequestInterface userService = new UserRequestInterfaceImplementation();
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("user", userService);
            System.out.println("Server is running...");
            String s = "lol";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
