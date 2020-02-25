package com.example.tmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEdit, passwordEdit;
    private Button loginBtn, signupBtn;
    private FirebaseAuth mAuth;
    private String email, password;
    private FirebaseFirestore db;
    private ProgressBar progressBar;
    private CheckBox saveLoginCheckBox;
    private SharedPreferences sharedPreferences;
    private Boolean saveLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);

        emailEdit = findViewById(R.id.editEmail);
        passwordEdit = findViewById(R.id.editPassword);

        progressBar = findViewById(R.id.progressBar1);
        progressBar.setVisibility(View.GONE);

        saveLoginCheckBox = findViewById(R.id.saveLoginCheckBox);

        sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        saveLogin = sharedPreferences.getBoolean("saveLogin", false);
        if(saveLogin == true){
            emailEdit.setText(sharedPreferences.getString("email", ""));
            passwordEdit.setText(sharedPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
        }

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEdit.getText().toString();
                password = passwordEdit.getText().toString();
                signIn(email, password);
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignUpActivity();
            }
        });
    }


    public void startHomePageActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void startSignUpActivity(){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void startCreateProfileActivity(){
        Intent intent = new Intent(this, CreateProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void signIn(final String email, final String password){
        if(checkFormValidation(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                System.out.println("Logged in!");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                if(saveLoginCheckBox.isChecked()){
                                    editor.putBoolean("saveLogin", true);
                                    editor.putString("email", email);
                                    editor.putString("password", password);
                                    editor.commit();
                                } else {
                                    editor.clear();
                                    editor.commit();
                                }
                                DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());
                                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        if(documentSnapshot.exists()){
                                            startHomePageActivity();
                                        } else {
                                            startCreateProfileActivity();
                                        }
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this,"Login failed!", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
    }

    public boolean checkFormValidation(String email, String password){
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        if(!pattern.matcher(email).matches() && password.length() < 6){
            emailEdit.setError("Wrong email pattern");
            passwordEdit.setError("Password must contain 6 characters");
            return false;
        } else if(!pattern.matcher(email).matches()){
            emailEdit.setError("Wrong email pattern");
            return false;
        } else if(password.length() < 6){
            passwordEdit.setError("Password must contain 6 characters");
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
        saveLogin = sharedPreferences.getBoolean("saveLogin", false);
        if(saveLogin == false){
            emailEdit.setText("");
            passwordEdit.setText("");
        }
    }
}
