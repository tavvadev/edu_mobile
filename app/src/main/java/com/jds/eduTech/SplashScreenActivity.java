package com.jds.eduTech;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textview.MaterialTextView;
import com.jds.eduTech.adapters.ViewAdapter;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class SplashScreenActivity extends AppCompatActivity implements ViewAdapter.OnSliderChangeListener {

    ViewPager viewPager;
    DotsIndicator dot1;
    ViewAdapter viewAdapter;
    Button next;
    MaterialTextView text_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.view_pager);
        next=findViewById(R.id.next);
        text_skip=findViewById(R.id.text_skip);
        dot1=findViewById(R.id.dots_indicator);

        viewAdapter=new ViewAdapter(this,viewPager);
        viewAdapter.setOnSliderChangeListener(this);
        viewPager.setAdapter(viewAdapter);
        dot1.setViewPager(viewPager);

        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        String sessionId = sharedPreferences.getString("sessionId", null);

        if (sessionId != null) {
            // Allow the user to access protected resources
            // Perform the necessary actions for authenticated users
            Intent intent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish(); // Optional: Prevent the user from returning to the login screen using the back button
        }

        next.setOnClickListener(v->{
            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        text_skip.setOnClickListener(v->{
            Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
            startActivity(intent);
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView textView = findViewById(R.id.text_content);
        String fullText = "Modern Amenities \n for modern Govt schools";
        String highlightText = "Govt schools";
        int highlightColor = getResources().getColor(R.color.highlight_color);

        SpannableString spannableString = new SpannableString(fullText);
        int startIndex = fullText.indexOf(highlightText);
        int endIndex = startIndex + highlightText.length();

        ForegroundColorSpan colorSpan = new ForegroundColorSpan(highlightColor);
        spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(spannableString);
    }

    public void onSliderChanged(int position) {
        System.out.println("position:::"+position);
        if(position == 1){
            TextView textView = findViewById(R.id.text_content);
            String fullText = " 1543 Govt Schools are\n" + "Benefited  So far";
            String highlightText = "Benefited";
            int highlightColor = getResources().getColor(R.color.highlight_color);

            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(highlightText);
            int endIndex = startIndex + highlightText.length();

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(highlightColor);
            spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spannableString);
            next.setText("Continue");
            text_skip.setVisibility(View.VISIBLE);
        }else if(position == 2){
            TextView textView = findViewById(R.id.text_content);
            String fullText = " 33 Cr Sanctioned\n" + "by the  Govt of State";
            String highlightText = "Sanctioned";
            int highlightColor = getResources().getColor(R.color.highlight_color);

            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(highlightText);
            int endIndex = startIndex + highlightText.length();

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(highlightColor);
            spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spannableString);
            next.setText("Get Started");
            text_skip.setVisibility(View.GONE);

        }else {
            TextView textView = findViewById(R.id.text_content);
            String fullText = "Modern Amenities \n for modern Govt schools";
            String highlightText = "Govt schools";
            int highlightColor = getResources().getColor(R.color.highlight_color);

            SpannableString spannableString = new SpannableString(fullText);
            int startIndex = fullText.indexOf(highlightText);
            int endIndex = startIndex + highlightText.length();

            ForegroundColorSpan colorSpan = new ForegroundColorSpan(highlightColor);
            spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            textView.setText(spannableString);
            next.setText("Continue");
            text_skip.setVisibility(View.VISIBLE);
        }
    }
}