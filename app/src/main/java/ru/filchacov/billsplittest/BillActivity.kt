package ru.filchacov.billsplittest

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.BillInfo.BillService
import ru.filchacov.billsplittest.BillInfo.isNetworkAvailable
import java.text.SimpleDateFormat

class BillActivity : AppCompatActivity() {
//    20200326T2909

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bill)


    }


    override fun onStart() {
        super.onStart()

        val anytext = findViewById<TextView>(R.id.anytext)
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
                    Log.d("gdeti", result.error)
                    anytext.text = result.error

                } else {
                    anytext.text = result.data?.totalSum.toString()
                }
                }catch (e:Exception) {
                    Log.e("Internet3", e.message.toString())
                Snackbar.make(findViewById(android.R.id.content), e.message.toString(), Snackbar.LENGTH_LONG)
                    .show()
            }
            }
        }
    }
}


