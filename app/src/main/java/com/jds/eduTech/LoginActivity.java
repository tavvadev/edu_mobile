package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonObject;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.Global;
import com.jds.eduTech.services.MyRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button singIn;
    TextInputEditText userName;
    TextInputEditText password;
    MyRetrofit retrofit;
    EduTechServices eduTechServices;
    Global global;
    ProgressBar pBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        singIn = findViewById(R.id.singIn);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        pBar = findViewById(R.id.pBar);

        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();
        global = Global.getInstance(LoginActivity.this);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String sessionId = sharedPreferences.getString("sessionId", null);

        if (sessionId != null) {
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }

        singIn.setOnClickListener(v->{
            if(userName.getText().toString().equals("")){
                Toast.makeText(this, "Please enter User Name", Toast.LENGTH_SHORT).show();
            }else if(password.getText().toString().equals("")){
                Toast.makeText(this, "Please enter Password", Toast.LENGTH_SHORT).show();
            }else {
                login();
            }
        });
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void login(){

        pBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("login_id",userName.getText().toString());
        jsonObject.addProperty("password",password.getText().toString());

        eduTechServices.getLogin(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.isSuccessful()){
                            System.out.println("entered is success");
                            if(response.body()!=null){
                                System.out.println("entered response.body");
                                if(response.body().getAsJsonObject().has("user")){
                                    System.out.println("entered response.user");
                                    if(response.body().getAsJsonObject().get("user")!=null){
                                        global.setLogin_id(response.body().getAsJsonObject().get("user").getAsJsonObject().get("login_id").getAsString());
                                        global.setFullName(response.body().getAsJsonObject().get("user").getAsJsonObject().get("name").getAsString());
                                        global.setEmail(response.body().getAsJsonObject().get("user").getAsJsonObject().get("email").getAsString());
                                        global.setRole_id(response.body().getAsJsonObject().get("user").getAsJsonObject().get("role_id").getAsString());
                                        global.setUser_id(response.body().getAsJsonObject().get("user").getAsJsonObject().get("id").getAsString());
                                        global.setDistrict_id(response.body().getAsJsonObject().get("user").getAsJsonObject().get("district_id").getAsString());
                                        global.setRoleName(response.body().getAsJsonObject().get("user").getAsJsonObject().get("roleName").getAsString());
                                        global.setSchoolId(response.body().getAsJsonObject().get("user").getAsJsonObject().get("schools").getAsJsonArray().get(0).getAsString());
                                        global.setSessionId(response.body().getAsJsonObject().get("sessionId").getAsString());

                                        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("sessionId", response.body().getAsJsonObject().get("sessionId").getAsString());
                                        editor.apply();

                                        Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                                        startActivity(intent);
                                    }
                                }else {
                                    Toast.makeText(LoginActivity.this, "Sorry something went wrong..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Sorry something went wrong..", Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        System.out.println("Exception in login:::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("enetered failuree::::");
                pBar.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Please enter valid details", Toast.LENGTH_SHORT).show();
            }


        });

    }


}