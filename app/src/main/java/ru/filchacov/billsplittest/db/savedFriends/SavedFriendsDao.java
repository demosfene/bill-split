package ru.filchacov.billsplittest.db.savedFriends;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SavedFriendsDao {
    @Query("SELECT * FROM savedfriends WHERE id = :id")
    List<SavedFriends> getFriendsById(String id);

    @Insert
    void insert(SavedFriends savedFriends);

    @Update
    void update(SavedFriends savedFriends);

    @Delete
    void delete(SavedFriends savedFriends);
}
