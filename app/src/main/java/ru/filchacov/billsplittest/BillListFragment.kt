package ru.filchacov.billsplittest

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.Bill.BillAdapter
import ru.filchacov.billsplittest.Bill.BillUser
import ru.filchacov.billsplittest.Bill.OnClickChangeAmount
import java.util.*
import kotlin.collections.ArrayList


class BillListFragment : Fragment(), OnClickChangeAmount {

    companion object{
        const val TAG = "BillListFragment"

        fun getNewInstance(args: Bundle): BillListFragment{
            val billListFragment = BillListFragment()
            billListFragment.arguments = args
            return billListFragment
        }
    }

    private var mAuth: FirebaseAuth? = null
    private var mDataBase: DatabaseReference? = null
    private var user: FirebaseUser? = null

    private var totalSumView: TextView? = null
    private var totalSum = 0
    private var friendItem: FriendItem? = null
    private var bill: Bill? = null
    private var listBill = ArrayList<BillUser>()
    private var listBillDB = ArrayList<BillUser>()
    private var adapter: BillAdapter? = null
    private var btnSave: Button? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.activity_bill_list, container, false)
        if (savedInstanceState != null) {
            bill = savedInstanceState.getParcelable("bill")
            friendItem = savedInstanceState.getParcelable("friendItem")
        } else {
            bill = arguments!!.getParcelable("bill")
            friendItem = arguments!!.getParcelable("friendItem")
        }

        mAuth = FirebaseAuth.getInstance()
        mDataBase = FirebaseDatabase.getInstance().reference
        user = mAuth!!.currentUser

        for(item in bill!!.items){
            val billUser = BillUser()
            billUser.setBillItem(item)
            listBill.add(billUser)
        }

        totalSumView = view.findViewById<TextView>(R.id.sum)

        btnSave = view.findViewById<Button>(R.id.button_save)
        btnSave!!.setOnClickListener(View.OnClickListener {
            mDataBase!!.child("users").child(user!!.uid).child("friends").child(bill!!.dateTime).child(friendItem!!.getmText()!!).setValue(listBillDB)
            mDataBase!!.child("users").child(user!!.uid).child("friends").child(bill!!.dateTime).child("savedFriends").child(friendItem!!.getKey()).child("isSelected").setValue(true)
            (activity as BillActivity).showFriendFragment(bill!!)
        })

        if (friendItem!!.getisSelected()){
            btnSave!!.visibility = INVISIBLE
            mDataBase!!
                    .child("users")
                    .child(user!!.uid)
                    .child("friends")
                    .child(bill!!.dateTime)
                    .child(friendItem!!.getmText()!!)
                    .addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {
                            TODO("Not yet implemented")
                        }

                        override fun onDataChange(p0: DataSnapshot) {
                            loadBillDB(p0)
                        }

                    } )
        }


//        totalSumView!!.text = String.format("%.2f", ((bill!!.totalSum.toDouble())/100))

        val recyclerView = view.findViewById<RecyclerView>(R.id.bill_list)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = BillAdapter(listBill, this, friendItem!!.getisSelected())
        recyclerView.adapter = adapter





        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
        outState.putParcelable("friendItem", friendItem)

    }

    fun updateTotalSum(list: ArrayList<BillUser>){
        totalSum = 0
        for (item in list)
            totalSum+= item.amount * item.item!!.price!!
        totalSumView!!.text = String.format("%.2f", ((totalSum.toDouble())/100))
    }

    override fun clickPlus(position: Int) {
        val item = listBill[position]
        if(item.amount < item.item!!.quantity!!){
            listBill[position].amount++
            adapter!!.updateAmount(position, item)
            var has = false
            for (itemDB in listBillDB) {
                if (itemDB == item) {
                    has = true
                }
            }
            if (!has){
                listBillDB.add(item)
            }
            updateTotalSum(listBillDB)
        }

    }

    override fun clickMinus(position: Int) {
        val item = listBill[position]
        if (item.amount > 0) {
            listBill[position].amount--
            adapter!!.updateAmount(position, item)

            val iterator = listBillDB.iterator()
            while (iterator.hasNext()) {
                val iterItem = iterator.next()
                if (iterItem.amount == 0) {
                    iterator.remove()
                }
                updateTotalSum(listBillDB)
            }
        }
    }

    private fun loadBillDB(dataSnapshot: DataSnapshot) {
        listBill.clear()
        var dataChildren = dataSnapshot.children
        var iter = dataChildren.iterator()
        while (iter.hasNext()) {
            var ds = iter.next()
            val map = ds.value as Map<String, String>
            var billDB = BillUser()
            billDB.amount = map["amount"].toString().toInt()
            var itemMap = map["item"] as Map<String, String>
            var billItem = Bill.Item()
            billItem.price = itemMap.get("price").toString().toInt()
            billItem.quantity = itemMap.get("quantity").toString().toInt()
            billItem.name = itemMap["name"].toString()
            billItem.sum = itemMap["sum"].toString().toInt()
            billDB.item = billItem
            listBill.add(billDB)
        }
        updateTotalSum(listBill)
        adapter!!.notifyDataSetChanged()


    }


}
