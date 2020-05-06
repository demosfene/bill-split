package ru.filchacov.billsplittest.User;

import androidx.annotation.Nullable;

public class User {

    private String id, email, name, phone;

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
