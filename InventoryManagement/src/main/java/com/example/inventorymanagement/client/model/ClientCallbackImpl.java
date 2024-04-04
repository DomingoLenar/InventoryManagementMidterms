package com.example.inventorymanagement.client.model;

import com.example.inventorymanagement.client.common.controllers.ControllerInterface;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.objects.User;

import java.rmi.RemoteException;

// #TODO: Populate with actual logic, discuss with team what to do with ui call to remotely change ui elements
public class ClientCallbackImpl implements ClientCallback {
    private Object returnedObject;
    private User user;
    private ControllerInterface currentController;

    public ClientCallbackImpl(User user){
        this.user = user;
    }

    @Deprecated
    @Override
    public void objectCall(Object objectReturn) throws RemoteException {
        returnedObject = objectReturn;
    }

    @Override
    public void uiCall() throws RemoteException {

    }

    @Deprecated
    @Override
    public Object getObject() {
        return returnedObject;
    }

    @Override
    public void setUser(User user) throws RemoteException {
        this.user = user;
    }

    @Override
    public User getUser() throws RemoteException {
        return this.user;
    }

    public void setCurrentPanel(ControllerInterface currentController){
        this.currentController = currentController;
    }

    @Override
    public String getCurrentPanelOfController() throws RemoteException {
        return currentController.getCurrentPanel();
    }

    @Override
    public void updateUICall() throws RemoteException{
        currentController.fetchAndUpdate();
    }


}
