package ru.filchacov.billsplittest.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class}, version = 6)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao getuserDao();
}
