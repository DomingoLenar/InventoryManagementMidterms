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
    public void changeUserRole(ClientCallback clientCallback, User requestBy, User toChange, String newRole) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {
      if (!clientCallbacks.containsKey(requestBy)){
          throw new NotLoggedInException("Requesting user is not logged in");
      }

      if (!"admin".equals(requestBy.getRole())) {
          throw new OutOfRoleException("User does not have permission to change roles.");
      }

      boolean success = GSONProcessing.changeUserRole(toChange, newRole);

      if (!success){
          throw new UserExistenceException("User does not exist. Failed to change the role");
      }

      clientCallback.objectCall(true);
    }

    @Override
    public void changePassword(ClientCallback clientCallback, User requestBy, User toChange, String newPassword) throws OutOfRoleException, NotLoggedInException, RemoteException, UserExistenceException {

        if (!clientCallbacks.containsKey(requestBy)){
            throw new UserExistenceException("Requesting user is not logged in");
        }

        boolean success = GSONProcessing.changePassword(toChange, newPassword);

        if (!success){
            throw new UserExistenceException("User does not exist. Failed to change the password");
        }

        clientCallback.objectCall(true);
    }
}
