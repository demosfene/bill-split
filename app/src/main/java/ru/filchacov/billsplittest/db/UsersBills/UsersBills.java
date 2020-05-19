package ru.filchacov.billsplittest.db.UsersBills;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.User.User;

@Entity()
public class UsersBills {
    private String id;
    @PrimaryKey
    @NonNull
    private String billUID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillUID() {
        return billUID;
    }

    public void setBillUID(String billUID) {
        this.billUID = billUID;
    }
}
