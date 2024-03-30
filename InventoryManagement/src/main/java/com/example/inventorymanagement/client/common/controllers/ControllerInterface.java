package com.example.inventorymanagement.client.common.controllers;

import java.rmi.Remote;
import java.rmi.RemoteException;

// #TODO: Implement this to all other controllers
public interface ControllerInterface extends Remote {

    public void fetchAndUpdate() throws RemoteException;

    public String getCurrentPanel() throws RemoteException;
}
