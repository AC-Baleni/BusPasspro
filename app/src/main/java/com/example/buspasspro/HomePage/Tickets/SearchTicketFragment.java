package com.example.buspasspro.HomePage.Tickets;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasspro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchTicketFragment extends Fragment {

    private TicketAdapter adapter;
    private List<Ticket> ticketList;
    private ProgressBar progressBar;
    private TextView noTickets;

    public SearchTicketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_ticket, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.rViewSearchTrip);
        progressBar = view.findViewById(R.id.progressBarSearchTicket);
        noTickets = view.findViewById(R.id.noTripsTxtSearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ticketList = new ArrayList<>();
        adapter = new TicketAdapter(ticketList, requireContext());
        recyclerView.setAdapter(adapter);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Show progress bar
        progressBar.setVisibility(View.VISIBLE);

        firestore.collection("BusTickets")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ticketList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ticket ticket = document.toObject(Ticket.class);
                            ticket.setTicketID(document.getId());
                            ticketList.add(ticket);

                            firestore.collection("Buses")
                                    .document(ticket.getBusId())
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        if (documentSnapshot.exists()) {
                                            Double rating = documentSnapshot.getDouble("rating");
                                            if (rating != null) {
                                                float floatRating = rating.floatValue(); // Convert double to float
                                                ticket.setRating(floatRating); // Set bus rating for the ticket
                                                adapter.notifyDataSetChanged(); // Update UI
                                            }
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        // Handle failure
                                    });

                        }
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);

                        if (ticketList.isEmpty()) {
                            noTickets.setVisibility(View.VISIBLE);
                        } else {
                            noTickets.setVisibility(View.GONE);
                        }
                    }
                });

        // Search functionality
        EditText searchFrom = view.findViewById(R.id.fromETSearch);
        EditText searchDestination = view.findViewById(R.id.searchDestinationET);
        searchFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        searchDestination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }
    private void filter(String searchText) {
        List<Ticket> filteredList = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            if (ticket.getDestination().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(ticket);
            } else if (ticket.getDestination().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(ticket);
            } else if (ticket.getStartingLocation().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(ticket);
            } else if (ticket.getStartingLocation().toLowerCase().contains(searchText.toLowerCase())) {
                filteredList.add(ticket);
            }
        }
        adapter.filterList(filteredList);
    }

}


