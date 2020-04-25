package ru.filchacov.billsplittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.*


class BillListFragment : Fragment(), OnClickChangeAmount {

    companion object{
        const val TAG = "BillListFragment"

        @JvmStatic fun getNewInstance(args: Bundle): BillListFragment{
            val billListFragment = BillListFragment()
            billListFragment.arguments = args
            return billListFragment
        }
    }
    private var presenter: BillListPresenter? = null
    /*private var mAuth: FirebaseAuth? = null
    private var mDataBase: DatabaseReference? = null
    private var user: FirebaseUser? = null*/
    private var totalSumView: TextView? = null
    private var friendItem: FriendItem? = null
    private var bill: Bill? = null
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
        presenter = BillListPresenter(this, bill!!, friendItem!!)


   /*     for(item in bill!!.items){
            val billUser = BillUser()
            billUser.setBillItem(item)
            listBill.add(billUser)
        }*/

        totalSumView = view.findViewById<TextView>(R.id.sum)

        btnSave = view.findViewById<Button>(R.id.button_save)
        btnSave!!.setOnClickListener {
            presenter!!.saveBillForFriend()
            /*mDataBase!!.child("users").child(user!!.uid).child("friends").child(bill!!.dateTime).child(friendItem!!.getmText()!!).setValue(listBillDB)
            mDataBase!!.child("users").child(user!!.uid).child("friends").child(bill!!.dateTime).child("savedFriends").child(friendItem!!.getKey()).child("isSelected").setValue(true)
            (activity as BillActivity).showFriendFragment(bill!!)*/

        }

        if (friendItem!!.getisSelected()){
            btnSave!!.visibility = INVISIBLE
            presenter!!.loadBillList()
            /*
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

                    } )*/
        }else{
            presenter!!.initBillList()
        }


//        totalSumView!!.text = String.format("%.2f", ((bill!!.totalSum.toDouble())/100))

        val recyclerView = view.findViewById<RecyclerView>(R.id.bill_list)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        adapter = BillAdapter(presenter!!.listBill, this, friendItem!!.getisSelected())
        recyclerView.adapter = adapter
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
        outState.putParcelable("friendItem", friendItem)

    }

    fun updateTotalSum(totalSum: Double){
        totalSumView!!.text = String.format("%.2f", totalSum)
    }

    override fun clickPlus(position: Int) {
       /* val item = listBill[position]
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
*/
        presenter!!.plus(position)
    }

    override fun clickMinus(position: Int) {
        /*val item = listBill[position]
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
*/
        presenter!!.minus(position)
    }

    fun updateAdapterAmount(position: Int, billUer: BillUser){
        adapter!!.updateAmount(position, billUer)
    }

    fun updateAdapter(){
        adapter!!.notifyDataSetChanged()
    }
}
