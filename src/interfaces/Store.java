package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import entities.Car;
import entities.CarType;

public interface Store extends Remote {

    boolean     addCar(Car car) throws RemoteException;
    boolean     updateCar(Car car) throws RemoteException;
    boolean     deleteCar(String str) throws RemoteException;
    Car         searchCar(String str) throws RemoteException;
    List<Car>   listAll() throws RemoteException;
    List<Car>   listCategory(CarType type) throws RemoteException;
    int         showTotal() throws RemoteException;
    boolean     buyCar(String str) throws RemoteException;

} 
