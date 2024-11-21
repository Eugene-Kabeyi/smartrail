package com.example.smartrail;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText trainNameEditText, departureTimeEditText, arrivalTimeEditText,
            dateEditText, seatsEditText, priceEditText;
    private Button saveTrainInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        // Find the UI elements by ID
        trainNameEditText = findViewById(R.id.trainNameEditText);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        arrivalTimeEditText = findViewById(R.id.arrivalTimeEditText);
        dateEditText = findViewById(R.id.dateEditText);
        seatsEditText = findViewById(R.id.seatsEditText);
        priceEditText = findViewById(R.id.priceEditText);
        saveTrainInfoButton = findViewById(R.id.saveTrainInfoButton);

        // Set the onClickListener for the save button
        saveTrainInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrainInfo();
            }
        });
    }

    private void saveTrainInfo() {
        // Get the text from the EditText fields
        String trainName = trainNameEditText.getText().toString().trim();
        String departureTime = departureTimeEditText.getText().toString().trim();
        String arrivalTime = arrivalTimeEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String seats = seatsEditText.getText().toString().trim();
        String price = priceEditText.getText().toString().trim();

        // Check if any field is empty
        if (trainName.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty() ||
                date.isEmpty() || seats.isEmpty() || price.isEmpty()) {
            Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a Train object with the input data
        Train train = new Train(trainName, departureTime, arrivalTime, date, seats, price);

        // Get a reference to the Firebase Realtime Database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("trains");

        // Generate a unique key for the new train entry
        String trainId = databaseReference.push().getKey();

        if (trainId != null) {
            // Save the train data in Firebase under the generated trainId
            databaseReference.child(trainId).setValue(train)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(AdminPanelActivity.this, "Train information saved successfully!", Toast.LENGTH_SHORT).show();
                            clearFields();  // Clear input fields after saving
                        } else {
                            Toast.makeText(AdminPanelActivity.this, "Failed to save train information!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // Clear all input fields after saving
    private void clearFields() {
        trainNameEditText.setText("");
        departureTimeEditText.setText("");
        arrivalTimeEditText.setText("");
        dateEditText.setText("");
        seatsEditText.setText("");
        priceEditText.setText("");
    }
}
