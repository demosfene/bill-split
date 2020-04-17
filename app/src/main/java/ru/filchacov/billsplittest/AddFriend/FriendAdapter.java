package ru.filchacov.billsplittest.AddFriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ru.filchacov.billsplittest.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private ArrayList<FriendItem> mFriendList;
    private OnCLickFriend onCLickFriend;

    public FriendAdapter(ArrayList<FriendItem> friendList, OnCLickFriend onCLickFriend) {
        mFriendList = friendList;
        this.onCLickFriend = onCLickFriend;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        FriendItem currentItem = mFriendList.get(position);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText());
        holder.bindClickFriend(position, onCLickFriend);
    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }


    class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView1;

        FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
        }

        void bindClickFriend(int position, OnCLickFriend onCLickFriend){
            itemView.setOnClickListener(v -> onCLickFriend.clickFriend(position));
        }
    }
}
