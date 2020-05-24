package ru.filchacov.billsplittest.db.billOfUser;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BillOfUserDao {
    @Query("SELECT * FROM billofuser")
    List<BillOfUser> getAll();

    @Query("SELECT * FROM billofuser WHERE billUid = :billUid")
    List<BillOfUser> getBillOfUserByBillUid(String billUid);

    @Insert
    void insert(BillOfUser billOfUser);

    @Update
    void update(BillOfUser billOfUser);

    @Delete
    void delete(BillOfUser billOfUser);

    @Query("DELETE FROM billofuser")
    void delete();
}
