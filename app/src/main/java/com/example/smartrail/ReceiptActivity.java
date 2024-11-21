package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    private TextView receiptTrainNameTextView;
    private TextView receiptDepartureTimeTextView;
    private TextView receiptArrivalTimeTextView;
    private TextView receiptDateTextView;
    private TextView receiptPassengerNameTextView;
    private TextView receiptNumberOfPassengersTextView;
    private TextView receiptPriceTextView;
    private TextView receiptTotalCostTextView;
    private Button doneButton; // Added Done button

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
        doneButton = findViewById(R.id.doneButton); // Initialize Done button

        // Retrieve booking data from Intent
        Intent intent = getIntent();

        if (intent != null) {
            String trainName = intent.getStringExtra("trainName");
            String departureTime = intent.getStringExtra("departureTime");
            String arrivalTime = intent.getStringExtra("arrivalTime");
            String date = intent.getStringExtra("date");
            String price = intent.getStringExtra("price");
            String passengerName = intent.getStringExtra("passengerName");
            String numberOfPassengers = intent.getStringExtra("numberOfPassengers");

            // Calculate total cost
            double pricePerSeat = Double.parseDouble(price);
            int numberOfSeats = Integer.parseInt(numberOfPassengers);
            double totalCost = pricePerSeat * numberOfSeats;

            // Display data in the UI
            receiptTrainNameTextView.setText("Train Name: " + trainName);
            receiptDepartureTimeTextView.setText("Departure Time: " + departureTime);
            receiptArrivalTimeTextView.setText("Arrival Time: " + arrivalTime);
            receiptDateTextView.setText("Date: " + date);
            receiptPassengerNameTextView.setText("Passenger Name: " + passengerName);
            receiptNumberOfPassengersTextView.setText("Number of Passengers: " + numberOfPassengers);
            receiptPriceTextView.setText("Price per Seat: $" + price);
            receiptTotalCostTextView.setText("Total Cost: $" + totalCost);
        } else {
            // Handle error if Intent is null
            Toast.makeText(this, "No booking details found!", Toast.LENGTH_SHORT).show();
        }

        // Handle Done button click
        doneButton.setOnClickListener(v -> {
            // Navigate back to the homepage (MainActivity)
            Intent homeIntent = new Intent(ReceiptActivity.this, HomepageActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(homeIntent);
            finish(); // Close the ReceiptActivity
        });
    }
}
