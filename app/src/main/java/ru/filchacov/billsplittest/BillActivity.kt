package ru.filchacov.billsplittest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill
import java.lang.Exception


class BillActivity : AppCompatActivity(), OnClickFriendToBill, ExitFromBill, GoToMainActivity, ShowFriendFragment {

    private var mDataBase: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        mDataBase = FirebaseDatabase.getInstance().reference

        val qrInfo = intent.getStringExtra("QRInfo")
        val presenter = BillActivityPresenter(qrInfo!!, this)

        if (savedInstanceState == null) {
            try {
                presenter.getBillInfo()
            } catch (e: Exception) {
                Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                goToMainActivity()
            }
        }

    }

    override fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showFriendFragment(bill: Bill) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.bill_activity, makeFragmentFriend(bill), AddFriendFragment.TAG)
                .commit()
    }

    private fun showBillForFriend(bill: Bill, friendItem: FriendItem) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.bill_activity, makeFragmentBill(bill, friendItem), BillListFragment.TAG)
                .addToBackStack(null)
                .commit()

    }

    private fun makeFragmentFriend(bill: Bill): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        return AddFriendFragment.getNewInstance(bundle)
    }

    private fun makeFragmentBill(bill: Bill, friendItem: FriendItem): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        bundle.putParcelable("friendItem", friendItem)
        return BillListFragment.getNewInstance(bundle)
    }

    override fun clickFriendToBill(bill: Bill, friendItem: FriendItem) {
        showBillForFriend(bill, friendItem)
    }

    override fun exitBill(bill: Bill) {
        supportFragmentManager.popBackStack()
    }


}




