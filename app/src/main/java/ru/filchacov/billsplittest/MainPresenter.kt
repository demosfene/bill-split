package ru.filchacov.billsplittest

import kotlinx.coroutines.*

class MainPresenter(val view: MainActivityInterface) {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.userDao



    fun signOut() {
        if(userDao.getByEmail(model.auth.currentUser?.email) != null){
            userDao.delete(userDao.getByEmail(model.auth.currentUser?.email))
        }
        model.auth.signOut()
    }

    fun init(){
        getUserEmail()
    }

    fun getUserEmail() {
        CoroutineScope(Dispatchers.IO).launch {
            if(userDao.getByEmail(model.auth.currentUser?.email) != null) {
                val userEmail = withContext(Dispatchers.IO) {
                    userDao.getByUid(model.auth.currentUser?.uid).email
                }

                val userName = withContext(Dispatchers.IO) {
                    userDao.getByUid(model.auth.currentUser?.uid).name
                }
                view.setHeaderEmail(userEmail, userName)
            }

        }
    }
}