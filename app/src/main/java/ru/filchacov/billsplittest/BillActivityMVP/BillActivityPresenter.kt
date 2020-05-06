package ru.filchacov.billsplittest.BillActivityMVP

import androidx.core.net.toUri
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.BillActivity
import ru.filchacov.billsplittest.BillInfo.BillService
import ru.filchacov.billsplittest.BillInfo.isNetworkAvailable
import ru.filchacov.billsplittest.ModelDB
import java.text.SimpleDateFormat

class BillActivityPresenter(billParameters: String, var view: BillActivity) {

    private val qrInfo = ("?$billParameters").toUri()
    private var time = ""
    private val modelDB = ModelDB()
    private var bill: Bill? = null

    fun getBillInfo() {

        time = qrInfo.getQueryParameter("t")!!.removeRange(8, 9)
        qrInfo.getQueryParameter("fn")!!
        qrInfo.getQueryParameter("i")!!
        qrInfo.getQueryParameter("fp")!!
        qrInfo.getQueryParameter("n")!!
        qrInfo.getQueryParameter("s")!!
        if (time.length > 12) {
            time = time.dropLast(2)
        }

        val date = SimpleDateFormat("yyyyMMddHHmm").parse(time)
        time = String.format("%td.%tm.%tY+%tR", date, date, date, date)


        val checkService = BillService()
        //проверяет включен ли интернет
        if (isNetworkAvailable.isNetworkAvailable(view.applicationContext)) {
            CoroutineScope(Dispatchers.Main).launch {
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
                    bill = result.data!!
                    writeNewBill(result.data!!, result.data!!.dateTime)
                    view.showFriendFragment(bill!!)
                }
            }
        } else {
            Snackbar.make(view.findViewById(android.R.id.content), "Интернет отключен", Snackbar.LENGTH_LONG)
                    .show()
            view.goToMainActivity()
        }
    }

    private fun writeNewBill(bill: Bill, dateTime: String) {
        modelDB.writeNewBill(bill, dateTime)
        modelDB.writeNewBillToFriend(dateTime)
    }
}