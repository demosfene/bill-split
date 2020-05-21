package ru.filchacov.billsplittest.db.UsersBills;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


@Dao
public interface UsersBillsDao {
    @Insert
    void insert(UsersBills usersBills);

    @Update
    void update(UsersBills usersBills);

    @Delete
    void delete(UsersBills usersBills);

    @Query("DELETE FROM UsersBills")
    void delete();
}
