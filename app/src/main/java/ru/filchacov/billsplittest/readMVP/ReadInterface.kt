package ru.filchacov.billsplittest.readMVP

import ru.filchacov.billsplittest.ShowFriendFragment

interface ReadInterface: ShowFriendFragment {
    fun showTextEmptyList()
    fun hideTextEmptyList()
    fun updateData()

}