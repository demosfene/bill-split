package ru.filchacov.billsplittest.bill

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.Collections.emptyList

class Bill() : Parcelable {
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

    constructor(parcel: Parcel) : this() {
        receiptCode = parcel.readInt()
        user = parcel.readString().toString()
        cashTotalSum = parcel.readInt()
        shiftNumber = parcel.readInt()
        totalSum = parcel.readInt()
        ecashTotalSum = parcel.readInt()
        taxationType = parcel.readInt()
        ndsNo = parcel.readInt()
        dateTime = parcel.readString().toString()
        fiscalDriveNumber = parcel.readString().toString()
        fiscalDocumentNumber = parcel.readInt()
        fiscalSign = parcel.readInt()
        userInn = parcel.readString()!!
        operationType = parcel.readInt()
        kktRegId = parcel.readString()!!
        rawData = parcel.readString()!!
        requestNumber = parcel.readInt()
        operator = parcel.readString()!!
    }

    constructor(items: MutableList<Item>?, dateTime: String) : this() {
        this.items = items
        this.dateTime = dateTime
    }

    constructor(dateTime: String) : this() {
        this.dateTime = dateTime
    }


    class Item() : Parcelable {
        @SerializedName("name")
        var name: String? = null

        @SerializedName("quantity")
        var quantity: Int? = null

        @SerializedName("sum")
        var sum: Int? = null

        @SerializedName("price")
        var price: Int? = null

        constructor(parcel: Parcel) : this() {
            name = parcel.readString()
            quantity = parcel.readValue(Int::class.java.classLoader) as? Int
            sum = parcel.readValue(Int::class.java.classLoader) as? Int
            price = parcel.readValue(Int::class.java.classLoader) as? Int
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeValue(quantity)
            parcel.writeValue(sum)
            parcel.writeValue(price)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Item> {
            override fun createFromParcel(parcel: Parcel): Item {
                return Item(parcel)
            }

            override fun newArray(size: Int): Array<Item?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(receiptCode)
        parcel.writeString(user)
        parcel.writeInt(cashTotalSum)
        parcel.writeInt(shiftNumber)
        parcel.writeInt(totalSum)
        parcel.writeInt(ecashTotalSum)
        parcel.writeInt(taxationType)
        parcel.writeInt(ndsNo)
        parcel.writeString(dateTime)
        parcel.writeString(fiscalDriveNumber)
        parcel.writeInt(fiscalDocumentNumber)
        parcel.writeInt(fiscalSign)
        parcel.writeString(userInn)
        parcel.writeInt(operationType)
        parcel.writeString(kktRegId)
        parcel.writeString(rawData)
        parcel.writeInt(requestNumber)
        parcel.writeString(operator)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Bill> {
        override fun createFromParcel(parcel: Parcel): Bill {
            return Bill(parcel)
        }

        override fun newArray(size: Int): Array<Bill?> {
            return arrayOfNulls(size)
        }
    }

}