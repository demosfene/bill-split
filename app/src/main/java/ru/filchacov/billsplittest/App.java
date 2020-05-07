package ru.filchacov.billsplittest;

import android.app.Application;

import androidx.room.Room;

import ru.filchacov.billsplittest.db.UserDB;

public class App extends Application {
    public static App instance;

    private UserDB database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, UserDB.class, "user.database")
                .allowMainThreadQueries().build();
    }

    public static App getInstance() {
        return instance;
    }

    public UserDB getDatabase() {
        return database;
    }
}
