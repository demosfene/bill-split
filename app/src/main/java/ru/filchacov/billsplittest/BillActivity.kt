package ru.filchacov.billsplittest

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.AddFriend.FriendItem
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.BillInfo.BillService
import ru.filchacov.billsplittest.BillInfo.isNetworkAvailable
import java.text.SimpleDateFormat


class BillActivity : AppCompatActivity(), OnClickFriendToBill, ExitFromBill{


    private var mDataBase: DatabaseReference? = null
    private var billFragment: Bill? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        mDataBase = FirebaseDatabase.getInstance().reference

        var time = ""

        val qrInfo = ("?" + intent.getStringExtra("QRInfo")).toUri()
        try {
            time = qrInfo.getQueryParameter("t")!!.removeRange(8, 9)
            qrInfo.getQueryParameter("fn")!!
            qrInfo.getQueryParameter("i")!!
            qrInfo.getQueryParameter("fp")!!
            qrInfo.getQueryParameter("n")!!
            qrInfo.getQueryParameter("s")!!
            if (time.length > 12){
                time = time.dropLast(2)
            }

        }catch (e:Exception){
            Log.e("Internet1", e.message.toString())
            Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
        }

        try{
            val date = SimpleDateFormat("yyyyMMddHHmm").parse(time)
            time = String.format("%td.%tm.%tY+%tR", date, date, date, date)
        }catch (e:Exception) {
            Log.e("Internet2", e.message.toString())
            Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
        }

        val checkService = BillService()
        //проверяет включен ли интернет
        if (isNetworkAvailable.isNetworkAvailable(applicationContext)) {
            CoroutineScope(Dispatchers.Main).launch {
                try{
                    val result = withContext(Dispatchers.IO) {
                        checkService.api.checkGet(
                                fn = qrInfo.getQueryParameter("fn")!!
                                , fd = qrInfo.getQueryParameter("i")!!
                                , fp = qrInfo.getQueryParameter("fp")!!
                                , n = qrInfo.getQueryParameter("n")!!.toInt()
                                , s = qrInfo.getQueryParameter("s")!!
                                , t = time
                                , qr = 0
                        ).execute().body()!!
                    }
                    if (result.data != null) {
                        result.data?.dateTime?.let {
                            writeNewBill(result.data!!, it)
                        }

                        Log.e("gdetii", "corutine")

                        billFragment = result.data!!
                        if (savedInstanceState == null) {
                            billFragment?.let { showFriendFragment(it) }
                        }
                    }
                }catch (e:Exception) {
                    Log.e("Internet3", e.stackTrace.toString())
                    Snackbar.make(findViewById(android.R.id.content), e.stackTrace.toString(), Snackbar.LENGTH_LONG)
                            .show()
                }
            }
        }
    }

    private fun writeNewBill(bill: Bill, dateTime: String) {
        mDataBase!!.child("bills").child(dateTime).setValue(bill)
    }

    fun showFriendFragment(bill: Bill) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.bill_activity, makeFragmentFriend(bill), AddFriendFragment.TAG)
                    .commit()
    }

    private fun showBillForFriend(bill: Bill, friendItem: FriendItem){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.bill_activity, makeFragmentBill(bill, friendItem), BillListFragment.TAG)
                .addToBackStack(null)
                .commit()

    }

    private fun makeFragmentFriend(bill:Bill): Fragment{
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        return AddFriendFragment.getNewInstance(bundle)
    }

    private fun makeFragmentBill(bill: Bill, friendItem: FriendItem):Fragment{
        val bundle = Bundle()
        bundle.putParcelable("bill", bill)
        bundle. putParcelable("friendItem", friendItem)
        return BillListFragment.getNewInstance(bundle)
    }

    override fun clickFriendToBill(bill: Bill, friendItem: FriendItem) {
        showBillForFriend(bill, friendItem)
    }

    override fun exitBill(bill: Bill) {
        showFriendFragment(bill)
    }

}




