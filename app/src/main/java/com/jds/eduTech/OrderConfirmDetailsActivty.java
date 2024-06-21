package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrderConfirmDetailsActivty extends AppCompatActivity {

    TextView name1,name2,quantity1,quantity2;
    ImageView menu_drawer;
    Button next,reject;
    LinearLayout firstLayout,secondLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm_details_activty);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        quantity1 = findViewById(R.id.quantity1);
        quantity2 = findViewById(R.id.quantity2);
        menu_drawer = findViewById(R.id.menu_drawer);
        next = findViewById(R.id.next);
        reject = findViewById(R.id.reject);
        firstLayout = findViewById(R.id.firstLayout);
        secondLayout = findViewById(R.id.secondLayout);
        menu_drawer.setOnClickListener(v->{
            finish();
        });

        next.setOnClickListener(v->{
            Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(OrderConfirmDetailsActivty.this,DashboardActivity.class);
            startActivity(intent);
        });

        reject.setOnClickListener(v->{
            Toast.makeText(this, "Order Rejected", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(OrderConfirmDetailsActivty.this,DashboardActivity.class);
            startActivity(intent);
        });


        if(!getIntent().getExtras().getString("name1").equals("") || getIntent().getExtras().getString("name1")!=null){
            name1.setText(getIntent().getExtras().getString("name1"));
        }

        if(!getIntent().getExtras().getString("name2").equals("") || getIntent().getExtras().getString("name2")!=null){
            name2.setText(getIntent().getExtras().getString("name2"));
        }

        if(!getIntent().getExtras().getString("quantity1").equals("") || getIntent().getExtras().getString("quantity1")!=null){
            quantity1.setText(getIntent().getExtras().getString("quantity1"));
        }

        if(!getIntent().getExtras().getString("quantity2").equals("") || getIntent().getExtras().getString("quantity2")!=null){
            quantity2.setText(getIntent().getExtras().getString("quantity2"));
        }

        if(getIntent().getExtras().getString("quantity2").equals("")){
            secondLayout.setVisibility(View.GONE);
        }

        if(getIntent().getExtras().getString("quantity1").equals("")){
            firstLayout.setVisibility(View.GONE);
        }
    }
}