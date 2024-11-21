package com.example.smartrail;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.List;

public class TrainAdapter extends RecyclerView.Adapter<TrainAdapter.TrainViewHolder> {

    private List<String> trainIds; // List of train IDs to query Firebase
    private List<Train> trains;    // List of Train objects to populate RecyclerView
    private OnItemClickListener listener;

    public TrainAdapter(List<String> trainIds, OnItemClickListener listener) {
        this.trainIds = trainIds;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TrainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_train_card, parent, false);
        return new TrainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrainViewHolder holder, int position) {
        String trainId = trainIds.get(position); // Get the trainId for this position
        getTrainFromFirebase(trainId, holder); // Query Firebase to get train data
    }

    @Override
    public int getItemCount() {
        return trainIds.size(); // Return the number of items in the list
    }

    private void getTrainFromFirebase(String trainId, final TrainViewHolder holder) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("trains");
        databaseReference.child(trainId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Train train = dataSnapshot.getValue(Train.class);
                if (train != null) {
                    // Bind the data to the ViewHolder
                    holder.trainNameTextView.setText(train.getTrainName());
                    holder.departureTimeTextView.setText("Departure: " + train.getDepartureTime());
                    holder.arrivalTimeTextView.setText("Arrival: " + train.getArrivalTime());
                    holder.dateTextView.setText("Date: " + train.getDate());
                    holder.seatsTextView.setText("Seats: " + train.getSeats());
                    holder.priceTextView.setText("Price: " + train.getPrice());

                    // Load the train image using Glide
                    Glide.with(holder.itemView.getContext())
                            .load(train.getImageUrl()) // Train image URL from Firebase
                            .into(holder.trainImageView);

                    // Handle click event for the Book Now button
                    holder.bookNowButton.setOnClickListener(v -> {
                        if (listener != null) {
                            listener.onItemClick(trainId); // Notify the activity to handle the click
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors
            }
        });
    }

    public class TrainViewHolder extends RecyclerView.ViewHolder {

        private TextView trainNameTextView;
        private TextView departureTimeTextView;
        private TextView arrivalTimeTextView;
        private TextView dateTextView;
        private TextView seatsTextView;
        private TextView priceTextView;
        private ImageView trainImageView;
        private TextView bookNowButton;

        public TrainViewHolder(View itemView) {
            super(itemView);

            // Initialize the views for each train item
            trainNameTextView = itemView.findViewById(R.id.trainNameTextView);
            departureTimeTextView = itemView.findViewById(R.id.departureTimeTextView);
            arrivalTimeTextView = itemView.findViewById(R.id.arrivalTimeTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            seatsTextView = itemView.findViewById(R.id.seatsTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            trainImageView = itemView.findViewById(R.id.trainImageView);
            bookNowButton = itemView.findViewById(R.id.bookNowButton);
        }
    }

    // Interface to handle item click
    public interface OnItemClickListener {
        void onItemClick(String trainId);
    }
}
