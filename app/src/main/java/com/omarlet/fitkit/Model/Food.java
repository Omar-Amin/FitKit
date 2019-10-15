package com.omarlet.fitkit.Model;

public class Food {
    private String name;
    private String cals;
    private int amount;

    public Food(String cals, String name,int amount){
        this.cals = cals;
        this.name = name;
        this.amount = amount;
    }

    public String getCals() {
        return cals;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
