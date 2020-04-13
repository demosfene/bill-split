package ru.filchacov.billsplittest

import android.util.Log.i
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BillAdapter(var items: List<Bill.Item>): RecyclerView.Adapter<BillAdapter.BillHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillHolder = BillHolder(LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false))

    override fun onBindViewHolder(holder: BillHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class BillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

       private val itemName = itemView.findViewById<TextView>(R.id.bill_item_name)
       private val itemCount = itemView.findViewById<TextView>(R.id.bill_item_count)
       private val itemPrice = itemView.findViewById<TextView>(R.id.bill_item_price)


       fun bind(item: Bill.Item){
            itemName.text = item.name
           itemCount.text = item.quantity.toString()
           itemPrice.text = item.price.toString()
       }

    }
}