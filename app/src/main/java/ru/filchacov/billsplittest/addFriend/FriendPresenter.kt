package ru.filchacov.billsplittest.addFriend

import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.MainActivity
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.R
import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.db.savedFriends.SavedFriends
import java.util.*

class FriendPresenter(var view: AddFriendInterface, var bill: Bill) {
    var mFriendList: ArrayList<FriendItem> = ArrayList()

    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val savedFriendsDao = userDB.savedFriendsDao
    private val billOfUserDao = userDB.billOfUserDao
    private val billOfUser = billOfUserDao.getBillOfUserByBillUid(bill.dateTime)

    fun getFriends() {
        val savedFriendsList = savedFriendsDao.getFriendsById(billOfUser[0].billUid)
        for (friend in savedFriendsList) {
            val friendItem = FriendItem(R.drawable.ic_android, friend.name, friend.totalSum)
            friendItem.setKey(friend.key)
            friendItem.setisSelected(friend.isSelected)
            mFriendList.add(friendItem)
        }
//        view.updateAdapter()
//        model.getFriendsList(bill.dateTime).addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.d("list123", databaseError.toString())
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                mFriendList!!.clear()
//                val dataChildren = dataSnapshot.children
//                val iter = dataChildren.iterator()
//                while (iter.hasNext()) {
//                    val ds = iter.next()
//                    val map = ds.value as Map<*, *>
//                    val friend = FriendItem(R.drawable.ic_android, map["mText"].toString())
//                    friend.setisSelected(map["isSelected"].toString().toBoolean())
//                    friend.setKey(ds.key.toString())
//                    mFriendList!!.add(friend)
//
//                }
//                view.updateAdapter()
//            }
//        })
    }

    fun insertItem(friendName: String) {
        val newFriendItem = FriendItem(R.drawable.ic_android, friendName)
        newFriendItem.setKey(UUID.randomUUID().toString())
        model.setFriend(bill.dateTime, newFriendItem.getKey(), newFriendItem)
        val savedFriendDB = SavedFriends(billOfUser[0].billUid, false, newFriendItem.getKey(), newFriendItem.getmText(), newFriendItem.getSum())
        savedFriendsDao.insert(savedFriendDB)
        mFriendList.add(newFriendItem)
        view.updateAdapter()
    }

    fun clickFriend(number: Int) {
        view.clickFriend(bill, mFriendList[number])
    }





}