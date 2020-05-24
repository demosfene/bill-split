package ru.filchacov.billsplittest.infoBillMVP

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.bill.BillLListInterface
import ru.filchacov.billsplittest.bill.BillListPresenter
import ru.filchacov.billsplittest.bill.BillUser
import ru.filchacov.billsplittest.billActivityMVP.BillInterface
import ru.filchacov.billsplittest.R
import ru.filchacov.billsplittest.ToolbarSettings

class InfoBillView : Fragment(), BillLListInterface {

    private var recyclerView: RecyclerView? = null
    private var adapter: InfoBillAdapter? = null
    private var presenter: BillListPresenter? = null
    private var btnToFriends: Button? = null
    private var bill: Bill? = null

    companion object {

        const val TAG = "InfoBill"

        @JvmStatic
        fun getNewInstance(args: Bundle): InfoBillView {
            val infoBillView = InfoBillView()
            infoBillView.arguments = args
            return infoBillView
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_info_bill_view, container, false)

        if (savedInstanceState != null) {
            bill = savedInstanceState.getParcelable("bill")
        } else {
            bill = requireArguments().getParcelable("bill")
        }

        presenter = BillListPresenter(this, bill!!)
        presenter!!.loadInfoBill()

        recyclerView = view.findViewById(R.id.items_of_bill)
        val mLayoutManager = LinearLayoutManager(context)
        recyclerView!!.layoutManager = mLayoutManager

        adapter = InfoBillAdapter(presenter!!.listBill)
        recyclerView!!.adapter = adapter

        btnToFriends = view.findViewById(R.id.split_button)
        btnToFriends!!.setOnClickListener {
            if (activity is BillInterface) {
                (activity as BillInterface).showFriendFragment(bill)
            }
        }

        return view
    }

    override fun updateAdapter() {
        adapter!!.notifyDataSetChanged()
    }

    override fun exitFromBill(bill: Bill) {
        // Nothing to do
    }

    override fun updateTotalSum(totalSum: Double) {
        // Nothing to do
    }

    override fun updateAdapterAmount(position: Int, billUer: BillUser) {
        // Nothing to do
    }

    override fun showSumZero() {
        TODO("Not yet implemented")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
    }

    override fun onResume() {
        super.onResume()
        if (activity is ToolbarSettings) {
            (activity as ToolbarSettings?)!!.setToolbarTitle(R.string.YourBill)
        }
    }

}
