package com.example.buspasspro.HomePage.Tickets;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buspasspro.BusTracking.TrackingFragment;
import com.example.buspasspro.HomePage.SeatSelection.AddSeatFragment;
import com.example.buspasspro.R;

import java.util.List;

public class ActiveTicketsAdapter extends RecyclerView.Adapter<ActiveTicketsAdapter.ViewHolder> {

    private List<ActiveTickets> ticketList;
    private Context context;

    public ActiveTicketsAdapter(List<ActiveTickets> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    public void filterList(List<ActiveTickets> filteredList) {
        ticketList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.active_ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActiveTickets ticket = ticketList.get(position);
        holder.bind(ticket, ticket.getBusId());
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewCompanyName;
        TextView textViewDepatureTime;
        TextView textViewTrip;
        TextView textViewTripPrice;
        TextView busNum;
        TextView ticketDate;
        TextView seatNum;
        ImageView qrCode;
        ImageButton trackTicket;
        String busId; // Store busId as an instance variable

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.complanyNameATI);
            busNum = itemView.findViewById(R.id.busNumberATI);
            ticketDate = itemView.findViewById(R.id.dateATI);
            textViewDepatureTime = itemView.findViewById(R.id.depatureTimeATI);
            seatNum = itemView.findViewById(R.id.seatATI);
            qrCode = itemView.findViewById(R.id.QRImgATI);
            textViewTrip = itemView.findViewById(R.id.destinationATI);
            textViewTripPrice = itemView.findViewById(R.id.priceATI);
            trackTicket = itemView.findViewById(R.id.trackBusBtnATI);


        }

        private void redirectToTracking(String busId) {
            AppCompatActivity activity = (AppCompatActivity) context;
            TrackingFragment trackingFragment = new TrackingFragment();
            // Pass busId to the fragment
            Bundle args = new Bundle();
            args.putString("busId", busId);
            trackingFragment.setArguments(args);

            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, trackingFragment)
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        public void onClick(View v) {
            // Handle click event
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ActiveTickets clickedTicket = ticketList.get(position);
                String busId = clickedTicket.getBusId();

                // Replace the current fragment with SeatSelectionFragment
                AppCompatActivity activity = (AppCompatActivity) context;
                AddSeatFragment addSeatFragment = new AddSeatFragment();

                // Pass busID and ticketID to the fragment
                Bundle args = new Bundle();
                args.putString("busId", busId);
                addSeatFragment.setArguments(args);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addSeatFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        // Setter method for busName
        @SuppressLint("SetTextI18n")
        public void bind(ActiveTickets ticket, String busId) {
            textViewCompanyName.setText(ticket.getCompanyName());
            textViewDepatureTime.setText("Departure Time: " + ticket.getDepartureTime());
            textViewTrip.setText("Bus Trip: from " + ticket.getStartingLocation() + " to " + ticket.getDestination());
            textViewTripPrice.setText("Trip Price: " + ticket.getTicketPrice());
            seatNum.setText("Seat Number: "+ticket.getSelectedSeat());
            busNum.setText("Bus Number: "+ticket.getBusLicensePlate());
            ticketDate.setText("Ticket date: "+ticket.getSelectedDate());

            Glide.with(context)
                    .load(ticket.getQrCodeUrl())
                    .into(qrCode);
            // Bind other ticket information to respective TextViews
            this.busId = busId; // Assign busId


            // Set click listener for trackTicket button
            trackTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    redirectToTracking(busId); // Call redirectToTracking with stored busId
                }
            });
        }
    }
}
