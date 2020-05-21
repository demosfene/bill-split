package ru.filchacov.billsplittest.db.SavedFriends;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface SavedFriendsDao {
    @Insert
    void insert(SavedFriends savedFriends);

    @Update
    void update(SavedFriends savedFriends);

    @Delete
    void delete(SavedFriends savedFriends);
}
