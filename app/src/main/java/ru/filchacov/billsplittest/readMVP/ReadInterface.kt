package ru.filchacov.billsplittest.readMVP

import ru.filchacov.billsplittest.ShowFriendFragment
import ru.filchacov.billsplittest.bill.Bill

interface ReadInterface : ShowFriendFragment {
    fun showTextEmptyList()
    fun hideTextEmptyList()
    fun updateData()
    fun addItemToAdapter(bill: Bill)

}