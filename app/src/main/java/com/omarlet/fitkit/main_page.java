package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.omarlet.fitkit.Adapter.MPAdapter;
import com.omarlet.fitkit.Model.Calorie;

import java.util.ArrayList;

public class main_page extends AppCompatActivity {
    private ListView listView;
    private MPAdapter mAdapter;
    private ArrayList<Calorie> calsLayout = new ArrayList<>();

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
            calsLayout.add(new Calorie("500","Morning"));
            calsLayout.add(new Calorie("500","Noon"));
            calsLayout.add(new Calorie("500","Afternoon"));
        }
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
