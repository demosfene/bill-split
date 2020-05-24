package ru.filchacov.billsplittest.bill

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import ru.filchacov.billsplittest.R

class BillAdapter(var items: ArrayList<BillUser>, var onClickPlus: OnClickChangeAmount, var isSelected: Boolean) : RecyclerView.Adapter<BillAdapter.BillHolder>() {

    fun updateAmount(position: Int, billUser: BillUser) {
        items[position] = billUser
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillHolder = BillHolder(LayoutInflater.from(parent.context).inflate(R.layout.bill_item, parent, false))

    override fun onBindViewHolder(holder: BillHolder, position: Int) {
        holder.bind(items[position], isSelected)
        holder.bindClickChangeAmount(position, onClickPlus)
    }

    override fun getItemCount(): Int = items.size


    inner class BillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val itemName = itemView.findViewById<TextView>(R.id.bill_item_name)
        private val itemCount = itemView.findViewById<TextView>(R.id.bill_item_count)
        private val itemPrice = itemView.findViewById<TextView>(R.id.bill_item_price)
        private val btnPlus = itemView.findViewById<ImageButton>(R.id.plus)
        private val btnMinus = itemView.findViewById<ImageButton>(R.id.minus)
        private val itemUserCount = itemView.findViewById<TextView>(R.id.bill_user_count)

        fun bind(billUser: BillUser, isSelected: Boolean) {
            if (isSelected) {
                btnMinus.visibility = GONE
                btnPlus.visibility = GONE
                itemCount.visibility = GONE
            }
            itemName.text = billUser.item!!.name
            itemCount.text = billUser.item!!.quantity.toString()
            itemPrice.text = String.format("%.2f", (billUser.item!!.price)!!.toDouble() / 100)
            itemUserCount.text = billUser.amount.toString()

        }

        fun bindClickChangeAmount(position: Int, onClickChangeAmount: OnClickChangeAmount) {
            btnPlus.setOnClickListener {
                onClickChangeAmount.clickPlus(position)
            }
            btnMinus.setOnClickListener {
                onClickChangeAmount.clickMinus(position)
            }
        }


    }
}