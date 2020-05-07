package ru.filchacov.billsplittest.BillActivityMVP

import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.ShowFriendFragment

interface BillInterface : ShowFriendFragment {
    fun progressBarInvisible()
    fun showErrorDialog()
    fun showBillIsDialog(bill: Bill)
}