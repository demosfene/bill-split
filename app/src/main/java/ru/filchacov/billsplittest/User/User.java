package ru.filchacov.billsplittest.User;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public class User {

    public String id, email, name, phone;

    public User() {
    }

    public User(String email, String id) {
        this.email = email;
        this.id = id;
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
