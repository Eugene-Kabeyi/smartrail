package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private TextView forgotPasswordLink, signUpLink;

    // Firebase Realtime Database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Realtime Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Find the views by ID
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.button);
        forgotPasswordLink = findViewById(R.id.forgotPasswordLink);
        signUpLink = findViewById(R.id.signUpLink);

        // Set onClickListener for login button
        loginButton.setOnClickListener(v -> loginUser());

        // Set onClickListener for "Sign Up" link
        signUpLink.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for "Forgot Password" link
        forgotPasswordLink.setOnClickListener(v ->
                Toast.makeText(LoginActivity.this, "Forgot Password Clicked", Toast.LENGTH_SHORT).show()
        );
    }

    private void loginUser() {
        String email = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate email and password
        if (TextUtils.isEmpty(email)) {
            usernameEditText.setError("Email is required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required.");
            return;
        }

        // Retrieve data from Firebase Realtime Database
        databaseReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        String userId = userSnapshot.getKey(); // Get the user ID
                        User user = userSnapshot.getValue(User.class);

                        if (user != null && user.password.equals(password)) {
                            // Login successful
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            // Pass userId to HomepageActivity
                            Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                            intent.putExtra("userId", userId); // Pass the userId
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }
                    // Incorrect password
                    passwordEditText.setError("Invalid password.");
                    Toast.makeText(LoginActivity.this, "Login Failed: Invalid credentials", Toast.LENGTH_SHORT).show();
                } else {
                    // Email not found
                    usernameEditText.setError("Email not registered.");
                    Toast.makeText(LoginActivity.this, "Login Failed: Email not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("LoginError", "Database error: " + databaseError.getMessage());
                Toast.makeText(LoginActivity.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // User model class
    public static class User {
        public String fullName;
        public String email;
        public String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String fullName, String email, String password) {
            this.fullName = fullName;
            this.email = email;
            this.password = password;
        }
    }
}
