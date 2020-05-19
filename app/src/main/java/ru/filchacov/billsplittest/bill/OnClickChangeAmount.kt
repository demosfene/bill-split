package ru.filchacov.billsplittest.bill

interface OnClickChangeAmount {
    fun clickPlus(position: Int)
    fun clickMinus(position: Int)
}