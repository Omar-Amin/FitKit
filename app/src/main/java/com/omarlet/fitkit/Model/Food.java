package com.omarlet.fitkit.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

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

    /**
     * @param list list to be saved
     * @param key key to the dataset
     * The function saves the arraylist so it can be retrieved later on
     * */
    public void saveArrayList(ArrayList<Food> list, String key, Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();
    }

    /**
     * @param key key to the dataset
     * The function retrieves the arraylist of objects holding Food
     * */
    public ArrayList<Food> getArrayList(String key,Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<ArrayList<Food>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
