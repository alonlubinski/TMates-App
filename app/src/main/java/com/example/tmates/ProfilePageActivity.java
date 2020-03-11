package com.example.tmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;



public class ProfilePageActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private TextView nameText, ageText, genderText, cityText, userDescriptionText;
    private TableLayout sportTable;
    private ImageView profileUserImage;
    private String userId;
    private ImageButton editProfileButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        nameText = findViewById(R.id.userName);
        ageText = findViewById(R.id.userAgeStr);
        genderText = findViewById(R.id.userGenderStr);
        cityText = findViewById(R.id.userCityStr);
        userDescriptionText = findViewById(R.id.userDescription);
        sportTable = findViewById(R.id.sportsTable);
        profileUserImage = findViewById(R.id.profileUserImage);
        editProfileButton = findViewById(R.id.editProfileBtn);

        editProfileButton.setOnClickListener(this);

        if(getIntent().getExtras() != null){
            userId = getIntent().getExtras().getString("otherUserId");
            editProfileButton.setVisibility(View.INVISIBLE);
        } else {
            userId = mAuth.getCurrentUser().getUid();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        sportTable.removeAllViews();
        TableRow tableRow = new TableRow(sportTable.getContext());
        TextView sport = new TextView(tableRow.getContext());
        sport.setText("Sports");
        sport.setPadding(30, 30, 30, 30);
        sport.setTextSize(20);
        sport.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        sport.setTextColor(getResources().getColor(R.color.white));
        TextView exp = new TextView(getApplicationContext());
        exp.setText("Experience");
        exp.setPadding(30, 30, 30, 30);
        exp.setTextSize(20);
        exp.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        exp.setTextColor(getResources().getColor(R.color.white));
        tableRow.addView(sport);
        tableRow.addView(exp);
        sportTable.addView(tableRow);
        updateUI();
    }

    private void updateUI() {
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        User user = documentSnapshot.toObject(User.class);
                        nameText.setText("" + user.getName());
                        ageText.setText("" + user.getAge());
                        genderText.setText("" + user.getGender());
                        cityText.setText("" + user.getCity());
                        userDescriptionText.setText(user.getDescription());
                        try {
                            getProfileImage(userId);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        if(!user.getSportsMap().isEmpty()){
                            for(int i=0; i < user.getSportsMap().size(); i++){
                                TableRow tableRow = new TableRow(sportTable.getContext());
                                TextView sport = new TextView(tableRow.getContext());
                                sport.setText(new ArrayList<String>(user.getSportsMap().keySet()).get(i));
                                sport.setPadding(30, 30, 30, 30);
                                sport.setTextSize(20);
                                sport.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
                                sport.setTextColor(getResources().getColor(R.color.white));
                                TextView exp = new TextView(getApplicationContext());
                                exp.setText(new ArrayList<String>(user.getSportsMap().values()).get(i));
                                exp.setPadding(30, 30, 30, 30);
                                exp.setTextSize(20);
                                exp.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
                                exp.setTextColor(getResources().getColor(R.color.white));
                                tableRow.addView(sport);
                                tableRow.addView(exp);
                                sportTable.addView(tableRow);
                            }
                        }
                    }
                }
            }
        });
    }

    private void getProfileImage(String id) throws IOException {
        String prefix = id;
        final File localFile = File.createTempFile(prefix, "");
        StorageReference userImage = mStorageRef.child("users_images/" + prefix);
        userImage.getFile(localFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        // Successfully downloaded data to local file
                        Glide.with(getApplicationContext()).load(localFile).circleCrop().into(profileUserImage);
                       // Picasso.with(getApplicationContext()).load(localFile).into(profileUserImage);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle failed download
                Glide.with(getApplicationContext()).load(R.drawable.profile_png).into(profileUserImage);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.editProfileBtn:
                startEditProfileActivity();
                break;
        }
    }

    public void startEditProfileActivity(){
        Intent intent = new Intent(ProfilePageActivity.this, CreateProfileActivity.class);
        intent.putExtra("edit", true);
        startActivity(intent);
    }
}
