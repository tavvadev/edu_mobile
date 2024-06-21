package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.jds.eduTech.adapters.CategoriesAdapter;
import com.jds.eduTech.beans.TextValue;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.Global;
import com.jds.eduTech.services.MyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    ImageView menuDrawer;
    DrawerLayout drawerLayout;

    TextView orders;
    TextView profile;
    TextView categories;
    TextView logout;
    CategoriesAdapter categoriesAdapter;
    MyRetrofit retrofit;
    EduTechServices eduTechServices;
    Global global;
    List<TextValue> categoriesList = new ArrayList<>();
    RecyclerView categoriesRecyclerView;
    ProgressBar pBar;
    ImageView search;
    EditText searchBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidemenu_dashboard);

        menuDrawer = findViewById(R.id.menu_drawer);
        drawerLayout = findViewById(R.id.drawer_layout);
        orders = findViewById(R.id.orders);
        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.profile);
        categories = findViewById(R.id.categories);
        pBar = findViewById(R.id.pBar);
        search = findViewById(R.id.search);
        searchBar = findViewById(R.id.searchBar);
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();
        global = Global.getInstance(DashboardActivity.this);

        search.setOnClickListener(v->{
            searchBar.setVisibility(View.VISIBLE);
            search.setVisibility(View.GONE);
        });

        getCategories();


        menuDrawer.setOnClickListener(v->{
            drawerLayout.openDrawer(GravityCompat.START);
        });

        categories.setOnClickListener(v->{
            drawerLayout.close();
        });

        orders.setOnClickListener(v->{
            Intent intent = new Intent(DashboardActivity.this,OrdersActivity.class);
            startActivity(intent);
        });

        profile.setOnClickListener(v->{
            Intent intent = new Intent(DashboardActivity.this,NewProfileActivity.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v->{
            SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove("sessionId");
            editor.apply();

            Intent intent = new Intent(DashboardActivity.this,SplashScreenActivity.class);
            startActivity(intent);
        });
    }

    private void getCategories() {

        pBar.setVisibility(View.VISIBLE);
        eduTechServices.getCategories().enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.isSuccessful()){
                            if(response.body()!=null){
                                for(int i=0;i<response.body().getAsJsonArray().size();i++){
                                    categoriesList.add(new TextValue(response.body().getAsJsonArray().get(i).getAsJsonObject().get("cat_name").getAsString(),response.body().getAsJsonArray().get(i).getAsJsonObject().get("id").getAsString()));

                                }
                                System.out.println("categoriess"+categoriesList.size());
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DashboardActivity.this,LinearLayoutManager.VERTICAL,false);
                                categoriesRecyclerView.setLayoutManager(linearLayoutManager);
                                categoriesAdapter = new CategoriesAdapter(DashboardActivity.this,categoriesList);
                                categoriesRecyclerView.setAdapter(categoriesAdapter);

                                searchBar.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {
                                        filter(s.toString());
                                    }
                                });

                                categoriesAdapter.setOnItemClickListener(((position, v, categoryCode, categoryName) -> {
                                    Intent intent = new Intent(DashboardActivity.this,ElectricItemsForm.class);
                                    intent.putExtra("categoryCode",categoryCode);
                                    intent.putExtra("categoryName",categoryName);
                                    startActivity(intent);
                                }));
                                categoriesAdapter.notifyDataSetChanged();

                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exceprion in get categoriess:::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                pBar.setVisibility(View.GONE);
                Toast.makeText(DashboardActivity.this, "Sorry SSomething went wrong..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filter(String text){
        ArrayList<TextValue> filteredList = new ArrayList<>();
        for(TextValue item: categoriesList){
            if(item.getText().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(item);
            }
        }
        categoriesAdapter.filterList(filteredList);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}