package com.example.android.myapplication;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    TextView tvFirstName, tvLastName, tvEmail;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String  userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fAuth = FirebaseAuth.getInstance();
       fStore = FirebaseFirestore.getInstance();



        FirebaseUser currentUser = fAuth.getCurrentUser();

        if (currentUser == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        userId = fAuth.getCurrentUser().getUid();

        tvFirstName = findViewById(R.id.tvFirstName);
        tvLastName = findViewById(R.id.tvLastName);
        tvEmail = findViewById(R.id.tvEmail);

        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                tvFirstName.setText("First Name: " + documentSnapshot.getString("fName"));
                tvLastName.setText("Last Name: " + documentSnapshot.getString("lName"));
                tvEmail.setText("Email: " + documentSnapshot.getString("email"));
            }
        });

      /*  FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("users").child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user != null){
                    tvFirstName.setText("First Name: " + user.firstName);
                    tvLastName.setText("Last Name: " + user.lastName);
                    tvEmail.setText("Email: " + user.email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/





        Button btnUsers = findViewById(R.id.btnUsers);
        btnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });

        Button btnImages = findViewById(R.id.activity_main_btnImage);
        btnImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImages();
            }
        });


        Button btnViewPager = findViewById(R.id.activity_main_btnViewPager);
        btnViewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getViewPager();
            }
        });

       @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button btnHorizontal = findViewById(R.id.activity_main_btnView);
       btnHorizontal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               getHorizontalView();
           }
       });



    }

    private void getImages() {
        Intent intent = new Intent(this,ImageActivity.class);
        startActivity(intent);
        finish();
    }

    private void getUsers() {
        Intent intent = new Intent(this, ViewRecycler.class);
        startActivity(intent);
        finish();
    }

    private void logoutUser(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }


    private void getViewPager(){
        Intent intent = new Intent(this,ViewActivity.class);
        startActivity(intent);
        finish();
    }

    private void getHorizontalView(){
        Intent intent  = new Intent(this, HorizontalViewRecycler.class);
        startActivity(intent);
        finish();
    }



}