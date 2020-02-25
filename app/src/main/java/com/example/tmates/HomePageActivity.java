package com.example.tmates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity {
    private Button profileBtn, whereToPlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        profileBtn = findViewById(R.id.profileBtn);
        whereToPlayBtn = findViewById(R.id.whereToPlayBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProfilePageActivity();
            }
        });
        whereToPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWhereToPlayActivity();
            }
        });
    }

    public void startProfilePageActivity(){
        Intent intent = new Intent(this, ProfilePageActivity.class);
        startActivity(intent);
    }

    public void startWhereToPlayActivity(){
        Intent intent = new Intent(HomePageActivity.this, WhereToPlayActivity.class);
        startActivity(intent);
    }
}
