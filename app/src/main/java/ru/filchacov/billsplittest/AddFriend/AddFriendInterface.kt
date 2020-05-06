package ru.filchacov.billsplittest.AddFriend

import ru.filchacov.billsplittest.Bill.Bill

interface AddFriendInterface {
    fun goToMainActivity()
    fun clickFriend(bill: Bill, friendItem: FriendItem)
    fun updateAdapter()
}