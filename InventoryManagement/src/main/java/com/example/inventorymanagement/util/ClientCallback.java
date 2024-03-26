package com.example.inventorymanagement.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    public void objectCall(Object objectReturn) throws RemoteException;

    public void uiCall() throws RemoteException;

    public Object getObject();

}
