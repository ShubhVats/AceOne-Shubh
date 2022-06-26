package com.ace.services.one.capital;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ace.services.one.capital.Models.Users_Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    // XML Components
    private TextView help, complete_kyc, name, course, institute, phone;

    private String userId = "";
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Link XML Components
        help = findViewById(R.id.help);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        course = findViewById(R.id.course);
        institute = findViewById(R.id.institute);
        complete_kyc = findViewById(R.id.complete_kyc);

        // Fetching UserID from Login Activity
        userId = getIntent().getStringExtra("UserID");

        // Firebase initialization
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Users");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                DataSnapshot value = dataSnapshot.child(userId);
                Users_Model userData = value.getValue(Users_Model.class);

                // Set text to UI
                name.setText(userData.getName());
                phone.setText(userData.getPhone());
                course.setText(userData.getCourse());
                institute.setText(userData.getInstitute());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}