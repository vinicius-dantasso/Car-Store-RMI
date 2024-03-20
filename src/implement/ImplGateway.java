package implement;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import entities.Car;
import entities.CarType;
import entities.User;
import interfaces.Auth;
import interfaces.Gateway;
import interfaces.Store;

public class ImplGateway implements Gateway {

    private Auth auth;
    private Store store;

    public ImplGateway() {
        authConnection();
        storeConnection();
    }

    private static void config() {
        System.setProperty("java.security.police", "java.policy");
    }

    // Conexão com o servidor de autenticação
    private void authConnection() {
        
        config();
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",50006);
            auth = (Auth) registry.lookup("Auth");
        }
        catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    // Conexão com o servidor da loja
    private void storeConnection() {

        config();
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",50007);
            store = (Store) registry.lookup("Store");
        }
        catch(RemoteException | NotBoundException e) {
            e.printStackTrace();
        }

    }

    // ============================================================================
    // Métodos de comunicação com o servidor de Autenticação
    @Override
    public User signIn(User user) throws RemoteException {
        
        User response = auth.signIn(user);
        if(response != null) {
            return response;
        }
        return null;

    }

    @Override
    public boolean signUp(User user) throws RemoteException {

        if(auth.signUp(user)) {
            return true;
        }
        return false;

    }

    @Override
    public void addUser(User user) throws RemoteException {
        auth.addUser(user);
    }
    // ============================================================================
    // Métodos de comunicação com o servidor da loja
    @Override
    public boolean addCar(Car car) throws RemoteException {
        if(store.addCar(car)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCar(Car car) throws RemoteException {
        if(store.updateCar(car)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCar(String str) throws RemoteException {
        if(store.deleteCar(str)) {
            return true;
        }
        return false;
    }

    @Override
    public Car searchCar(String str) throws RemoteException {
        Car response = store.searchCar(str);
        if(response != null) {
            return response;
        }
       return null;
    }

    @Override
    public List<Car> listAll() throws RemoteException {
        
        List<Car> list = new ArrayList<>();
        List<Car> receveid = store.listAll();
        if(receveid != null) {
            list = receveid;
            return list;
        }
        return null;

    }

    @Override
    public List<Car> listCategory(CarType type) throws RemoteException {
        List<Car> list = new ArrayList<>();
        List<Car> receveid = store.listCategory(type);
        if(receveid != null) {
            list = receveid;
            return list;
        }
        return null;
    }

    @Override
    public int showTotal() throws RemoteException {
        return store.showTotal();
    }

    @Override
    public boolean buyCar(String str) throws RemoteException {
        if(store.buyCar(str)) {
            return true;
        }
        return false;
    }

}
