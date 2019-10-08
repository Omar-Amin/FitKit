package com.omarlet.fitkit.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(int i, View view, final ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View v = inflater.inflate(R.layout.food_items,viewGroup,false);
        TextView TVName, TVkcal;
        TVName = v.findViewById(R.id.foodName);
        TVkcal = v.findViewById(R.id.foodCalories);
        TVName.setText(foods.get(i).getName());
        TVkcal.setText(foods.get(i).getCals() + " kcal");
        final String test = foods.get(i).getName();
        final ImageView img = v.findViewById(R.id.buttonImage);
        //Quick add button so the user doesn't have to click on the item and then add it
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(viewGroup.getContext(),test,Toast.LENGTH_LONG).show();
                img.setImageResource(R.drawable.noun_check_1604777);
                //TODO: Maybe add to a preference? which is deleted each day? Which is later retrieved from the activity CalorieCounter
            }
        });
        return v;
    }

    public FoodItemAdapter(ArrayList<Food> foods){
        this.foods = foods;
    }

}
