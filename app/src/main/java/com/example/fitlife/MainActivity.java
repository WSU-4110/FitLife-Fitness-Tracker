
package com.example.fitlife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

//The Home page of the application. This is used as a way to get to the other activities not supposed to have much other functionality besides that
public class MainActivity extends AppCompatActivity {

    Button social, fit;
    TextView userName;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userName = findViewById(R.id.userP);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String First = snapshot.child("FirstName").getValue(String.class);
                userName.setText(First);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        social = findViewById(R.id.social);
        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), socialMainPage.class));
                finish();
            }
        });

        fit = findViewById(R.id.fit);
        fit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), fitnessMain.class));
                finish();
            }
        });



    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//Log out of User
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();

    }
}