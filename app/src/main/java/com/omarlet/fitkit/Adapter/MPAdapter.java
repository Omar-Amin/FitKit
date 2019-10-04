package com.omarlet.fitkit.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omarlet.fitkit.R;

import java.util.ArrayList;

public class MPAdapter extends RecyclerView.Adapter<MPAdapter.MPViewHolder>{
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
    }

    @NonNull
    @Override
    public MPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MPViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
