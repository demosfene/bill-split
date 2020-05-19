package ru.filchacov.billsplittest.db.FriendsIsChoose;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.Bill.Bill;

@Entity(foreignKeys = @ForeignKey(entity = Bill.class, parentColumns = "friendsIsChooseUid", childColumns = "id"),
        indices = @Index(value = {"choose"}, unique = true))
public class FriendsIsChoose {

    private String id;
    private String name;
    @PrimaryKey
    @NonNull
    private String choose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChoose() {
        return choose;
    }

    public void setChoose(String choose) {
        this.choose = choose;
    }
}
