package com.example.buspasspro.HomePage.Checkout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.buspasspro.BusTracking.TrackingFragment;
import com.example.buspasspro.HomePage.Home;
import com.example.buspasspro.databinding.ActivityCheckoutSuccessBinding;


public class CheckoutSuccessActivity extends AppCompatActivity {

    private ImageButton homeBtn;
    private Button trackBus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCheckoutSuccessBinding layoutBinding = ActivityCheckoutSuccessBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        homeBtn = layoutBinding.homeBtnCS;
        trackBus = layoutBinding.trackBusBtn;

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutSuccessActivity.this, Home.class));
            }
        });
        trackBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redirectToTracking();
            }
        });

    }
    private void redirectToTracking() {
        // Assuming CheckoutSuccessActivity is hosted within an AppCompatActivity
        Intent intent = getIntent();
        if (intent != null) {
            String busId = intent.getStringExtra("busId");
            String ticketId = intent.getStringExtra("ticketId");

            AppCompatActivity activity = (AppCompatActivity) CheckoutSuccessActivity.this;
            TrackingFragment trackingFragment = new TrackingFragment();

            // Create arguments bundle and set busId and ticketId
            Bundle args = new Bundle();
            args.putString("busId", busId);
            args.putString("ticketId", ticketId);
            trackingFragment.setArguments(args);

            // Begin fragment transaction
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, trackingFragment) // Use android.R.id.content as the container
                    .addToBackStack(null)
                    .commit();
        }
    }

}