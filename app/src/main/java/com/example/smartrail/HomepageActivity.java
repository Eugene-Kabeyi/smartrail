package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomepageActivity extends AppCompatActivity {

    private String userId; // Declare userId variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Retrieve userId passed from LoginActivity
        userId = getIntent().getStringExtra("userId");

        // Check if userId is null
        if (TextUtils.isEmpty(userId)) {
            // If userId is null, show an error message and navigate to LoginActivity
            Toast.makeText(this, "Error: User not found. Please log in again.", Toast.LENGTH_SHORT).show();

            // Redirect to LoginActivity
            Intent loginIntent = new Intent(HomepageActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish(); // Close the current activity so user cannot go back to it
            return; // Stop further execution of onCreate()
        }

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


        // Profile Card
        CardView profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileActivity and pass the userId
                Intent intent = new Intent(HomepageActivity.this, ProfileActivity.class);
                intent.putExtra("userId", userId); // Pass userId to ProfileActivity
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
