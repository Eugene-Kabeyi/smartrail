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
                // Navigate to TrainListActivity
                Intent intent = new Intent(HomepageActivity.this, TrainListActivity.class);
                startActivity(intent);
            }
        });

        // My Bookings Card
        CardView myBookingsCard = findViewById(R.id.myBookingsCard);
        myBookingsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MyBookingsActivity
                Intent intent = new Intent(HomepageActivity.this, MyBookingsActivity.class);
                startActivity(intent);
            }
        });

        // Profile Card
        CardView profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileActivity
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        // Admin Panel Card
        CardView adminPanelCard = findViewById(R.id.adminPanelCard);
        adminPanelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to AdminPanelActivity
                Intent intent = new Intent(HomepageActivity.this, AdminPanelActivity.class);
                startActivity(intent);
            }
        });
    }
}
