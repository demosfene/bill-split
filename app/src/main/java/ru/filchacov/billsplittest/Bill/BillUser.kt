package ru.filchacov.billsplittest.Bill


import android.os.Parcel
import android.os.Parcelable

class BillUser() : Parcelable {

    var item: Bill.Item? = null
    var amount: Int = 0

    constructor(parcel: Parcel) : this() {
        item = parcel.readParcelable(Bill.Item::class.java.classLoader)
        amount = parcel.readInt()
    }

    fun setBillItem(item: Bill.Item){
        this.item = item
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(item, flags)
        parcel.writeInt(amount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BillUser> {
        override fun createFromParcel(parcel: Parcel): BillUser {
            return BillUser(parcel)
        }

        override fun newArray(size: Int): Array<BillUser?> {
            return arrayOfNulls(size)
        }

    }

}