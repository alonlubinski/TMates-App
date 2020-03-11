package com.example.tmates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.MyViewHolder> {

    private ArrayList<ChatRoom> mDataset;
    private User curUser, otherUser;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView chatNameTextView;
        private String cid;
        private User user1, user2;
        private ArrayList<ChatMessage> chatMessageArrayList;
        private Context context;

        public MyViewHolder(@NonNull View itemView, String cid) {
            super(itemView);
            chatNameTextView = itemView.findViewById(R.id.chatNameTextView);
            this.cid = cid;
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, ChatRoomActivity.class);
            intent.putExtra("chatId", this.cid);
            context.startActivity(intent);
        }
    }

    public ChatListAdapter(ArrayList<ChatRoom> myDataset, User curUser){
        mDataset = myDataset;
        this.curUser = curUser;
    }
    @NonNull
    @Override
    public ChatListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chatlist_row, parent, false);
        ChatListAdapter.MyViewHolder vh = new ChatListAdapter.MyViewHolder(view, "");
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListAdapter.MyViewHolder holder, int position) {
        holder.chatNameTextView.setText((mDataset.get(position).getUser2()).getName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


}
