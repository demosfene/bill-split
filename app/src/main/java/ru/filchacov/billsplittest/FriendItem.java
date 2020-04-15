package ru.filchacov.billsplittest;

import java.util.UUID;

public class FriendItem {
    private int mImageResource;
    private String mText1;
    private String mUid;

    public FriendItem(int mImageResource, String mText1, String mUid) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mUid = mUid;
    }

    public int getmImageResource() {
        return mImageResource;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmUid() {
        return mUid;
    }
}
