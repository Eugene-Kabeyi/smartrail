package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // Set the layout for login

        // Find the login button by its ID
        Button loginButton = findViewById(R.id.button);
        TextView signUpLink = findViewById(R.id.signUpLink); // Find the "Sign Up" link

        // Set an onClickListener to navigate to HomepageActivity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to HomepageActivity
                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish(); // Close LoginActivity so user can't go back to it
            }
        });

        // Set an onClickListener to navigate to SignupActivity when the user clicks "Don't have an account? Sign Up"
        signUpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignupActivity
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
