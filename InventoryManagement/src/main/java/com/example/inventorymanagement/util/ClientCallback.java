package com.example.inventorymanagement.util;

import com.example.inventorymanagement.util.objects.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCallback extends Remote {

    public void objectCall(Object objectReturn) throws RemoteException;

    public void uiCall() throws RemoteException;

    public Object getObject();

    public void setUser(User user) throws RemoteException;

    public User getUser() throws RemoteException;

}
