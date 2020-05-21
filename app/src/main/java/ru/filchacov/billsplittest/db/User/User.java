package ru.filchacov.billsplittest.db.User;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"email"})
public class User {
    @NonNull
    public String email;
    @ColumnInfo(name = "user_uid")
    public String userUid;
    public String name;
    public String phone;

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public User() {
    }

//    public User(@NonNull String email, String userUid) {
//        this.email = email;
//        this.userUid = userUid;
//    }

    public User(@NonNull String email, String userUid, String name, String phone) {
        this.email = email;
        this.userUid = userUid;
        this.name = name;
        this.phone = phone;
    }
}
