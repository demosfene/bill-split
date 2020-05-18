package ru.filchacov.billsplittest.AboutUserMVP

import android.util.Log
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.UserProfileChangeRequest
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.AuthMVP.AuthInterface
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.db.User
import java.util.*

class AboutUserPresenter(var view: AboutUserInterface) {

    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.getuserDao()

    fun init() {

    }

    fun getName(): String? {
        return userDao.getById(model.user.uid).name
    }

    fun getEmail(): String? {
        return userDao.getById(model.user.uid).email
    }

    fun getPassword(): String? {
        return "Введите новый пароль"
    }

    fun getPhone(): String? {
        return userDao.getById(model.user.uid).phone
    }

    fun updateUser(name: String, email:String, phone: String, password: String) {
        if (!password.equals("")) {
            model.user.updatePassword(password)
        }
        val newUser = userDao.getById(model.user.uid)
        userDao.delete(newUser)
        newUser.name = name
        newUser.email = email
        newUser.phone = phone
        userDao.insert(newUser)
        val test = userDao.getById(model.user.uid)
        val testList = userDao.all

        // Редактирование в FireBase
        model.user.updateEmail(email)
        val updates = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build()
        model.user.updateProfile(updates).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d("UpdUser","Profile's updates")
            } else Log.d("UpdUser","Profile's not updates")
        }
        model.authReference.child("users").child(model.user.uid).child("phone").setValue(phone)
    }
}