package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class OrderSuccessActivity extends AppCompatActivity {

    TextView orderId;
    ImageView menu_drawer;
    Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        menu_drawer = findViewById(R.id.menu_drawer);
        orderId = findViewById(R.id.orderId);
        next = findViewById(R.id.next);

        menu_drawer.setOnClickListener(v->{
            finish();
        });

        orderId.setText("Order Id: "+getIntent().getExtras().getString("orderId"));

        next.setOnClickListener(v->{
            Intent intent = new Intent(OrderSuccessActivity.this,DashboardActivity.class);
            startActivity(intent);
        });
    }
}