package ru.filchacov.billsplittest.aboutUserMVP

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.filchacov.billsplittest.MainActivityInterface
import ru.filchacov.billsplittest.R
import ru.filchacov.billsplittest.ShowUpButton
import ru.filchacov.billsplittest.ToolbarSettings
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class AboutUserView : Fragment(), AboutUserInterface {

    private var name: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var phone: EditText? = null
    private var oldPassword: EditText? = null
    private var btnSave: Button? = null

    private var presenter: AboutUserPresenter? = AboutUserPresenter(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about_user_view, container, false)
        name = view.findViewById(R.id.aUName)
        email = view.findViewById(R.id.aUEmail)
        password = view.findViewById(R.id.aUPassword)
        phone = view.findViewById(R.id.aUPhone)
        val formatWatcher: FormatWatcher = MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
        )
        formatWatcher.installOn(phone!!)
        oldPassword = view.findViewById(R.id.aUOldPassword)
        btnSave = view.findViewById(R.id.aUSave)

        init()
        return view
    }

    companion object {
        const val TAG = "AboutUser"

        @JvmStatic
        fun getNewInstance(): AboutUserView {
            return AboutUserView()
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is ShowUpButton) {
            (activity as ShowUpButton).showUpButton(true)
        }
        if (activity is MainActivityInterface) {
            (activity as MainActivityInterface?)!!.navigationDrawerInvisible()
        }
        if (activity is ToolbarSettings) {
            (activity as ToolbarSettings?)!!.setToolbarTitle(R.string.user_info)
        }
    }

    override fun init() {
        name?.setText("${presenter?.getName()}")
        email?.setText("${presenter?.getEmail()}")
        phone?.setText("${presenter?.getPhone()}")

        btnSave!!.setOnClickListener {
            if (!(name!!.text.equals("") && email!!.text.equals("") && phone!!.text.equals(""))) {
                presenter?.updateUser(
                        name!!.text.toString().trim(),
                        email!!.text.toString().trim(),
                        phone!!.text.toString().trim(),
                        password!!.text.toString().trim(),
                        oldPassword!!.text.toString().trim())
                Toast.makeText(activity?.applicationContext,
                        "Изменеия сохранены", Toast.LENGTH_SHORT).show()
                (activity as MainActivityInterface).setHeaderEmail(
                        email = email!!.text.toString(), name = name!!.text.toString())
            }
        }
    }

    override fun errorUpdateEmail() {
        Toast.makeText(activity?.applicationContext, "Такой email уже занят!",
                Toast.LENGTH_SHORT).show()
    }
}
