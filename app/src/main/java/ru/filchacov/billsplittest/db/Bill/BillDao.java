package ru.filchacov.billsplittest.db.Bill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.filchacov.billsplittest.db.User.User;

@Dao
public interface BillDao {
    @Query("SELECT * FROM bill")
    List<Bill> getAll();

    @Insert
    void insert(Bill bill);

    @Update
    void update(Bill bill);

    @Delete
    void delete(Bill bill);

    @Query("DELETE FROM Bill")
    void delete();
}
