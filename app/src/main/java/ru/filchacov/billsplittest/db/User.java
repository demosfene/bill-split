package ru.filchacov.billsplittest.db;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    private String email;

    @PrimaryKey(autoGenerate = true)
    private int counter = 0;

    public boolean isSignIn;

    String id, name, phone;


    public void setSignIn(boolean signIn) {
        isSignIn = signIn;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

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

    public User(String email, String id, String name, String phone, boolean isSignIn) {
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.isSignIn = isSignIn;
    }

    @Nullable
    public String getEmail() {
        return email;
    }
}
