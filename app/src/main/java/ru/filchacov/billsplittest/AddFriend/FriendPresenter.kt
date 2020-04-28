package ru.filchacov.billsplittest.AddFriend

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.*
import ru.filchacov.billsplittest.Bill.Bill
import java.util.*

class FriendPresenter(var view: AddFriendFragment, var bill: Bill) {
    var mFriendList: ArrayList<FriendItem>? = ArrayList()

    private val model = ModelDB()


    fun getFriends() {
        model.getFriendsList(bill.dateTime).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("list123", databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mFriendList!!.clear()
                val dataChildren = dataSnapshot.children
                val iter = dataChildren.iterator()
                while (iter.hasNext()) {
                    val ds = iter.next()
                    val map = ds.value as Map<*, *>
                    val friend = FriendItem(R.drawable.ic_android, map["mText"].toString())
                    friend.setisSelected(map["isSelected"].toString().toBoolean())
                    friend.setKey(ds.key.toString())
                    mFriendList!!.add(friend)

                }
                view.updateAdapter()
            }
        })
    }

    fun insertItem(friendName: String) {
        model.setFriend(bill.dateTime, FriendItem(R.drawable.ic_android, friendName))
    }

    fun clickFriend(number: Int) {
        (view.activity as OnClickFriendToBill).clickFriendToBill(bill, mFriendList!![number])
    }

    fun goToMainActivity() {
        if (view.activity is MainActivity)
            (view.activity as MainActivity).showMainFragment()
        else {
            (view.activity as GoToMainActivity).goToMainActivity()
        }
    }

}