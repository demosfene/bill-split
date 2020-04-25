package ru.filchacov.billsplittest.Bill

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.BillActivity
import ru.filchacov.billsplittest.BillListFragment
import ru.filchacov.billsplittest.ExitFromBill
import ru.filchacov.billsplittest.ModelDB

class BillListPresenter(val view: BillListFragment, val bill: Bill, val friendItem: FriendItem) {

    var listBill = ArrayList<BillUser>()
    var listBillDB = ArrayList<BillUser>()
    private val model = ModelDB()
    private var totalSum = 0

    fun initBillList() {
        for (item in bill.items) {
            val billUser = BillUser()
            billUser.setBillItem(item)
            listBill.add(billUser)
        }
    }

    fun saveBillForFriend(){
        model.setBillForFriend(bill.dateTime, friendItem.getmText().toString(), listBillDB)
        model.isSelected(bill.dateTime, friendItem.getKey())
        (view.activity as ExitFromBill).exitBill(bill)
    }


    fun updateTotalSum(list: ArrayList<BillUser>){
        totalSum = 0
        for (item in list)
            totalSum+= item.amount * item.item!!.price!!
        view.updateTotalSum((totalSum.toDouble())/100)
    }

    fun plus(position: Int) {
        val item = listBill[position]
        if(item.amount < item.item!!.quantity!!){
            listBill[position].amount++
            view.updateAdapterAmount(position, item)
            var has = false
            for (itemDB in listBillDB) {
                if (itemDB == item) {
                    has = true
                }
            }
            if (!has){
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
        model.getBillForFriend(bill.dateTime, friendItem.getmText()!!)
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
}