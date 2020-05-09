package ru.filchacov.billsplittest.AuthMVP

import ru.filchacov.billsplittest.Registration.UserAuthInterface

interface AuthInterface : UserAuthInterface {
    fun onClickRead()
    fun onLocalEnabled(name: String)
}