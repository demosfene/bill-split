package ru.filchacov.billsplittest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_bill.*
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.BillActivityMVP.BillActivityPresenter
import ru.filchacov.billsplittest.BillActivityMVP.BillErrorDialogFragment
import ru.filchacov.billsplittest.BillActivityMVP.BillInterface
import ru.filchacov.billsplittest.BillActivityMVP.BillIsDialogFragment


class BillActivity : AppCompatActivity(), OnClickFriendToBill, ExitFromBill, GoToMainActivity, ShowFriendFragment, BillInterface {

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
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                progressBar.visibility = VISIBLE
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
        if (supportFragmentManager.findFragmentByTag(AddFriendFragment.TAG) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.bill_activity, makeFragmentFriend(bill), AddFriendFragment.TAG)
                    .commit()
        }
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

    override fun progressBarInvisible() {
        progressBar.visibility = View.INVISIBLE
    }

    override fun showErrorDialog() {
        BillErrorDialogFragment().show(supportFragmentManager.beginTransaction(), "ErrorDialog")
    }

    override fun showBillIsDialog(bill: Bill) {
        makeBillIsDialogFragment(bill).show(supportFragmentManager.beginTransaction(), "BillIsDialog")
    }

    private fun makeBillIsDialogFragment(bill: Bill): DialogFragment {
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        return BillIsDialogFragment.getNewInstance(bundle)
    }
}




