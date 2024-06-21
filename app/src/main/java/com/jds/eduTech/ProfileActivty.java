package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jds.eduTech.services.Global;

public class ProfileActivty extends AppCompatActivity {

    ImageView menu_drawer;
    TextView role;
    TextView name;
    TextView email;
    TextView id;
    Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_activty);
        menu_drawer = findViewById(R.id.menu_drawer);
        role = findViewById(R.id.role);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        email = findViewById(R.id.email);
        global = Global.getInstance(ProfileActivty.this);
        menu_drawer.setOnClickListener(v->finish());

        role.setText("Role: "+global.getRoleName());
        name.setText("Name: "+global.getFullName());
        email.setText("Email: "+global.getEmail());
        id.setText("ID: "+global.getLogin_id());
    }
}