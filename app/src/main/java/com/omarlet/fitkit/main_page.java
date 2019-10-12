package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.omarlet.fitkit.Adapter.MPAdapter;
import com.omarlet.fitkit.Model.Calorie;

import java.util.ArrayList;
import java.util.Calendar;

public class main_page extends AppCompatActivity {
    private ListView listView;
    private MPAdapter mAdapter;
    private ArrayList<Calorie> calsLayout = new ArrayList<>();
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        /*
        * TODO:
        *  Improvement so that the main page can have more
        *  than 3 meals, so default is 3 but can add up to 6
        */

        listView = findViewById(R.id.mpAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Calorie calorie = calsLayout.get(i);
                Intent intent = new Intent(main_page.this,CalorieCounter.class);
                intent.putExtra("Information",calorie);
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
            }

        }
    }

    /**
     * Getting the calories stored from CalorieCounter class
     * */
    private void findCalories(){
        SharedPreferences prefs;
        for (int i = 0; i < calsLayout.size(); i++) {
            Calorie cals = calsLayout.get(i);
            prefs = this.getSharedPreferences(cals.getDay(),Context.MODE_PRIVATE);
            String CALORIECOUNTED = "CalorieCounted";
            int currentKcal = prefs.getInt(CALORIECOUNTED,0);
            calsLayout.set(i,new Calorie(currentKcal+"",cals.getDay()));
        }
    }
}
