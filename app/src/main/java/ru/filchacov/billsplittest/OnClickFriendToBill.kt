package ru.filchacov.billsplittest

import ru.filchacov.billsplittest.addFriend.FriendItem
import ru.filchacov.billsplittest.bill.Bill

interface OnClickFriendToBill {
    fun clickFriendToBill(bill: Bill, friendItem: FriendItem)
}