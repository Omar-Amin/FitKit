package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.omarlet.fitkit.Adapter.FoodItemAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import java.util.ArrayList;

public class already_eaten extends AppCompatActivity {
    private int CALORIE_COUNTED = 109;
    private ArrayList<Food> foods;
    private ListView listView;
    private FoodItemAdapter mAdapter;
    private String SHAREDKEY = "FoodItems";
    private int currentCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_eaten);
        CalorieCounter cc = new CalorieCounter();

        Intent intent = getIntent();
        Calorie calorie = (Calorie) intent.getSerializableExtra("Information");
        //Each key is which day is chosen
        SHAREDKEY = calorie.getDay()+"Eaten";
        currentCalories = Integer.parseInt(calorie.getCals());
        listView = findViewById(R.id.foodEaten);
        //TODO: Extract saved data from phone then add to list when opening
        foods = cc.getArrayList(SHAREDKEY);
        if(foods == null){ // If nothing is returned from getArrayList it returns null, thus this is necessary
            foods = new ArrayList<>();
        }

        mAdapter = new FoodItemAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);

    }

}
