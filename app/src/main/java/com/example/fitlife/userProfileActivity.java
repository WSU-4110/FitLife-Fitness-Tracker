package com.example.fitlife;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

//The Basic layout of the user home page. User can view their information and change their pfp, reset password and other unique things as well.
public class userProfileActivity extends AppCompatActivity {
    TextView first, last, email, user;
    FirebaseAuth fAuth;
    DatabaseReference reference;
    FirebaseDatabase fData;
    String userId;
    CircleImageView profileImage;
    Button changeImage, reset, main;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        first= findViewById(R.id.firstName);
        last = findViewById(R.id.LastName);
        email = findViewById(R.id.email);
        user = findViewById(R.id.myUser);

        profileImage = findViewById(R.id.imageView);
        profileImage.setImageResource(R.mipmap.ic_launcher);
        changeImage = findViewById(R.id.changePic);
        reset = findViewById(R.id.resetBtn);
        main = findViewById(R.id.homeBtn2);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        fAuth = FirebaseAuth.getInstance();
        fData = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        //Stores the profile image in a separate folder for the user
        StorageReference profileRef = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        userId = fAuth.getCurrentUser().getUid();



        reference = fData.getReference("Users").child(userId);
        //Shows the user their information
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user.setText(snapshot.child("Username").getValue(String.class));
                first.setText(snapshot.child("FirstName").getValue(String.class));
                last.setText(snapshot.child("LastName").getValue(String.class));
                email.setText(snapshot.child("Email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        changeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //User Will go to gallery and choose image
                //Open Gallery
                Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallery, 1000);
            }
        });

        //Reset Password link for the email and all
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText restMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Receive Reset Link");
                passwordResetDialog.setView(restMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail = restMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(userProfileActivity.this, "Reset Link Sent to Email", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(userProfileActivity.this, "Failed To Send Email " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //User Will Return Back to Login Menu
                    }
                });

                passwordResetDialog.create().show();

            }
        });

    }

    //changing the profile image of the user
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode == Activity.RESULT_OK){
                Uri content = data.getData();
                //profileImage.setImageURI(content);

                uploadImageToFirebase(content);
            }
        }
    }

    //This function upload the image that the user selected for their pfp to firestore
    private void uploadImageToFirebase(Uri content) {
        //Logic to Upload Image to Fire Base Storage
        StorageReference fileReference = storageReference.child("users/"+fAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileReference.putFile(content).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(userProfileActivity.this, "Profile Image Has Been Uploaded", Toast.LENGTH_SHORT).show();
                    final String downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    reference.child("ProfileImage").setValue(downloadUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(userProfileActivity.this, "Stored withing Database", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    }


    //A function that just logs the user out for the time being
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();//Log out of User
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();

    }
}