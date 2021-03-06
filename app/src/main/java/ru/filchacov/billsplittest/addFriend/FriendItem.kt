package ru.filchacov.billsplittest.addFriend

import android.os.Parcel
import android.os.Parcelable

class FriendItem(
        private var mImageResource: Int = 0,
        private var mText: String? = "",
        private var sum:Int = 0
): Parcelable{


    private var isSelected: Boolean = false

    private var key: String = ""

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt()) {
        isSelected = parcel.readByte() != 0.toByte()
        key = parcel.readString().toString()
    }

    fun getKey(): String {
        return key
    }

    fun setKey(key: String) {
        this.key = key
    }

    fun getisSelected(): Boolean {
        return isSelected
    }

    fun setisSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }

    fun getmImageResource(): Int {
        return mImageResource
    }

    fun getmText(): String? {
        return mText
    }

    fun setSum(sum: Int){
        this.sum = sum
    }

    fun getSum(): Int{
        return sum
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mImageResource)
        parcel.writeString(mText)
        parcel.writeInt(sum)
        parcel.writeByte(if (isSelected) 1 else 0)
        parcel.writeString(key)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendItem> {
        override fun createFromParcel(parcel: Parcel): FriendItem {
            return FriendItem(parcel)
        }

        override fun newArray(size: Int): Array<FriendItem?> {
            return arrayOfNulls(size)
        }
    }


}