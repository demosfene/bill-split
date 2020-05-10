package ru.filchacov.billsplittest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import ru.filchacov.billsplittest.Registration.RegistrationPresenter;
import ru.filchacov.billsplittest.Registration.UserAuthInterface;
import ru.tinkoff.decoro.Mask;
import ru.tinkoff.decoro.MaskImpl;
import ru.tinkoff.decoro.slots.PredefinedSlots;
import ru.tinkoff.decoro.watchers.FormatWatcher;
import ru.tinkoff.decoro.watchers.MaskFormatWatcher;

public class RegistrationView extends Fragment implements UserAuthInterface {

    private RegistrationPresenter presenter = new RegistrationPresenter(this);

    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirmPassword;
    private Button signUp;

    public RegistrationView() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration_view, container, false);
        name = view.findViewById(R.id.edit_name);
        email = view.findViewById(R.id.edit_email);
        phone = view.findViewById(R.id.edit_phone);
        password = view.findViewById(R.id.edit_password);
        confirmPassword = view.findViewById(R.id.edit_confirm);
        signUp = view.findViewById(R.id.btn_sign_up);
        FormatWatcher formatWatcher = new MaskFormatWatcher(
                MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER) // маска для серии и номера
        );
        formatWatcher.installOn(phone);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof ShowUpButton) {
            ((ShowUpButton)getActivity()).showUpButton(true);
        }
        if (getActivity() instanceof MainActivityInterface) {
            (( MainActivityInterface)getActivity()).navigationDrawerInvisible();
        }
        if (getActivity() instanceof ToolbarSettings) {
            ((ToolbarSettings) getActivity()).setToolbarTitle(R.string.registration);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        signUp.setOnClickListener(v -> {
            if (password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
                presenter.createAccount(
                        name.getText().toString().trim(), email.getText().toString().trim(),
                        phone.getText().toString().trim(), password.getText().toString().trim());
            } else Toast.makeText(getActivity(), "Please check your password!",
                    Toast.LENGTH_SHORT).show();
            });
    }

    @Override
    public void userValid(@NotNull FirebaseUser user) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showMainFragment();
        }
        Toast.makeText(getActivity(), "Welcome " + name.getText() + "!",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void userNotValid() {
        Toast.makeText(getActivity(), "Authentication failed.",
                Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Please sign up!",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userValidForLocalDB() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showMainFragment();
        }
    }
}
