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

        totalSumView = view.findViewById(R.id.sum)

        btnSave = view.findViewById(R.id.button_save)
        btnSave!!.setOnClickListener {
            presenter!!.saveBillForFriend()
        }

        if (friendItem!!.getisSelected()){
            btnSave!!.visibility = INVISIBLE
            presenter!!.loadBillList()
        }else{
            presenter!!.initBillList()
        }

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
        presenter!!.plus(position)
    }

    override fun clickMinus(position: Int) {
        presenter!!.minus(position)
    }

    fun updateAdapterAmount(position: Int, billUer: BillUser){
        adapter!!.updateAmount(position, billUer)
    }

    fun updateAdapter(){
        adapter!!.notifyDataSetChanged()
    }
}
