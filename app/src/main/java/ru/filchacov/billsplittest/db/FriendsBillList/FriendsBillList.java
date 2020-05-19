package ru.filchacov.billsplittest.db.FriendsBillList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.FriendsIsChoose.FriendsIsChoose;

@Entity(foreignKeys = @ForeignKey(entity = FriendsIsChoose.class, parentColumns = "choose", childColumns = "id"),
        indices = @Index(value = {"item"}, unique = true))
public class FriendsBillList {
    @PrimaryKey
    @NonNull
    private String id;
    private int amount;
    private String item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
