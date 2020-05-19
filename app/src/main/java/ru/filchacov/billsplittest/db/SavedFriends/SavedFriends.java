package ru.filchacov.billsplittest.db.SavedFriends;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import ru.filchacov.billsplittest.db.Bill.Bill;

@Entity(foreignKeys = @ForeignKey(entity = Bill.class, parentColumns = "savedFriend", childColumns = "id"),
        indices = @Index(value = {"id"},unique = true))
public class SavedFriends {

    private String id;
    private int isSelected;
    @PrimaryKey
    @NonNull
    private String key;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
