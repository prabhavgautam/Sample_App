package com.example.android.myapplication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class userDetails extends AppCompatActivity {

    TextView userFirstName, userLastName, userEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        userEmail = findViewById(R.id.userDetails_email);
       Intent intent = getIntent();
       String value = intent.getStringExtra("email");
       userEmail.setText(intent.getStringExtra("email"));



        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




        userFirstName = findViewById(R.id.userDetails_firstName);
        userLastName = findViewById(R.id.userDetails_lastName);


        CollectionReference collectionReference = fStore.collection("users");
        Query query = collectionReference.whereEqualTo("email", value
        );

        fStore.collection("users")
                .whereEqualTo("email", value)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        userFirstName.setText(document.getString("fName"));
                        userLastName.setText(document.getString("lName"));
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });


    }
}