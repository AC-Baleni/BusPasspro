package com.example.buspasspro.HomePage.Tickets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.buspasspro.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class RatingFragment extends Fragment {

    private RatingBar ratingBar;
    private Button submitButton;

    public RatingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ratin , container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ratingBar = view.findViewById(R.id.ratingBar);
        submitButton = view.findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newRating = ratingBar.getRating();
                String busId = getArguments().getString("busId");
                String ticketId = getArguments().getString("ticketId");
                String userId = getArguments().getString("userId");
                calculateAndSetAverageRating(busId, newRating);
                updateTicketStatus(ticketId,userId);
            }
        });
    }

    private void updateTicketStatus(String ticketId,String userId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("ticketStatus", "rated");

        firestore.collection("BookedTickets")
                .document(userId)
                .collection("Booked_Tickets")
                .document(ticketId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Ticket status updated successfully
                    Toast.makeText(requireContext(), "Ticket status updated to rated!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to update ticket status
                    Toast.makeText(requireContext(), "Failed to update ticket status!", Toast.LENGTH_SHORT).show();
                });
    }

    private void calculateAndSetAverageRating(String busId, float newRating) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Buses")
                .document(busId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    float currentRating = 0.0f;
                    int totalRatings = 0;

                    if (documentSnapshot.exists()) {
                        currentRating = documentSnapshot.getDouble("rating") != null ? documentSnapshot.getDouble("rating").floatValue() : 0.0f;
                        totalRatings = documentSnapshot.getLong("totalRatings") != null ? documentSnapshot.getLong("totalRatings").intValue() : 0;
                    }

                    totalRatings++;

                    float averageRating;
                    if (totalRatings > 0) {
                        averageRating = (currentRating * (totalRatings - 1) + newRating) / totalRatings;
                    } else {
                        averageRating = newRating;
                    }

                    updateBusRatingInDatabase(busId, averageRating, totalRatings);
                })
                .addOnFailureListener(e -> {
                    // Handle failure to retrieve bus data
                });
    }

    private void updateBusRatingInDatabase(String busId, float newRating, int totalRatings) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("rating", newRating);
        data.put("totalRatings", totalRatings);

        firestore.collection("Buses")
                .document(busId)
                .set(data, SetOptions.merge())
                .addOnSuccessListener(aVoid -> {
                    // Rating updated successfully
                    Toast.makeText(requireContext(), "Rating updated successfully!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to update rating
                    Toast.makeText(requireContext(), "Failed to update rating!", Toast.LENGTH_SHORT).show();
                });
    }
}
