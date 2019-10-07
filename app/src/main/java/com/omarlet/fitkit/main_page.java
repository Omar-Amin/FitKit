package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
        calsLayout.add(new Calorie("500","Morning"));
        calsLayout.add(new Calorie("500","Noon"));
        calsLayout.add(new Calorie("500","Afternoon"));

        listView = findViewById(R.id.mpAdapter);

        mAdapter = new MPAdapter(calsLayout);
        listView.setAdapter(mAdapter);
        //TODO: New activity when pressing an item, can add calories etc.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Calorie calorie = calsLayout.get(i);
                Intent intent = new Intent(main_page.this,CalorieCounter.class);
                intent.putExtra("Information",calorie);
                startActivity(intent);
                //Toast.makeText(main_page.this,"Calorie: " + calorie.getCals() + " Day: " + calorie.getDay(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
