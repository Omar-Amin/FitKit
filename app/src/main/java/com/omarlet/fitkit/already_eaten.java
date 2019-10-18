package com.omarlet.fitkit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omarlet.fitkit.Adapter.AlreadyEatenAdapter;
import com.omarlet.fitkit.Adapter.FoodItemAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class already_eaten extends AppCompatActivity {
    private ArrayList<Food> foods;
    private ListView listView;
    private AlreadyEatenAdapter mAdapter;
    private String SHAREDKEY = "FoodItems";
    private String DAY;
    private int currentCalories;
    private Food operation = new Food("","",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_eaten);

        Intent intent = getIntent();
        final Calorie calorie = (Calorie) intent.getSerializableExtra("InformationEaten");
        //Each key is which day is chosen
        SHAREDKEY = calorie.getDay()+"current";
        DAY = calorie.getDay();
        currentCalories = Integer.parseInt(calorie.getCals());
        listView = findViewById(R.id.foodEaten);
        //TODO: Extract saved data from phone then add to list when opening
        foods = operation.getArrayList(SHAREDKEY,this);
        if(foods == null){ // If nothing is returned from getArrayList it returns null, thus this is necessary
            foods = new ArrayList<>();
            Toast.makeText(already_eaten.this,"HALLO",Toast.LENGTH_LONG).show();
        }

        mAdapter = new AlreadyEatenAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);

        findViewById(R.id.addMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(already_eaten.this,CalorieCounter.class);
                intent.putExtra("Information",calorie);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume(); //To make sure that the list is updated after adding more food
        foods = operation.getArrayList(SHAREDKEY,this);
        SharedPreferences prefs = this.getSharedPreferences(DAY,Context.MODE_PRIVATE);
        currentCalories = prefs.getInt("CalorieCounted",0);
        mAdapter = new AlreadyEatenAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeCurrentData();
        storeData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        storeCurrentData();
        storeData();
    }

    private void storeData(){
        operation.saveArrayList(mAdapter.getFoods(),SHAREDKEY,this);
        ArrayList<Food> ccFood = operation.getArrayList(DAY,this);
        ArrayList<Food> tmp = mAdapter.getFoods();
        for (int i = 0; i < ccFood.size(); i++) {
            boolean isFound = false;
            Food food = ccFood.get(i);
            for (int j = 0; j < tmp.size(); j++) {
                Food aeFood = tmp.get(j);
                if(food.getName().equals(aeFood.getName())){
                    food.setAmount(aeFood.getAmount());
                    isFound = true;
                    break;
                }
            }
            if(isFound){
                ccFood.set(i,food);
            }else {
                food.setAmount(0);
                ccFood.set(i,food);
            }
        }
        operation.saveArrayList(ccFood,DAY,this);
    }

    private void storeCurrentData(){
        SharedPreferences prefs = this.getSharedPreferences(DAY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String CALORIECOUNTED = "CalorieCounted";
        editor.putInt(CALORIECOUNTED,mAdapter.getCurrentKcal());
        editor.apply();
    }

}
