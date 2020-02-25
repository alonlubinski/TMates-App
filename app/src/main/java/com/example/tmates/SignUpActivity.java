package com.example.tmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, passwordEditText2;
    private Button signupBtn;
    private String email, password, password2;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText2 = findViewById(R.id.password2EditText);
        signupBtn = findViewById(R.id.signup2Btn);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailEditText.getText().toString();
                password = passwordEditText.getText().toString();
                password2 = passwordEditText2.getText().toString();
                if(checkFormValidation(password, password2)){
                        signUp(email, password);
                        progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //Toast.makeText(SignUpActivity.this,"YES!", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.

                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(SignUpActivity.this,task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public boolean checkFormValidation(String password, String password2){
        if(password.compareTo(password2) != 0){
            Toast.makeText(SignUpActivity.this,"Passwords don't match.", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
