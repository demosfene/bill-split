package ru.filchacov.billsplittest.db.FriendsBillList;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import ru.filchacov.billsplittest.db.FriendsIsChoose.FriendsIsChoose;

@Dao
public interface FriendsBillListDao {
    @Insert
    void insert(FriendsBillList friendsBillList);

    @Update
    void update(FriendsBillList friendsBillList);

    @Delete
    void delete(FriendsBillList friendsBillList);
}
