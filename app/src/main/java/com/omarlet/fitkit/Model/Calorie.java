package com.omarlet.fitkit.Model;

public class Calorie {
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
