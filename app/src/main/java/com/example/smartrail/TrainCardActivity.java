package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class TrainCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_train_card);

        // Find the Book Now button
        Button bookNowButton = findViewById(R.id.bookNowButton);

        // Set OnClickListener to navigate to BookingActivity
        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrainCardActivity.this, BookingActivity.class);
                startActivity(intent);
            }
        });
    }
}
