package ru.filchacov.billsplittest.AddFriend

import android.os.Parcel
import android.os.Parcelable

class FriendItem(private var mImageResource: Int, private var mText: String?, private var mUid: String?): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    fun getmImageResource(): Int {
        return mImageResource
    }

    fun getmText(): String? {
        return mText
    }

    fun getmUid(): String? {
        return mUid
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(mImageResource)
        parcel.writeString(mText)
        parcel.writeString(mUid)
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