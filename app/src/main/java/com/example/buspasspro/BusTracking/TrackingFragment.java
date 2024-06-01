package com.example.buspasspro.BusTracking;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;


import com.example.buspasspro.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.TravelMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrackingFragment extends Fragment {

    private FirebaseFirestore db;
    private FusedLocationProviderClient fusedLocationClient;
    private FirebaseUser currentUser;
    private GoogleMap myMap;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private String busId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve busId from arguments
        if (getArguments() != null) {
            busId = getArguments().getString("busId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tracking, container, false);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        // Get current user
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                myMap = googleMap;
                // Fetch and display bus location and route on the map
                fetchBusData();
            }
        });

        return view;
    }

    private void fetchBusData() {
        // Check location permission
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permission already granted, fetch the bus data
            db.collection("Buses")
                    .document(busId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                // Retrieve bus data
                                double latitude = document.getDouble("currentLat");
                                double longitude = document.getDouble("currentLong");

                              //  String busNumber = document.getString("busNumber");

                                // Add marker to bus location on map
                                LatLng busLocation = new LatLng(latitude, longitude);
                              //  myMap.addMarker(new MarkerOptions().position(busLocation).title("Bus " + busNumber));

                                // Retrieve starting location and destination
                                String startingLocation = document.getString("startingLocation");
                                String destination = document.getString("destination");

                                // Draw polyline between starting location and destination
                                drawRoute(startingLocation, destination,busLocation);
                            } else {
                                // Document does not exist
                                // Handle accordingly
                            }
                        } else {
                            // Handle errors
                        }
                    });
        } else {
            // Location permission not granted, request permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        }
    }

    private void drawRoute(String startingLocation, String destination,LatLng busLocation)
    {
        // Get starting location and destination LatLng
        LatLng startingLatLng = getLocationFromAddress(startingLocation);
        LatLng destinationLatLng = getLocationFromAddress(destination);

        // Add marker for starting location, destination, and bus location
        myMap.addMarker(new MarkerOptions().position(startingLatLng).title("Starting Location"));
        myMap.addMarker(new MarkerOptions().position(destinationLatLng).title("Destination"));
        // Add bus marker with a custom icon
        MarkerOptions busMarkerOptions = new MarkerOptions()
                .position(busLocation)
                .title("Bus Location")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.travel_bus));
        myMap.addMarker(busMarkerOptions);

        // Draw the actual road between starting location and destination
        // Extend bounds to include all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(startingLatLng);
        builder.include(destinationLatLng);
        builder.include(busLocation); // Include bus location in bounds calculation
        LatLngBounds bounds = builder.build();

        // Set camera position to encompass all markers
        int padding = 100; // Padding of 100 pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        myMap.moveCamera(cu);
    }


    // Helper method to get LatLng from location name using Geocoder
    private LatLng getLocationFromAddress(String strAddress) {
        Geocoder coder = new Geocoder(getActivity());
        try {
            List<Address> address = coder.getFromLocationName(strAddress, 1);
            if (address == null || address.isEmpty()) {
                return null;
            }
            Address location = address.get(0);
            return new LatLng(location.getLatitude(), location.getLongitude());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Handle the user's response to the permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, fetch bus data
                fetchBusData();
            } else {
                // Location permission denied
                // Handle the denial accordingly, for example, show a message to the user
            }
        }
    }
}
