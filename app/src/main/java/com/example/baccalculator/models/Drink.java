package com.example.baccalculator.models;

import java.io.Serializable;
import java.util.Date;

public class Drink implements Serializable {
    private double alcohol, size;
    private Date addedOn;

    public Drink(double alcohol, double size, Date date) {
        this.alcohol = alcohol;
        this.size = size;
        this.addedOn = date;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }
    public Date getAddedOn() {
        return addedOn;
    }
    public void setAddedOn(Date addedOn) {
        this.addedOn = addedOn;
    }
}
