package ru.filchacov.billsplittest.ReadMVP;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.User.User;

public class ReadPresenter {

    public List<User> result = new ArrayList<>();
    public List<User> listTemp = new ArrayList<>();

    ReadFragment view;
    ModelDB model  = new ModelDB();

    public ReadPresenter(ReadFragment view) {
        this.view = view;
    }

    public void initPresenter() {
        model.initModel();
        getDataFromDB();
        updateList();
    }

    public void updateData() {
        view.updateData();
    }

    public void removeData(int index) {
        view.removeItem(index);
    }

    void removeUser(int position) {
        model.getReference().child(result.get(position).id).removeValue();
    }

    void addUser(int position) {
        Toast toast = Toast.makeText(view.getContext(), "Добавлено в группу", Toast.LENGTH_SHORT);
        toast.show();
    }

    public int getItemIndex(User user) {
        int index = -1;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).id.equals(user.id)){
                index = i;
                break;
            }
        }
        return index;
    }
    public void updateList() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                result.add(dataSnapshot.getValue(User.class));
                updateData();
                view.checkIfEmpty();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                int index = getItemIndex(user);
                result.set(index, user);
                updateData();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                int index = getItemIndex(user);
                result.remove(index);
                removeData(index);
                view.checkIfEmpty();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    public void getDataFromDB() {
        ValueEventListener vListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (result.size() > 0) {
                    result.clear();
                }
                if (listTemp.size() > 0) {
                    listTemp.clear();
                }
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    assert user != null;
                    result.add(user);
                    listTemp.add(user);
                }
                updateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        model.getReference().addValueEventListener(vListener);
    }
}
