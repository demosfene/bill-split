package ru.filchacov.billsplittest

class MainPresenter {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.getUserDao()

    fun signOut() {
        if(userDao.getByEmail(model.auth.currentUser?.email) != null){
            userDao.delete(userDao.getByEmail(model.auth.currentUser?.email))
        }
        model.auth.signOut()
    }
}