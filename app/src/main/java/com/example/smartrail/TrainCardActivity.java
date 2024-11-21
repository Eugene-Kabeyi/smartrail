package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TrainCardActivity extends AppCompatActivity {

    private TextView trainNameTextView;
    private TextView departureTimeTextView;
    private TextView arrivalTimeTextView;
    private TextView dateTextView;
    private TextView seatsTextView;
    private TextView priceTextView;
    private Button bookNowButton;

    private Train selectedTrain; // Store the train data retrieved from Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_train_card);

        // Initialize views
        trainNameTextView = findViewById(R.id.trainNameTextView);
        departureTimeTextView = findViewById(R.id.departureTimeTextView);
        arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        seatsTextView = findViewById(R.id.seatsTextView);
        priceTextView = findViewById(R.id.priceTextView);
        bookNowButton = findViewById(R.id.bookNowButton);

        // Retrieve train ID passed from TrainAdapter
        String trainId = getIntent().getStringExtra("trainId");

        // Check if trainId exists
        if (trainId != null && !trainId.isEmpty()) {
            // Retrieve train data from Firebase using the trainId
            getTrainFromFirebase(trainId);
        } else {
            // Handle case when trainId is missing
            trainNameTextView.setText("Error: Train data not available.");
        }

        // Set OnClickListener for the Book Now button
        bookNowButton.setOnClickListener(v -> {
            if (selectedTrain != null) {
                // Pass the train data to BookingActivity via Intent
                Intent intent = new Intent(TrainCardActivity.this, BookingActivity.class);
                intent.putExtra("trainName", selectedTrain.getTrainName());
                intent.putExtra("departureTime", selectedTrain.getDepartureTime());
                intent.putExtra("arrivalTime", selectedTrain.getArrivalTime());
                intent.putExtra("date", selectedTrain.getDate());
                intent.putExtra("seats", selectedTrain.getSeats());
                intent.putExtra("price", selectedTrain.getPrice());
                startActivity(intent);
            }
        });
    }

    private void getTrainFromFirebase(String trainId) {
        // Reference to the Firebase database for "trains"
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("trains");
        databaseReference.child(trainId) // Use the dynamic trainId passed from the adapter
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        selectedTrain = dataSnapshot.getValue(Train.class);
                        if (selectedTrain != null) {
                            // Populate the UI with the train data
                            trainNameTextView.setText(selectedTrain.getTrainName());
                            departureTimeTextView.setText("Departure: " + selectedTrain.getDepartureTime());
                            arrivalTimeTextView.setText("Arrival: " + selectedTrain.getArrivalTime());
                            dateTextView.setText("Date: " + selectedTrain.getDate());
                            seatsTextView.setText("Seats: " + selectedTrain.getSeats());
                            priceTextView.setText("Price: " + selectedTrain.getPrice());
                        } else {
                            // Handle case when data is not available
                            trainNameTextView.setText("Error: No train data found.");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Handle error
                        trainNameTextView.setText("Error: " + databaseError.getMessage());
                    }
                });
    }
}
