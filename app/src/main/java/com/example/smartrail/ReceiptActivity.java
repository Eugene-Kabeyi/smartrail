package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReceiptActivity extends AppCompatActivity {

    private TextView receiptTrainNameTextView;
    private TextView receiptDepartureTimeTextView;
    private TextView receiptArrivalTimeTextView;
    private TextView receiptDateTextView;
    private TextView receiptPassengerNameTextView;
    private TextView receiptNumberOfPassengersTextView;
    private TextView receiptPriceTextView;
    private TextView receiptTotalCostTextView;
    private Button doneButton;

    private DatabaseReference bookingsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        // Initialize views
        receiptTrainNameTextView = findViewById(R.id.receiptTrainNameTextView);
        receiptDepartureTimeTextView = findViewById(R.id.receiptDepartureTimeTextView);
        receiptArrivalTimeTextView = findViewById(R.id.receiptArrivalTimeTextView);
        receiptDateTextView = findViewById(R.id.receiptDateTextView);
        receiptPassengerNameTextView = findViewById(R.id.receiptPassengerNameTextView);
        receiptNumberOfPassengersTextView = findViewById(R.id.receiptNumberOfPassengersTextView);
        receiptPriceTextView = findViewById(R.id.receiptPriceTextView);
        receiptTotalCostTextView = findViewById(R.id.receiptTotalCostTextView);
        doneButton = findViewById(R.id.doneButton);

        // Firebase database reference for bookings
        bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");

        // Retrieve booking ID from Intent
        Intent intent = getIntent();
        String bookingId = intent.getStringExtra("bookingId");

        if (bookingId != null) {
            fetchBookingDetails(bookingId);
        } else {
            Toast.makeText(this, "No booking ID found!", Toast.LENGTH_SHORT).show();
        }

        // Handle Done button click
        doneButton.setOnClickListener(v -> {
            Intent homeIntent = new Intent(ReceiptActivity.this, HomepageActivity.class);
            startActivity(intent);
        });
    }

    private void fetchBookingDetails(String bookingId) {
        // Fetch booking details from Firebase
        bookingsRef.child(bookingId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Parse the booking data
                    Booking booking = snapshot.getValue(Booking.class);

                    if (booking != null) {
                        displayBookingDetails(booking);
                    } else {
                        Toast.makeText(ReceiptActivity.this, "Failed to load booking details!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ReceiptActivity.this, "Booking not found!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ReceiptActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBookingDetails(Booking booking) {
        // Populate the TextViews with the booking data
        receiptTrainNameTextView.setText("Train Name: " + booking.getTrainName());
        receiptDepartureTimeTextView.setText("Departure Time: " + booking.getDepartureTime());
        receiptArrivalTimeTextView.setText("Arrival Time: " + booking.getArrivalTime());
        receiptDateTextView.setText("Date: " + booking.getDate());
        receiptPassengerNameTextView.setText("Passenger Name: " + booking.getPassengerName());
        receiptNumberOfPassengersTextView.setText("Number of Passengers: " + booking.getNumberOfPassengers());
        receiptPriceTextView.setText("Price per Seat: $" + booking.getPricePerSeat());
        receiptTotalCostTextView.setText("Total Cost: $" + booking.getTotalCost());
    }
}
