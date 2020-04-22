package ru.filchacov.billsplittest;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ModelDB {
    private DatabaseReference reference;

    public void initModel() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        assert user != null;
        reference = database.getReference("users").child(user.getUid()).child("friends");
    }


    public DatabaseReference getReference() {
        return reference;
    }
}
