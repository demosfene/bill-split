package ru.filchacov.billsplittest.db.savedFriends;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.billOfUser.BillOfUser;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = BillOfUser.class, parentColumns = "billUid", childColumns = "id", onDelete = CASCADE))
public class SavedFriends {

    private String id;
    private boolean isSelected;
    @PrimaryKey
    @NonNull
    private String key;
    private String name;
    private int totalSum;

    public SavedFriends(String id, boolean isSelected, @NonNull String key, String name, int totalSum) {
        this.id = id;
        this.isSelected = isSelected;
        this.key = key;
        this.name = name;
        this.totalSum = totalSum;
    }

    public int getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(int totalSum) {
        this.totalSum = totalSum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    public void setKey(@NotNull String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
