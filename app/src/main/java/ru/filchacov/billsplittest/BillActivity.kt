package ru.filchacov.billsplittest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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


class BillActivity : AppCompatActivity() {
//    20200326T2909

    private var mAuth: FirebaseAuth? = null

    private var mDataBase: DatabaseReference? = null

    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null

    private var mFriendList: ArrayList<FriendItem>? = null

    private var mFriendListDB: ArrayList<String>? = null

    private var buttonInsert: Button? = null
    private var editTextInsert: EditText? = null
    private var billCount: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)
        mAuth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser
        mDataBase = FirebaseDatabase.getInstance().reference

        mFriendList = ArrayList()
        buildRecyclerView()

        buttonInsert = findViewById(R.id.button_insert)
        editTextInsert = findViewById(R.id.edittext_insert)

        buttonInsert!!.setOnClickListener {
            val name = editTextInsert!!.getText().toString()
            if (editTextInsert!!.getText().length == 0){
                editTextInsert!!.setError("Введите имя друга");
            } else {
                insertItem(name, billCount.toString())
                editTextInsert!!.setText("")
            }
        }

    }

    fun insertItem(name: String, bill: String) {
        mFriendList?.add(FriendItem(R.drawable.ic_android, name))
        mAdapter!!.notifyDataSetChanged()
        val user = mAuth!!.currentUser
        writeNewFriend(user!!.uid, bill)
    }

    private fun writeNewFriend(userId: String, billCounter: String) {
        mFriendListDB?.add(editTextInsert!!.getText().toString())
        mDataBase!!.child("users").child(userId).child("friends").child(billCounter).push().setValue(editTextInsert!!.getText().toString())
    }

    fun buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView!!.setHasFixedSize(true)
        mLayoutManager = LinearLayoutManager(this)
        mAdapter = FriendAdapter(mFriendList)
        mRecyclerView!!.setLayoutManager(mLayoutManager)
        mRecyclerView!!.setAdapter(mAdapter)
    }


    override fun onStart() {
        super.onStart()

        var time = ""
//        val s = "t=ututututututuut&s=517.00&&i=57851&fp=3481384931&n=1"
//        val ss = "t=20200405T1439&s=4124.00&fn=9280440300752035&i=53562&fp=135155323&n=1"

        val qrInfo = ("?" + intent.getStringExtra("QRInfo")).toUri()
//        var qrInfo = ("?" + s).toUri()
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


        //преобразоывние даты в нужный формат
        //        var date = SimpleDateFormat("yyyyMMddHHmm").parse(qrInfo.getQueryParameter("t")?.removeRange(8, 9))


//         }catch (e:Exception) {
//             Snackbar.make(View(applicationContext), e.message.toString(), Snackbar.LENGTH_LONG)
//                 .show();
//         }

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
                if (result.error != null) {
                    Log.d("gdetii", result.error)

                } else {
                    result.data?.dateTime?.let { writeNewBill(result.data?.items, it)
                    billCount = result.data?.dateTime!!
                    }
                }
                }catch (e:Exception) {
                    Log.e("Internet3", e.message.toString())
                Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
            }
        }
    }
    private fun writeNewBill(items: MutableList<Bill.Item>?, dateTime: String) {
        val bill = Bill(items, dateTime)
        mDataBase!!.child("bills").child(dateTime).setValue(bill)
    }
}


