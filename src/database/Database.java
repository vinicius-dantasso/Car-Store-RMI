package database;

import java.io.Serializable;
import java.util.Map;

import entities.Car;
import entities.User;

public class Database implements Serializable{
    
    // Banco para os usuários
    public static Map<String, User> users;

    // Banco para os carros
    public static Map<String, Car> carRena;

}
