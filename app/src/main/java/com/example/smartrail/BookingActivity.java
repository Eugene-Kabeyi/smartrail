package com.example.smartrail;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class BookingActivity extends AppCompatActivity {

    private Button confirmBookingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        confirmBookingButton = findViewById(R.id.confirmBookingButton);

        // Set an onClick listener for the Confirm Booking button
        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Receipt Activity
                Intent intent = new Intent(BookingActivity.this, ReceiptActivity.class);
                startActivity(intent);
            }
        });
    }
}
