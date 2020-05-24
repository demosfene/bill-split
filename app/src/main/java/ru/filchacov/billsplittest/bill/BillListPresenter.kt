package ru.filchacov.billsplittest.bill

import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.addFriend.FriendItem
import ru.filchacov.billsplittest.ModelDB

class BillListPresenter(var view: BillLListInterface, var bill: Bill) {

    private var mFriendItem: FriendItem? = null

    constructor(view: BillLListInterface, bill: Bill,  friendItem: FriendItem) : this(view, bill) {
        mFriendItem = friendItem
    }

    var listBill = ArrayList<BillUser>()
    private var listBillDB = ArrayList<BillUser>()
    private val model = ModelDB()
    private var totalSum = 0

    fun initBillList() {
        bill
        for (item in bill.items) {
            val billUser = BillUser()
            billUser.setBillItem(item)
            listBill.add(billUser)
        }
    }

    fun saveBillForFriend() {
        if (totalSum != 0) {
            model.setBillForFriend(bill.dateTime, mFriendItem?.getKey(), listBillDB)
            model.isSelected(bill.dateTime, mFriendItem?.getKey())
            model.setSumToFriend(bill.dateTime, mFriendItem?.getKey(), totalSum)
            view.exitFromBill(bill)
        }else{
            view.showSumZero()
        }
    }


    fun updateTotalSum(list: ArrayList<BillUser>) {
        totalSum = 0
        for (item in list)
            totalSum += item.amount * item.item!!.price!!
        view.updateTotalSum((totalSum.toDouble()) / 100)
    }

    fun plus(position: Int) {
        val item = listBill[position]
        if (item.amount < item.item!!.quantity!!) {
            listBill[position].amount++
            view.updateAdapterAmount(position, item)
            var has = false
            for (itemDB in listBillDB) {
                if (itemDB == item) {
                    has = true
                }
            }
            if (!has) {
                listBillDB.add(item)
            }
            updateTotalSum(listBillDB)
        }

    }

    fun minus(position: Int) {
        val item = listBill[position]
        if (item.amount > 0) {
            listBill[position].amount--
            view.updateAdapterAmount(position, item)
            val iterator = listBillDB.iterator()
            while (iterator.hasNext()) {
                val iterItem = iterator.next()
                if (iterItem.amount == 0) {
                    iterator.remove()
                }
                updateTotalSum(listBillDB)
            }
        }
    }

    fun loadBillList() {
        model.getBillForFriend(bill.dateTime, mFriendItem?.getmText()!!)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        listBill.clear()
                        val dataChildren = dataSnapshot.children
                        val iter = dataChildren.iterator()
                        while (iter.hasNext()) {
                            val ds = iter.next()
                            val map = ds.value as Map<*, *>
                            val billDB = BillUser()
                            billDB.amount = map["amount"].toString().toInt()
                            val itemMap = map["item"] as Map<*, *>
                            val billItem = Bill.Item()
                            billItem.price = itemMap["price"].toString().toInt()
                            billItem.quantity = itemMap["quantity"].toString().toInt()
                            billItem.name = itemMap["name"].toString()
                            billItem.sum = itemMap["sum"].toString().toInt()
                            billDB.item = billItem
                            listBill.add(billDB)
                        }
                        updateTotalSum(listBill)
                        view.updateAdapter()
                    }

                })
    }

    fun loadInfoBill() {
        model.getInfoBill(bill.dateTime)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        listBill.clear()
                        val dataChildren = dataSnapshot.children
                        val iter = dataChildren.iterator()
                        while (iter.hasNext()) {
                            val ds = iter.next()
                            val map = ds.value as Map<*, *>
                            val billDB = BillUser()
                            //billDB.amount = map["amount"].toString().toInt()
                           // val itemMap = map["items"] as Map<*, *>
                            val billItem = Bill.Item()
                            billItem.price = map["price"].toString().toInt()
                            billItem.quantity = map["quantity"].toString().toInt()
                            billItem.name = map["name"].toString()
                            billItem.sum = map["sum"].toString().toInt()
                            billDB.item = billItem
                            listBill.add(billDB)
                        }
                        //updateTotalSum(listBill)
                        view.updateAdapter()
                    }

                })
    }

}