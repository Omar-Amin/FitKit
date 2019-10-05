package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.omarlet.fitkit.Adapter.MPAdapter;
<<<<<<< HEAD
import com.omarlet.fitkit.Model.Calorie;

import java.util.ArrayList;
=======
>>>>>>> master

public class main_page extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
<<<<<<< HEAD
    private ArrayList<Calorie> calsLayout = new ArrayList<>();
=======
>>>>>>> master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
<<<<<<< HEAD
        //Test data, need to improve later on
        calsLayout.add(new Calorie("500","Morning"));
        calsLayout.add(new Calorie("500","Noon"));
        calsLayout.add(new Calorie("500","Afternoon"));
=======
>>>>>>> master

        recyclerView = findViewById(R.id.mpAdapter);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

<<<<<<< HEAD
        mAdapter = new MPAdapter(calsLayout);
        recyclerView.setAdapter(mAdapter);
=======

>>>>>>> master

    }
}
