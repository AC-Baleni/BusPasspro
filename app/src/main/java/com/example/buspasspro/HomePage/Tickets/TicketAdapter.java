package com.example.buspasspro.HomePage.Tickets;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasspro.HomePage.SeatSelection.AddSeatFragment;
import com.example.buspasspro.R;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.ViewHolder> {

    private List<Ticket> ticketList;
    private Context context;

    public TicketAdapter(List<Ticket> ticketList, Context context) {
        this.ticketList = ticketList;
        this.context = context;
    }

    public void filterList(List<Ticket> filteredList) {
        ticketList = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trip_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);
        holder.bind(ticket);
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
        RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.companyNameTxtTrip);
            textViewDepatureTime = itemView.findViewById(R.id.timeTxtTI);
            textViewTrip = itemView.findViewById(R.id.destinationTxtST);
            textViewTripPrice = itemView.findViewById(R.id.tripPriceTI);
            ratingBar = itemView.findViewById(R.id.ratingBarTI);
            ratingBar.setIsIndicator(true); // This makes the RatingBar non-interactive

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Handle click event
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                Ticket clickedTicket = ticketList.get(position);
                String busId = clickedTicket.getBusId();
                String ticketId = clickedTicket.getTicketID();

                // Replace the current fragment with SeatSelectionFragment
                AppCompatActivity activity = (AppCompatActivity) context;
                AddSeatFragment addSeatFragment = new AddSeatFragment();

                // Pass busID and ticketID to the fragment
                Bundle args = new Bundle();
                args.putString("busId", busId);
                args.putString("ticketId", ticketId);
                addSeatFragment.setArguments(args);

                activity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, addSeatFragment)
                        .addToBackStack(null)
                        .commit();
            }
        }

        // Setter method for busName
        public void bind(Ticket ticket) {
            textViewCompanyName.setText(ticket.getCompanyName());
            textViewDepatureTime.setText("Departure Time: " + ticket.getDepartureTime());
            textViewTrip.setText("Bus Trip: from " + ticket.getStartingLocation() + " to " + ticket.getDestination());
            textViewTripPrice.setText("Trip Price: " + ticket.getTicketPrice());

            // Set the rating value to the RatingBar
            if (ticket.getRating() != null) {
                float ratingValue = ticket.getRating(); // Assuming getRating() returns the float rating value
                ratingBar.setRating(ratingValue);
            }
        }
    }
}
