package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

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
    private int CALORIE_COUNTED = 109;
    private ArrayList<Food> foods;
    private ListView listView;
    private AlreadyEatenAdapter mAdapter;
    private String SHAREDKEY = "FoodItems";
    private int currentCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_eaten);

        Intent intent = getIntent();
        final Calorie calorie = (Calorie) intent.getSerializableExtra("InformationEaten");
        //Each key is which day is chosen
        SHAREDKEY = calorie.getDay()+"current";
        currentCalories = Integer.parseInt(calorie.getCals());
        listView = findViewById(R.id.foodEaten);
        //TODO: Extract saved data from phone then add to list when opening
        foods = getArrayList(SHAREDKEY);
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

    private ArrayList<Food> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<ArrayList<Food>>(){}.getType();
        return gson.fromJson(json, type);
    }

    @Override
    protected void onResume() {
        super.onResume();
        foods = getArrayList(SHAREDKEY);
        mAdapter = new AlreadyEatenAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);
    }
}
