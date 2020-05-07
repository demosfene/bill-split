package ru.filchacov.billsplittest.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String email;

    String id, name, phone;

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public User() {
    }

    public User(String email, String id) {
        this.email = email;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User(String email, String id, String name, String phone) {
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.name = name;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
