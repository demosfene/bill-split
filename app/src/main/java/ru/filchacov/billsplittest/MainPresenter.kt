package ru.filchacov.billsplittest

import ru.filchacov.billsplittest.db.User

class MainPresenter {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.getuserDao()

    fun signOut() {
        if(userDao.getByEmail(model.auth.currentUser?.email) != null){
            userDao.delete(userDao.getByEmail(model.auth.currentUser?.email))
        }
        model.auth.signOut()
    }
}