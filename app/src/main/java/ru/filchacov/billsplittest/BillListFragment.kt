package ru.filchacov.billsplittest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.addFriend.FriendItem
import ru.filchacov.billsplittest.bill.*
import ru.filchacov.billsplittest.billActivityMVP.BillInterface


class BillListFragment : Fragment(), OnClickChangeAmount, BillLListInterface {

    companion object {
        const val TAG = "BillListFragment"

        @JvmStatic
        fun getNewInstance(args: Bundle): BillListFragment {
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
    private var btnCancel: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_bill_list, container, false)
        if (savedInstanceState != null) {
            bill = savedInstanceState.getParcelable("bill")
            friendItem = savedInstanceState.getParcelable("friendItem")
        } else {
            bill = requireArguments().getParcelable("bill")
            friendItem = requireArguments().getParcelable("friendItem")
        }
        presenter = BillListPresenter(this, bill!!, friendItem!!)

        totalSumView = view.findViewById(R.id.sum)

        if (activity is BillInterface) {
            (activity as BillInterface).progressBarInvisible()
        }

        btnSave = view.findViewById(R.id.button_save)
        btnSave!!.setOnClickListener {
            presenter!!.saveBillForFriend()
        }
        btnCancel = view.findViewById(R.id.button_cancel)
        btnCancel!!.setOnClickListener {
            exitFromBill(bill!!)
        }

        if (friendItem!!.getisSelected()) {
            btnSave!!.visibility = INVISIBLE
            presenter!!.loadBillList()
        } else {
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

    override fun onResume() {
        super.onResume()
        if (activity is ToolbarSettings) {
            (activity as ToolbarSettings).setToolbarTitle(R.string.position_selection)
        }
        if (activity is ShowUpButton) {
            (activity as ShowUpButton).showUpButton(true)
        }
        if (activity is MainActivityInterface) {
            (activity as MainActivityInterface?)!!.navigationDrawerInvisible()
        }
    }


    override fun updateTotalSum(totalSum: Double) {
        totalSumView!!.text = String.format("%.2f", totalSum)
    }

    override fun clickPlus(position: Int) {
        presenter!!.plus(position)
    }

    override fun clickMinus(position: Int) {
        presenter!!.minus(position)
    }

    override fun updateAdapterAmount(position: Int, billUer: BillUser) {
        adapter!!.updateAmount(position, billUer)
    }

    override fun showSumZero() {
        Toast.makeText(activity, "Вы ничего не выбрали!", Toast.LENGTH_SHORT).show()
    }

    override fun updateAdapter() {
        adapter!!.notifyDataSetChanged()
    }

    override fun exitFromBill(bill: Bill){
        (activity as ExitFromBill).exitBill(bill)
    }

    private fun shareCashDebt(activity: Activity, debt: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            putExtra(Intent.EXTRA_TEXT, "Верни мне $debt рублей пожалуйста")
        }

        val manager = activity.packageManager
        if (intent.resolveActivity(manager) == null) {
            Toast.makeText(activity, "ERROR", Toast.LENGTH_SHORT)
                    .show()
        }

        val title = "Через что просим денег?"
        val chooser = Intent.createChooser(intent, title)
        activity.startActivity(chooser)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                activity?.let { shareCashDebt(it, totalSumView!!.text as String) }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


}
