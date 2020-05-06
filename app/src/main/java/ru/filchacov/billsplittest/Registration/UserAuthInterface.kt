package ru.filchacov.billsplittest.Registration

import com.google.firebase.auth.FirebaseUser

interface UserAuthInterface {
    fun userValid(user: FirebaseUser)
    fun userNotValid()
}