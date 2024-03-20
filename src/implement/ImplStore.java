package implement;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import database.Database;
import entities.Car;
import entities.CarType;
import interfaces.Store;

public class ImplStore implements Store {

    @Override
    public boolean addCar(Car car) throws RemoteException {

        Car modelStored = searchCar(car.getModel());

        if(modelStored != null) {

            car.setQuant(modelStored.getQuant() + 1);
            
            for(Entry<String, Car> entry : Database.carRena.entrySet()) {

                Car stored = entry.getValue();
                String model = stored.getModel();

                if(car.getModel().equals(model)) {
                    stored.setQuant(car.getQuant());
                    Database.carRena.put(stored.getRenavam(), stored);
                }

            }

            Database.carRena.put(car.getRenavam(), car);
            return true;

        }

        car.setQuant(1);
        Database.carRena.put(car.getRenavam(), car);

        return true;

    }

    @Override
    public boolean updateCar(Car car) throws RemoteException {

        Car info = searchCar(car.getRenavam());

        if(info != null) {
            Database.carRena.put(info.getRenavam(), car);
            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCar(String str) throws RemoteException {

        Car info = searchCar(str);

        if(info != null) {

            if(info.getQuant() > 1) {
                
                info.setQuant(info.getQuant() - 1);

                for(Entry<String, Car> entry : Database.carRena.entrySet()) {
                    String model = entry.getValue().getModel();

                    if(model.equals(info.getModel()) && !entry.getKey().equals(info.getRenavam())) {
                        entry.getValue().setQuant(info.getQuant());
                        Database.carRena.put(entry.getKey(), entry.getValue());
                    }
                }

                Database.carRena.remove(info.getRenavam());
                return true;

            }
            else {
                Database.carRena.remove(info.getRenavam());
                return true;
            }

        }

        return false;
    }

    @Override
    public Car searchCar(String str) throws RemoteException {

        // Verifica se a mensagem é composta apenas por números
        if(str.matches("\\d+")) {

            // Caso verdadeiro, faz uma busca por renavam
            for(Entry<String,Car> entrys : Database.carRena.entrySet()) {
                String renavam = entrys.getKey();
                if(renavam.equals(str)) {
                    return entrys.getValue();
                }
            }

        }

        // Caso falso, faz uma busca por nome
        for(Entry<String,Car> entrys : Database.carRena.entrySet()) {
            String model = entrys.getValue().getModel();
            if(model.equals(str)) {
                return entrys.getValue();
            }
        }

        return null;
    }

    @Override
    public List<Car> listAll() throws RemoteException {
        
        List<Car> list = new ArrayList<>(Database.carRena.values());
        list.sort(Comparator.comparing(Car::getModel));

        return list;
    }

    @Override
    public List<Car> listCategory(CarType type) throws RemoteException {

        List<Car> list = new ArrayList<>(Database.carRena.values());
        list.sort(Comparator.comparing(Car::getModel));

        List<Car> copy = new ArrayList<>(list);

        for(Car car : copy) {
            if(car.getType() != type) {
                list.remove(car);
            }
        }

        return list;
    }

    @Override
    public int showTotal() throws RemoteException {

        int total = Database.carRena.size();
        return total;
        
    }

    @Override
    public boolean buyCar(String str) throws RemoteException {

        if(deleteCar(str)) {
            return true;
        }
        return false;
        
    }
    
}
