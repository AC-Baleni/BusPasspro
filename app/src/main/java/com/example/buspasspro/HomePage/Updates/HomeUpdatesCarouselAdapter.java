package com.example.buspasspro.HomePage.Updates;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.buspasspro.R;

import java.util.List;

public class HomeUpdatesCarouselAdapter extends RecyclerView.Adapter<HomeUpdatesCarouselAdapter.ViewHolder> {

    private List<Update> updateList;
    private Context context;

    public HomeUpdatesCarouselAdapter(List<Update> updateList, Context context) {
        this.updateList = updateList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.updates_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Update update = updateList.get(position);
        holder.bind(update);
    }

    @Override
    public int getItemCount() {
        return updateList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleUpdateTXT;
        TextView messageUpdateTXT;
        TextView timeSpan;
        ImageView companyLogo;
        TextView companyName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleUpdateTXT = itemView.findViewById(R.id.titleHomeUpdateTxt);
            messageUpdateTXT = itemView.findViewById(R.id.messageTxtUpdate);
            timeSpan = itemView.findViewById(R.id.timeTxtUpdate);
            companyLogo = itemView.findViewById(R.id.logoImageHome);
            companyName = itemView.findViewById(R.id.companyNameTxtUpdate);
        }

        // Setter method for binding data to views
        public void bind(Update update) {
            titleUpdateTXT.setText(update.getTitle());
            messageUpdateTXT.setText(update.getMessage());
            timeSpan.setText("Update from " + update.getStartTime() + " to " + update.getEndTime());
            companyName.setText(update.getCompanyName());
            Glide.with(context)
                    .load(update.getLogoUrl())
                    .circleCrop()
                    .into(companyLogo);
        }
    }
}
