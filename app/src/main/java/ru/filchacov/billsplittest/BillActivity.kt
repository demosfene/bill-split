package ru.filchacov.billsplittest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.VISIBLE
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import ru.filchacov.billsplittest.InfoBillMVP.InfoBillView


class BillActivity : AppCompatActivity(), OnClickFriendToBill, ExitFromBill, GoToMainActivity, ShowFriendFragment, BillInterface, ShowUpButton, ToolbarSettings {

    private var mDataBase: DatabaseReference? = null
    private lateinit var toolbar: Toolbar
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mDataBase = FirebaseDatabase.getInstance().reference

        val qrInfo = intent.getStringExtra("QRInfo")
        val presenter = BillActivityPresenter(qrInfo!!, this)

        if (savedInstanceState == null) {
            try {
                presenter.getBillInfo()
                val progressBar = findViewById<ProgressBar>(R.id.progressBar)
                textView = findViewById(R.id.progressBarMessage)
                textView.visibility = VISIBLE
                progressBar.visibility = VISIBLE
            } catch (e: Exception) {
                Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                goToMainActivity()
            }
        }
        showUpButton(true)
    }

    override fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun showFriendFragment(bill: Bill) {
        if (supportFragmentManager.findFragmentByTag(AddFriendFragment.TAG) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, makeFragmentFriend(bill), AddFriendFragment.TAG)
                    .commit()
        }
    }

    private fun showBillForFriend(bill: Bill, friendItem: FriendItem) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentBill(bill, friendItem), BillListFragment.TAG)
                .addToBackStack(null)
                .commit()

    }

    override fun showInfoBill(bill: Bill) {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentInfoBill(bill), InfoBillView.TAG)
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

    private fun makeFragmentInfoBill(bill: Bill): Fragment {
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        return InfoBillView.getNewInstance(bundle)
    }

    override fun clickFriendToBill(bill: Bill, friendItem: FriendItem) {
        showBillForFriend(bill, friendItem)
    }

    override fun exitBill(bill: Bill) {
        supportFragmentManager.popBackStack()
    }

    override fun progressBarInvisible() {
        textView.visibility = View.INVISIBLE
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

    override fun showUpButton(boolean: Boolean) {
        if (supportActionBar != null) {
            toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_action_back)
            toolbar.setNavigationOnClickListener { onSupportNavigateUp() }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun setToolbarTitle(res: Int) {
        toolbar.setTitle(res)
    }
}




