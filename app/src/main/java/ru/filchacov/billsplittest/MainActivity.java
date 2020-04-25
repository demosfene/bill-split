package ru.filchacov.billsplittest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.AddFriend.FriendItem;
import ru.filchacov.billsplittest.AuthMVP.AuthFragment;
import ru.filchacov.billsplittest.Bill.Bill;

public class MainActivity extends AppCompatActivity implements OnClickFriendToBill, ExitFromBill {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            showAuthFragment();
        }
    }

    private void showAuthFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new AuthFragment())
                .commit();
    }

    private void showFriendFragment(Bill bill) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentFriend(bill), AddFriendFragment.getTAG())
                .commit();
    }

    private AddFriendFragment makeFragmentFriend(Bill bill) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);
        return AddFriendFragment.getNewInstance(bundle);
    }

    private void showBillForFriend(Bill bill, FriendItem friendItem){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentBill(bill, friendItem), BillListFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private BillListFragment makeFragmentBill(Bill bill, FriendItem friendItem){
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);
        bundle. putParcelable("friendItem", friendItem);
        return BillListFragment.getNewInstance(bundle);
    }

    @Override
    public void clickFriendToBill(@NotNull Bill bill, @NotNull FriendItem friendItem) {
        showBillForFriend(bill, friendItem);
    }

    @Override
    public void exitBill(@NotNull Bill bill) {
        showFriendFragment(bill);
    }
}