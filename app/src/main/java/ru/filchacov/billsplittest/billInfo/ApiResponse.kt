package ru.filchacov.billsplittest.billInfo

class ApiResponse<DATA> {
    var code = 0
    var data: DATA? = null
    var error: String? = null
}