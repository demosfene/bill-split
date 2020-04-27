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
import ru.filchacov.billsplittest.AddFriend.FriendAdapter
import ru.filchacov.billsplittest.AddFriend.FriendPresenter
import ru.filchacov.billsplittest.Bill.Bill

class AddFriendFragment() : Fragment(), OnCLickFriend {

    private var bill: Bill? = null
    private var presenter: FriendPresenter? = null

    companion object {
        @JvmStatic val TAG: String? = "AddFriendFragment"

        @JvmStatic fun getNewInstance(args: Bundle?): AddFriendFragment{
            val addFriendFragment = AddFriendFragment()
            addFriendFragment.arguments = args
            return addFriendFragment
        }
    }

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private var buttonInsert: Button? = null
    private var editTextInsert: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_friends_fragment, container, false)
        bill = if (savedInstanceState != null){
            savedInstanceState.getParcelable("bill")
        }else{
            arguments!!.getParcelable("bill")
        }
        presenter = FriendPresenter(this, bill!!)

        presenter!!.getFriends()

        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        mAdapter = FriendAdapter(presenter!!.mFriendList, this)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = mAdapter

        buttonInsert = view.findViewById(R.id.button_insert)
        editTextInsert = view.findViewById(R.id.edittext_insert)

        buttonInsert!!.setOnClickListener {
            if (editTextInsert!!.text.isEmpty()){
                editTextInsert!!.error = "Введите имя Вашего друга"
            } else {
                presenter!!.insertItem(editTextInsert!!.text.toString())
                editTextInsert!!.setText("")
            }
        }

        return view
    }

    override fun clickFriend(number: Int) {
        presenter!!.clickFriend(number)

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("bill", bill)
    }

    fun updateAdapter() {
        mAdapter!!.notifyDataSetChanged()
    }


}