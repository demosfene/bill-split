package ru.filchacov.billsplittest.Bill

interface BillLListInterface {
    fun updateAdapter()
    fun exitFromBill(bill:Bill)
    fun updateTotalSum(totalSum: Double)
    fun updateAdapterAmount(position: Int, billUer: BillUser)
}