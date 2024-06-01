package com.example.buspasspro.HomePage.SeatSelection;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.buspasspro.HomePage.Checkout.CheckoutActivity;
import com.example.buspasspro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Empty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddSeatFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Button confirmBtn;
    private Button selectSeatBtn;
    private TextView showSelected;
    private EditText selectTripDate;
    private String selectedSeat;
    private Calendar selectedDate;
    private TextView headingSelected;


    public AddSeatFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_seat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        // Initialize buttons and TextView
        confirmBtn = view.findViewById(R.id.confirmBtnAS);
        selectSeatBtn = view.findViewById(R.id.btnSelectSeatAS);
        showSelected = view.findViewById(R.id.seatNumberTXTAS);
        selectTripDate = view.findViewById(R.id.setDepatureDate);
        headingSelected = view.findViewById(R.id.headingSelectedSeat);

        Bundle argReturn = getArguments();
        if(argReturn != null){
            String selectedDateReturn = argReturn.getString("firstSelectedDate");
            if (selectedDateReturn != null)
            {
                selectTripDate.setText(selectedDateReturn);
                // Assuming selectedDateReturn is your String
// Create a SimpleDateFormat object to parse the string
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                selectedDate = Calendar.getInstance();

                try {
                    // Parse the string to Date object
                    Date date = sdf.parse(selectedDateReturn);
                    // Set the Date object to Calendar
                    selectedDate.setTime(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        // Set click listener for select trip date EditText
        selectTripDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        Bundle args = getArguments();
        if (args != null) {
            selectedSeat = args.getString("selectedSeat");
            // Display selected seat in TextView
            if (selectedSeat != null && !selectedSeat.isEmpty()) {
                showSelected.setText(selectedSeat);
                // Make confirm button visible
                selectTripDate.setVisibility(View.VISIBLE);
                confirmBtn.setVisibility(View.VISIBLE);
            } else {
                headingSelected.setText("Please Select Seat");
            }
        } else {
            // Handle case where arguments are null
            Toast.makeText(getActivity(), "No seat selected.", Toast.LENGTH_SHORT).show();
        }
        // Set click listener for confirm button
        // Set click listener for confirm button
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate == null) {
                    Toast.makeText(getActivity(), "Please select a date.", Toast.LENGTH_SHORT).show();
                    return;
                }

                    String busId = args.getString("busId");

                    String ticketId = args.getString("ticketId");
                    String selectedSeatStr = args.getString("selectedSeat");
                    String firstSelectedDate = args.getString("firstSelectedDate");
                    // Navigate to CheckOutActivity
                    Intent intent = new Intent(getActivity(), CheckoutActivity.class);
                    // Pass busId, ticketId, and selected date as extras
                    intent.putExtra("busId", busId);
                    intent.putExtra("ticketId", ticketId);
                    intent.putExtra("selectedSeat", selectedSeatStr);
                    intent.putExtra("selectedDate",  firstSelectedDate);
                    startActivity(intent);

            }
        });

        // Set click listener for select seat button
        selectSeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to seat selection fragment
                if (selectedDate == null) {
                    Toast.makeText(getActivity(), "Please select a date.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Replace the current fragment with SeatSelectionFragment
                AppCompatActivity activity = (AppCompatActivity) getActivity();
                SeatSelectionFragment seatSelectionFragment = new SeatSelectionFragment();

                // Convert selectedDate to string representation
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                String selectedDateString = dateFormat.format(selectedDate.getTime());
                seatSelectionFragment.setArguments(args);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, seatSelectionFragment)
                        .addToBackStack(null)
                        .commit();

                // Redirect to SearchTicketFragment
                String busId = args.getString("busId");
                String ticketId = args.getString("ticketId");
                // Navigate to SeatSelectionFragment
                redirectToSelectSeatFragment(busId,ticketId,selectedDateString);

            }
        });
    }

    // Method to show date picker dialog
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = Calendar.getInstance();
                        selectedDate.set(Calendar.YEAR, year);
                        selectedDate.set(Calendar.MONTH, monthOfYear);
                        selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        selectTripDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, year, month, dayOfMonth);

        // Disable past dates
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }
    // Method to redirect to SearchTicketFragment
    private void redirectToSelectSeatFragment(String busId,String ticketID,String selectedDate) {
        // Create a new instance of SearchTicketFragment
        SeatSelectionFragment seatSelectionFragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString("busId",busId);
        args.putString("firstSelectedDate",selectedDate);
        args.putString("ticketId",ticketID);
        seatSelectionFragment.setArguments(args);
        // Navigate to SearchTicketFragment using FragmentManager
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, seatSelectionFragment)
                .addToBackStack(null)
                .commit();
    }
}
