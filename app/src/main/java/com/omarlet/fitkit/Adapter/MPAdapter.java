package com.omarlet.fitkit.Adapter;

<<<<<<< HEAD
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
=======
import android.view.LayoutInflater;
>>>>>>> master
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< HEAD
import com.omarlet.fitkit.Model.Calorie;
=======
>>>>>>> master
import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class MPAdapter extends RecyclerView.Adapter<MPAdapter.MPViewHolder>{
<<<<<<< HEAD
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

=======
    private ArrayList<String[]> calories;

    public static class MPViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public MPViewHolder(TextView v){
            super(v);
            textView = v;
        }
    }

    public MPAdapter(ArrayList<String[]> calories){
        calories = calories;
>>>>>>> master
    }

    @NonNull
    @Override
    public MPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
<<<<<<< HEAD
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
=======
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MPViewHolder holder, int position) {

>>>>>>> master
    }

    @Override
    public int getItemCount() {
<<<<<<< HEAD
        return calories.size();
=======
        return 0;
>>>>>>> master
    }


}
