package ru.filchacov.billsplittest.billActivityMVP

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import ru.filchacov.billsplittest.MainActivity

class BillErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Не удалось получить информацию из чека. Попробуйте позже")
                    .setNegativeButton("Главное меню") { dialog, id ->
                        val intent = Intent(activity, MainActivity::class.java)
                        startActivity(intent)
                    }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}