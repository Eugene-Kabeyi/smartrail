package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingActivity extends AppCompatActivity {

    private TextView trainNameTextView;
    private TextView departureTimeTextView;
    private TextView arrivalTimeTextView;
    private TextView dateTextView;
    private TextView priceTextView;
    private ImageView trainImageView;
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
        trainImageView = findViewById(R.id.trainImageView);
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
        String imageUrl = intent.getStringExtra("imageUrl");

        // Populate UI with train data
        trainNameTextView.setText(trainName);
        departureTimeTextView.setText("Departure: " + departureTime);
        arrivalTimeTextView.setText("Arrival: " + arrivalTime);
        dateTextView.setText("Date: " + date);
        priceTextView.setText("Price per Seat: " + price);

        // Load the train image using Glide
        Glide.with(this)
                .load(imageUrl)
                .into(trainImageView);

        // Handle Confirm Booking button click
        confirmBookingButton.setOnClickListener(v -> {
            String passengerName = passengerNameEditText.getText().toString().trim();
            String numberOfPassengersStr = numberOfPassengersEditText.getText().toString().trim();

            if (!passengerName.isEmpty() && !numberOfPassengersStr.isEmpty()) {
                int numberOfPassengers = Integer.parseInt(numberOfPassengersStr);
                double costPerSeat = Double.parseDouble(price);
                double totalCost = numberOfPassengers * costPerSeat; // Calculate total cost

                // Save booking data to Firebase
                saveBookingToFirebase(trainName, departureTime, arrivalTime, date, price, passengerName, numberOfPassengers, totalCost);
            } else {
                // Show an error message if fields are empty
                Toast.makeText(BookingActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveBookingToFirebase(String trainName, String departureTime, String arrivalTime, String date, String price,
                                       String passengerName, int numberOfPassengers, double totalCost) {
        // Get a reference to the Firebase database
        DatabaseReference bookingsRef = FirebaseDatabase.getInstance().getReference("bookings");

        // Create a unique ID for the booking
        String bookingId = bookingsRef.push().getKey();

        // Create a booking object
        Booking booking = new Booking(bookingId, trainName, departureTime, arrivalTime, date, price, passengerName, numberOfPassengers, totalCost);

        // Save the booking object to Firebase
        bookingsRef.child(bookingId).setValue(booking).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                // Show a success message
                Toast.makeText(BookingActivity.this, "Booking confirmed!", Toast.LENGTH_SHORT).show();

                // Navigate to ReceiptActivity
                Intent intent = new Intent(BookingActivity.this, ReceiptActivity.class);
                intent.putExtra("bookingId", bookingId);
                intent.putExtra("trainName", trainName);
                intent.putExtra("departureTime", departureTime);
                intent.putExtra("arrivalTime", arrivalTime);
                intent.putExtra("date", date);
                intent.putExtra("price", price);
                intent.putExtra("passengerName", passengerName);
                intent.putExtra("numberOfPassengers", String.valueOf(numberOfPassengers));
                intent.putExtra("totalCost", totalCost);
                startActivity(intent);

                // Optionally finish this activity
                finish();
            } else {
                // Show an error message
                Toast.makeText(BookingActivity.this, "Failed to confirm booking. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
