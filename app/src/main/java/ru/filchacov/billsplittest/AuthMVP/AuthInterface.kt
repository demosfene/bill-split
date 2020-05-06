package ru.filchacov.billsplittest.AuthMVP

import com.google.firebase.auth.FirebaseUser
import ru.filchacov.billsplittest.Registration.UserAuthInterface
import ru.filchacov.billsplittest.User.User

interface AuthInterface : UserAuthInterface {
    fun onClickRead()
}