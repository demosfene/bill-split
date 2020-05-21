package ru.filchacov.billsplittest.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.filchacov.billsplittest.db.Bill.Bill;
import ru.filchacov.billsplittest.db.Bill.BillDao;
import ru.filchacov.billsplittest.db.FriendsBillList.FriendsBillList;
import ru.filchacov.billsplittest.db.FriendsBillList.FriendsBillListDao;
import ru.filchacov.billsplittest.db.FriendsIsChoose.FriendsIsChoose;
import ru.filchacov.billsplittest.db.FriendsIsChoose.FriendsIsChooseDao;
import ru.filchacov.billsplittest.db.ItemFromBill.ItemFromBill;
import ru.filchacov.billsplittest.db.ItemFromBill.ItemFromBillDao;
import ru.filchacov.billsplittest.db.SavedFriends.SavedFriends;
import ru.filchacov.billsplittest.db.SavedFriends.SavedFriendsDao;
import ru.filchacov.billsplittest.db.User.User;
import ru.filchacov.billsplittest.db.User.UserDao;
import ru.filchacov.billsplittest.db.UsersBills.UsersBills;
import ru.filchacov.billsplittest.db.UsersBills.UsersBillsDao;

@Database(
        entities = {User.class, Bill.class, FriendsBillList.class, FriendsIsChoose.class, ItemFromBill.class, SavedFriends.class, UsersBills.class},
        version = 12)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao getUserDao();
    public abstract BillDao getBillDao();
    public abstract FriendsBillListDao getFriendsBillListDao();
    public abstract FriendsIsChooseDao getFriendsIsChooseDao();
    public abstract ItemFromBillDao getItemFromBillDao();
    public abstract SavedFriendsDao getSavedFriendsDao();
    public abstract UsersBillsDao getUsersBillsDao();
}
