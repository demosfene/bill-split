package ru.filchacov.billsplittest.BillActivityMVP

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.filchacov.billsplittest.Bill.Bill
import ru.filchacov.billsplittest.MainActivity
import ru.filchacov.billsplittest.ShowFriendFragment

class BillIsDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        fun getNewInstance(args: Bundle?): BillIsDialogFragment {
            val billIsDialogFragment = BillIsDialogFragment()
            billIsDialogFragment.arguments = args
            return billIsDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Данный чек уже был отсканирован.")
                    .setCancelable(true)
                    .setPositiveButton("К чеку") { dialog, id ->
                        (activity as ShowFriendFragment).showFriendFragment(arguments!!.get("bill") as Bill?)
                    }
                    .setNegativeButton("В главное меню") { dialog, id ->
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}