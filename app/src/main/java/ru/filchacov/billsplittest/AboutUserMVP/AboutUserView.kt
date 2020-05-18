package ru.filchacov.billsplittest.AboutUserMVP

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import ru.filchacov.billsplittest.R

class AboutUserView : Fragment(), AboutUserInterface {

    private var name: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var phone: EditText? = null
    private var btnSave: Button? = null

    private var presenter: AboutUserPresenter? = AboutUserPresenter(this)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_about_user_view, container, false)
        name = view.findViewById(R.id.aUName)
        email = view.findViewById(R.id.aUEmail)
        password = view.findViewById(R.id.aUPassword)
        phone = view.findViewById(R.id.aUPhone)
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

    override fun init() {
        name?.setText(presenter?.getName() + "")
        email?.setText(presenter?.getEmail() + "")
        phone?.setText(presenter?.getPhone() + "")

        btnSave!!.setOnClickListener(View.OnClickListener {
            if (!(name!!.text.equals("") && email!!.text.equals("") && phone!!.text.equals(""))) {
                presenter?.updateUser(name!!.text.toString().trim(), email!!.text.toString().trim(),
                        phone!!.text.toString().trim(), password!!.text.toString().trim())
            }
        })
    }
}
