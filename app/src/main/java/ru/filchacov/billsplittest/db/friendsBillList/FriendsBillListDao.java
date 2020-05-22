package ru.filchacov.billsplittest.db.friendsBillList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface FriendsBillListDao {
    @Insert
    void insert(FriendsBillList friendsBillList);

    @Update
    void update(FriendsBillList friendsBillList);

    @Delete
    void delete(FriendsBillList friendsBillList);
}
