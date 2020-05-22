package ru.filchacov.billsplittest.readMVP;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import ru.filchacov.billsplittest.App;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.bill.Bill;
import ru.filchacov.billsplittest.db.UserDB;
import ru.filchacov.billsplittest.db.bill.BillDB;
import ru.filchacov.billsplittest.db.bill.BillDao;
import ru.filchacov.billsplittest.db.bill.ItemDao;
import ru.filchacov.billsplittest.db.usersBills.UsersBills;
import ru.filchacov.billsplittest.db.usersBills.UsersBillsDao;

class ReadPresenter {

    List<UsersBills> result = new ArrayList<>();
    private List<String> listTemp = new ArrayList<>();

    private Bill bill = new Bill();
    private ReadInterface view;
    String BillDate;
    private ModelDB model = new ModelDB();
    private UserDB userDB = App.getInstance().getDatabase();
    private UsersBillsDao usersBills = userDB.getUsersBillsDao();
    private BillDao billDBDao = userDB.getBillDao();
    private ItemDao itemDao = userDB.getItemDao();

    ReadPresenter(ReadInterface view) {
        this.view = view;
    }

    void initPresenter() {
        getDataFromDB();
        if (result.size() == 0) {
            view.showTextEmptyList();
        }
    }

    private void updateData() {
        view.updateData();
    }


    private void getDataFromDB() {
        result = usersBills.getAll();
    }

    void onNoteClick(int position) {
        Bill bill = billDBDao.getByDateTime(result.get(position).getBillUID());
        bill.setItems(itemDao.getItems(result.get(position).getBillUID()));
        view.showFriendFragment(bill);
//        model.getBill(listTemp.get(position))
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Iterable<DataSnapshot> dataChildren = dataSnapshot.getChildren();
//                        Iterator<DataSnapshot> iter = dataChildren.iterator();
//                        HashMap map = new HashMap();
//                        while (iter.hasNext()) {
//                            DataSnapshot ds = iter.next();
//                            map.put(ds.getKey(), ds.getValue());
//                        }
//                        makeBillObject(map);
//                        view.showFriendFragment(bill);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

    }

//    private void makeBillObject(Map map) {
//        bill.setReceiptCode(Integer.parseInt(Objects.requireNonNull(map.get("receiptCode")).toString()));
//        bill.setUser((String) Objects.requireNonNull(map.get("user")));
//        bill.setCashTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("cashTotalSum")).toString()));
//        bill.setDateTime((String) Objects.requireNonNull(map.get("dateTime")));
//        bill.setEcashTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("ecashTotalSum")).toString()));
//        bill.setShiftNumber(Integer.parseInt(Objects.requireNonNull(map.get("shiftNumber")).toString()));
//        bill.setTotalSum(Integer.parseInt(Objects.requireNonNull(map.get("totalSum")).toString()));
//        bill.setTaxationType(Integer.parseInt(Objects.requireNonNull(map.get("taxationType")).toString()));
//        bill.setNdsNo(Integer.parseInt((Objects.requireNonNull(map.get("ndsNo")).toString())));
//        bill.setFiscalDocumentNumber(Integer.parseInt(Objects.requireNonNull(map.get("fiscalDocumentNumber")).toString()));
//        bill.setFiscalDriveNumber(Objects.requireNonNull(map.get("fiscalDriveNumber")).toString());
//        bill.setFiscalSign(Integer.parseInt(Objects.requireNonNull(map.get("fiscalSign")).toString()));
//        bill.setUserInn((String) Objects.requireNonNull(map.get("userInn")));
//        bill.setOperationType(Integer.parseInt(Objects.requireNonNull(map.get("operationType")).toString()));
//        bill.setKktRegId((String) Objects.requireNonNull(map.get("kktRegId")));
//        bill.setRawData((String) Objects.requireNonNull(map.get("rawData")));
//        bill.setRequestNumber(Integer.parseInt(Objects.requireNonNull(map.get("requestNumber")).toString()));
//        bill.setOperator((String) Objects.requireNonNull(map.get("operator")));
//        ArrayList list = (ArrayList) map.get("items");
//        ArrayList<Bill.Item> listItem = new ArrayList<>();
//        assert list != null;
//        for (int i = 0; i < list.size(); i++) {
//            HashMap mapItem = (HashMap) list.get(i);
//            Bill.Item item = new Bill.Item();
//            item.setName(Objects.requireNonNull(mapItem.get("name")).toString());
//            item.setPrice(Integer.parseInt(Objects.requireNonNull(mapItem.get("price")).toString()));
//            item.setQuantity(Integer.parseInt(Objects.requireNonNull(mapItem.get("quantity")).toString()));
//            item.setSum(Integer.parseInt(Objects.requireNonNull(mapItem.get("sum")).toString()));
//            listItem.add(item);
//        }
//        bill.setItems(listItem);
//    }

    public List<String> getSum() {
        List<String> sum = new ArrayList<>();
        for (UsersBills usersBills : result) {
            int totalSum = billDBDao.getByDateTime(usersBills.getBillUID()).getTotalSum();
            sum.add(String.format("%.2f",  Double.parseDouble(totalSum + "")/100));
        }
        return sum;
    }

}


