package ru.filchacov.billsplittest.readMVP;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.bill.Bill;

public class BillDateAdapter extends RecyclerView.Adapter<BillDateAdapter.BillDateViewHolder> {

    private List<Bill> list;
    private OnNoteListener mOnNoteListener;

    BillDateAdapter(List<Bill> list, OnNoteListener onNoteListener) {
        this.list = list;
        this.mOnNoteListener = onNoteListener;
    }

    void add(Bill bill) {
        list.add(bill);
        notifyItemInserted(getItemCount() - 1);
    }

    @NonNull
    @Override
    public BillDateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BillDateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_item, parent, false), mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BillDateViewHolder holder, int position) {
        holder.bind(list.get(position));

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

        void bind(Bill bill) {
            String[] bufDateTime = splitDateTime(bill);
            String date = getDate(bufDateTime);
            String time = getTime(bufDateTime);
            textEmail.setText(date + time);
            String totalSum = String.valueOf(bill.getTotalSum());
            textSum.setText(String.format("%.2f", Double.parseDouble(totalSum) / 100));
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }

        private String[] splitDateTime(Bill bill) {
            String dateTime = bill.getDateTime();
            String delimeterDateTime = "T";
            return dateTime.split(delimeterDateTime);
        }

        private String getDate(String[] bufDateTime) {
            String month;
            String date = bufDateTime[0];
            String delimeterDayMonthYear = "-";
            String[] values = date.split(delimeterDayMonthYear);
            switch (values[1]) {
                case ("01"):
                    month = "Января";
                    break;
                case ("02") :
                    month = "Февраля";
                    break;
                case ("03") :
                    month = "Марта";
                    break;
                case ("04") :
                    month = "Апреля";
                    break;
                case ("05") :
                    month = "Мая";
                    break;
                case ("06") :
                    month = "Июня";
                    break;
                case ("07") :
                    month = "Июля";
                    break;
                case ("08") :
                    month = "Августа";
                    break;
                case ("09") :
                    month = "Сентября";
                    break;
                case ("10") :
                    month = "Октября";
                    break;
                case ("11") :
                    month = "Ноября";
                    break;
                case ("12") :
                    month = "Декабря";
                    break;
                default: month = "ОшибОчка";
                break;
            }
            return values[2]+ " " + month +  " ";
        }

        private String getTime(String[] bufDateTime) {
            String time = bufDateTime[1];
            String delimeterTime = ":";
            String[] bufTime = time.split(delimeterTime);
            return bufTime[0] + ":" + bufTime[1];
        }

    }

    public interface OnNoteListener {
        void onNoteClick(int position);
    }
}
