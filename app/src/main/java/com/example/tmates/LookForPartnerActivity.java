package com.example.tmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Collections;

public class LookForPartnerActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<Post> postsList = new ArrayList<>();
    private Button addPostBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_for_partner);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();




        addPostBtn = findViewById(R.id.addPostBtn);
        addPostBtn.setOnClickListener(this);

        //getDataFromFireBase();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.addPostBtn:
                startAddPostActivity();
                break;

            default:
                System.out.println();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        postsList.clear();
        getDataFromFireBase();

    }

    public void startAddPostActivity(){
        Intent intent = new Intent(this, AddPostActivity.class);
        startActivity(intent);
    }

    public void initRecyclerView(){
        recyclerView = findViewById(R.id.postsRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(postsList);
        recyclerView.setAdapter(mAdapter);
    }
//
    public void getDataFromFireBase(){
        final CollectionReference collectionReference = db.collection("posts");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Post post = document.toObject(Post.class);
                        postsList.add(post);
                    }
                    Collections.sort(postsList);
                    initRecyclerView();
                }
            }
        });
    }

}


