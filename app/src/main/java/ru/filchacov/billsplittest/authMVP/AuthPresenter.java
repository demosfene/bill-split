package ru.filchacov.billsplittest.authMVP;

import android.net.Uri;
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

import ru.filchacov.billsplittest.App;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.db.UserDB;
import ru.filchacov.billsplittest.db.bill.BillDao;
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

class AuthPresenter {
    private ModelDB model = new ModelDB();
    private AuthInterface view;
    private UserDB userDB = App.getInstance().getDatabase();
    private UserDao userDao = userDB.getUserDao();
    private User currentUser;
    private UsersBillsDao usersBillsDao = userDB.getUsersBillsDao();
    private BillOfUserDao billDao = userDB.getBillOfUserDao();
    private FriendsIsChooseDao friendsIsChooseDao = userDB.getFriendsIsChooseDao();
    private FriendsBillListDao friendsBillListDao = userDB.getFriendsBillListDao();
    private ItemFromBillDao itemFromBillDao = userDB.getItemFromBillDao();
    private SavedFriendsDao savedFriendsDao = userDB.getSavedFriendsDao();
    private ArrayList listDateTime = new ArrayList<String>();

    AuthPresenter(AuthInterface view) {
        this.view = view;
    }

    void init() {
        model.getAuthReference();
        if (userDao.getByUid(model.getAuth().getUid()) != null) {
            currentUser = userDao.getByUid(model.getAuth().getUid());
            String name = currentUser.getName(); //model.getUser().getDisplayName();
            String email = currentUser.getEmail(); //model.getUser().getEmail();
            String uid = currentUser.getUserUid(); // model.getUser().getUid();
            view.onClickRead();
            view.onLocalEnabled(name);
        } else if (model.getUser() != null) {
            String name = model.getUser().getDisplayName();
            String email = model.getUser().getEmail();
            Uri photoUrl = model.getUser().getPhotoUrl();
            boolean emailVerified = model.getUser().isEmailVerified();
            String uid = model.getUser().getUid();
            view.onClickRead();
        }

    }


    void signIn(String email, String password) {

        model.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = model.getAuth().getCurrentUser();
                        usersBillsDao.delete();
                        model.getUserData()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
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
                                                HashMap friends = (HashMap) ((HashMap) ds.getValue()).get("friends");
                                                for (Map.Entry<String, HashMap> friendsItem : (Iterable<Map.Entry<String, HashMap>>) friends.entrySet()) {
                                                    billUuid = friendsItem.getKey();
                                                    UsersBills usersBills = new UsersBills(billUuid);
                                                    usersBillsDao.insert(usersBills);
                                                    UUID friendUuid = UUID.randomUUID();
                                                    BillOfUser billOfUser = new BillOfUser(billUuid, friendUuid.toString());
                                                    billDao.insert(billOfUser);
                                                    listDateTime.add(billUuid);
                                                    HashMap billMap = friendsItem.getValue();
                                                    for (Object o : billMap.entrySet()) {
                                                        Map.Entry billMapItem = (Map.Entry) o;
                                                        if (!billMapItem.getKey().equals("savedFriends")) {
                                                            UUID chooseUUID = UUID.randomUUID();
                                                            FriendsIsChoose friendsIsChoose = new FriendsIsChoose(friendUuid.toString(), (String) billMapItem.getKey(), chooseUUID.toString());
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
                                                                SavedFriends savedFriends = new SavedFriends(friendUuid.toString(), (Boolean) friendMap.get("isSelected"), friendItem.getKey().toString(), friendMap.get("mText").toString(), (friendMap.get("map") != null) ? Integer.parseInt(friendMap.get("sum").toString()) : 0);
                                                                savedFriendsDao.insert(savedFriends);
                                                            }
                                                            billMapItem.getValue();
                                                        }
                                                    }

                                                }
                                                String phone = Objects.requireNonNull(((HashMap) ds.getValue()).get("phone")).toString();
                                                User curUser = new User(email, uid, name, phone);
                                                List<User> list = userDao.getAll();
                                                userDao.insert(curUser);
                                                Log.d("Local_DB", "signIn with Network");
                                                userDao.update(curUser);
                                                updateUI(user);
                                            }
                                        }
                                        view.btnEnable(true);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        //Toast.LENGTH_LONG).show();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Local_DB", "signIn not completed");
                        updateUI(null);
                        view.btnEnable(true);
                    }

                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            view.userValid(user);
        } else view.userNotValid();
    }

    void updateUIFromPresenter() {
        updateUI(model.getUser());
    }

}
