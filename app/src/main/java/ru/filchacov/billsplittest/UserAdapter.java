package ru.filchacov.billsplittest;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> list;
    private OnNoteListener mOnNoteListener;

    public UserAdapter(List<User> list, OnNoteListener onNoteListener) {
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false), mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = list.get(position);
        holder.textEmail.setText(user.email);

        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(holder.getAdapterPosition(), 0, 0, "Добавить в группу");
                menu.add(holder.getAdapterPosition(), 1, 0, "Удалить");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textEmail;
        OnNoteListener onNoteListener;

        public UserViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textEmail = itemView.findViewById(R.id.text_email);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
