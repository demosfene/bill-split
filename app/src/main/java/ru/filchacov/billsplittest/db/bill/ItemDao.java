package ru.filchacov.billsplittest.db.bill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import ru.filchacov.billsplittest.bill.Bill;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM item WHERE billTime = :billTime")
    List<Bill.Item> getItems(String billTime);
}
