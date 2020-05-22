package ru.filchacov.billsplittest.db.usersBills;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(indices = @Index(value = "billUID", unique = true))
public class UsersBills {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String billUID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotNull
    public String getBillUID() {
        return billUID;
    }

    public void setBillUID(@NotNull String billUID) {
        this.billUID = billUID;
    }

    public UsersBills(@NonNull String billUID) {
        this.billUID = billUID;
    }
}
