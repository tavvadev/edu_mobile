package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jds.eduTech.adapters.ProductsAdapter;
import com.jds.eduTech.beans.TextValueUnits;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.Global;
import com.jds.eduTech.services.MyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricItemsForm extends AppCompatActivity {

    ImageView menu_drawer;
    Button next;
    TextView category;

    ProductsAdapter productsAdapter;
    MyRetrofit retrofit;
    EduTechServices eduTechServices;
    List<TextValueUnits> productsList = new ArrayList<>();
    RecyclerView productsRecyclerView;
    ProgressBar pBar;
    JsonArray dataArray = new JsonArray();
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_items_form);
        menu_drawer = findViewById(R.id.menu_drawer);
        next = findViewById(R.id.next);
        category = findViewById(R.id.category);
        pBar = findViewById(R.id.pBar);
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();
        global = Global.getInstance(ElectricItemsForm.this);

        category.setText(getIntent().getExtras().getString("categoryName"));

        menu_drawer.setOnClickListener(v->{
            finish();
        });

        next.setOnClickListener(v->{

            dataArray = new JsonArray();

            for (TextValueUnits product : productsList) {
                String productId = product.getValue();
                String units = productsAdapter.getUnitsForProduct(productId);
                if(units!=null){
                    JsonObject productObject = new JsonObject();
                    productObject.addProperty("product_id", productId);
                    productObject.addProperty("quantity", units);
                    dataArray.add(productObject);
                }

                // Create a JSON object for each product

            }
            if(dataArray.size() == 0){
                Toast.makeText(this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
            }
            System.out.println("dataaa:::"+dataArray.toString());
            createOrder();
        });

        getProducts();
    }

    private void createOrder() {
        pBar.setVisibility(View.VISIBLE);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("school_id",global.getSchoolId());
        jsonObject.addProperty("requester_id",global.getUser_id());
        jsonObject.addProperty("category",getIntent().getExtras().getString("categoryCode"));
        jsonObject.addProperty("login_id",global.getLogin_id());
        jsonObject.add("products", dataArray);
        eduTechServices.createOrder(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("message").getAsString().equalsIgnoreCase("Order created successfully.")){
                               // Toast.makeText(ElectricItemsForm.this, response.body().getAsJsonObject().get("message").getAsString(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ElectricItemsForm.this,OrderSuccessActivity.class);
                                intent.putExtra("orderId",response.body().getAsJsonObject().get("order_id").getAsString());
                                startActivity(intent);
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in create Order.."+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void getProducts(){

        pBar.setVisibility(View.VISIBLE);
        eduTechServices.getProducts(getIntent().getExtras().getString("categoryCode")).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                for(int i=0;i<response.body().getAsJsonArray().size();i++){
                                    System.out.println("name:::"+response.body().getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString());
                                    System.out.println("id:::"+response.body().getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString());
                                    productsList.add(new TextValueUnits(response.body().getAsJsonArray().get(i).getAsJsonObject().get("name").getAsString(),response.body().getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString(),response.body().getAsJsonArray().get(i).getAsJsonObject().get("units").getAsString()));

                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ElectricItemsForm.this,LinearLayoutManager.VERTICAL,false);
                                    productsRecyclerView.setLayoutManager(linearLayoutManager);
                                    productsAdapter = new ProductsAdapter(ElectricItemsForm.this,productsList);
                                    productsRecyclerView.setAdapter(productsAdapter);
                                    productsAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in getProducts::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(ElectricItemsForm.this, "Sorry something went wrong..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}