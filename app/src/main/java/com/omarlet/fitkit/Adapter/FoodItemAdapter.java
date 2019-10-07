package com.omarlet.fitkit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;
import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class FoodItemAdapter extends BaseAdapter {
    private ArrayList<Food> foods;

    @Override
    public int getCount() {
        return foods.size();
    }

    @Override
    public Object getItem(int i) {
        return foods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.food_items,viewGroup,false);
        TextView TVName, TVkcal;
        TVName = v.findViewById(R.id.foodName);
        TVkcal = v.findViewById(R.id.foodCalories);
        TVName.setText(foods.get(i).getName());
        TVkcal.setText(foods.get(i).getCals() + " kcal");
        return v;
    }

    public FoodItemAdapter(ArrayList<Food> foods){
        this.foods = foods;
    }

}
