package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileNameTextView, profileEmailTextView;
    private Button logoutButton;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the profile activity layout

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        // Find the UI elements by ID
        profileNameTextView = findViewById(R.id.profileNameTextView);
        profileEmailTextView = findViewById(R.id.profileEmailTextView);
        logoutButton = findViewById(R.id.logoutButton);

        // Check if the user is logged in
        if (currentUser != null) {
            // Set the user's profile information
            profileNameTextView.setText(currentUser.getDisplayName() != null ? currentUser.getDisplayName() : "No name provided");
            profileEmailTextView.setText(currentUser.getEmail());
        } else {
            Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
        }

        // Set the OnClickListener for the logout button
        logoutButton.setOnClickListener(v -> {
            // Log out the user
            mAuth.signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class); // Redirect to LoginActivity
            startActivity(intent);
            finish(); // Close the ProfileActivity
        });
    }
}


