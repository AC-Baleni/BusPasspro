package com.example.buspasspro.HomePage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasspro.HomePage.Updates.HomeUpdatesCarouselAdapter;
import com.example.buspasspro.HomePage.Updates.Update;
import com.example.buspasspro.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeUpdatesCarouselAdapter adapter;
    private List<Update> updateList;
    private ProgressBar progressBar;
    private TextView noUpdates;
    private Handler handler = new Handler(Looper.getMainLooper());
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.updatesHomeRView);
        progressBar = view.findViewById(R.id.progressBarHomeMain);
        noUpdates = view.findViewById(R.id.noUpdatesHomeTXT);
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        updateList = new ArrayList<>();
        adapter = new HomeUpdatesCarouselAdapter(updateList, requireContext());
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(View.VISIBLE);


        // Snap helper for snapping items to the center
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        // Retrieve update data from Firestore
        firestore.collection("Updates")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        updateList.clear(); // Clear existing list
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Update update = document.toObject(Update.class);
                            update.setUpdateID(document.getId()); // Set the update ID
                            updateList.add(update);
                        }
                        adapter.notifyDataSetChanged();
                        // Hide progress bar after loading updates
                        progressBar.setVisibility(View.GONE);
                        // Show "no updates" message if no updates found
                        if (updateList.isEmpty()) {
                            noUpdates.setVisibility(View.VISIBLE);
                        } else {
                            noUpdates.setVisibility(View.GONE);
                            // Start looping animation
                            startLoopingAnimation();
                        }
                    }
                });

    }

    // Method to start looping animation
    private void startLoopingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int nextPosition = layoutManager.findLastCompletelyVisibleItemPosition() + 1;
                if (nextPosition == adapter.getItemCount()) {
                    nextPosition = 0;
                }
                recyclerView.smoothScrollToPosition(nextPosition);
                handler.postDelayed(this, 5000); // Delay for animation change
            }
        }, 5000); // Delay for initial animation start
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null); // Remove all callbacks and messages
    }
}
