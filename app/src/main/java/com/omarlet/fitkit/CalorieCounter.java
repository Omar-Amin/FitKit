package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.omarlet.fitkit.Model.Calorie;

import java.util.ArrayList;

public class CalorieCounter extends AppCompatActivity {
    private int CALORIE_COUNTED = 109;
    private ArrayList<>
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        Intent intent = getIntent();
        final Calorie calorie = (Calorie) intent.getSerializableExtra("Information");
        ImageButton addFood = findViewById(R.id.addFood);
        //TODO: Add functionality so the person can add name, grams and calories
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CalorieCounter.this,"Calorie: " + calorie.getCals() + " Day: " + calorie.getDay(),Toast.LENGTH_LONG).show();
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
                Toast.makeText(CalorieCounter.this,"Food: " + food + " Kcal: " + kcal,Toast.LENGTH_LONG).show();
            }
        }
    }
}
