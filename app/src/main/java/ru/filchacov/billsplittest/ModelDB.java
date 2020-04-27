package ru.filchacov.billsplittest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ru.filchacov.billsplittest.AddFriend.FriendItem;
import ru.filchacov.billsplittest.Bill.Bill;

public class ModelDB {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DatabaseReference getBillsList() {
        assert user != null;
        return database.getReference("users")
                .child(user.getUid())
                .child("friends");

    }

    public DatabaseReference getFriendsList(String dateTime) {
        assert user != null;
        return database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .child("savedFriends");
    }


    public void setBillForFriend(String dateTime, String friendName, ArrayList listBillDB) {
        assert user != null;
        database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .child(friendName)
                .setValue(listBillDB);
    }

    public void setFriend(String dateTime, FriendItem friendItem) {
        assert user != null;
        database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .child("savedFriends")
                .push()
                .setValue(friendItem);
    }

    public void isSelected(String dateTime, String friendUid) {
        database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .child("savedFriends")
                .child(friendUid)
                .child("isSelected")
                .setValue(true);
    }

    public DatabaseReference getBillForFriend(String dateTime, String friendName) {
        return database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .child(friendName);
    }

    public FirebaseUser getUser() {
        user = mAuth.getCurrentUser();
        return user;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public DatabaseReference getAuthReference() {
        assert user != null;
        return database.getReference();

    }

    public DatabaseReference getBill(String dateTime) {
        return database.getReference("bills")
                .child(dateTime);
    }

    public void writeNewBill(Bill bill, String dateTime) {
        database.getReference("bills")
                .child(dateTime)
                .setValue(bill);
    }

    public void writeNewBillToFriend(String dateTime) {
        database.getReference("users")
                .child(user.getUid())
                .child("friends")
                .child(dateTime)
                .setValue("");
    }


}
