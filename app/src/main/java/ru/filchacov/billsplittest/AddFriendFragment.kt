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

    /*private var mAuth: FirebaseAuth? = null
    private var mDataBase: DatabaseReference? = null*/

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

/*    private var mFriendList: ArrayList<FriendItem>? = null
    private var user: FirebaseUser? = null
    private var mFriendListDB: ArrayList<String>? = null*/

    private var buttonInsert: Button? = null
    private var editTextInsert: EditText? = null

//    var changedFriend: FriendItem? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_friends_fragment, container, false)
        bill = if (savedInstanceState != null){
            savedInstanceState.getParcelable("bill")
        }else{
            arguments!!.getParcelable("bill")
        }
        presenter = FriendPresenter(this, bill!!)

        presenter!!.getFriends()

        /*mAuth = FirebaseAuth.getInstance()
        mDataBase = FirebaseDatabase.getInstance().reference
        user = mAuth!!.currentUser

        mFriendListDB = ArrayList()
        mFriendList = ArrayList()*/

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

    /*private fun loadUserDB(dataSnapshot: DataSnapshot) {
        mFriendList!!.clear()
        var dataChildren = dataSnapshot.children
        var iter = dataChildren.iterator()
        while (iter.hasNext()) {
            var ds = iter.next()
            val map = ds.value as Map<String, String>
            var friend = FriendItem(R.drawable.ic_android, map["mText"])
            friend.setisSelected(map.get("isSelected").toString().toBoolean())
            friend.setKey(ds.key.toString())
            mFriendList!!.add(friend)
            mAdapter!!.notifyDataSetChanged()
            }

    }*/


/*    private fun insertItem(billTime: String) {
        val user = mAuth!!.currentUser
        writeNewFriend(user!!.uid)
    }*/

  /*  private fun writeNewFriend(userId: String) {
        var toDataBase = ToDataBase()
        toDataBase.toDatabse(mDataBase, "вася", false, userId, bill!!.dateTime, FriendItem(R.drawable.ic_android, "Вася"))
        mDataBase!!.child("users").child(userId).child("friends").child(bill!!.dateTime).child("savedFriends").push().setValue(FriendItem(R.drawable.ic_android, editTextInsert!!.text.toString()))
    }*/

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