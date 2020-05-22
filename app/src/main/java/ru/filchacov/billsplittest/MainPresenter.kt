package ru.filchacov.billsplittest

import kotlinx.coroutines.*

class MainPresenter(val view: MainActivityInterface) {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.userDao
    private val usersBillDao = userDB.usersBillsDao
    private val billDB = userDB.billDao



    fun signOut() {
        if(userDao.getByEmail(model.auth.currentUser?.email) != null){
            userDao.delete(userDao.getByEmail(model.auth.currentUser?.email))
        }
        model.auth.signOut()
        usersBillDao.delete()
        billDB.delete()

    }

    fun init(){
        getUserEmail()
    }

    private fun getUserEmail() {
        CoroutineScope(Dispatchers.IO).launch {
            if(userDao.getByEmail(model.auth.currentUser?.email) != null) {
                val userEmail = withContext(Dispatchers.IO) {
                    userDao.getByUid(model.auth.currentUser?.uid).email
                }

                val userName = withContext(Dispatchers.IO) {
                    userDao.getByUid(model.auth.currentUser?.uid).name
                }
                CoroutineScope(Dispatchers.Main).launch {
                    view.setHeaderEmail(userEmail, userName)
                }
            }

        }
    }
}