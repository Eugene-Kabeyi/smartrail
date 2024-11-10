package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiptActivity extends AppCompatActivity {

    private Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        backButton = findViewById(R.id.backButton);

        // Set an onClick listener for the Back to Home button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the Homepage Activity
                Intent intent = new Intent(ReceiptActivity.this, HomepageActivity.class);
                startActivity(intent);
                finish(); // Optionally finish the current activity to remove it from the back stack
            }
        });
    }
}
