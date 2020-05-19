package ru.filchacov.billsplittest.authMVP

import ru.filchacov.billsplittest.registration.UserAuthInterface

interface AuthInterface : UserAuthInterface {
    fun onClickRead()
    fun onLocalEnabled(name: String)
    fun btnEnable(b: Boolean)
}