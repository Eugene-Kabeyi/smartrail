package com.example.smartrail;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingActivity extends AppCompatActivity {

    private TextView trainNameTextView;
    private TextView departureTimeTextView;
    private TextView arrivalTimeTextView;
    private TextView dateTextView;
    private TextView priceTextView;
    private EditText passengerNameEditText;
    private EditText numberOfPassengersEditText;
    private Button confirmBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        // Initialize views
        trainNameTextView = findViewById(R.id.trainNameTextView);
        departureTimeTextView = findViewById(R.id.departureTimeTextView);
        arrivalTimeTextView = findViewById(R.id.arrivalTimeTextView);
        dateTextView = findViewById(R.id.dateTextView);
        priceTextView = findViewById(R.id.priceTextView);
        passengerNameEditText = findViewById(R.id.passengerNameEditText);
        numberOfPassengersEditText = findViewById(R.id.numberofPassengersEditText);
        confirmBookingButton = findViewById(R.id.confirmBookingButton);

        // Retrieve train data from intent
        Intent intent = getIntent();
        String trainName = intent.getStringExtra("trainName");
        String departureTime = intent.getStringExtra("departureTime");
        String arrivalTime = intent.getStringExtra("arrivalTime");
        String date = intent.getStringExtra("date");
        String price = intent.getStringExtra("price");

        // Populate UI with train data
        trainNameTextView.setText(trainName);
        departureTimeTextView.setText("Departure: " + departureTime);
        arrivalTimeTextView.setText("Arrival: " + arrivalTime);
        dateTextView.setText("Date: " + date);
        priceTextView.setText("Price per Seat: Ksh" + price);

        // Handle Confirm Booking button click
        confirmBookingButton.setOnClickListener(v -> {
            String passengerName = passengerNameEditText.getText().toString().trim();
            String numberOfPassengersStr = numberOfPassengersEditText.getText().toString().trim();

            if (!passengerName.isEmpty() && !numberOfPassengersStr.isEmpty()) {
                int numberOfPassengers = Integer.parseInt(numberOfPassengersStr);
                double costPerSeat = Double.parseDouble(price);
                double totalCost = numberOfPassengers * costPerSeat; // Calculate total cost

                // Show a confirmation dialog
                new AlertDialog.Builder(BookingActivity.this)
                        .setTitle("Confirm Booking")
                        .setMessage("Do you want to finalize this booking?")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Save booking to Firebase
                            saveBookingToFirebase(trainName, departureTime, arrivalTime, date, price, passengerName, numberOfPassengers, totalCost);
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            } else {
                Toast.makeText(BookingActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveBookingToFirebase(String trainName, String departureTime, String arrivalTime, String date, String price,
                                       String passengerName, int numberOfPassengers, double totalCost) {
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");
        String bookingId = bookingsRef.push().getKey();
        Booking booking = new Booking(bookingId, trainName, departureTime, arrivalTime, date, price, passengerName, numberOfPassengers, totalCost);

        bookingsRef.child(bookingId).setValue(booking).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(BookingActivity.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();

                // Navigate to ReceiptActivity
                Intent intent = new Intent(BookingActivity.this, ReceiptActivity.class);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("trainName", trainName);
                intent.putExtra("departureTime", departureTime);
                intent.putExtra("arrivalTime", arrivalTime);
                intent.putExtra("date", date);
                intent.putExtra("pricePerSeat", price);
                intent.putExtra("passengerName", passengerName);
                intent.putExtra("numberOfPassengers", numberOfPassengers);
                intent.putExtra("totalCost", totalCost);
                startActivity(intent);

                finish(); // Close the current activity
            } else {
                Toast.makeText(BookingActivity.this, "Failed to confirm booking. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

