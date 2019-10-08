package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.omarlet.fitkit.Adapter.FoodItemAdapter;
import com.omarlet.fitkit.Adapter.MPAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CalorieCounter extends AppCompatActivity {
    private int CALORIE_COUNTED = 109;
    private ArrayList<Food> foods = new ArrayList<>();
    private ListView listView;
    private FoodItemAdapter mAdapter;
    private Calorie calorie;
    private SharedPreferences pref;
    private String SHAREDKEY = "FoodItems";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        Intent intent = getIntent();
        calorie = (Calorie) intent.getSerializableExtra("Information");
        //Each key is which day is chosen
        SHAREDKEY = calorie.getDay();
        listView = findViewById(R.id.foodList);
        //TODO: Extract saved data from phone then add to list when opening
        foods = getArrayList(SHAREDKEY);
        if(foods != null){ //If nothing is returned from getArrayList it returns null, thus this is necessary
            mAdapter = new FoodItemAdapter(foods);
            listView.setAdapter(mAdapter);
        }

        ImageButton addFood = findViewById(R.id.addFood);
        //TODO: Add functionality so the person can add name, grams and calories
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalorieCounter.this,InsertData.class);
                startActivityForResult(intent,CALORIE_COUNTED);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CalorieCounter.this,"LISTVIEW",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CALORIE_COUNTED) {
            if (resultCode == RESULT_OK) {
                if(foods == null){ //will result in null when the user haven't added anything at all
                    foods = new ArrayList<>(); //because of Gson
                }
                String food = data.getStringExtra("foodAdded");
                String kcal = data.getStringExtra("kcalAdded");
                foods.add(new Food(kcal,food));
                mAdapter = new FoodItemAdapter(foods);
                listView.setAdapter(mAdapter);
                saveArrayList(foods,SHAREDKEY);
                //Item added message?
                //Toast.makeText(CalorieCounter.this,"Food: " + food + " Kcal: " + kcal,Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * @param list list to be saved
     * @param key key to the dataset
     * The function saves the arraylist so it can be retrieved later on
     * */
    public void saveArrayList(ArrayList<Food> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    /**
     * @param key key to the dataset
     * The function retrieves the arraylist of objects holding Food
     * */
    public ArrayList<Food> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString(key, "");
        Type type = new TypeToken<ArrayList<Food>>(){}.getType();
        return gson.fromJson(json, type);
    }

}
