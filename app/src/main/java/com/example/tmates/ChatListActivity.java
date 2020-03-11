package com.example.tmates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.Collections;

public class ChatListActivity extends AppCompatActivity {
    private ArrayList<ChatRoom> chatRoomList = new ArrayList<>();
    private ArrayList<String> chatRoomIdList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String otherUserId;
    private User curUser, otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if(getIntent().getExtras() != null){
            otherUserId = getIntent().getExtras().getString("otherUserId");
            getOtherUser(otherUserId);
        }

        getUser(mAuth.getCurrentUser().getUid());

    }

    @Override
    public void onResume() {
        super.onResume();
        chatRoomList.clear();
        chatRoomIdList.clear();
        getDataFromFireBase();
    }

    public void initRecyclerView(){
        if(!chatRoomList.isEmpty()){
            recyclerView = findViewById(R.id.chatsRecyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new ChatListAdapter(chatRoomList, curUser);
            recyclerView.setAdapter(mAdapter);
        }
    }

    public void getDataFromFireBase(){
        final DocumentReference documentReference = db.collection("users").document(mAuth.getCurrentUser().getUid());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    User user = documentSnapshot.toObject(User.class);
                    if(!user.getChatRoomArrayList().isEmpty()){
                        for(int i=0; i < user.getChatRoomArrayList().size(); i++){
                            addChatRoomToArrayList(user.getChatRoomArrayList().get(i));
                            chatRoomIdList.add(user.getChatRoomArrayList().get(i));
                        }
                    }
                    if(otherUserId != null && otherUser != null){
                        if(!checkIfChatRoomExist(mAuth.getCurrentUser().getUid(), otherUserId)){
                            CollectionReference chatsRef = db.collection("chats");
                            String id = chatsRef.document().getId();
                            ChatRoom newChatRoom = new ChatRoom(id, curUser, otherUser, new ArrayList<ChatMessage>());
                            chatRoomList.add(newChatRoom);
                            chatRoomIdList.add(id);
                            db.collection("chats").document(id).set(newChatRoom);
                            curUser.setChatRoomArrayList(chatRoomIdList);
                            db.collection("users").document(curUser.getUid()).set(curUser, SetOptions.merge());
                        }
                    }
                    initRecyclerView();
                }
            }
        });

    }

    public Boolean checkIfChatRoomExist(String uid1, String uid2){
        for(int i=0; i < chatRoomList.size(); i++){
            if((chatRoomList.get(i).getUser1().getUid() == uid1 && chatRoomList.get(i).getUser2().getUid() == uid2) || (chatRoomList.get(i).getUser1().getUid() == uid2 && chatRoomList.get(i).getUser2().getUid() == uid1)){
                return true;
            }
        }
        return false;
    }

    public void getUser(String id){
        DocumentReference documentReference = db.collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        curUser = documentSnapshot.toObject(User.class);
                    }
                }
            }
        });
    }

    public void getOtherUser(String id){
        DocumentReference documentReference = db.collection("users").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        otherUser = documentSnapshot.toObject(User.class);
                    }
                }
            }
        });
    }

    public void addChatRoomToArrayList(String id){
        DocumentReference documentReference = db.collection("chats").document(id);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        ChatRoom chatRoom = documentSnapshot.toObject(ChatRoom.class);
                        chatRoomList.add(chatRoom);
                    }
                }
            }
        });
    }
}
