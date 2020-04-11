package ru.filchacov.billsplittest.BillInfo

class ApiResponse<DATA> {
    var code = 0
    var data: DATA? = null
    var error: String? = null
}