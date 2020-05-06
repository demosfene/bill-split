package ru.filchacov.billsplittest.ReadMVP

import ru.filchacov.billsplittest.ShowFriendFragment

interface ReadInterface: ShowFriendFragment {
    fun showTextEmptyList()
    fun hideTextEmptyList()
    fun updateData()

}