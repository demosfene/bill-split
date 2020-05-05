package ru.filchacov.billsplittest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.filchacov.billsplittest.AddFriend.FriendAdapter
import ru.filchacov.billsplittest.AddFriend.FriendPresenter
import ru.filchacov.billsplittest.Bill.Bill



class AddPermanentFriendView : Fragment(), OnCLickFriend {

    private var presenter: AddPermanentFriendPresenter? = null
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private var buttonInsert: FloatingActionButton? = null
    private var editTextInsert: EditText? = null

    val text = "Да, это Ваш друг"
    val duration = Toast.LENGTH_SHORT

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_permanent_friend_view, container, false)
        presenter = AddPermanentFriendPresenter(this)

        presenter!!.getPermanentFriends()
        presenter!!.updateList()

        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        mAdapter = FriendAdapter(presenter!!.mFriendList, this)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = mAdapter

        buttonInsert = view.findViewById(R.id.button_insert)
        editTextInsert = view.findViewById(R.id.edittext_insert)

        buttonInsert!!.setOnClickListener {
            if (editTextInsert!!.text.isEmpty()) {
                editTextInsert!!.error = "Введите имя Вашего друга"
            } else {
                presenter!!.insertItem(editTextInsert!!.text.toString())
                editTextInsert!!.setText("")
            }


        }



        return view
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> presenter!!.removeItem(item.groupId)
        }
        return super.onContextItemSelected(item)
    }



    companion object {
        @JvmStatic
        val TAG: String? = "AddPermanentFriendFragment"
    }

    fun updateAdapter() {
        mAdapter!!.notifyDataSetChanged()
    }

    override fun clickFriend(number: Int) {
        Toast.makeText(context, text, duration).show()
    }

}
