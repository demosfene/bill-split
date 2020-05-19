package ru.filchacov.billsplittest.db.Bill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.UsersBills.UsersBills;

@Entity(foreignKeys = @ForeignKey(entity = UsersBills.class, parentColumns = "billUID", childColumns = "billUid"),
        indices = {@Index(value = "friendsIsChooseUid", unique = true), @Index(value = "savedFriend", unique = true)})
public class Bill {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String billUid;
    private String savedFriend;
    @NonNull
    private String friendsIsChooseUid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillUid() {
        return billUid;
    }

    public void setBillUid(String billUid) {
        this.billUid = billUid;
    }

    public String getSavedFriend() {
        return savedFriend;
    }

    public void setSavedFriend(String savedFriend) {
        this.savedFriend = savedFriend;
    }

    public String getFriendsIsChooseUid() {
        return friendsIsChooseUid;
    }

    public void setFriendsIsChooseUid(String friendsIsChooseUid) {
        this.friendsIsChooseUid = friendsIsChooseUid;
    }
}
