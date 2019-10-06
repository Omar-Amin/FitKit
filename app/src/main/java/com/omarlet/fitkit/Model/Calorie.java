package com.omarlet.fitkit.Model;


import java.io.Serializable;

public class Calorie implements Serializable {
    private String day;
    private String cals;

    public Calorie(String cals, String day){
        this.cals = cals;
        this.day = day;
    }

    public String getCals() {
        return cals;
    }

    public String getDay() {
        return day;
    }

}
