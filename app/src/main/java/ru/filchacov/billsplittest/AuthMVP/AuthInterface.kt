package ru.filchacov.billsplittest.AuthMVP

import com.google.firebase.auth.FirebaseUser
import ru.filchacov.billsplittest.User.User

interface AuthInterface {
    fun onClickRead()
    fun userValid(user: FirebaseUser)
    fun userNotValid()
}