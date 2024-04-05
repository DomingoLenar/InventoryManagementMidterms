package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public void run() throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(2018);
        reg.rebind("userRequest", new UserRequestInterfaceImplementation());
        reg.rebind("itemOrder",new ItemOrderRequestImpl());
        reg.rebind("item", new ItemRequestImpl());
        System.out.println("Server has started");
    }

    public static void main (String[]args){
        try {
            Server server = new Server();
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
