package ru.filchacov.billsplittest.billActivityMVP

import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.addFriend.FriendItem
import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.billInfo.BillService
import ru.filchacov.billsplittest.db.bill.Item
import ru.filchacov.billsplittest.db.billOfUser.BillOfUser
import ru.filchacov.billsplittest.db.savedFriends.SavedFriends
import ru.filchacov.billsplittest.db.usersBills.UsersBills
import java.text.SimpleDateFormat
import java.util.*

class BillActivityPresenter(billParameters: String, var view: BillInterface) {

    private val qrInfo = ("?$billParameters").toUri()
    private var time = ""
    private val modelDB = ModelDB()
    private var bill: Bill? = null
    private val userDB = App.getInstance().database
    private val userDao = userDB.userDao
    private val billDBDao = userDB.billDao
    private val itemDao = userDB.itemDao
    private val usersBillsDao = userDB.usersBillsDao
    private val billOfUserDao = userDB.billOfUserDao
    private val savedFriendsDao = userDB.savedFriendsDao

    fun getBillInfo() {

        time = qrInfo.getQueryParameter("t")!!.removeRange(8, 9)
        qrInfo.getQueryParameter("fn")!!
        qrInfo.getQueryParameter("i")!!
        qrInfo.getQueryParameter("fp")!!
        qrInfo.getQueryParameter("n")!!
        qrInfo.getQueryParameter("s")!!
        if (time.length > 12) {
            time = time.dropLast(2)
        }

        val date = SimpleDateFormat("yyyyMMddHHmm").parse(time)
        time = String.format("%td.%tm.%tY+%tR", date, date, date, date)


        val checkService = BillService()


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    checkService.api.checkGet(
                            fn = qrInfo.getQueryParameter("fn")!!
                            , fd = qrInfo.getQueryParameter("i")!!
                            , fp = qrInfo.getQueryParameter("fp")!!
                            , n = qrInfo.getQueryParameter("n")!!.toInt()
                            , s = qrInfo.getQueryParameter("s")!!
                            , t = time
                            , qr = 0
                    ).execute().body()!!
                }
                if (result.data != null) {
                    bill = result.data!!
                    writeNewBill(result.data!!, result.data!!.dateTime)
                    view.progressBarInvisible()
                }
            } catch (e: Exception) {
                view.progressBarInvisible()
                view.showErrorDialog()
            }
        }

    }

    private fun writeNewBill(bill: Bill, dateTime: String) {
        modelDB.writeNewBill(bill, dateTime)
        modelDB.getUserBill(dateTime).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("DB Error", databaseError.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataChildren = dataSnapshot.children
                val iter = dataChildren.iterator()
                if (!iter.hasNext()) {
                    billDBDao.insert(bill)
                    for (item in bill.items) {
                        val itemDB = Item(bill.dateTime, item.name, item.quantity!!, item.sum!!, item.price!!)
                        itemDao.insert(itemDB)
                    }
                    val usersBills = UsersBills(dateTime)
                    usersBillsDao.insert(usersBills)
                    val billUUID = UUID.randomUUID().toString()
                    val billOfUser = BillOfUser(dateTime, billUUID)
                    billOfUserDao.insert(billOfUser)
                    if(userDao.getByEmail(modelDB.auth.currentUser?.email) != null) {
                        val friendKey = UUID.randomUUID().toString()
                        val friendName = userDao.getByUid(modelDB.auth.currentUser?.uid)?.name
                        modelDB.setFriend(dateTime, friendKey,FriendItem(mText = friendName))
                        val savedFriendDB = SavedFriends(bill.dateTime, false, friendKey, friendName, 0)
                        savedFriendsDao.insert(savedFriendDB)
                    }
                    view.showInfoBill(bill)
                } else {
                    view.showBillIsDialog(bill)
                }
            }
        })
    }
}