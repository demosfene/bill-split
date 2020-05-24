package ru.filchacov.billsplittest.db.billOfUser;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.usersBills.UsersBills;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = UsersBills.class, parentColumns = "billUID", childColumns = "billUid", onDelete = CASCADE),
        indices = {@Index(value = "savedFriend", unique = true)})
public class BillOfUser {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String billUid;
    private String savedFriend;

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


    public BillOfUser(@NonNull String billUid, String savedFriend) {
        this.billUid = billUid;
        this.savedFriend = savedFriend;
    }
}
