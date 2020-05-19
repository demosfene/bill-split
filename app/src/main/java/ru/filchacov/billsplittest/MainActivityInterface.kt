package ru.filchacov.billsplittest

interface MainActivityInterface {
    fun navigationDrawerInvisible()
    fun navigationDrawerVisible()
    fun setupDrawerContent()
    fun hideDrawerIndicator()
    fun setHeaderEmail(email: String, name: String)
    fun updateUserInfo()
}