package com.omarlet.fitkit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omarlet.fitkit.Model.Calorie;

import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class MPAdapter extends BaseAdapter {
    private ArrayList<Calorie> calories;

    @Override
    public int getCount() {
        return calories.size();
    }

    @Override
    public Object getItem(int i) {
        return calories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.calorie_main,viewGroup,false);
        TextView TVDay, TVkcal;
        TVDay = v.findViewById(R.id.day);
        TVkcal = v.findViewById(R.id.kcal);
        TVDay.setText(calories.get(i).getDay());
        TVkcal.setText(calories.get(i).getCals() + " out of 500 kcal");
        return v;
    }

    public MPAdapter(ArrayList<Calorie> calories){
        this.calories = calories;
    }






}
