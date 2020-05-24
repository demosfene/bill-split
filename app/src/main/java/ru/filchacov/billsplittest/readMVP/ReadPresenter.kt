package ru.filchacov.billsplittest.readMVP

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.db.bill.BillRepository
import ru.filchacov.billsplittest.db.usersBills.UsersBills

class ReadPresenter(private val view: ReadInterface) {
    private var result: List<UsersBills> = ArrayList()
    var listTemp: ArrayList<Bill> = ArrayList()
    private val modelDB = ModelDB()
    private val userDB = App.getInstance().database
    private val usersBills = userDB.usersBillsDao
    private val itemDao = userDB.itemDao
    private val billRepository = BillRepository(userDB.billDao, modelDB, itemDao)

    fun initPresenter() {
        getDataFromDB()
        if (result.isEmpty()) {
            view.showTextEmptyList()
        }
    }

    private fun updateData() {
        view.updateData()
    }

    private fun getDataFromDB() {
        result = usersBills.all
        for (usersBills in result) {
            if (billRepository.searchBill(usersBills.billUID) != 1) {
                billRepository.loadBill(usersBills.billUID)
                checkUpdateBillList()
            } else {
                listTemp.add(billRepository.getByDateTime(usersBills.billUID))
            }
        }
    }

    private fun checkUpdateBillList() {
        CoroutineScope(Dispatchers.IO).launch {
            result = usersBills.all
            while (listTemp.size != result.size) {
                for (usersBills in result) {
                    if (billRepository.searchBill(usersBills.billUID) == 1) {
                        CoroutineScope(Dispatchers.Main).launch {
                            val bill = billRepository.getByDateTime(usersBills.billUID)
                            if (listTemp.none { it.dateTime == bill.dateTime }) {
                                listTemp.add(bill)
                                updateData()
                            }
                        }
                    }
                }
            }
        }
    }


    fun onNoteClick(position: Int) {
        val bill = listTemp[position]
        view.showFriendFragment(bill)
    }

}
