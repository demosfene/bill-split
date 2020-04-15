package ru.filchacov.billsplittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BillListFragment (private var friendItem: FriendItem, private var bill: Bill) : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.activity_bill_list, container, false)

        val totalSum = view.findViewById<TextView>(R.id.sum)


        totalSum.text = ((bill.totalSum.toDouble())/100).toString()

        val recyclerView = view.findViewById<RecyclerView>(R.id.bill_list)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BillAdapter(bill.items)

        return view
    }








//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_bill_list)
////        val dateTime = findViewById<TextView>(android.R.id.date)
//        val totalSum = findViewById<TextView>(android.R.id.total_sum)
//        dateTime.text = bill!!.dateTime
//        totalSum.text = ((bill!!.totalSum.toDouble())/100).toString()
//
//        val recyclerView = findViewById<RecyclerView>(android.R.id.bill_list)
//        val layoutManager: LinearLayoutManager = LinearLayoutManager(applicationContext)
//        recyclerView.layoutManager = layoutManager
//        recyclerView.adapter = BillAdapter(bill.items)
//

}
