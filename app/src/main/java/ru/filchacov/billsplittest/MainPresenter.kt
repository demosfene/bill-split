package ru.filchacov.billsplittest

import ru.filchacov.billsplittest.db.User

class MainPresenter {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.getuserDao()

    fun signOut() {
        model.auth.signOut()
        val currentUser: User = userDao.getByCounter(userDao.all.size)
        currentUser.setSignIn(false)
        userDao.update(currentUser)
    }
}