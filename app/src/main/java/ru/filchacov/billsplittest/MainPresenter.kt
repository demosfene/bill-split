package ru.filchacov.billsplittest

class MainPresenter() {
    private val model = ModelDB()

    fun signOut() {
        model.auth.signOut()
    }
}