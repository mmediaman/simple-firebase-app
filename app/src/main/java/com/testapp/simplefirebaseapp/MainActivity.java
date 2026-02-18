package com.testapp.simplefirebaseapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView counterTextView;
    private Button incrementButton;
    private Button saveButton;
    private int counter = 0;
    
    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        db = FirebaseFirestore.getInstance();

        // Initialize views
        counterTextView = findViewById(R.id.counterTextView);
        incrementButton = findViewById(R.id.incrementButton);
        saveButton = findViewById(R.id.saveButton);

        // Log app open event
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.SCREEN_NAME, "MainActivity");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle);

        // Increment button listener
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                counterTextView.setText(String.valueOf(counter));
                
                // Log increment event
                Bundle eventBundle = new Bundle();
                eventBundle.putInt("counter_value", counter);
                mFirebaseAnalytics.logEvent("counter_incremented", eventBundle);
            }
        });

        // Save to Firestore button listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCounterToFirestore();
            }
        });
    }

    private void saveCounterToFirestore() {
        Map<String, Object> counterData = new HashMap<>();
        counterData.put("value", counter);
        counterData.put("timestamp", System.currentTimeMillis());

        db.collection("counters")
            .add(counterData)
            .addOnSuccessListener(documentReference -> {
                // Log save event
                Bundle bundle = new Bundle();
                bundle.putInt("saved_value", counter);
                mFirebaseAnalytics.logEvent("counter_saved", bundle);
                
                counterTextView.setText("Saved: " + counter);
            })
            .addOnFailureListener(e -> {
                counterTextView.setText("Error: " + e.getMessage());
            });
    }
}
