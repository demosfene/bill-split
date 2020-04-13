package ru.filchacov.billsplittest

import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

class Bill {
    @SerializedName("receiptCode")
    var receiptCode = 0
    @SerializedName("user")
    var user = "" // Дата совершения покупки
    @SerializedName("items")
    var items = emptyList<Item>() // Лист списка покупок (вся инфа по покупкам тут)
    @SerializedName("cashTotalSum")
    var cashTotalSum: Int = 0
    @SerializedName("shiftNumber")
    var shiftNumber: Int = 0
    @SerializedName("totalSum")
    var totalSum = 0 // Сумма чека
    @SerializedName("ecashTotalSum")
    var ecashTotalSum = 0 // Общая сумма покупки
    @SerializedName("taxationType")
    var taxationType = 0
    @SerializedName("ndsNo")
    var ndsNo = 0
    @SerializedName("dateTime")
    var dateTime = "" // Дата совершения покупки
    @SerializedName("fiscalDriveNumber")
    var fiscalDriveNumber = "" // Фискальный накопитель
    @SerializedName("fiscalDocumentNumber")
    var fiscalDocumentNumber = 0 // Номер фискального документа
    @SerializedName("fiscalSign")
    var fiscalSign = 0
    @SerializedName("userInn")
    var userInn = ""
    @SerializedName("operationType")
    var operationType = 0
    @SerializedName("kktRegId")
    var kktRegId = "" // РЕГ.НОМЕР ККТ
    @SerializedName("rawData")
    var rawData = ""
    @SerializedName("requestNumber")
    var requestNumber = 0
    @SerializedName("operator")
    var operator = ""

    constructor(items: MutableList<Item>?, dateTime: String) {
        this.items = items
        this.dateTime = dateTime
    }


    class Item {
        @SerializedName("name") var name: String? = null
        @SerializedName("quantity") var quantity: Int? = null
        @SerializedName("sum") var sum: Int? = null
        @SerializedName("price") var price: Int? = null
    }

}