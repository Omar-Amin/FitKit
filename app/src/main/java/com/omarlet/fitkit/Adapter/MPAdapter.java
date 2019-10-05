package com.omarlet.fitkit.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omarlet.fitkit.Model.Calorie;

import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class MPAdapter extends RecyclerView.Adapter<MPAdapter.MPViewHolder>{
    private ArrayList<Calorie> calories;

    static class MPViewHolder extends RecyclerView.ViewHolder{
        TextView TVDay;
        TextView TVkcal;
        MPViewHolder(View v){
            super(v);
            TVkcal = v.findViewById(R.id.kcal);
            TVDay = v.findViewById(R.id.day);
        }
    }

    public MPAdapter(ArrayList<Calorie> calories){
        this.calories = calories;
    }


    @NonNull
    @Override
    public MPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.calorie_main, parent, false);
        return new MPViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MPViewHolder holder, int position) {
        Calorie cals = calories.get(position);

        String day = cals.getDay();
        String kcal = cals.getCals();

        holder.TVDay.setText(day);
        holder.TVkcal.setText(kcal + " of kcal");

    }


    @Override
    public int getItemCount() {

        return calories.size();


    }


}
