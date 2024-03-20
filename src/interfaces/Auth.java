package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import entities.User;

public interface Auth extends Remote {

    User    signIn(User user) throws RemoteException;
    boolean signUp(User user) throws RemoteException;
    void    addUser(User user) throws RemoteException;

}
