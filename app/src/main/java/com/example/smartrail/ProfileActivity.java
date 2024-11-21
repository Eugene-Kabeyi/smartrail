package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileNameTextView, profileEmailTextView;
    private Button logoutButton;

    // Firebase Realtime Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile); // Set the profile activity layout

        // Find the UI elements by ID
        profileNameTextView = findViewById(R.id.profileNameTextView);
        profileEmailTextView = findViewById(R.id.profileEmailTextView);
        logoutButton = findViewById(R.id.logoutButton);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Retrieve user ID passed from the login activity or a session manager
        String userId = getIntent().getStringExtra("userId");

        if (userId != null) {
            // Fetch user data from Firebase
            fetchUserData(userId);
        } else {
            Toast.makeText(this, "No user ID found. Please log in again.", Toast.LENGTH_SHORT).show();
            redirectToLogin();
        }

        // Set the OnClickListener for the logout button
        logoutButton.setOnClickListener(v -> {
            // Log out the user and redirect to LoginActivity
            redirectToLogin();
        });
    }

    private void fetchUserData(String userId) {
        // Fetch the user data from Firebase Realtime Database using the userId
        databaseReference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Map the data snapshot to the User model
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        profileNameTextView.setText(user.fullName); // Set the full name in the UI
                        profileEmailTextView.setText(user.email);   // Set the email in the UI
                    } else {
                        Toast.makeText(ProfileActivity.this, "Failed to load user data.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ProfileActivity.this, "User not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ProfileActivity", "Database error: " + databaseError.getMessage());
                Toast.makeText(ProfileActivity.this, "Failed to retrieve user data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToLogin() {
        // Navigate back to LoginActivity
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the ProfileActivity
    }

    // User model class
    public static class User {
        public String fullName;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String fullName, String email) {
            this.fullName = fullName;
            this.email = email;
        }
    }
}
