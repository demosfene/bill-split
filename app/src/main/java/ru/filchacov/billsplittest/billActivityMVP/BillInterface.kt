package ru.filchacov.billsplittest.billActivityMVP

import ru.filchacov.billsplittest.bill.Bill
import ru.filchacov.billsplittest.ShowFriendFragment

interface BillInterface : ShowFriendFragment {
    fun progressBarInvisible()
    fun showErrorDialog()
    fun showInfoBill(bill: Bill)
    fun showBillIsDialog(bill: Bill)
}