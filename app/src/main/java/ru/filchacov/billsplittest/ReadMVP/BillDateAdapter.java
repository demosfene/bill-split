package ru.filchacov.billsplittest.ReadMVP;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.filchacov.billsplittest.R;

public class BillDateAdapter extends RecyclerView.Adapter<BillDateAdapter.BillDateViewHolder> {

    private List<String> list;
    private OnNoteListener mOnNoteListener;

    BillDateAdapter(List<String> list, OnNoteListener onNoteListener) {
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public BillDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillDateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false), mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDateViewHolder holder, int position) {
        String bf = list.get(position);
        holder.textEmail.setText(bf);

        /*holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(holder.getAdapterPosition(), 0, 0, "Добавить в группу");
                menu.add(holder.getAdapterPosition(), 1, 0, "Удалить");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class BillDateViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textEmail;
        OnNoteListener onNoteListener;

        BillDateViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
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
