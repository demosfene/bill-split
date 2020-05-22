package ru.filchacov.billsplittest.db.bill;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import ru.filchacov.billsplittest.bill.Bill;

@Dao
public interface BillDao {
//    @Query("SELECT * FROM bill")
//    List<BillOfUser> getAll();
//
//    @Insert
//    void insert(BillDB billDB);
//
//    @Update
//    void update(BillDB billDB);

    @Insert
    void insert(Bill billDB);

    @Update
    void update(Bill billDB);

    @Delete
    void delete(Bill billDB);

    @Query("SELECT * FROM bill WHERE dateTime = :dateTime")
    Bill getByDateTime(String dateTime);

//    @Delete
//    void delete(BillDB billDB);

    @Query("DELETE FROM bill")
    void delete();
}
