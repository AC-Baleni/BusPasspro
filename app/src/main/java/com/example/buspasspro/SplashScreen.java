package com.example.buspasspro;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.buspasspro.Auth.Login;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);
        ImageView sloganImage = findViewById(R.id.sloganImage);

        ObjectAnimator logoAnimation = ObjectAnimator.ofFloat(logo, "translationY", -200f, 0f);
        logoAnimation.setDuration(2000); // Adjust duration as needed

        ObjectAnimator sloganAnimation = ObjectAnimator.ofFloat(sloganImage, "translationY", 200f, 0f);
        sloganAnimation.setDuration(2000); // Adjust duration as needed

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(logoAnimation, sloganAnimation);
        animatorSet.start();

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                // Navigate to the next activity after animation ends
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }
        });
    }
}
