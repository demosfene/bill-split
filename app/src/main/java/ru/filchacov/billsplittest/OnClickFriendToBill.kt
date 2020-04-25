package ru.filchacov.billsplittest

import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill

interface OnClickFriendToBill {
    fun clickFriendToBill(bill: Bill, friendItem: FriendItem)
}