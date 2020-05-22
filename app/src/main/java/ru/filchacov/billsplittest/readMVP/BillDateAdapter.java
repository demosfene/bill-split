package ru.filchacov.billsplittest.readMVP;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.db.usersBills.UsersBills;

public class BillDateAdapter extends RecyclerView.Adapter<BillDateAdapter.BillDateViewHolder> {

    private List<UsersBills> list;
    private List<String> sum;
    private OnNoteListener mOnNoteListener;

    BillDateAdapter(List<UsersBills> list, List<String> sum, OnNoteListener onNoteListener) {
        this.list = list;
        this.sum = sum;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public BillDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillDateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false), mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDateViewHolder holder, int position) {
        holder.bind(list.get(position), sum.get(position));

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
        TextView textSum;
        OnNoteListener onNoteListener;

        BillDateViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            textEmail = itemView.findViewById(R.id.text_email);
            textSum = itemView.findViewById(R.id.sum_text);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);

        }

        void bind (UsersBills usersBills, String sum){
            textEmail.setText(usersBills.getBillUID());
            textSum.setText(sum);
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
