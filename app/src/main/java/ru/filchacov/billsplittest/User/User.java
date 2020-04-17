package ru.filchacov.billsplittest.User;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseUser;

public class User {

    public String id, email;

    public User() {
    }

    public User(String email, String id) {
        this.email = email;
        this.id = id;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
