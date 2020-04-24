package ru.filchacov.billsplittest.ReadMVP;

import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import ru.filchacov.billsplittest.ModelDB;

public class ReadPresenter {

    List<String> result = new ArrayList<>();
    List<String> listTemp = new ArrayList<>();

    private ReadFragment view;
    private ModelDB model  = new ModelDB();

    ReadPresenter(ReadFragment view) {
        this.view = view;
    }

    void initPresenter() {
        /*model.initModel();*/
        getDataFromDB();
        /*updateList();*/
    }

    private void updateData() {
        view.updateData();
    }
/*
    public void removeData(int index) {
        view.removeItem(index);
    }

    void removeUser(int position) {
        model.getReference().child(result.get(position)).removeValue();
    }

    void addUser(int position) {
        Toast toast = Toast.makeText(view.getContext(), "Добавлено в группу", Toast.LENGTH_SHORT);
        toast.show();
    }

    public int getItemIndex(String bill) {
        int index = -1;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).equals(bill)){
                index = i;
                break;
            }
        }
        return index;
    }*/

   /* public void updateList() {
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.add(ds.getKey());
                    updateData();
                    view.checkIfEmpty();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String bf = ds.getKey();
                    int index = getItemIndex(bf);
                    result.set(index, bf);
                    updateData();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                String bf = dataSnapshot.getValue(Bill.class);
                int index = getItemIndex(bf);
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
    }*/

    private void getDataFromDB() {
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
                    String bf = "Чек от " + ds.getKey();
                    result.add(bf);
                    listTemp.add(bf);
                }
                updateData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
//        model.getReference().addValueEventListener(vListener);
        model.getBillsList().addValueEventListener(vListener);
    }
}
