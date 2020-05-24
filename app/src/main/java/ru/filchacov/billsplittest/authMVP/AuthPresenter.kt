package ru.filchacov.billsplittest.authMVP

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.App
import ru.filchacov.billsplittest.ModelDB
import ru.filchacov.billsplittest.Singleton
import java.util.*

internal class AuthPresenter(private val view: AuthInterface) {
    private val model = ModelDB()
    private val userDB = App.getInstance().database
    private val userDao = userDB.userDao
    private lateinit var currentUser: FirebaseUser
    private val usersBillsDao = userDB.usersBillsDao
    private val singleton = App.getInstance().singleton
    private val billDao = userDB.billOfUserDao
    private val friendsIsChooseDao = userDB.friendsIsChooseDao
    private val friendsBillListDao = userDB.friendsBillListDao
    private val itemFromBillDao = userDB.itemFromBillDao
    private val savedFriendsDao = userDB.savedFriendsDao
    private val listDateTime: ArrayList<*> = ArrayList<String>()
    fun init() {
        model.authReference
        if (model.user != null) {
            view.onClickRead()
        }
    }

    fun signIn(email: String?, password: String?) {
        model.auth.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        val user = model.auth.currentUser
                        usersBillsDao.delete()
                        /* model.getUserData()
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
                                                if (friends != null) {
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
                                });*/singleton.someMethod(user, email)
                        CoroutineScope(Dispatchers.IO).launch {
                            var b = true
                            while (b) {
                                if (singleton.state == Singleton.COMPlETE) {
                                    withContext(Dispatchers.Main) {
                                        view.btnEnable(true)
                                        updateUI(user)
                                    }
                                    b = false
                                }
                            }
                        }
                        //Toast.LENGTH_LONG).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Local_DB", "signIn not completed")
                        updateUI(null)
                        view.btnEnable(true)
                    }
                }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            view.userValid(user)
        } else view.userNotValid()
    }

    fun updateUIFromPresenter() {
        updateUI(model.user)
    }

}