package com.example.inventorymanagement.client.model;

import com.example.inventorymanagement.util.ClientCallback;

import java.rmi.RemoteException;

// #TODO: Populate with actual logic, discuss with team what to do with ui call to remotely change ui elements
public class ClientCallbackImplementation implements ClientCallback {
    private Object returnedObject;

    @Override
    public void objectCall(Object objectReturn) throws RemoteException {
        returnedObject = objectReturn;
    }

    @Override
    public void uiCall() throws RemoteException {

    }

    @Override
    public Object getObject() {
        return returnedObject;
    }
}
