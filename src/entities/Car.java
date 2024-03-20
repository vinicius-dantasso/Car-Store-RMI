package entities;

import java.io.Serializable;

public class Car implements Serializable {
    
    private String model;
    private String renavam;
    private String year;
    private double price;
    private int quant;
    private CarType type;

    public Car(String m, String r, String y, double p, CarType c) {
        this.model = m;
        this.renavam = r;
        this.year = y;
        this.price = p;
        this.type = c;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRenavam() {
        return this.renavam;
    }

    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuant() {
        return this.quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public CarType getType() {
        return this.type;
    }

    public void setType(CarType type) {
        this.type = type;
    }

}
