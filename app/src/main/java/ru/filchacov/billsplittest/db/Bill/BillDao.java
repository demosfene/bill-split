package ru.filchacov.billsplittest.db.Bill;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BillDao {
    @Query("SELECT * FROM bill")
    List<Bill> getAll();

}
