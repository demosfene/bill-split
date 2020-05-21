package ru.filchacov.billsplittest.db.FriendsIsChoose;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface FriendsIsChooseDao {
    @Insert
    void insert(FriendsIsChoose friendsIsChoose);

    @Update
    void update(FriendsIsChoose friendsIsChoose);

    @Delete
    void delete(FriendsIsChoose friendsIsChoose);
}
