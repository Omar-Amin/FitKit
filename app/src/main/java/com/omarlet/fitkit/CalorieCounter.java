package com.omarlet.fitkit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.omarlet.fitkit.Adapter.FoodItemAdapter;
import com.omarlet.fitkit.Model.Calorie;
import com.omarlet.fitkit.Model.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CalorieCounter extends AppCompatActivity {
    private int CALORIE_COUNTED = 109;
    private ArrayList<Food> foods;
    private ListView listView;
    private FoodItemAdapter mAdapter;
    private String SHAREDKEY = "FoodItems";
    private int currentCalories;
    private Food operations = new Food("","",0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorie_counter);
        Intent intent = getIntent();
        Calorie calorie = (Calorie) intent.getSerializableExtra("Information");
        //Each key is which day is chosen
        SHAREDKEY = calorie.getDay();
        currentCalories = Integer.parseInt(calorie.getCals());
        listView = findViewById(R.id.foodList);
        //TODO: Extract saved data from phone then add to list when opening
        foods = operations.getArrayList(SHAREDKEY,this);
        if(foods == null){ // If nothing is returned from getArrayList it returns null, thus this is necessary
            foods = new ArrayList<>();
        }

        mAdapter = new FoodItemAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);

        ImageButton addFood = findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalorieCounter.this,InsertData.class);
                startActivityForResult(intent,CALORIE_COUNTED);
            }
        });

        /*TODO: Add functionality so the person can exactly how much grams
           the user has eaten. New activity is opened, and the amount of
           calories is calculated based on how much kcal pr. 100 grams.
           The user should also be able to remove the food, if the user
           decides not to eat it anyway.
         * */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(CalorieCounter.this,"AMOUNT : " + foods.get(i).getAmount(),Toast.LENGTH_LONG).show();
            }
        });

        ImageButton addBarcode = findViewById(R.id.checkBarcode);

        addBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(CalorieCounter.this);
                integrator.initiateScan();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CALORIE_COUNTED) {
            if (resultCode == RESULT_OK) {
                String food = data.getStringExtra("foodAdded");
                String kcal = data.getStringExtra("kcalAdded");
                foods.add(new Food(kcal,food,0));
                currentCalories = mAdapter.getCurrentKcal();
                foods = mAdapter.getFoods();
                mAdapter = new FoodItemAdapter(foods,currentCalories);
                listView.setAdapter(mAdapter);
                operations.saveArrayList(foods,SHAREDKEY,this);
            }
        }
        int BARCODEREQUEST = 49374;
        if(requestCode == BARCODEREQUEST){
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if(result != null) {
                if(result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Scanned", Toast.LENGTH_LONG).show();
                    String barcode = result.getContents();
                    new AddBarcodeItem().execute("https://world.openfoodfacts.org/api/v0/product/" + barcode + ".json");
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class AddBarcodeItem extends AsyncTask<String, Integer, Food> {
        // Extract data from JSON in the background
        protected Food doInBackground(String... strings) {
            String url = strings[0];
            InputStream is;
            try {
                is = new URL(url).openStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                String jsonText = readAll(rd);
                JSONObject jsonObject = new JSONObject(jsonText);
                // Check if the product is found, if it is extract nutriments and return Food
                if(jsonObject.get("status_verbose").equals("product found")){
                    JSONObject product = (JSONObject) jsonObject.get("product");
                    JSONObject nutriments = (JSONObject) product.get("nutriments");
                    double kcal = 0.239005736*Double.parseDouble(String.valueOf(nutriments.get("energy_100g")));
                    return new Food(String.valueOf((int) Math.round(kcal)),String.valueOf(product.get("product_name")),0);
                }
            } catch (NetworkOnMainThreadException | IOException | JSONException e){
                System.out.println("problem");
            }

            return null;
        }

        /**
         * @param rd what is to be read
         * Reads information from the URL
         * */
        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            return sb.toString();
        }

        // On finish call storeData
        protected void onPostExecute(Food result) {
            if(result!= null)
                storeData(result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(!foods.isEmpty()){
            storeCurrentData();
            operations.saveArrayList(foods,SHAREDKEY,this);
            operations.saveArrayList(getCurrentFood(),SHAREDKEY+"current",this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!foods.isEmpty()){
            storeCurrentData();
            operations.saveArrayList(foods,SHAREDKEY,this);
            operations.saveArrayList(getCurrentFood(),SHAREDKEY+"current",this);
        }
    }

    private ArrayList<Food> getCurrentFood(){
        ArrayList<Food> tmp = operations.getArrayList(SHAREDKEY+"current",this);
        ArrayList<Food> aeFood = new ArrayList<>();
        ArrayList<String> isIn = new ArrayList<>();
        if(tmp.isEmpty()){
            for (Food food:foods) {
                if(food.getAmount() > 0){
                    aeFood.add(food);
                }
            }
        }else {
            for (int i = 0; i < foods.size(); i++) {
                for (int j = 0; j < tmp.size(); j++) {
                    if(foods.get(i).getName().equals(tmp.get(j).getName())){
                        if(foods.get(i).getAmount() > 0){
                            tmp.get(j).setAmount(foods.get(i).getAmount());
                            aeFood.add(tmp.get(j));
                            isIn.add(tmp.get(j).getName());
                        }
                        if(!isIn.contains(foods.get(i).getName()) && foods.get(i).getAmount() > 0){
                            aeFood.add(foods.get(i));
                        }
                    }
                }
            }
        }
        return aeFood;
    }

    /**
     * storeData updates current ListView and saves the array for later use (closing and opening etc.)
     * */
    private void storeData(Food food){
        foods.add(food);
        currentCalories = mAdapter.getCurrentKcal();
        mAdapter = new FoodItemAdapter(foods,currentCalories);
        listView.setAdapter(mAdapter);
        operations.saveArrayList(foods,SHAREDKEY,this);
    }

    /**
     * The function stores the current calories counted from the meal.
     * */
    private void storeCurrentData(){
        SharedPreferences prefs = this.getSharedPreferences(SHAREDKEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String CALORIECOUNTED = "CalorieCounted";
        editor.putInt(CALORIECOUNTED,mAdapter.getCurrentKcal());
        editor.apply();
    }

}
