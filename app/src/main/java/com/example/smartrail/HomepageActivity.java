package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Set up navigation for each card

        // View Trains Card
        CardView viewTrainsCard = findViewById(R.id.viewTrainsCard);
        viewTrainsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to item_train_card activity
                Intent intent = new Intent(HomepageActivity.this, TrainCardActivity.class);
                startActivity(intent);
            }
        });

        // My Bookings Card
        CardView myBookingsCard = findViewById(R.id.myBookingsCard);
        myBookingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to activity_my_bookings
                Intent intent = new Intent(HomepageActivity.this, MyBookingsActivity.class);
                startActivity(intent);
            }
        });

        // Profile Card
        CardView profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to activity_profile
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
