package ru.filchacov.billsplittest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.filchacov.billsplittest.addFriend.AddFriendInterface
import ru.filchacov.billsplittest.addFriend.FriendAdapter
import ru.filchacov.billsplittest.addFriend.FriendItem
import ru.filchacov.billsplittest.addFriend.FriendPresenter
import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.billActivityMVP.BillInterface

class AddFriendFragment : Fragment(), OnCLickFriend, AddFriendInterface {

    private lateinit var bill: Bill
    private lateinit var presenter: FriendPresenter

    companion object {
        @JvmStatic
        val TAG: String? = "AddFriendFragment"

        @JvmStatic
        fun getNewInstance(args: Bundle?): AddFriendFragment {
            val addFriendFragment = AddFriendFragment()
            addFriendFragment.arguments = args
            return addFriendFragment
        }
    }

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: RecyclerView.Adapter<*>
    private lateinit var mLayoutManager: RecyclerView.LayoutManager

    private lateinit var buttonInsert: Button
    private lateinit var editTextInsert: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bill = if (savedInstanceState != null) {
            savedInstanceState.getParcelable("bill")!!
        } else {
            requireArguments().getParcelable("bill")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_friends_fragment, container, false)


        presenter = FriendPresenter(this, bill)

        if (activity is BillInterface) {
            (activity as BillInterface).progressBarInvisible()
        }

        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        mAdapter = FriendAdapter(presenter.mFriendList, this)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.adapter = mAdapter

        buttonInsert = view.findViewById(R.id.button_insert)
        editTextInsert = view.findViewById(R.id.edittext_insert)


        buttonInsert.setOnClickListener {
            if (editTextInsert.text.isEmpty()) {
                editTextInsert.error = "Введите имя Вашего друга"
            } else {
                presenter.insertItem(editTextInsert.text.toString())
                editTextInsert.setText("")
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity is ShowUpButton) {
            (activity as ShowUpButton).showUpButton(true)
        }
        if (activity is MainActivityInterface) {
            (activity as MainActivityInterface?)!!.navigationDrawerInvisible()
        }
        if (activity is ToolbarSettings) {
            (activity as ToolbarSettings?)!!.setToolbarTitle(R.string.list_of_friends)
        }
        presenter.getFriends()
    }

    override fun clickFriend(number: Int) {
        presenter.clickFriend(number)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
    }


    override fun clickFriend(bill: Bill, friendItem: FriendItem) {
        (activity as OnClickFriendToBill).clickFriendToBill(bill, friendItem)
    }

    override fun updateAdapter() {
        mAdapter.notifyDataSetChanged()
    }
}