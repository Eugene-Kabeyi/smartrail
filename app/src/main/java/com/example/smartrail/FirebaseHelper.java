package com.example.smartrail;

import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FirebaseHelper {

    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    public FirebaseHelper() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = firebaseDatabase.getReference("trains");
        storageReference = firebaseStorage.getReference("train_images");
    }

    public interface UploadCallback {
        void onUploadSuccess(String imageUrl);
        void onUploadFailure(Exception e);
    }

    public void uploadTrainImage(Uri imageUri, final UploadCallback callback) {
        StorageReference imageRef = storageReference.child(imageUri.getLastPathSegment());

        // Start uploading the file to Firebase Storage
        imageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL after upload success
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                callback.onUploadSuccess(uri.toString());
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                callback.onUploadFailure(e);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onUploadFailure(e);
                    }
                });
    }

    public void saveTrainData(Train train) {
        String trainId = databaseReference.push().getKey();
        if (trainId != null) {
            databaseReference.child(trainId).setValue(train);
        }
    }
}
