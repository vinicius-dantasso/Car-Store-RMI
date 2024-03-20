package implement;

import java.rmi.RemoteException;
import java.util.Map.Entry;

import database.Database;
import entities.User;
import interfaces.Auth;

public class ImplAuth implements Auth{

    @Override
    public User signIn(User user) throws RemoteException {

        for(Entry<String,User> entry : Database.users.entrySet()) {

            String email = entry.getKey();
            User stored = entry.getValue();
            if(email.equals(user.getEmail()) && user.getPassword().equals(stored.getPassword())) {
                return entry.getValue();
            }

        }

        return null;

    }

    @Override
    public boolean signUp(User user) throws RemoteException {

        for(Entry<String,User> entry : Database.users.entrySet()) {

            String email = entry.getKey();
            if(email.equals(user.getEmail())) {
                return false;
            }

        }

        Database.users.put(user.getEmail(), user);
        return true;

    }

    @Override
    public void addUser(User user) throws RemoteException {
        Database.users.put(user.getEmail(), user);
    }
    
}
