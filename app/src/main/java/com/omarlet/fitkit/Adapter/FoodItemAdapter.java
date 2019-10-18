package com.omarlet.fitkit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.omarlet.fitkit.Model.Food;
import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class FoodItemAdapter extends BaseAdapter {
    private ArrayList<Food> foods;
    private int currentKcal;

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
    public View getView(int i, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.food_items,viewGroup,false);
        TextView TVName, TVkcal;
        TVName = v.findViewById(R.id.foodName);
        TVkcal = v.findViewById(R.id.foodCalories);
        TVName.setText(foods.get(i).getName());
        TVkcal.setText(foods.get(i).getCals() + " kcal");
        final int kcal = Integer.parseInt(foods.get(i).getCals());
        final int pos = i;
        final ImageView img = v.findViewById(R.id.buttonImage);
        final ImageView checkImg = v.findViewById(R.id.buttonImage2);
        //Quick add button so the user doesn't have to click on the item and then add it
        if(foods.get(pos).getAmount() > 0){
            img.setVisibility(View.GONE);
            checkImg.setVisibility(View.VISIBLE);
        }
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img.setVisibility(View.GONE);
                checkImg.setVisibility(View.VISIBLE);
                foods.get(pos).setAmount(foods.get(pos).getAmount()+1);
                currentKcal += kcal;
                //TODO: Maybe add to a sharedpreference? which is deleted each day? Which is later retrieved from the activity CalorieCounter
            }
        });
        //Uncheck button
        checkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkImg.setVisibility(View.GONE);
                img.setVisibility(View.VISIBLE);
                foods.get(pos).setAmount(foods.get(pos).getAmount()-1);
                currentKcal -= kcal;
            }
        });
        return v;
    }

    public FoodItemAdapter(ArrayList<Food> foods,int currentKcal){
        this.foods = foods;
        this.currentKcal = currentKcal;
    }

    public int getCurrentKcal() {
        return currentKcal;
    }

    public ArrayList<Food> getFoods(){
        return foods;
    }
}
