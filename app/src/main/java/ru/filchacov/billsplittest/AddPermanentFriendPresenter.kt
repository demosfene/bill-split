package ru.filchacov.billsplittest

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.AddFriend.FriendItem
import java.util.ArrayList

class AddPermanentFriendPresenter(var view: AddPermanentFriendView) {
    var mFriendList: ArrayList<FriendItem>? = ArrayList()
    private val model = ModelDB()

    fun getPermanentFriends() {
        model.permanentFriendsList.addValueEventListener(object : ValueEventListener {
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
        model.setPermanentFriend(FriendItem(R.drawable.ic_android, friendName))
    }
}