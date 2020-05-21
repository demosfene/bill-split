package ru.filchacov.billsplittest.db.Bill;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.UsersBills.UsersBills;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = UsersBills.class, parentColumns = "billUID", childColumns = "billUid", onDelete = CASCADE),
        indices = {@Index(value = "friendsIsChooseUid", unique = true),
                   @Index(value = "savedFriend", unique = true)})
public class Bill {
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

    @NotNull
    public String getBillUid() {
        return billUid;
    }

    public void setBillUid(@NotNull String billUid) {
        this.billUid = billUid;
    }

    public String getSavedFriend() {
        return savedFriend;
    }

    public void setSavedFriend(String savedFriend) {
        this.savedFriend = savedFriend;
    }

    @NotNull
    public String getFriendsIsChooseUid() {
        return friendsIsChooseUid;
    }

    public void setFriendsIsChooseUid(@NotNull String friendsIsChooseUid) {
        this.friendsIsChooseUid = friendsIsChooseUid;
    }

    public Bill(@NonNull String billUid, String savedFriend, @NonNull String friendsIsChooseUid) {
        this.billUid = billUid;
        this.savedFriend = savedFriend;
        this.friendsIsChooseUid = friendsIsChooseUid;
    }
}
