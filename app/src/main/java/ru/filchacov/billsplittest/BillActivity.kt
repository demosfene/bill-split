package ru.filchacov.billsplittest

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.BillInfo.BillService
import ru.filchacov.billsplittest.BillInfo.isNetworkAvailable
import java.text.SimpleDateFormat


class BillActivity() : AppCompatActivity() {
//    20200326T2909


    private var mDataBase: DatabaseReference? = null
    private var billFragment: Bill? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        mDataBase = FirebaseDatabase.getInstance().reference

    }



    override fun onStart() {
        super.onStart()

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
                            writeNewBill(result.data?.items, it)
                        }

                    Log.e("gdetii", "corutine")

                    billFragment = result.data!!
                    billFragment?.let { showFriendFragment(it) }
                }
                }catch (e:Exception) {
                    Log.e("Internet3", e.stackTrace.toString())
                    Snackbar.make(findViewById(android.R.id.content), e.stackTrace.toString(), Snackbar.LENGTH_LONG)
                        .show()
            }
            }
        }

    }


    private fun writeNewBill(items: MutableList<Bill.Item>?, dateTime: String) {
        val bill = Bill(items, dateTime)
        mDataBase!!.child("bills").child(dateTime).setValue(bill)
    }

    private fun showFriendFragment(bill:Bill){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.bill_activity, AddFriendFragment(bill))
                .commit()
    }

    private fun showBillForFriend(bill: Bill, friendItem: FriendItem){
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.bill_activity, BillListFragment(friendItem, bill))
                .commit()
    }


    fun clickFriend(bill: Bill, friendItem: FriendItem) {
        showBillForFriend(bill, friendItem)
    }


}


