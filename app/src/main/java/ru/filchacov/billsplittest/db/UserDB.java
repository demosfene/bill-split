package ru.filchacov.billsplittest.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.filchacov.billsplittest.bill.Bill;
import ru.filchacov.billsplittest.db.bill.BillDao;
import ru.filchacov.billsplittest.db.bill.Item;
import ru.filchacov.billsplittest.db.bill.ItemDao;
import ru.filchacov.billsplittest.db.billOfUser.BillOfUser;
import ru.filchacov.billsplittest.db.billOfUser.BillOfUserDao;
import ru.filchacov.billsplittest.db.friendsBillList.FriendsBillList;
import ru.filchacov.billsplittest.db.friendsBillList.FriendsBillListDao;
import ru.filchacov.billsplittest.db.friendsIsChoose.FriendsIsChoose;
import ru.filchacov.billsplittest.db.friendsIsChoose.FriendsIsChooseDao;
import ru.filchacov.billsplittest.db.itemFromBill.ItemFromBill;
import ru.filchacov.billsplittest.db.itemFromBill.ItemFromBillDao;
import ru.filchacov.billsplittest.db.savedFriends.SavedFriends;
import ru.filchacov.billsplittest.db.savedFriends.SavedFriendsDao;
import ru.filchacov.billsplittest.db.user.User;
import ru.filchacov.billsplittest.db.user.UserDao;
import ru.filchacov.billsplittest.db.usersBills.UsersBills;
import ru.filchacov.billsplittest.db.usersBills.UsersBillsDao;

@Database(
        entities = {User.class, BillOfUser.class, FriendsBillList.class, FriendsIsChoose.class, ItemFromBill.class, SavedFriends.class, UsersBills.class, Bill.class, Item.class},
        version = 21)
public abstract class UserDB extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract BillOfUserDao getBillOfUserDao();

    public abstract FriendsBillListDao getFriendsBillListDao();

    public abstract FriendsIsChooseDao getFriendsIsChooseDao();

    public abstract ItemFromBillDao getItemFromBillDao();

    public abstract SavedFriendsDao getSavedFriendsDao();

    public abstract UsersBillsDao getUsersBillsDao();

    public abstract BillDao getBillDao();

    public abstract ItemDao getItemDao();
}
