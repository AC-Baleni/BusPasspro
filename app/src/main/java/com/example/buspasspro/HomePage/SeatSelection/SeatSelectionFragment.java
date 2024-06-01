package com.example.buspasspro.HomePage.SeatSelection;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;


import com.example.buspasspro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SeatSelectionFragment extends Fragment {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    public SeatSelectionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_seat_selection, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        progressBar = view.findViewById(R.id.selecteSeatProgressBar);
        // Retrieve busID and ticketID
        Bundle args = getArguments();
        if (args != null) {
            String busId = args.getString("busId");
            String ticketId = args.getString("ticketId");
            //Check availability of each seat section A
            checkSeatAvailability("A1", view.findViewById(R.id.seatA1), busId, ticketId);
            checkSeatAvailability("A2", view.findViewById(R.id.seatA2), busId, ticketId);
            checkSeatAvailability("A3", view.findViewById(R.id.seatA3), busId, ticketId);
            checkSeatAvailability("A4", view.findViewById(R.id.seatA4), busId, ticketId);
            checkSeatAvailability("A5", view.findViewById(R.id.seatA5), busId, ticketId);
            checkSeatAvailability("A6", view.findViewById(R.id.seatA6), busId, ticketId);
            checkSeatAvailability("A7", view.findViewById(R.id.seatA7), busId, ticketId);
            checkSeatAvailability("A8", view.findViewById(R.id.seatA8), busId, ticketId);
            checkSeatAvailability("A9", view.findViewById(R.id.seatA9), busId, ticketId);
            checkSeatAvailability("A10", view.findViewById(R.id.seatA10), busId, ticketId);
            checkSeatAvailability("A11", view.findViewById(R.id.seatA11), busId, ticketId);
            checkSeatAvailability("A12", view.findViewById(R.id.seatA12), busId, ticketId);
            checkSeatAvailability("A13", view.findViewById(R.id.seatA13), busId, ticketId);
            checkSeatAvailability("A14", view.findViewById(R.id.seatA14), busId, ticketId);
            checkSeatAvailability("A15", view.findViewById(R.id.seatA15), busId, ticketId);
            checkSeatAvailability("A16", view.findViewById(R.id.seatA16), busId, ticketId);
            checkSeatAvailability("A17", view.findViewById(R.id.seatA17), busId, ticketId);
            checkSeatAvailability("A18", view.findViewById(R.id.seatA18), busId, ticketId);
            checkSeatAvailability("A19", view.findViewById(R.id.seatA19), busId, ticketId);
            checkSeatAvailability("A20", view.findViewById(R.id.seatA20), busId, ticketId);
            checkSeatAvailability("A21", view.findViewById(R.id.seatA21), busId, ticketId);
            checkSeatAvailability("A22", view.findViewById(R.id.seatA22), busId, ticketId);
            checkSeatAvailability("A23", view.findViewById(R.id.seatA23), busId, ticketId);
            checkSeatAvailability("A24", view.findViewById(R.id.seatA24), busId, ticketId);
            checkSeatAvailability("A25", view.findViewById(R.id.seatA25), busId, ticketId);
            checkSeatAvailability("A26", view.findViewById(R.id.seatA26), busId, ticketId);
            checkSeatAvailability("A27", view.findViewById(R.id.seatA27), busId, ticketId);
            checkSeatAvailability("A28", view.findViewById(R.id.seatA28), busId, ticketId);
            checkSeatAvailability("A29", view.findViewById(R.id.seatA29), busId, ticketId);
            checkSeatAvailability("A30", view.findViewById(R.id.seatA30), busId, ticketId);

            //Check availability of each seat section B
            checkSeatAvailability("B1", view.findViewById(R.id.seatB1), busId, ticketId);
            checkSeatAvailability("B2", view.findViewById(R.id.seatB2), busId, ticketId);
            checkSeatAvailability("B3", view.findViewById(R.id.seatB3), busId, ticketId);
            checkSeatAvailability("B4", view.findViewById(R.id.seatB4), busId, ticketId);
            checkSeatAvailability("B5", view.findViewById(R.id.seatB5), busId, ticketId);
            checkSeatAvailability("B6", view.findViewById(R.id.seatB6), busId, ticketId);
            checkSeatAvailability("B7", view.findViewById(R.id.seatB7), busId, ticketId);
            checkSeatAvailability("B8", view.findViewById(R.id.seatB8), busId, ticketId);
            checkSeatAvailability("B9", view.findViewById(R.id.seatB9), busId, ticketId);
            checkSeatAvailability("B10", view.findViewById(R.id.seatB10), busId, ticketId);
            checkSeatAvailability("B11", view.findViewById(R.id.seatB11), busId, ticketId);
            checkSeatAvailability("B12", view.findViewById(R.id.seatB12), busId, ticketId);
            checkSeatAvailability("B13", view.findViewById(R.id.seatB13), busId, ticketId);
            checkSeatAvailability("B14", view.findViewById(R.id.seatB14), busId, ticketId);
            checkSeatAvailability("B15", view.findViewById(R.id.seatB15), busId, ticketId);
            checkSeatAvailability("B16", view.findViewById(R.id.seatB16), busId, ticketId);
            checkSeatAvailability("B17", view.findViewById(R.id.seatB17), busId, ticketId);
            checkSeatAvailability("B18", view.findViewById(R.id.seatB18), busId, ticketId);
            checkSeatAvailability("B19", view.findViewById(R.id.seatB19), busId, ticketId);
            checkSeatAvailability("B20", view.findViewById(R.id.seatB20), busId, ticketId);
            checkSeatAvailability("B21", view.findViewById(R.id.seatB21), busId, ticketId);



            // Check availability of other seats similarly...
        }
        // A section
        final ImageButton seatB1 = view.findViewById(R.id.seatB1);
        final ImageButton seatB2 = view.findViewById(R.id.seatB2);
        final ImageButton seatB3 = view.findViewById(R.id.seatB3);
        final ImageButton seatB4 = view.findViewById(R.id.seatB4);
        final ImageButton seatB5 = view.findViewById(R.id.seatB5);
        final ImageButton seatB6 = view.findViewById(R.id.seatB6);
        final ImageButton seatB7 = view.findViewById(R.id.seatB7);
        final ImageButton seatB8 = view.findViewById(R.id.seatB8);
        final ImageButton seatB9 = view.findViewById(R.id.seatB9);
        final ImageButton seatB10 = view.findViewById(R.id.seatB10);
        final ImageButton seatB11 = view.findViewById(R.id.seatB11);
        final ImageButton seatB12 = view.findViewById(R.id.seatB12);
        final ImageButton seatB13 = view.findViewById(R.id.seatB13);
        final ImageButton seatB14 = view.findViewById(R.id.seatB14);
        final ImageButton seatB15 = view.findViewById(R.id.seatB15);
        final ImageButton seatB16 = view.findViewById(R.id.seatB16);
        final ImageButton seatB17 = view.findViewById(R.id.seatB17);
        final ImageButton seatB18 = view.findViewById(R.id.seatB18);
        final ImageButton seatB19 = view.findViewById(R.id.seatB19);
        final ImageButton seatB20 = view.findViewById(R.id.seatB20);
        final ImageButton seatB21 = view.findViewById(R.id.seatB21);

        // B section
        final ImageButton seatA1 = view.findViewById(R.id.seatA1);
        final ImageButton seatA2 = view.findViewById(R.id.seatA2);
        final ImageButton seatA3 = view.findViewById(R.id.seatA3);
        final ImageButton seatA4 = view.findViewById(R.id.seatA4);
        final ImageButton seatA5 = view.findViewById(R.id.seatA5);
        final ImageButton seatA6 = view.findViewById(R.id.seatA6);
        final ImageButton seatA7 = view.findViewById(R.id.seatA7);
        final ImageButton seatA8 = view.findViewById(R.id.seatA8);
        final ImageButton seatA9 = view.findViewById(R.id.seatA9);
        final ImageButton seatA10 = view.findViewById(R.id.seatA10);
        final ImageButton seatA11 = view.findViewById(R.id.seatA11);
        final ImageButton seatA12 = view.findViewById(R.id.seatA12);
        final ImageButton seatA13 = view.findViewById(R.id.seatA13);
        final ImageButton seatA14 = view.findViewById(R.id.seatA14);
        final ImageButton seatA15 = view.findViewById(R.id.seatA15);
        final ImageButton seatA16 = view.findViewById(R.id.seatA16);
        final ImageButton seatA17 = view.findViewById(R.id.seatA17);
        final ImageButton seatA18 = view.findViewById(R.id.seatA18);
        final ImageButton seatA19 = view.findViewById(R.id.seatA19);
        final ImageButton seatA20 = view.findViewById(R.id.seatA20);
        final ImageButton seatA21 = view.findViewById(R.id.seatA21);
        final ImageButton seatA22 = view.findViewById(R.id.seatA22);
        final ImageButton seatA23 = view.findViewById(R.id.seatA23);
        final ImageButton seatA24 = view.findViewById(R.id.seatA24);
        final ImageButton seatA25 = view.findViewById(R.id.seatA25);
        final ImageButton seatA26 = view.findViewById(R.id.seatA26);
        final ImageButton seatA27 = view.findViewById(R.id.seatA27);
        final ImageButton seatA28 = view.findViewById(R.id.seatA28);
        final ImageButton seatA29 = view.findViewById(R.id.seatA29);
        final ImageButton seatA30 = view.findViewById(R.id.seatA30);

        // Add references for other seat ImageButtons similarly...
        // Retrieve busID and ticketID

        // Check availability of other seats similarly...

        // Set OnClickListener for each seat IN section B
        seatB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");

                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B1",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");

                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B2",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");

                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B3",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B4",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B5",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B6",busId,ticketId,firstSelectedDate);
                }
            }
        }); seatB7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B7",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B8",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B9",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B10",busId,ticketId,firstSelectedDate);
                }
            }
        }); seatB11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B11",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B12",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B13",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B14",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B15",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B16",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B17",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    String ticketId = args.getString("ticketId");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B18",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B19",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B20",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatB21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("B21",busId,ticketId,firstSelectedDate);
                }
            }
        });

        // Set OnClickListener for each seat IN section A
        seatA1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A1",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A2",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A3",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A4",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A5",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A6",busId,ticketId,firstSelectedDate);
                }
            }
        });   seatA7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A7",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A8",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A9",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A10",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A11",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A12",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A13",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A14",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A15",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A16",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A17",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A18",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A19",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A20",busId,ticketId,firstSelectedDate);
                }
            }
        });   seatA21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A21",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A22",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A23",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A24",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A25",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A26",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A27",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A28",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A29",busId,ticketId,firstSelectedDate);
                }
            }
        });
        seatA30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve busID and ticketID
                Bundle args = getArguments();
                if (args != null) {
                    String busId = args.getString("busId");
                    String ticketId = args.getString("ticketId");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Do whatever you need to do with busId and ticketId
                    navigateToAddSeatFragment("A30",busId,ticketId,firstSelectedDate);
                }
            }
        });



        // Set OnClickListener for other seat ImageButtons similarly...
    }

    private void navigateToAddSeatFragment(String selectedSeat,String busId, String ticketID, String selectedDate) {
        // Create a new instance of AddSeatFragment and pass the selected seat as an argument
        AddSeatFragment addSeatFragment = new AddSeatFragment();
        Bundle args = new Bundle();
        args.putString("selectedSeat", selectedSeat);
        args.putString("busId",busId);
        args.putString("ticketId",ticketID);
        args.putString("firstSelectedDate",selectedDate);
        addSeatFragment.setArguments(args);
        // Navigate to SearchTicketFragment using FragmentManager
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, addSeatFragment)
                .addToBackStack(null)
                .commit();
    }


    private void checkSeatAvailability(final String seatNumber, final ImageButton seatButton, String busId, String ticketId) {
        progressBar.setVisibility(View.VISIBLE); // Show progress bar

        // Retrieve arguments
        Bundle args = getArguments();
        if (args != null) {
            String selectedDateString = args.getString("firstSelectedDate");
            if (selectedDateString != null) {
                db.collection("Buses").document(busId)
                        .collection(selectedDateString).document("selected_seat").get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    String selectedSeat = (String) document.get(seatNumber);
                                    if (selectedSeat != null && selectedSeat.equals(seatNumber)) {
                                        // Seat already booked by another user, disable ImageButton
                                        seatButton.setEnabled(false);
                                        // Change ImageButton background to seat_background drawable
                                        seatButton.setBackgroundResource(R.drawable.seat_background);
                                    }
                                }
                                progressBar.setVisibility(View.GONE); // Hide progress bar
                            }
                        });
            }
        }
    }
}
