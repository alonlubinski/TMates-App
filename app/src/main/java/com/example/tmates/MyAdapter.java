package com.example.tmates;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Post> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView postTitleTextView, postAuthorTextView, postDateTextView, postDescriptionTextView;
        public String postId, userAuthorId;
        private Context context;

        public MyViewHolder(View v, String postId, String userAuthorId) {
            super(v);
            postTitleTextView = v.findViewById(R.id.postTitleTextView);
            postAuthorTextView = v.findViewById(R.id.postAuthorTextView);
            postDateTextView = v.findViewById(R.id.postDateTextView);
            postDescriptionTextView = v.findViewById(R.id.postDescriptionTextView);
            this.postId = postId;
            this.userAuthorId = userAuthorId;
            context = v.getContext();
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            startProfilePageActivity();
        }

        public void startProfilePageActivity(){
            Intent intent = new Intent(context, ChatListActivity.class);
            intent.putExtra("otherUserId", this.userAuthorId);
            context.startActivity(intent);
        }


    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Post> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        MyViewHolder vh = new MyViewHolder(view, "", "");
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.postTitleTextView.setText(mDataset.get(position).getPostTitle());
        holder.postAuthorTextView.setText(mDataset.get(position).getAuthor().getName());
        holder.postDateTextView.setText(mDataset.get(position).getPostDate());
        holder.postDescriptionTextView.setText(mDataset.get(position).getPostDescription());
        holder.postId = mDataset.get(position).getPostId();
        holder.userAuthorId = mDataset.get(position).getAuthorId();

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}


