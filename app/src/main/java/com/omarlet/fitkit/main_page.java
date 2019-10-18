package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.omarlet.fitkit.Adapter.MPAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import java.util.ArrayList;
import java.util.Calendar;

public class main_page extends AppCompatActivity {
    private ListView listView;
    private MPAdapter mAdapter;
    private ArrayList<Calorie> calsLayout = new ArrayList<>();
    private ProgressBar progressBar;
    private ProgressBar redProgress;
    private TextView TVTotal;
    private Food operations = new Food("","",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        /*
        * TODO:
        *  Improvement so that the main page can have more
        *  than 3 meals, so default is 3 but can add up to 6
        */

        progressBar = findViewById(R.id.progress_Calories);
        redProgress = findViewById(R.id.red_progress);
        TVTotal = findViewById(R.id.totalCalories);

        listView = findViewById(R.id.mpAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Calorie calorie = calsLayout.get(i);
                Intent intent = new Intent(main_page.this,already_eaten.class);
                intent.putExtra("InformationEaten",calorie);
                startActivity(intent);
            }
        });

    }


    //onStart/onResume to make sure the list is always updated with the current kcal
    @Override
    protected void onStart() {
        super.onStart();
        if(calsLayout.isEmpty()){ //TODO: Maybe change so the user can decide what to write
            calsLayout.add(new Calorie("0","Morning"));
            calsLayout.add(new Calorie("0","Noon"));
            calsLayout.add(new Calorie("0","Afternoon"));
        }
        setUpResetter();
        findCalories();
        mAdapter = new MPAdapter(calsLayout);
        listView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findCalories();
        mAdapter = new MPAdapter(calsLayout);
        listView.setAdapter(mAdapter);
    }

    /**
     * Checks date and resets if it is a new day
     * */
    private void setUpResetter(){
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences prefs = getSharedPreferences("Reset",Context.MODE_PRIVATE);
        int lastDay = prefs.getInt("resetter",0);

        if(lastDay != currentDay){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("resetter",currentDay);
            editor.apply();

            //Reset calories to 0 for all day
            for (int i = 0; i < calsLayout.size(); i++) {
                String day = calsLayout.get(i).getDay();
                prefs = this.getSharedPreferences(day, Context.MODE_PRIVATE);
                editor = prefs.edit();
                editor.clear().apply();
                operations.saveArrayList(new ArrayList<Food>(),day+"current",this);
                ArrayList<Food> foods = operations.getArrayList(day,this);
                for (int j = 0; j < foods.size(); j++) {
                    foods.get(j).setAmount(0);
                }
                operations.saveArrayList(foods,day,this);
            }


        }
    }

    /**
     * Getting the calories stored from CalorieCounter class
     * and changing the progress based on calories eaten
     * */
    private void findCalories(){
        SharedPreferences prefs;
        int totalCals = 0;
        for (int i = 0; i < calsLayout.size(); i++) {
            Calorie cals = calsLayout.get(i);
            totalCals += Integer.parseInt(cals.getCals());
            prefs = this.getSharedPreferences(cals.getDay(),Context.MODE_PRIVATE);
            String CALORIECOUNTED = "CalorieCounted";
            int currentKcal = prefs.getInt(CALORIECOUNTED,0);
            calsLayout.set(i,new Calorie(currentKcal+"",cals.getDay()));
        }
        int progress = (totalCals *100)/1500;
        TVTotal.setText(totalCals + "/1500");
        progressBar.setProgress(progress);
        while (progress > 100){
            progress -= 100;
            redProgress.setProgress(progress);
        }

    }
}
