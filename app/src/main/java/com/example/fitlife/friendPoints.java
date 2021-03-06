package com.example.fitlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class friendPoints extends AppCompatActivity {
    Button home;
    TextView first, last, email, user, points;
    CircleImageView profileImage;
    FirebaseAuth fAuth;
    DatabaseReference reference, userRef, friendsRef;
    FirebaseDatabase fData;
    StorageReference storageReference;
    String sendUserId, recieverUserId, currentState, saveCurrentDate;

    //Page to display differnt users and their points. Works similar to the user profile page.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_points);
        recieverUserId = getIntent().getExtras().get("user_id").toString();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        IntializeFields();

        storageReference = FirebaseStorage.getInstance().getReference().child("users/"+recieverUserId+"/profile.jpg");

        //Retrieving the Clicked person Information From Firebase
        userRef.child(recieverUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.setText(snapshot.child("Username").getValue(String.class));
                first.setText(snapshot.child("FirstName").getValue(String.class));
                last.setText(snapshot.child("LastName").getValue(String.class));
                email.setText(snapshot.child("Email").getValue(String.class));
                String x = snapshot.child("Points").getValue().toString();
                points.setText(x);
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImage);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        home = findViewById(R.id.homeBtn11);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });



    }

    //Initialize fields for the global variables. Just to make code cleaner.
    private void IntializeFields() {
        first= findViewById(R.id.firstName2);
        last = findViewById(R.id.LastName2);
        email = findViewById(R.id.email2);
        user = findViewById(R.id.myUser2);
        points = findViewById(R.id.friendpoints);
        profileImage = findViewById(R.id.imageView2);
    }
}