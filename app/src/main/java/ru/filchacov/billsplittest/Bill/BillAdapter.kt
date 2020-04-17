package ru.filchacov.billsplittest.Bill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.R

class BillAdapter(var items: ArrayList<BillUser>): RecyclerView.Adapter<BillAdapter.BillHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillHolder = BillHolder(LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false))

    override fun onBindViewHolder(holder: BillHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size




    inner class BillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemName = itemView.findViewById<TextView>(R.id.bill_item_name)
        private val itemCount = itemView.findViewById<TextView>(R.id.bill_item_count)
        private val itemPrice = itemView.findViewById<TextView>(R.id.bill_item_price)
        private val btnPlus= itemView.findViewById<Button>(R.id.plus)
        private val btnMinus = itemView.findViewById<Button>(R.id.minus)
        private val itemUserCount = itemView.findViewById<TextView>(R.id.bill_user_count)

        fun bind(billUser: BillUser){
            itemName.text = billUser.item!!.name
            itemCount.text = billUser.item!!.quantity.toString()
            itemPrice.text = ((billUser.item!!.price)!!.toDouble()/100).toString()
            itemUserCount.text = billUser.amount.toString()
            btnPlus.setOnClickListener{
                billUser.amount++
                notifyDataSetChanged()

            }
        }



    }
}