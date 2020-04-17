package ru.filchacov.billsplittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.Bill.BillAdapter
import ru.filchacov.billsplittest.Bill.BillUser

class BillListFragment () : Fragment() {

    companion object{
        val TAG = "BillListFragment"

        fun getNewInstance(args: Bundle): BillListFragment{
            val billListFragment = BillListFragment()
            billListFragment.arguments = args
            return billListFragment
        }
    }

    private var friendItem: FriendItem? = null
    private var bill: Bill? = null
    private var listBill = ArrayList<BillUser>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.activity_bill_list, container, false)
        if (savedInstanceState != null) {
            bill = savedInstanceState.getParcelable("bill")
            friendItem = savedInstanceState.getParcelable("friendItem")
        } else {
            bill = arguments!!.getParcelable("bill")
            friendItem = arguments!!.getParcelable("friendItem")
        }

        for(item in bill!!.items){
            val billUser = BillUser()
            billUser.setBillItem(item)
            listBill.add(billUser)
        }

        val totalSum = view.findViewById<TextView>(R.id.sum)


        totalSum.text = ((bill!!.totalSum.toDouble())/100).toString()

        val recyclerView = view.findViewById<RecyclerView>(R.id.bill_list)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = BillAdapter(listBill)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
        outState.putParcelable("friensItem", friendItem)

    }



}
