package com.example.buspasspro.BusTracking;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DirectionsFetcher {

    public String fetchData(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            // Get the response
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            // Close streams
            bufferedReader.close();
            inputStream.close();

            // Disconnect the connection
            connection.disconnect();

            return stringBuilder.toString();
        } catch (Exception e) {
            Log.d("DirectionsFetcher", "Error fetching data: " + e.getMessage());
            return null;
        }
    }
}
