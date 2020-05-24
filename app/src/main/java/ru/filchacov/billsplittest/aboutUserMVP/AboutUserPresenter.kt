package ru.filchacov.billsplittest.aboutUserMVP

import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.db.user.User

class AboutUserPresenter(var view: AboutUserInterface) {

    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.getUserDao()

    fun init() {

    }

    fun getName(): String? {
        return userDao.getByUid(model.user.uid).name
    }

    fun getEmail(): String? {
        return userDao.getByUid(model.user.uid).email
    }

    fun getPassword(): String? {
        return "Введите новый пароль"
    }

    fun getPhone(): String? {
        return userDao.getByUid(model.user.uid).phone
    }

    fun updateUser(name: String, email:String, phone: String, password: String, oldPassword: String) {
        val newUser = userDao.getByUid(model.user.uid)
        userDao.delete(newUser)
        newUser.name = name
        newUser.email = email
        newUser.phone = phone
        userDao.insert(newUser)
        val test = userDao.getByUid(model.user.uid)
        val testList = userDao.all

        // Редактирование в FireBase
        if (email == "" && password != "") {
            updatePassword(password)
        } else if (password == "" && email != ""){
            updateEmail(email)
        } else if (!(password == "" && email == "")) {
            updatePasswordEmail(password, email)
        }
        model.authReference.child("users").child(model.user.uid).setValue(
                User(email, model.user.uid, name, phone))
                .addOnCompleteListener {
                    if (it.isSuccessful) Log.d("UpdUser", "FBDatabase update")
                    else Log.d("UpdUser", it.exception.toString() + "FBDATABASE")
                }
    }

    private fun updatePasswordEmail(password: String, email: String) {
        if (password != "") {
            model.user.updatePassword(password).addOnCompleteListener(object: OnCompleteListener<Void>{
                override fun onComplete(p0: Task<Void>) {
                    if (p0.isSuccessful) {
                        Log.d("UpdUser", "Password update")
                        var oldUser = FirebaseAuth.getInstance().currentUser
                        var credential = model.user.email?.let {
                            EmailAuthProvider .getCredential(it, password)
                        }
                        if (credential != null) {
                            oldUser!!.reauthenticate(credential).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Log.d("UpdUser", "User re-authenticated.")
                                    oldUser.updateEmail(email).addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Log.d("UpdUser", "Email update")
                                        } else Log.d("UpdUser", it.exception.toString() + "EMAIL")
                                    }
                                } else Log.d("UpdUser", it.exception.toString() + "CREDENTIAL")
                            }
                        }
                    } else Log.d("UpdUser", p0.exception.toString() + "PASSWORD")
                }
            })
        }
    }

    private fun updatePassword(password: String) {
        model.user.updatePassword(password).addOnCompleteListener {
            if (it.isSuccessful)Log.d("UpdUser", "Password update")
            else Log.d("UpdUser", it.exception.toString() + "PASSWORD_SINGE")
        }
    }

    private fun updateEmail(email: String) {
        model.user.updateEmail(email).addOnCompleteListener {
            if (it.isSuccessful)Log.d("UpdUser", "Email update")
            else Log.d("UpdUser", it.exception.toString() + "EMAIL_SINGE")
        }
    }
}