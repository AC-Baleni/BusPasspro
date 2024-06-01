package com.example.buspasspro.HomePage.Tickets;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasspro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketFragment extends Fragment {

    private Button bookTicket;
    private TextView noTicketsTxt;
    private ImageView noTicketsIMG;
    private ActiveTicketsAdapter adapter;
    private List<ActiveTickets> ticketList;
    private ProgressBar progressBar;
    private FirebaseUser currentUser;

    public TicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.activeTicketsRView);
        progressBar = view.findViewById(R.id.progressBarAT);
        noTicketsIMG = view.findViewById(R.id.boredImg);
        noTicketsTxt = view.findViewById(R.id.noTicketsTXT);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = currentUser.getUid();
        bookTicket = view.findViewById(R.id.bookTicketBtn);

        // Set OnClickListener to handle button click
        bookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to SearchTicketFragment
                // Create a new instance of SearchTicketFragment
                SearchTicketFragment searchTicketFragment = new SearchTicketFragment();
                // Navigate to SearchTicketFragment using FragmentManager
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, searchTicketFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));


        ticketList = new ArrayList<>();
        adapter = new ActiveTicketsAdapter(ticketList, requireContext());
        recyclerView.setAdapter(adapter);
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);
        // Retrieve ticket data from Firestore
        firestore.collection("BookedTickets")
                .document(userId)
                .collection("Booked_Tickets")
                .whereEqualTo("userId", userId)
                .whereIn("ticketStatus", Arrays.asList("cleared", "Not Used"))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ticketList.clear(); // Clear existing list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            ActiveTickets activeTicket = document.toObject(ActiveTickets.class);
                            activeTicket.setTicketID(document.getId()); // Set the ticket ID
                            ticketList.add(activeTicket);

                            // Check if ticketStatus is "cleared"
                            if (activeTicket.getTicketStatus().equals("cleared")) {
                                // Show message dialog to rate the trip
                                showRateTripDialog(activeTicket);

                            }
                        }
                        adapter.notifyDataSetChanged();
                        // Hide progress bar after loading tickets
                        progressBar.setVisibility(View.GONE);
                        // Show "no tickets" message if no tickets found
                        if (ticketList.isEmpty()) {
                            noTicketsTxt.setVisibility(View.VISIBLE);
                            noTicketsIMG.setVisibility(View.VISIBLE);
                        } else {
                            noTicketsTxt.setVisibility(View.GONE);
                            noTicketsIMG.setVisibility(View.GONE);
                        }
                    }
                });
    }



    private void showRateTripDialog(ActiveTickets activeTicket) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Rate Your Trip");
        builder.setMessage("Would you like to rate your trip for Bus:"+activeTicket.getBusLicensePlate()+"?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Redirect to RatingFragment
                RatingFragment ratingFragment = new RatingFragment();
                Bundle bundle = new Bundle();
                bundle.putString("busId", activeTicket.getBusId());
                bundle.putString("ticketId", activeTicket.getTicketID());
                bundle.putString("userId",activeTicket.getUserId());
                ratingFragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, ratingFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing or handle differently if user chooses not to rate
                updateTicketStatus(activeTicket.getUserId(),activeTicket.getTicketID());
            }
        });
        builder.setCancelable(false);
        builder.show();

    }
    private void updateTicketStatus(String userId, String ticketId) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("ticketStatus", "Completed Unrated");
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
}
