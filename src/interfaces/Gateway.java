package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Car;
import entities.CarType;
import entities.User;

public interface Gateway extends Remote {

    // Autenticação
    User        signIn(User user) throws RemoteException;
    boolean     signUp(User user) throws RemoteException;
    void        addUser(User user) throws RemoteException;

    // Loja
    boolean     addCar(Car car) throws RemoteException;
    boolean     updateCar(Car car) throws RemoteException;
    boolean     deleteCar(String str) throws RemoteException;
    Car         searchCar(String str) throws RemoteException;
    List<Car>   listAll() throws RemoteException;
    List<Car>   listCategory(CarType type) throws RemoteException;
    int         showTotal() throws RemoteException;
    boolean     buyCar(String str) throws RemoteException;

}