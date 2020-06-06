package ru.filchacov.billsplittest.db.bill

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.bill.Bill

class BillRepository(var mDao: BillDao, var modelDB: ModelDB, var itemDao: ItemDao) : BillDao {


    override fun searchBill(dateTime: String): Int {
        return mDao.searchBill(dateTime)
    }

    override fun insert(billDB: Bill) {
        mDao.insert(billDB)
    }

    override fun update(billDB: Bill) {
        mDao.update(billDB)
    }

    override fun delete(billDB: Bill) {
        mDao.delete(billDB)
    }

    override fun getByDateTime(dateTime: String): Bill {
        return mDao.getByDateTime(dateTime)
    }

    fun loadBill(dateTime: String) {
        modelDB.getBill(dateTime).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val bill = dataSnapshot.getValue(Bill::class.java)
                if (bill != null) {
                    insert(bill)
                    for (item in bill.items) {
                        val itemDB = Item(bill.dateTime, item.name, item.quantity!!, item.sum!!, item.price!!)
                        itemDao.insert(itemDB)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


    override fun delete() {
        mDao.delete()
    }

}