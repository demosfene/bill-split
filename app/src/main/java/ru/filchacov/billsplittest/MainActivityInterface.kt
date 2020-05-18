package ru.filchacov.billsplittest

import ru.filchacov.billsplittest.db.User

interface MainActivityInterface {
    fun navigationDrawerInvisible()
    fun navigationDrawerVisible()
    fun setupDrawerContent()
    fun hideDrawerIndicator()
}