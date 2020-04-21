package ru.filchacov.billsplittest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ru.filchacov.billsplittest.ReadMVP.ReadPresenter;
import ru.filchacov.billsplittest.User.User;
import ru.filchacov.billsplittest.User.UserAdapter;

public class ModelDB {
    ReadPresenter presenter;
    FirebaseDatabase database;
    DatabaseReference reference;
    private ChildEventListener childEventListener;

    public void initModel() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");
    }

    public void updateList() {
        reference.addChildEventListener(childEventListener);
    }

    public DatabaseReference getReference() {
        return reference;
    }
}
