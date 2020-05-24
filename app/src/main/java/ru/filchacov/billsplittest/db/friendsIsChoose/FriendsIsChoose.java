package ru.filchacov.billsplittest.db.friendsIsChoose;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.db.billOfUser.BillOfUser;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = BillOfUser.class, parentColumns = "savedFriend", childColumns = "id", onDelete = CASCADE),
        indices = @Index(value = {"choose"}, unique = true))
public class FriendsIsChoose {
    private String id;
    private String name;
    @PrimaryKey
    @NonNull
    private String choose;

    public FriendsIsChoose(String id, String name, @NonNull String choose) {
        this.id = id;
        this.name = name;
        this.choose = choose;
    }

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

    @NotNull
    public String getChoose() {
        return choose;
    }

    public void setChoose(@NotNull String choose) {
        this.choose = choose;
    }
}
