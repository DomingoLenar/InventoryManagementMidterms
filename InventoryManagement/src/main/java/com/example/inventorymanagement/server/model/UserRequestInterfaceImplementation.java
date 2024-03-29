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
import java.util.LinkedList;

// #TODO: Implement GSONProcessing methods, add real logic
public class UserRequestInterfaceImplementation implements UserRequestInterface {
    LinkedList<ClientCallback> clientCallbacks = new LinkedList<>();

    @Override
    public void login(ClientCallback clientCallback) throws RemoteException, AlreadyLoggedInException, UserExistenceException {
        if(clientCallbacks.contains(clientCallback)){
            throw new AlreadyLoggedInException("User is already logged in");
        }else{
            User toLogIn = clientCallback.getUser();
            User localUserData = GSONProcessing.fetchUser(toLogIn.getUsername());
            if(localUserData!=null && toLogIn.getPassword().equals(localUserData.getPassword())){
                clientCallbacks.addFirst(clientCallback);
                clientCallback.setUser(localUserData);
            }else{
                throw new UserExistenceException("Invalid credentials");
            }
        }

    }

    @Override
    public void logout(ClientCallback clientCallback) throws RemoteException, NotLoggedInException {
        if(!clientCallbacks.contains(clientCallback)){
            throw new NotLoggedInException("User is not logged in");
        }else{
            clientCallbacks.remove(clientCallback);
        }
    }

    @Override
    public void getActiveUser(ClientCallback clientCallback) throws OutOfRoleException, NotLoggedInException, RemoteException {
        if(clientCallback.getUser().getRole().equals("admin")){

            if(!clientCallbacks.contains(clientCallback)){
                throw new NotLoggedInException("Not logged in");
            }else{
                LinkedList<User> listOfUsers = new LinkedList<>();
                for(ClientCallback clients : clientCallbacks){
                    listOfUsers.addLast(clients.getUser());
                }
                clientCallback.objectCall(listOfUsers);
            }
        }else{
            throw new OutOfRoleException("Your are not allowed to perform this request");
        }
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
