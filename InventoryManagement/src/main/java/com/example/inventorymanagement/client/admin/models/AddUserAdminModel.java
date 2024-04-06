package com.example.inventorymanagement.client.admin.models;

import com.example.inventorymanagement.client.microservices.AddUserService;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;
import com.example.inventorymanagement.util.requests.UserRequestInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class AddUserAdminModel {
    private static AddUserService addUserService;
    private static Registry registry;
    private static ClientCallback clientCallback;

    public AddUserAdminModel() {
        this.addUserService = new AddUserService();
        this.registry = registry;
        this.clientCallback = clientCallback;
    }

    public static boolean process(User newUser) throws NotLoggedInException {
        try {
            return addUserService.process(clientCallback, newUser);
        } catch (RuntimeException | UserExistenceException | OutOfRoleException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return false;
        }
    }


    public boolean addUser(User newUser) throws RemoteException {
        try {
            // Check if the user has the necessary role to add a user
            if (!clientCallback.getUser().getRole().equals("Admin")) {
                throw new OutOfRoleException("Insufficient permission to add user");
            }

            // Perform validation checks on the new user (no username duplicates, password length)
            if (!isUsernameUnique(newUser.getUsername())) {
                throw new UserExistenceException("Username already exists");
            }
            if (newUser.getPassword().length() < 7) {
                throw new IllegalArgumentException("Password must be at least 7 characters long");
            }

            // Once validation is successful, proceed with adding the user
            UserRequestInterface userRequest = (UserRequestInterface) registry.lookup("userRequest");
            boolean success = userRequest.addUser(clientCallback, newUser);
            return success;
        } catch (RemoteException | NotBoundException | OutOfRoleException | NotLoggedInException e) {
            // Handle exceptions related to RMI registry lookup
            e.printStackTrace(); // Log or handle the exception appropriately
            throw new RemoteException("Failed to communicate with server", e); // Throw a custom exception or handle the error
        } catch (UserExistenceException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isUsernameUnique(String username) {
        List<User> existingUsers = fetchExistingUsers();

        // Iterate over the existing users to check if the username already exists
        for (User user : existingUsers) {
            if (user.getUsername().equals(username)) {
                return false; // Username already exists
            }
        }
        return true; // Username is unique
    }

    private List<User> fetchExistingUsers() {
        return new ArrayList<>();
    }
}