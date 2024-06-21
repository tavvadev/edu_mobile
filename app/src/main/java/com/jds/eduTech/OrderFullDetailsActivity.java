package com.jds.eduTech;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jds.eduTech.adapters.OrderProductsAdapter;
import com.jds.eduTech.beans.Orders;
import com.jds.eduTech.beans.Products;
import com.jds.eduTech.services.EduTechServices;
import com.jds.eduTech.services.MyRetrofit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFullDetailsActivity extends AppCompatActivity {

    RecyclerView productDetailsRecyclerView;
    ImageView menu_drawer;
    TextView orderId;
    TextView orderNum;
    TextView headMasterName;
    TextView headMasterContact;
    TextView schoolName;
    TextView supplierName;
    TextView supplierNumber;
    TextView status;
    LinearLayout statusLayout;
    LinearLayout rejectApproveLayout;
    LinearLayout approvedLayout;
    ProgressBar pBar;
    MyRetrofit retrofit;
    EduTechServices eduTechServices;
    ScrollView scrollableContents;
    int orderIdNum = 0;
    Button acknowledge;
    Button reject;
    List<Products> productsList = new ArrayList<>();
    HashMap<String, Integer> ackQtyMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_full_details);
        productDetailsRecyclerView = findViewById(R.id.productDetailsRecyclerView);
        menu_drawer = findViewById(R.id.menu_drawer);
        orderId = findViewById(R.id.orderId);
        orderNum = findViewById(R.id.orderNum);
        headMasterName = findViewById(R.id.headMasterName);
        headMasterContact = findViewById(R.id.headMasterContact);
        schoolName = findViewById(R.id.schoolName);
        supplierName = findViewById(R.id.supplierName);
        supplierNumber = findViewById(R.id.supplierNumber);
        status = findViewById(R.id.status);
        statusLayout = findViewById(R.id.statusLayout);
        rejectApproveLayout = findViewById(R.id.rejectApproveLayout);
        approvedLayout = findViewById(R.id.approvedLayout);
        acknowledge = findViewById(R.id.acknowledge);
        reject = findViewById(R.id.reject);
        scrollableContents = findViewById(R.id.scrollableContents);
        pBar = findViewById(R.id.pBar);
        retrofit = MyRetrofit.getInstance();
        eduTechServices = retrofit.getEduTechServices();

        menu_drawer.setOnClickListener(v->finish());



        String ordersListJson = getIntent().getStringExtra("ordersList");
        List<Orders> ordersList = new Gson().fromJson(ordersListJson, new TypeToken<List<Orders>>(){}.getType());

        System.out.println("List:::::::"+ordersList);

        Orders order = ordersList.get(0); // 'position' is the position of the clicked item
        orderIdNum = order.getOrderId();

        getFullDetails();
       /* orderId.setText(String.valueOf(order.getOrderId()));
        orderNum.setText(order.getOrder_num());
        headMasterName.setText(order.getHm_name());
        headMasterContact.setText(order.getHm_contact_num());
        schoolName.setText(order.getSchool_name());
        supplierName.setText(order.getSupplierName());
        supplierNumber.setText(order.getSupplierNumber());

        if(order.getInvoice_status() == 0){
            status.setText("Pending");
            statusLayout.setVisibility(View.VISIBLE);
            rejectApproveLayout.setVisibility(View.GONE);
            approvedLayout.setVisibility(View.GONE);
        }else if(order.getInvoice_status() == 1){
            rejectApproveLayout.setVisibility(View.VISIBLE);
            statusLayout.setVisibility(View.GONE);
            approvedLayout.setVisibility(View.GONE);
        }else if(order.getInvoice_status() == 2){
            statusLayout.setVisibility(View.GONE);
            rejectApproveLayout.setVisibility(View.GONE);
            approvedLayout.setVisibility(View.VISIBLE);
        } else if (order.getInvoice_status() == 3) {
            status.setText("Rejected");
            statusLayout.setVisibility(View.VISIBLE);
            rejectApproveLayout.setVisibility(View.GONE);
            approvedLayout.setVisibility(View.GONE);
        }
        List<Products> productsList = order.getProducts();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderFullDetailsActivity.this,LinearLayoutManager.VERTICAL,false);
        productDetailsRecyclerView.setLayoutManager(linearLayoutManager);
        OrderProductsAdapter adapter = new OrderProductsAdapter(OrderFullDetailsActivity.this,productsList,order.getInvoice_status());
        productDetailsRecyclerView.setAdapter(adapter);*/
    }

    private void getFullDetails() {
        pBar.setVisibility(View.VISIBLE);
        scrollableContents.setVisibility(View.GONE);
        eduTechServices.getOrderFullDetails(String.valueOf(orderIdNum)).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        scrollableContents.setVisibility(View.VISIBLE);
                        System.out.println("Enteerd herree");
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject()!=null){
                                orderId.setText(String.valueOf(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("orderId").getAsInt()));
                                orderNum.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_num").getAsString());
                                headMasterName.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("hm_name").getAsString());
                                supplierName.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("supplierName").getAsString());
                                headMasterContact.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("hm_contact_num").getAsString());
                                supplierNumber.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("supplierNumber").getAsString());
                                schoolName.setText(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("school_name").getAsString());

                                if(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_status").getAsInt() == 0){
                                    status.setText("Pending");
                                    statusLayout.setVisibility(View.VISIBLE);
                                    rejectApproveLayout.setVisibility(View.GONE);
                                    approvedLayout.setVisibility(View.GONE);
                                }else if(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_status").getAsInt() == 1){
                                    rejectApproveLayout.setVisibility(View.VISIBLE);
                                    statusLayout.setVisibility(View.GONE);
                                    approvedLayout.setVisibility(View.GONE);
                                }else if(response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_status").getAsInt() == 2){
                                    statusLayout.setVisibility(View.GONE);
                                    rejectApproveLayout.setVisibility(View.GONE);
                                    approvedLayout.setVisibility(View.VISIBLE);
                                } else if (response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_status").getAsInt() == 3) {
                                    status.setText("Rejected");
                                    statusLayout.setVisibility(View.VISIBLE);
                                    rejectApproveLayout.setVisibility(View.GONE);
                                    approvedLayout.setVisibility(View.GONE);
                                }

                                productsList = new ArrayList<>();

                                JsonArray productsArray = response.body().getAsJsonObject()
                                        .get("orderDetails").getAsJsonObject()
                                        .get("products").getAsJsonArray();

                                for (int i = 0; i < productsArray.size(); i++) {
                                    JsonObject productJson = productsArray.get(i).getAsJsonObject();

                                    String product_name = "";
                                    JsonElement product_nameElement = productJson.get("product_name");
                                    if (!product_nameElement.isJsonNull()) {
                                        product_name = product_nameElement.getAsString();
                                    }
                                    String units = "";
                                    JsonElement unitsElement = productJson.get("units");
                                    if (!unitsElement.isJsonNull()) {
                                        units = unitsElement.getAsString();
                                    }
                                    int quantity = 0;
                                    JsonElement quantityElement = productJson.get("quantity");
                                    if (!quantityElement.isJsonNull()) {
                                        quantity = quantityElement.getAsInt();
                                    }

                                    int product_id = 0;
                                    JsonElement product_idElement = productJson.get("product_id");
                                    if (!product_idElement.isJsonNull()) {
                                        product_id = product_idElement.getAsInt();
                                    }
                                    String price = "";
                                    JsonElement priceElement = productJson.get("price");
                                    if (!priceElement.isJsonNull()) {
                                        price = priceElement.getAsString();
                                    }
                                    Products product = new Products(product_name, units, quantity,product_id,price);
                                    productsList.add(product);
                                }

                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(OrderFullDetailsActivity.this,LinearLayoutManager.VERTICAL,false);
                                productDetailsRecyclerView.setLayoutManager(linearLayoutManager);
                                OrderProductsAdapter adapter = new OrderProductsAdapter(OrderFullDetailsActivity.this,productsList,response.body().getAsJsonObject().get("orderDetails").getAsJsonObject().get("invoice_status").getAsInt());
                                productDetailsRecyclerView.setAdapter(adapter);

                                acknowledge.setOnClickListener(v->{
                                    ackQtyMap = new HashMap<>();
                                    boolean isAnyFieldEmpty = false;
                                    for (int i = 0; i < productDetailsRecyclerView.getChildCount(); i++) {
                                        View itemView = productDetailsRecyclerView.getChildAt(i);
                                        EditText receivedQuantity = itemView.findViewById(R.id.receivedQuantity);

                                        Products product = productsList.get(i);

                                        int prodId = product.getProduct_id();

                                        String quantityStr = receivedQuantity.getText().toString();

                                        if (receivedQuantity.getText().toString().isEmpty()) {
                                            isAnyFieldEmpty = true;
                                            break;
                                        }else {
                                            int receivedQty = Integer.parseInt(quantityStr);
                                            ackQtyMap.put(String.valueOf(prodId), receivedQty);
                                        }
                                    }
                                    if (isAnyFieldEmpty) {
                                        Toast.makeText(OrderFullDetailsActivity.this, "Please fill in all received quantity", Toast.LENGTH_SHORT).show();
                                    } else {
                                        updateOrder();
                                    }
                                });

                                reject.setOnClickListener(v->{
                                    ackQtyMap = new HashMap<>();
                                    boolean isAnyFieldEmpty = false;
                                    for (int i = 0; i < productDetailsRecyclerView.getChildCount(); i++) {
                                        View itemView = productDetailsRecyclerView.getChildAt(i);
                                        EditText receivedQuantity = itemView.findViewById(R.id.receivedQuantity);

                                        Products product = productsList.get(i);

                                        int prodId = product.getProduct_id();

                                        String quantityStr = receivedQuantity.getText().toString();

                                        if (receivedQuantity.getText().toString().isEmpty()) {
                                            isAnyFieldEmpty = true;
                                            break;
                                        }else {
                                            int receivedQty = Integer.parseInt(quantityStr);
                                            ackQtyMap.put(String.valueOf(prodId), receivedQty);
                                        }
                                    }
                                    if (isAnyFieldEmpty) {
                                        Toast.makeText(OrderFullDetailsActivity.this, "Please fill in all received quantity", Toast.LENGTH_SHORT).show();
                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderFullDetailsActivity.this);
                                        LayoutInflater inflater = getLayoutInflater();
                                        View dialogView = inflater.inflate(R.layout.reject_reason_dialog, null);
                                        builder.setView(dialogView);

                                        EditText reasonEditText = dialogView.findViewById(R.id.reasonEditText);

                                        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                String reason = reasonEditText.getText().toString().trim();
                                                if (!reason.isEmpty()) {
                                                    updateRejectOrder(reason);
                                                } else {
                                                    Toast.makeText(OrderFullDetailsActivity.this, "Please enter a reason", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                });
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in getFullDetails:::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    private void updateOrder() {
        pBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("order_id",orderIdNum);
        JsonObject ackQtyObject = new JsonObject();
        for (Map.Entry<String, Integer> entry : ackQtyMap.entrySet()) {
            ackQtyObject.addProperty(entry.getKey(), entry.getValue());
        }
        jsonObject.add("ack_qty", ackQtyObject);
        jsonObject.addProperty("order_status",2);
        eduTechServices.updateOrder(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("message").getAsString().equalsIgnoreCase("Order updated successfully.")){
                                Toast.makeText(OrderFullDetailsActivity.this, "Acknowledged Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrderFullDetailsActivity.this,OrdersActivity.class);
                                startActivity(intent);
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in update Order:::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void updateRejectOrder(String reason){
        pBar.setVisibility(View.VISIBLE);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("order_id",orderIdNum);
        JsonObject ackQtyObject = new JsonObject();
        for (Map.Entry<String, Integer> entry : ackQtyMap.entrySet()) {
            ackQtyObject.addProperty(entry.getKey(), entry.getValue());
        }
        jsonObject.add("ack_qty", ackQtyObject);
        jsonObject.addProperty("order_status",3);
        jsonObject.addProperty("reason",reason);
        eduTechServices.updateOrder(jsonObject).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                runOnUiThread(()->{
                    try{
                        pBar.setVisibility(View.GONE);
                        if(response.body()!=null){
                            if(response.body().getAsJsonObject().get("message").getAsString().equalsIgnoreCase("Order updated successfully.")){
                                Toast.makeText(OrderFullDetailsActivity.this, "Rejected Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(OrderFullDetailsActivity.this,OrdersActivity.class);
                                startActivity(intent);
                            }
                        }
                    }catch (Exception e){
                        System.out.println("Exception in reject orderrr:::"+e.getMessage());
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}