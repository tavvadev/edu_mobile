package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jds.eduTech.adapters.OrdersAdapter;
import com.jds.eduTech.beans.Orders;
import com.jds.eduTech.beans.Products;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.Global;
import com.jds.eduTech.services.MyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersActivity extends AppCompatActivity {

    ImageView menu_drawer;
    OrdersAdapter ordersAdapter;
    RecyclerView ordersRecyclerView;
    MyRetrofit retrofit;
    EduTechServices eduTechServices;
    ProgressBar pBar;
    List<Orders> ordersList = new ArrayList<>();
    Global global;
    int orderId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        menu_drawer = findViewById(R.id.menu_drawer);
        ordersRecyclerView = findViewById(R.id.ordersRecyclerView);
        pBar = findViewById(R.id.pBar);
        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();
        global = Global.getInstance(OrdersActivity.this);

        menu_drawer.setOnClickListener(v->{
            finish();
        });

        getOrders();

    }

    private void getOrders() {
        pBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user_id",global.getUser_id());
        eduTechServices.getOrders(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                  //  try{
                        pBar.setVisibility(View.GONE);
                        if(response.body()!=null){

                            JsonObject ordersObject = response.body().getAsJsonObject().get("orders").getAsJsonObject();
                            for (String key : ordersObject.keySet()) {
                                JsonObject orderObject = ordersObject.getAsJsonObject(key);

                                String dist_name = "";
                                JsonElement dist_nameElement = orderObject.get("dist_name");
                                if (!dist_nameElement.isJsonNull()) {
                                    dist_name = dist_nameElement.getAsString();
                                }

                                String village_name = "";
                                JsonElement village_nameElement = orderObject.get("village_name");
                                if (!village_nameElement.isJsonNull()) {
                                    village_name = village_nameElement.getAsString();
                                }

                                String code = "";
                                JsonElement codeElement = orderObject.get("code");
                                if (!codeElement.isJsonNull()) {
                                    code = codeElement.getAsString();
                                }

                                String school_name = "";
                                JsonElement school_nameElement = orderObject.get("school_name");
                                if (!school_nameElement.isJsonNull()) {
                                    school_name = school_nameElement.getAsString();
                                }

                                String hm_contact_num = "";
                                JsonElement hm_contact_numElement = orderObject.get("hm_contact_num");
                                if (!hm_contact_numElement.isJsonNull()) {
                                    hm_contact_num = hm_contact_numElement.getAsString();
                                }
                                String hm_name = "";
                                JsonElement hm_nameElement = orderObject.get("hm_name");
                                if (!hm_nameElement.isJsonNull()) {
                                    hm_name = hm_nameElement.getAsString();
                                }
                                String supplierName = "";
                                JsonElement supplierNameElement = orderObject.get("supplierName");
                                if (!supplierNameElement.isJsonNull()) {
                                    supplierName = supplierNameElement.getAsString();
                                }
                                String supplierNumber = "";
                                JsonElement supplierNumberElement = orderObject.get("supplierNumber");
                                if (!supplierNumberElement.isJsonNull()) {
                                    supplierNumber = supplierNumberElement.getAsString();
                                }
                                String UDISE_code = "";
                                JsonElement UDISE_codeElement = orderObject.get("UDISE_code");
                                if (!UDISE_codeElement.isJsonNull()) {
                                    UDISE_code = UDISE_codeElement.getAsString();
                                }
                                String order_num = "";
                                JsonElement order_numElement = orderObject.get("order_num");
                                if (!order_numElement.isJsonNull()) {
                                    order_num = order_numElement.getAsString();
                                }


                                JsonElement orderIdElement = orderObject.get("orderId");
                                if (!orderIdElement.isJsonNull()) {
                                    orderId = orderIdElement.getAsInt();
                                }
                                int status = 0;
                                JsonElement statusElement = orderObject.get("invoice_status");
                                if (!statusElement.isJsonNull()) {
                                    status = statusElement.getAsInt();
                                }
                                int apc_approved_status = 0;
                                JsonElement apc_approved_statusElement = orderObject.get("apc_approved_status");
                                if (!apc_approved_statusElement.isJsonNull()) {
                                    apc_approved_status = apc_approved_statusElement.getAsInt();
                                }

                                List<Products> productsList = new ArrayList<>();
                                JsonArray productsArray = orderObject.getAsJsonArray("products");
                                for (JsonElement productElement : productsArray) {
                                    JsonObject productObject = productElement.getAsJsonObject();

                                    String product_name = "";
                                    JsonElement product_nameElement = productObject.get("product_name");
                                    if (!product_nameElement.isJsonNull()) {
                                        product_name = product_nameElement.getAsString();
                                    }
                                    String units = "";
                                    JsonElement unitsElement = productObject.get("units");
                                    if (!unitsElement.isJsonNull()) {
                                        units = unitsElement.getAsString();
                                    }
                                    int quantity = 0;
                                    JsonElement quantityElement = productObject.get("quantity");
                                    if (!quantityElement.isJsonNull()) {
                                        quantity = quantityElement.getAsInt();
                                    }
                                    int product_id = 0;
                                    JsonElement product_idElement = productObject.get("product_id");
                                    if (!product_idElement.isJsonNull()) {
                                        product_id = product_idElement.getAsInt();
                                    }
                                    String price = "";
                                    JsonElement priceElement = productObject.get("price");
                                    if (!priceElement.isJsonNull()) {
                                        price = priceElement.getAsString();
                                    }


                                    Products product = new Products(product_name, units, quantity,product_id,price);
                                    productsList.add(product);
                                }

                                Orders order = new Orders(dist_name, village_name, code, school_name, hm_contact_num,UDISE_code,order_num,hm_name,supplierName,supplierNumber, orderId,status,apc_approved_status, productsList);
                                ordersList.add(order);
                            }

                            System.out.println("sizee:::"+ordersList.size());

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrdersActivity.this,LinearLayoutManager.VERTICAL,false);
                            ordersRecyclerView.setLayoutManager(linearLayoutManager);
                            OrdersAdapter adapter = new OrdersAdapter(OrdersActivity.this, ordersList);
                            ordersRecyclerView.setAdapter(adapter);

                            adapter.setOnFullDetailsClickListener(new OrdersAdapter.OnFullDetailsClickListener() {
                                @Override
                                public void onFullDetailsClick(int position) {


                                    Orders order = ordersList.get(position);

                                    List<Orders> clickedOrderList = new ArrayList<>();
                                    clickedOrderList.add(order);


                                    Intent intent = new Intent(OrdersActivity.this, OrderFullDetailsActivity.class);
                                    intent.putExtra("ordersList", new Gson().toJson(clickedOrderList));
                                   // intent.putExtra("orderId", orderId);
                                    startActivity(intent);
                                }
                            });
                        }
//                    }catch (Exception e){
//                        System.out.println("Exception in Orderss:::"+e.getMessage());
//                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}