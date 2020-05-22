package ru.filchacov.billsplittest.db.usersBills;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface UsersBillsDao {

    @Query("SELECT * FROM usersbills")
    List<UsersBills> getAll();

    @Insert
    void insert(UsersBills usersBills);

    @Update
    void update(UsersBills usersBills);

    @Delete
    void delete(UsersBills usersBills);

    @Query("DELETE FROM UsersBills")
    void delete();
}
