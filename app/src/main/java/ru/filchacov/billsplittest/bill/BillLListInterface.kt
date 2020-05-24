package ru.filchacov.billsplittest.bill

interface BillLListInterface {
    fun updateAdapter()
    fun exitFromBill(bill:Bill)
    fun updateTotalSum(totalSum: Double)
    fun updateAdapterAmount(position: Int, billUer: BillUser)
    fun showSumZero()
}