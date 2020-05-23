package ru.filchacov.billsplittest.db.bill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ru.filchacov.billsplittest.bill.Bill;

@Dao
public interface BillDao {

    @Query("SELECT EXISTS(SELECT * FROM bill WHERE dateTime = :dateTime LIMIT 1)")
    int searchBill(String dateTime);

    @Insert
    void insert(Bill billDB);

    @Update
    void update(Bill billDB);

    @Delete
    void delete(Bill billDB);

    @Query("SELECT * FROM bill WHERE dateTime = :dateTime")
    Bill getByDateTime(String dateTime);

    @Query("DELETE FROM bill")
    void delete();
}
