package com.example.smartrail;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class TrainListActivity extends AppCompatActivity implements TrainAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private TrainAdapter trainAdapter;
    private List<String> trainIds;  // List of train IDs from Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_list);

        recyclerView = findViewById(R.id.recyclerView);

        trainIds = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch train IDs from Firebase
        fetchTrainIdsFromFirebase();
    }

    private void fetchTrainIdsFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("trains");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Collect train IDs from Firebase
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    trainIds.add(snapshot.getKey());
                }

                // Initialize the adapter with the list of train IDs
                trainAdapter = new TrainAdapter(trainIds, TrainListActivity.this);
                recyclerView.setAdapter(trainAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(TrainListActivity.this, "Failed to load data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(String trainId) {
        // Handle the train item click
        Intent intent = new Intent(TrainListActivity.this, TrainCardActivity.class);
        intent.putExtra("trainId", trainId);  // Pass the selected trainId
        startActivity(intent);
    }
}
