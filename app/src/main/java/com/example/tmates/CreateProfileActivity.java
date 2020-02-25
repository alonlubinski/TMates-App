package com.example.tmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import static com.example.tmates.R.id.maleCheckBox;

public class CreateProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private String name, gender, city, description;
    private int age;
    private AppCompatCheckBox maleCheckBox, femaleCheckBox;
    private EditText nameEditText, ageEditText, cityEditText, descriptionEditText;
    private Button continueBtn;
    private ArrayList<String> sportsArray = new ArrayList<>();
    private String[] sportsOptions = {"Soccer", "Basketball", "Football", "Volleyball", "Baseball", "Running", "Tennis", "TRX"};
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private TableLayout tableLayout;
    private TableRow soccerRow, basketballRow, footballRow, volleyballRow, baseballRow, runningRow, tennisRow;
    private ArrayList<TableRow> tableRowsArray = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        findById();
        setClickListeners();

    }

    public boolean checkFormValidation(){
            if(nameEditText.getText().toString().length() == 0){
                nameEditText.setError("Please enter name.");
                return false;
            }
            if(ageEditText.getText().toString().length() == 0){
                ageEditText.setError("Please enter age.");
                return false;
            }
            if(cityEditText.getText().toString().length() == 0){
                cityEditText.setError("Please enter city");
                return false;
            }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.maleCheckBox:
                if(femaleCheckBox.isChecked()){
                    femaleCheckBox.setChecked(false);
                    maleCheckBox.setChecked(true);
                }
                break;
            case R.id.femaleCheckBox:
                if(maleCheckBox.isChecked()){
                    maleCheckBox.setChecked(false);
                    femaleCheckBox.setChecked(true);
                }
                break;

            case R.id.continueBtn:
                if(checkFormValidation()){
                    name = nameEditText.getText().toString();
                    age = Integer.parseInt(ageEditText.getText().toString());
                    if(maleCheckBox.isChecked()){
                        gender = "Male";
                    } else{
                        gender = "Female";
                    }
                    city = cityEditText.getText().toString();
                    description = descriptionEditText.getText().toString();
                    sportsArray.clear();
                    for(int i = 0; i < tableRowsArray.size(); i++){
                        if(((ColorDrawable)tableRowsArray.get(i).getBackground()).getColor() == Color.GREEN){
                            sportsArray.add(((TextView)tableRowsArray.get(i).getChildAt(0)).getText().toString());
                        }
                    }
                    User newUser = new User(mAuth.getCurrentUser().getUid(), mAuth.getCurrentUser().getEmail(), name, gender, description, age, city, sportsArray);
                    db.collection("users").document(mAuth.getCurrentUser().getUid()).set(newUser);
                    startHomePageActivity();
                }
                break;

            default:
                if(((ColorDrawable)view.getBackground()).getColor() == Color.GREEN){
                    view.setBackgroundColor(Color.TRANSPARENT);
                } else {
                    view.setBackgroundColor(Color.GREEN);
                }
        }

    }

    public void findById(){
        nameEditText = findViewById(R.id.nameEditText);
        ageEditText = findViewById(R.id.ageEditText);
        maleCheckBox = findViewById(R.id.maleCheckBox);
        femaleCheckBox = findViewById(R.id.femaleCheckBox);
        cityEditText = findViewById(R.id.cityEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        continueBtn = findViewById(R.id.continueBtn);
        tableLayout = findViewById(R.id.sportsSelectTable);
        soccerRow = findViewById(R.id.soccerRow);
        basketballRow = findViewById(R.id.basketballRow);
        footballRow = findViewById(R.id.footballRow);
        volleyballRow = findViewById(R.id.volleyballRow);
        baseballRow = findViewById(R.id.baseballRow);
        runningRow = findViewById(R.id.runningRow);
        tennisRow = findViewById(R.id.tennisRow);
    }

    public void setClickListeners(){
        maleCheckBox.setOnClickListener(this);
        femaleCheckBox.setOnClickListener(this);
        continueBtn.setOnClickListener(this);
        soccerRow.setOnClickListener(this);
        basketballRow.setOnClickListener(this);
        footballRow.setOnClickListener(this);
        volleyballRow.setOnClickListener(this);
        baseballRow.setOnClickListener(this);
        runningRow.setOnClickListener(this);
        tennisRow.setOnClickListener(this);

        tableRowsArray.add(soccerRow);
        tableRowsArray.add(basketballRow);
        tableRowsArray.add(footballRow);
        tableRowsArray.add(volleyballRow);
        tableRowsArray.add(baseballRow);
        tableRowsArray.add(runningRow);
        tableRowsArray.add(tennisRow);
    }

    public void startHomePageActivity(){
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
        finish();
    }
}
