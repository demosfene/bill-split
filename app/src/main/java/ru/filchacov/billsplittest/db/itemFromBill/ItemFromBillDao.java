package ru.filchacov.billsplittest.db.itemFromBill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface ItemFromBillDao {
    @Insert
    void insert(ItemFromBill itemFromBill);

    @Update
    void update(ItemFromBill itemFromBill);

    @Delete
    void delete(ItemFromBill itemFromBill);
}
