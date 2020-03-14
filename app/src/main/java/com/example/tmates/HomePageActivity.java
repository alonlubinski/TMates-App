package com.example.tmates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomePageActivity extends AppCompatActivity {
    private ImageButton profileBtn, messagesBtn, lookForPartnerBtn, whereToPlayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        profileBtn = findViewById(R.id.profileBtn);
        messagesBtn = findViewById(R.id.messageListBtn);
        lookForPartnerBtn = findViewById(R.id.lookForPartnerBtn);
        whereToPlayBtn = findViewById(R.id.whereToPlayBtn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startProfilePageActivity();
            }
        });
        messagesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMessageListActivity();
            }
        });
        lookForPartnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLookForPartnerActivity();
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

    public void startMessageListActivity(){
        Intent intent = new Intent(this, MessageListActivity.class);
        startActivity(intent);
    }

    public void startWhereToPlayActivity(){
        Intent intent = new Intent(HomePageActivity.this, WhereToPlayActivity.class);
        startActivity(intent);
    }

    public void startLookForPartnerActivity(){
        Intent intent = new Intent(this, LookForPartnerActivity.class);
        startActivity(intent);
    }


}
