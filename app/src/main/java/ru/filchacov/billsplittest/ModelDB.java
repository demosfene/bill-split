package ru.filchacov.billsplittest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModelDB {
    private DatabaseReference reference;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    public DatabaseReference getBillsList() {
        assert user != null;
        reference = database.getReference("users").child(user.getUid()).child("friends");
        return reference;
    }

    public DatabaseReference getFriendsList(String dateTime){
        return database.getReference("users").child(user.getUid()).child("friends").child(dateTime).child("savedFriends");
    }

    public DatabaseReference getReference() {
        return reference;
    }

    //------------- Для аутентификации ----------------
    public FirebaseUser getUser() {
        user = mAuth.getCurrentUser();
        return user;
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public DatabaseReference getAuthReference() {
        assert  user != null;
        reference = database.getReference();
        return reference;
    }
}
