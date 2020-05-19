package ru.filchacov.billsplittest.addFriend

import ru.filchacov.billsplittest.bill.Bill

interface AddFriendInterface {
    fun goToMainActivity()
    fun clickFriend(bill: Bill, friendItem: FriendItem)
    fun updateAdapter()
}