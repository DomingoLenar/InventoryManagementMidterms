package com.example.inventorymanagement.server.model;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public void run() throws RemoteException {
        Registry reg = LocateRegistry.createRegistry(2018);
        reg.rebind("user", new UserRequestInterfaceImplementation());

    }
}
