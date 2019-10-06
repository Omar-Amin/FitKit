package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.omarlet.fitkit.Model.Calorie;

public class CalorieCounter extends AppCompatActivity {

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
                Toast.makeText(CalorieCounter.this,"Calorie: " + calorie.getCals() + " Day: " + calorie.getDay(),Toast.LENGTH_LONG).show();

            }
        });
    }
}
