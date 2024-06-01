package com.example.buspasspro.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buspasspro.Auth.Login;
import com.example.buspasspro.Auth.SignUp;
import com.example.buspasspro.R;

public class WelcomePage extends AppCompatActivity {

    private TextView[] textViews;
    private LinearLayout dotsContainer;
    private int currentPage = 0;
    private Handler handler;
    private Runnable runnable;

    private Button getStartedbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        // Initialize Views
        textViews = new TextView[]{
                findViewById(R.id.text1),
                findViewById(R.id.text2),
                findViewById(R.id.text3),
                findViewById(R.id.text4)
        };
        dotsContainer = findViewById(R.id.dotsContainer);
        getStartedbtn = findViewById(R.id.continue_btn_Welcome);
        getStartedbtn.setOnClickListener(view -> startActivity(new Intent(WelcomePage.this, Home.class)));

        // Initialize Dots
        addDots();

        // Start Carousel
        startCarousel();
    }

    private void addDots() {
        for (int i = 0; i < textViews.length; i++) {
            TextView dot = new TextView(this);
            dot.setText("• ");
            dot.setTextSize(30);
            dot.setTextColor(getResources().getColor(android.R.color.darker_gray));
            dotsContainer.addView(dot);
        }
    }

    private void startCarousel() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentPage++;
                if (currentPage == textViews.length) {
                    currentPage = 0;
                }
                showTextAndDots(currentPage);
                handler.postDelayed(this, 3000); // Change slide every 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void showTextAndDots(int position) {
        for (int i = 0; i < textViews.length; i++) {
            if (i == position) {
                textViews[i].setVisibility(View.VISIBLE);
                ((TextView) dotsContainer.getChildAt(i)).setTextColor(getResources().getColor(android.R.color.white));
            } else {
                textViews[i].setVisibility(View.GONE);
                ((TextView) dotsContainer.getChildAt(i)).setTextColor(getResources().getColor(android.R.color.darker_gray));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
