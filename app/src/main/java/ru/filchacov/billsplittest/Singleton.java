package ru.filchacov.billsplittest;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import ru.filchacov.billsplittest.db.UserDB;
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

public class Singleton {

    private ModelDB model = new ModelDB();
    private UserDB userDB = App.getInstance().getDatabase();
    private UserDao userDao = userDB.getUserDao();
    private UsersBillsDao usersBillsDao = userDB.getUsersBillsDao();
    private BillOfUserDao billDao = userDB.getBillOfUserDao();
    private FriendsIsChooseDao friendsIsChooseDao = userDB.getFriendsIsChooseDao();
    private FriendsBillListDao friendsBillListDao = userDB.getFriendsBillListDao();
    private ItemFromBillDao itemFromBillDao = userDB.getItemFromBillDao();
    private SavedFriendsDao savedFriendsDao = userDB.getSavedFriendsDao();
    public static final String NONE = "none";
    public static final String IN_PROCESS = "in_process";
    public static final String COMPlETE = "complete";

    Singleton() {
    }

    public String state = NONE;

    public void someMethod(FirebaseUser user, String email) {
        state = IN_PROCESS;
        model.getUserData().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> dataChildren = dataSnapshot.getChildren();
                for (DataSnapshot ds : dataChildren) {
                    String userFirebaseEmail = (String) ((HashMap) ds.getValue()).get("email");
                    assert user != null;
                    if (user.getEmail().equals(userFirebaseEmail)) {
                        String name = Objects.requireNonNull(((HashMap) ds.getValue()).get("name")).toString();
                        String uid;
                        if (((HashMap) ds.getValue()).get("userUid") != null) {
                            uid = ((HashMap) ds.getValue()).get("userUid").toString();
                        } else {
                            uid = ((HashMap) ds.getValue()).get("id").toString();
                        }
                        String billUuid;
                        if (((HashMap) ds.getValue()).get("friends") != null) {
                            HashMap friends = (HashMap) ((HashMap) ds.getValue()).get("friends");
                            for (Map.Entry<String, HashMap> friendsItem : (Iterable<Map.Entry<String, HashMap>>) friends.entrySet()) {
                                billUuid = friendsItem.getKey();
                                UsersBills usersBills = new UsersBills(billUuid);
                                usersBillsDao.insert(usersBills);
                                BillOfUser billOfUser = new BillOfUser(billUuid, billUuid);
                                billDao.insert(billOfUser);
                                HashMap billMap = friendsItem.getValue();
                                for (Object o : billMap.entrySet()) {
                                    Map.Entry billMapItem = (Map.Entry) o;
                                    if (!billMapItem.getKey().equals("savedFriends")) {
                                        UUID chooseUUID = UUID.randomUUID();
                                        FriendsIsChoose friendsIsChoose = new FriendsIsChoose(billUuid, (String) billMapItem.getKey(), chooseUUID.toString());
                                        friendsIsChooseDao.insert(friendsIsChoose);
                                        ArrayList chooseList = (ArrayList) billMapItem.getValue();
                                        for (int i = 0; i < chooseList.size(); i++) {
                                            HashMap friendBillItem = (HashMap) chooseList.get(i);
                                            UUID itemUUID = UUID.randomUUID();
                                            FriendsBillList friendsBillList =
                                                    new FriendsBillList(
                                                            chooseUUID.toString(),
                                                            Integer.parseInt(friendBillItem.get("amount").toString()),
                                                            itemUUID.toString());
                                            friendsBillListDao.insert(friendsBillList);
                                            HashMap itemFromBillMap = (HashMap) friendBillItem.get("item");
                                            ItemFromBill itemFromBill = new ItemFromBill(itemUUID.toString(), (String) itemFromBillMap.get("name"), Integer.parseInt(itemFromBillMap.get("price").toString()), Integer.parseInt(itemFromBillMap.get("quantity").toString()), Integer.parseInt(itemFromBillMap.get("sum").toString()));
                                            itemFromBillDao.insert(itemFromBill);
                                        }
                                    } else {
                                        HashMap savedFriend = (HashMap) billMapItem.getValue();
                                        for (Object friend : savedFriend.entrySet()) {
                                            Map.Entry friendItem = (Map.Entry) friend;
                                            HashMap friendMap = (HashMap) friendItem.getValue();
                                            SavedFriends savedFriends = new SavedFriends(billUuid, (Boolean) friendMap.get("isSelected"), friendItem.getKey().toString(), friendMap.get("mText").toString(), Integer.parseInt(friendMap.get("sum").toString()));
                                            savedFriendsDao.insert(savedFriends);
                                        }
                                        billMapItem.getValue();
                                    }
                                }

                            }
                        }
                        String phone = Objects.requireNonNull(((HashMap) ds.getValue()).get("phone")).toString();
                        User curUser = new User(email, uid, name, phone);
                        List<User> list = userDao.getAll();
                        userDao.insert(curUser);
                        Log.d("Local_DB", "signIn with Network");
                        userDao.update(curUser);
                        state = COMPlETE;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
