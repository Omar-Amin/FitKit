package com.omarlet.fitkit.Model;

public class Food {
    private String name;
    private String cals;

    public Food(String cals, String name){
        this.cals = cals;
        this.name = name;
    }

    public String getCals() {
        return cals;
    }

    public String getName() {
        return name;
    }

}
