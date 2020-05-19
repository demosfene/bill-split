package ru.filchacov.billsplittest.aboutUserMVP

import android.util.Log
import com.google.firebase.auth.UserProfileChangeRequest
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.ModelDB

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

    fun updateUser(name: String, email:String, phone: String, password: String) {
        if (!password.equals("")) {
            model.user.updatePassword(password)
        }
        val newUser = userDao.getByUid(model.user.uid)
        userDao.delete(newUser)
        newUser.name = name
        newUser.email = email
        newUser.phone = phone
        userDao.insert(newUser)
        val test = userDao.getByUid(model.user.uid)
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