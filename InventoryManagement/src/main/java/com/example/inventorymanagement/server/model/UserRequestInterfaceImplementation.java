package com.example.inventorymanagement.server.model;

import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.requests.UserRequestInterface;
import com.example.inventorymanagement.util.exceptions.AlreadyLoggedInException;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;

// #TODO: Implement GSONProcessing methods, add real logic
public class UserRequestInterfaceImplementation implements UserRequestInterface {
    LinkedHashMap<User, ClientCallback> clientCallbacks = new LinkedHashMap<>();

    @Override
    public void login(ClientCallback clientCallback, User toLogin) throws RemoteException, AlreadyLoggedInException {
        if(clientCallbacks.containsKey(toLogin)){
            throw new AlreadyLoggedInException("User is already logged in");
        }else{
            //Use gson processor to validate login of user if valid then:
            User localUserData = new User(null,null,null); // Use gson processor to acquire this
            clientCallbacks.putFirst(toLogin,clientCallback);
            clientCallback.objectCall(localUserData);
        }

    }

    @Override
    public void logout(ClientCallback clientCallback, User toLogOut) throws RemoteException, NotLoggedInException {
        if(!clientCallbacks.containsKey(toLogOut)){
            throw new NotLoggedInException("User is not logged in");
        }else{
            clientCallbacks.remove(toLogOut);
        }
    }

    @Override
    public void getActiveUser(ClientCallback clientCallback, User requestBy) throws OutOfRoleException, NotLoggedInException, RemoteException {
        //clientCallback.returnCall();
    }

    @Override
    public void addUser(ClientCallback clientCallback, User requestBy, User toAdd) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {
        clientCallback.objectCall(false);
    }

    @Override
    public void removeUser(ClientCallback clientCallback, User requestBy, User toRemove) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {
        clientCallback.objectCall(false);
    }

    @Override
    public void changeUserRole(ClientCallback clientCallback, User requestBy, User toChange) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {
        clientCallback.objectCall(false);
    }

    @Override
    public void changePassword(ClientCallback clientCallback, User requestBy, User toChange) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {
        clientCallback.objectCall(false);
    }
}
