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

public class AlreadyEatenAdapter extends BaseAdapter {
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
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.already_eaten_items,viewGroup,false);
        final TextView TVName, TVkcal;
        TVName = v.findViewById(R.id.foodNameEaten);
        TVkcal = v.findViewById(R.id.foodCaloriesEaten);
        TVName.setText(foods.get(i).getName());
        TVkcal.setText(foods.get(i).getCals() + " kcal");
        final int kcal = Integer.parseInt(foods.get(i).getCals());
        final ImageView img = v.findViewById(R.id.addMoreFood);
        final ImageView checkImg = v.findViewById(R.id.addLess);
        final int pos = i;
        //Quick add button so the user doesn't have to click on the item and then add it
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Increasing the amount of the selected food
                currentKcal += kcal;
                foods.get(pos).setAmount(foods.get(pos).getAmount()+1);
                TVkcal.setText(kcal*foods.get(pos).getAmount()+" kcal");

            }
        });
        //Uncheck button
        checkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Decreasing the amount of selected food
                int amount = foods.get(pos).getAmount();
                //Makes sure that we don't get negative numbers
                if(foods.get(pos).getAmount() > 0){
                    currentKcal -= kcal;
                    foods.get(pos).setAmount(foods.get(pos).getAmount()-1);
                    TVkcal.setText(kcal*(amount-1)+" kcal");
                }
            }
        });
        return v;
    }

    public AlreadyEatenAdapter(ArrayList<Food> foods,int currentKcal){
        this.foods = foods;
        this.currentKcal = currentKcal;
    }

    public int getCurrentKcal() {
        return currentKcal;
    }

}