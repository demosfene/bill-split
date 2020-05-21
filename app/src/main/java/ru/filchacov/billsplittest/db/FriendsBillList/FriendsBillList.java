package ru.filchacov.billsplittest.db.FriendsBillList;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.FriendsIsChoose.FriendsIsChoose;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = FriendsIsChoose.class, parentColumns = "choose", childColumns = "chooseUid", onDelete = CASCADE),
        indices = @Index(value = {"item"}, unique = true))
public class FriendsBillList {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String chooseUid;
    private int amount;
    private String item;

    public FriendsBillList(@NonNull String chooseUid, int amount, String item) {
        this.chooseUid = chooseUid;
        this.amount = amount;
        this.item = item;
    }

    @NotNull
    public String getChooseUid() {
        return chooseUid;
    }

    public void setChooseUid(@NotNull String chooseUid) {
        this.chooseUid = chooseUid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
