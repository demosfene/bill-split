package ru.filchacov.billsplittest.addFriend;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

import ru.filchacov.billsplittest.OnCLickFriend;
import ru.filchacov.billsplittest.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private ArrayList<FriendItem> mFriendList;
    private OnCLickFriend onCLickFriend;
    private OnClickShare onClickShare;

    public FriendAdapter(ArrayList<FriendItem> friendList, OnCLickFriend onCLickFriend, OnClickShare onClickShare) {
        mFriendList = friendList;
        this.onCLickFriend = onCLickFriend;
        this.onClickShare = onClickShare;
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
        holder.bind(currentItem);

        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText(currentItem.getmText());
        holder.bindClickFriend(position, onCLickFriend);
        holder.bindClickShare(currentItem, onClickShare);
        holder.itemView.setOnCreateContextMenuListener((menu, v, menuInfo) -> menu.add(holder.getAdapterPosition(), 0, 0, "Удалить"));
    }


    @Override
    public int getItemCount() {
        return mFriendList.size();
    }


    static class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView1;
        ImageView imageViewShare;
        TextView textViewSum;
        Group group;

        FriendViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextView1 = itemView.findViewById(R.id.textView);
            imageViewShare = itemView.findViewById(R.id.share);
            textViewSum = itemView.findViewById(R.id.friend_sum);
            group = itemView.findViewById(R.id.group);
        }

        void bindClickFriend(int position, OnCLickFriend onCLickFriend) {
            itemView.setOnClickListener(v -> onCLickFriend.clickFriend(position));
        }

        void bind(FriendItem friendItem) {
            mImageView.setImageResource(friendItem.getmImageResource());
            mTextView1.setText(friendItem.getmText());
            DecimalFormat df = new DecimalFormat();
            df.setGroupingUsed(true);
            df.setGroupingSize(3);

            DecimalFormatSymbols decimalFormatSymbols = df.getDecimalFormatSymbols();
            decimalFormatSymbols.setDecimalSeparator(',');
            decimalFormatSymbols.setGroupingSeparator(' ');

            df.setDecimalFormatSymbols(decimalFormatSymbols);

            if (friendItem.getisSelected()) {
                group.setVisibility(View.VISIBLE);
                textViewSum.setText(df.format(Double.parseDouble(String.valueOf(friendItem.getSum()))/100));
            }else{
                group.setVisibility(View.INVISIBLE);
            }
        }

        void bindClickShare(FriendItem friendItem, OnClickShare onClickShare) {
            imageViewShare.setOnClickListener(v -> onClickShare.clockShare(Double.parseDouble(String.valueOf(friendItem.getSum()))/100));
        }
    }
}
