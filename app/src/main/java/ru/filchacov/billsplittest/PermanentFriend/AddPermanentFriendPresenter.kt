package ru.filchacov.billsplittest.PermanentFriend

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.AddPermanentFriendView
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.R
import java.util.*


class AddPermanentFriendPresenter(var view: PermanentFriendInterface) {
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

    fun updateList() {
        model.permanentFriendsList.addChildEventListener(object : ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildRemoved(p0: DataSnapshot) {
                val friendItem: FriendItem? = p0.getValue(FriendItem::class.java)
                val index: Int = getItemIndex(friendItem!!)
                mFriendList?.removeAt(index)
                view.updateAdapterForDelete(index)
            }

        })
    }

    fun insertItem(friendName: String) {
        model.setPermanentFriend(FriendItem(R.drawable.ic_android, friendName))
    }

    fun removeItem(position: Int) {
        model.permanentFriendsList.child(mFriendList!!.get(position).getKey()).removeValue()
    }

    fun getItemIndex(friendItem: FriendItem): Int {
        var index = 0
        for (i in 0 until mFriendList!!.size) {
            if (mFriendList!!.get(i).getKey().equals(friendItem.getKey())) {
                index = i
                break
            }
        }
        return index
    }

    fun getItemCount():Int {
        return mFriendList!!.size
    }
}