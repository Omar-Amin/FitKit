package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertData extends AppCompatActivity {
    private EditText food;
    private EditText kcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);

        Button addFood = findViewById(R.id.addFoodButton);
        Button cancel = findViewById(R.id.cancelFood);
        food = findViewById(R.id.addFood);
        kcal = findViewById(R.id.addCalorie);

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foodS = food.getText().toString();
                String kcalS = kcal.getText().toString();
                if(foodS.equals("") || kcalS.equals("")){
                    Toast.makeText(InsertData.this,"Missing arguments",Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("foodAdded",foodS);
                    intent.putExtra("kcalAdded",kcalS);
                    setResult(RESULT_OK,intent);
                    finish();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
