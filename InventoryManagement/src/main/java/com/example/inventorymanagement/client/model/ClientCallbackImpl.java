package com.example.inventorymanagement.client.model;

import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientCallbackImpl extends UnicastRemoteObject implements Serializable, ClientCallback {
    private User user;
    public ClientCallbackImpl(User user) throws RemoteException {
        this.user = user;
    }

    @Override
    public User getUser() throws RemoteException {
        return user;
    }

    @Override
    public void objectCall(Object objectReturn) throws RemoteException {

    }

    @Override
    public void uiCall() throws RemoteException {

    }

    @Override
    public Object getObject() {
        return null;
    }


}
