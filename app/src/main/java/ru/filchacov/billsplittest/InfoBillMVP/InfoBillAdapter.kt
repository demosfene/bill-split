package ru.filchacov.billsplittest.InfoBillMVP

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_of_bill.view.*
import ru.filchacov.billsplittest.Bill.BillUser
import ru.filchacov.billsplittest.R

class InfoBillAdapter(var items: ArrayList<BillUser>) :
        RecyclerView.Adapter<InfoBillAdapter.InfoBillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoBillViewHolder =
            InfoBillViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.item_of_bill, parent,false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: InfoBillViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class InfoBillViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var itemText = itemView.findViewById<TextView>(R.id.itemText)
        var itemCount= itemView.findViewById<TextView>(R.id.itemCount)
        var itemPrice = itemView.findViewById<TextView>(R.id.itemPrice)

        fun bind(billUser: BillUser) {
            itemText.text = billUser.item!!.name
            itemCount.text = billUser.item!!.quantity.toString()
            itemPrice.text = String.format("%.2f", (billUser.item!!.price)!!.toDouble() / 100)
        }

    }
}