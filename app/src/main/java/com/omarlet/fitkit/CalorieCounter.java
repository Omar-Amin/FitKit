package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.omarlet.fitkit.Adapter.FoodItemAdapter;
import com.omarlet.fitkit.Adapter.MPAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import java.util.ArrayList;

public class CalorieCounter extends AppCompatActivity {
    private int CALORIE_COUNTED = 109;
    private ArrayList<Food> foods = new ArrayList<>();
    private ListView listView;
    private FoodItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);

        listView = findViewById(R.id.foodList);
        //TODO: Extract saved data from phone then add to list when opening
        mAdapter = new FoodItemAdapter(foods);
        listView.setAdapter(mAdapter);

        Intent intent = getIntent();
        final Calorie calorie = (Calorie) intent.getSerializableExtra("Information");
        ImageButton addFood = findViewById(R.id.addFood);
        //TODO: Add functionality so the person can add name, grams and calories
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalorieCounter.this,InsertData.class);
                startActivityForResult(intent,CALORIE_COUNTED);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CALORIE_COUNTED) {
            if (resultCode == RESULT_OK) {
                String food = data.getStringExtra("foodAdded");
                String kcal = data.getStringExtra("kcalAdded");
                foods.add(new Food(food,kcal));
                mAdapter = new FoodItemAdapter(foods);
                listView.setAdapter(mAdapter);
                //Item added message?
                //Toast.makeText(CalorieCounter.this,"Food: " + food + " Kcal: " + kcal,Toast.LENGTH_LONG).show();
            }
        }
    }
}
