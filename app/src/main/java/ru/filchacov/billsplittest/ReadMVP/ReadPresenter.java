package ru.filchacov.billsplittest.ReadMVP;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.filchacov.billsplittest.Bill.Bill;
import ru.filchacov.billsplittest.ExitFromBill;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.ShowFriendFragment;

public class ReadPresenter implements ShowFriendFragment {

    List<String> result = new ArrayList<>();
    private List<String> listTemp = new ArrayList<>();

    private Bill bill = new Bill();
    private ReadFragment view;
    private ModelDB model = new ModelDB();

    ReadPresenter(ReadFragment view) {
        this.view = view;
    }

    void initPresenter() {
        /*model.initModel();*/
        getDataFromDB();
        if (result.size() == 0){
            view.showTextEmptyList();
        }
        //updateList();
    }

    private void updateData() {
        view.updateData();
    }

    void signOut() {
        model.getAuth().signOut();
    }

   /* public void removeData(int index) {
        view.removeItem(index);
    }*/
    /*
        void removeUser(int position) {
            model.getReference().child(result.get(position)).removeValue();
        }

        void addUser(int position) {
            Toast toast = Toast.makeText(view.getContext(), "Добавлено в группу", Toast.LENGTH_SHORT);
            toast.show();
        }
    */
    /*public int getItemIndex(String bill) {
        int index = -1;

        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).equals(bill)){
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
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    result.add(ds.getKey());
                    updateData();
                    if (result.size() == 0){
                        view.showTextEmptyList();
                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

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
                    String BillDate = ds.getKey();
                    result.add(BillDate);
                    listTemp.add(BillDate);
                }
                if(result.size() != 0){
                    view.hideTextEmptyList();
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

    void onNoteClick(int position) {
        model.getBill(listTemp.get(position))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterable<DataSnapshot> dataChildren = dataSnapshot.getChildren();
                        Iterator<DataSnapshot> iter = dataChildren.iterator();
                        HashMap map = new HashMap();
                        while (iter.hasNext()) {
                            DataSnapshot ds = iter.next();
                            map.put(ds.getKey(), ds.getValue());
                        }
                        makeBillObject(map);
                        showFriendFragment(bill);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }

    private void makeBillObject(Map map) {
        bill.setReceiptCode(Integer.parseInt(Objects.requireNonNull(map.get("receiptCode")).toString()));
        bill.setUser((String) Objects.requireNonNull(map.get("user")));
        bill.setCashTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("cashTotalSum")).toString()));
        bill.setDateTime((String) Objects.requireNonNull(map.get("dateTime")));
        bill.setEcashTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("ecashTotalSum")).toString()));
        bill.setShiftNumber(Integer.parseInt(Objects.requireNonNull(map.get("shiftNumber")).toString()));
        bill.setTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("totalSum")).toString()));
        bill.setTaxationType(Integer.parseInt(Objects.requireNonNull(map.get("taxationType")).toString()));
        bill.setNdsNo(Integer.parseInt((Objects.requireNonNull(map.get("ndsNo")).toString())));
        bill.setFiscalDocumentNumber(Integer.parseInt(Objects.requireNonNull(map.get("fiscalDocumentNumber")).toString()));
        bill.setFiscalDriveNumber(Objects.requireNonNull(map.get("fiscalDriveNumber")).toString());
        bill.setFiscalSign(Integer.parseInt(Objects.requireNonNull(map.get("fiscalSign")).toString()));
        bill.setUserInn((String) Objects.requireNonNull(map.get("userInn")));
        bill.setOperationType(Integer.parseInt(Objects.requireNonNull(map.get("operationType")).toString()));
        bill.setKktRegId((String) Objects.requireNonNull(map.get("kktRegId")));
        bill.setRawData((String) Objects.requireNonNull(map.get("rawData")));
        bill.setRequestNumber(Integer.parseInt(Objects.requireNonNull(map.get("requestNumber")).toString()));
        bill.setOperator((String) Objects.requireNonNull(map.get("operator")));
        ArrayList list = (ArrayList) map.get("items");
        ArrayList<Bill.Item> listItem = new ArrayList<>();
        assert list != null;
        for (int i = 0; i < list.size(); i++) {
            HashMap mapItem = (HashMap) list.get(i);
            Bill.Item item = new Bill.Item();
            item.setName(Objects.requireNonNull(mapItem.get("name")).toString());
            item.setPrice(Integer.parseInt(Objects.requireNonNull(mapItem.get("price")).toString()));
            item.setQuantity(Integer.parseInt(Objects.requireNonNull(mapItem.get("quantity")).toString()));
            item.setSum(Integer.parseInt(Objects.requireNonNull(mapItem.get("sum")).toString()));
            listItem.add(item);
        }
        bill.setItems(listItem);
    }


    @Override
    public void showFriendFragment(Bill bill) {
        if (view.getActivity() instanceof ShowFriendFragment) {
            ((ShowFriendFragment) view.getActivity()).showFriendFragment(bill);
        }
    }
}


