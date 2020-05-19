package ru.filchacov.billsplittest.registration

import com.google.firebase.auth.FirebaseUser

interface UserAuthInterface {
    fun userValid(user: FirebaseUser)
    fun userNotValid()
    fun userValidForLocalDB()
}