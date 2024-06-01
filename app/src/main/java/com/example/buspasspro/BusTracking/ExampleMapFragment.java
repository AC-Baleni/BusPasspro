package com.example.buspasspro.BusTracking;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasspro.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExampleMapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    private static final LatLng START_POINT = new LatLng(40.7128, -74.0060); // Example start point
    private static final LatLng END_POINT = new LatLng(34.0522, -118.2437); // Example end point

    private static final String API_KEY = "YOUR_API_KEY";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_example_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(START_POINT, 12)); // Zoom to the start point

        // Add markers for start and end points
        mMap.addMarker(new MarkerOptions().position(START_POINT).title("Start"));
        mMap.addMarker(new MarkerOptions().position(END_POINT).title("End"));

        // Draw the route between start and end points
        drawRoute(START_POINT, END_POINT);
    }

    private void drawRoute(LatLng origin, LatLng destination) {
        String url = "https://maps.googleapis.com/maps/api/directions/json" +
                "?origin=" + origin.latitude + "," + origin.longitude +
                "&destination=" + destination.latitude + "," + destination.longitude +
                "&key=" + API_KEY;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                // Handle failure
                Log.e("DrawRoute", "Request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        if (responseBody != null) {
                            String responseBodyString = responseBody.string();
                            // Parse the JSON response to get the route information
                            LatLng[] path = parseRouteFromJson(responseBodyString);
                            if (path != null) {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(() -> drawPolyline(path));
                                } else {
                                    Log.e("DrawRoute", "Fragment's activity is null, cannot run on UI thread");
                                }
                            } else {
                                Log.e("DrawRoute", "Failed to parse route from JSON");
                            }
                        } else {
                            Log.e("DrawRoute", "Response body is null");
                        }
                    } else {
                        // Handle unsuccessful response
                        Log.e("DrawRoute", "Request failed with code: " + response.code());
                    }
                } catch (IOException | JSONException e) {
                    Log.e("DrawRoute", "Error processing response: " + e.getMessage());
                }
            }
        });
    }

    private LatLng[] parseRouteFromJson(String jsonResponse) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray routesArray = jsonObject.getJSONArray("routes");
        if (routesArray.length() > 0) {
            JSONObject routeObject = routesArray.getJSONObject(0);
            JSONObject overviewPolyline = routeObject.getJSONObject("overview_polyline");
            String polylinePoints = overviewPolyline.getString("points");
            return decodePolyline(polylinePoints);
        } else {
            Log.e("ParseRoute", "No routes found in JSON response");
            return null;
        }
    }

    private LatLng[] decodePolyline(String encoded) {
        List<LatLng> polyline = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            polyline.add(p);
        }
        return polyline.toArray(new LatLng[0]);
    }

    private void drawPolyline(LatLng[] path) {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(Arrays.asList(path))
                .color(Color.RED)
                .width(5);
        mMap.addPolyline(polylineOptions);
    }
}
