package ru.filchacov.billsplittest

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class AddFriendFragment(private var bill: Bill) : Fragment(), OnCLickFriend{

    private var mAuth: FirebaseAuth? = null

    private var mDataBase: DatabaseReference? = null

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private var mFriendList: ArrayList<FriendItem>? = null

    private var mFriendListDB: ArrayList<String>? = null

    private var buttonInsert: Button? = null
    private var editTextInsert: EditText? = null
    private var billCount: String? = null
    private var user: FirebaseUser? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.add_friends_fragment, container, false)

        mAuth = FirebaseAuth.getInstance()
        mDataBase = FirebaseDatabase.getInstance().reference
        user = mAuth!!.currentUser

        mFriendListDB = ArrayList()
        mFriendList = ArrayList()
        mDataBase!!.child("users").child(user!!.uid).child("friends").child(bill.dateTime).addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                Log.d("list123", p0.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadUserDB(dataSnapshot)
            }
        })

        mRecyclerView = view.findViewById(R.id.recyclerView)
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(context)
        mAdapter = FriendAdapter(mFriendList, this)
        mRecyclerView!!.layoutManager = mLayoutManager
        mRecyclerView!!.adapter = mAdapter

        buttonInsert = view.findViewById(R.id.button_insert)
        editTextInsert = view.findViewById(R.id.edittext_insert)

        buttonInsert!!.setOnClickListener {
            val name = editTextInsert!!.text.toString()
            if (editTextInsert!!.text.isEmpty()){
                editTextInsert!!.error = "Введите имя Вашего друга"
            } else {
                insertItem(bill.dateTime)
                editTextInsert!!.setText("")
            }
        }

        return view
    }

    private fun loadUserDB(dataSnapshot: DataSnapshot) {
        mFriendList?.clear()
        for (ds in dataSnapshot.children) {
            val friends = ds.value as String
            val key = ds.key
            mFriendList!!.add(FriendItem(R.drawable.ic_android, friends, key))
            mAdapter!!.notifyDataSetChanged()
        }
    }




    private fun insertItem(billTime: String) {
        writeNewFriend(user!!.uid, billTime)
    }

    private fun writeNewFriend(userId: String, billCounter: String) {
        mDataBase!!.child("users").child(userId).child("friends").child(billCounter).push().setValue(editTextInsert!!.text.toString())
    }

    override fun clickFriend(number: Int) {
        activity?.let {
            (it as BillActivity).clickFriend(bill, mFriendList!![number])
        }
    }


}