package com.example.smartrail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AdminPanelActivity extends AppCompatActivity {

    private EditText trainNameEditText, departureTimeEditText, arrivalTimeEditText,
            dateEditText, seatsEditText, priceEditText;
    private Button uploadImageButton, saveTrainInfoButton;
    private Uri imageUri;
    private FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        trainNameEditText = findViewById(R.id.trainNameEditText);
        departureTimeEditText = findViewById(R.id.departureTimeEditText);
        arrivalTimeEditText = findViewById(R.id.arrivalTimeEditText);
        dateEditText = findViewById(R.id.dateEditText);
        seatsEditText = findViewById(R.id.seatsEditText);
        priceEditText = findViewById(R.id.priceEditText);
        uploadImageButton = findViewById(R.id.uploadImageButton);
        saveTrainInfoButton = findViewById(R.id.saveTrainInfoButton);

        firebaseHelper = new FirebaseHelper();

        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery to select image
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });

        saveTrainInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrainInfo();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Toast.makeText(this, "Image selected successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTrainInfo() {
        String trainName = trainNameEditText.getText().toString();
        String departureTime = departureTimeEditText.getText().toString();
        String arrivalTime = arrivalTimeEditText.getText().toString();
        String date = dateEditText.getText().toString();
        String seats = seatsEditText.getText().toString();
        String price = priceEditText.getText().toString();

        if (trainName.isEmpty() || departureTime.isEmpty() || arrivalTime.isEmpty() ||
                date.isEmpty() || seats.isEmpty() || price.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all fields and upload an image!", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseHelper.uploadTrainImage(imageUri, new FirebaseHelper.UploadCallback() {
            @Override
            public void onUploadSuccess(String imageUrl) {
                Train train = new Train(trainName, departureTime, arrivalTime, date, seats, price, imageUrl);
                firebaseHelper.saveTrainData(train);
                Toast.makeText(AdminPanelActivity.this, "Train information saved successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUploadFailure(Exception e) {
                Toast.makeText(AdminPanelActivity.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
