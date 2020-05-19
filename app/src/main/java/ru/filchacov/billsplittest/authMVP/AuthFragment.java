package ru.filchacov.billsplittest.authMVP;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.MainActivity;
import ru.filchacov.billsplittest.MainActivityInterface;
import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.readMVP.ReadFragment;
import ru.filchacov.billsplittest.RegistrationView;
import ru.filchacov.billsplittest.ToolbarSettings;

public class AuthFragment extends Fragment implements AuthInterface {
    private EditText ETemail;
    private EditText ETpassword;
    private Button signButton;
    private Button regButton;
    private TextView textView;

    private AuthPresenter presenter = new AuthPresenter(this);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.init();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.auth_fragment, container, false);
        ETemail = view.findViewById(R.id.et_email);
        ETpassword = view.findViewById(R.id.et_password);
        signButton = view.findViewById(R.id.btn_sign_in);
        regButton = view.findViewById(R.id.btn_registration);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivityInterface) {
            ((MainActivityInterface) getActivity()).navigationDrawerInvisible();
            ((MainActivityInterface) getActivity()).hideDrawerIndicator();
        }
        if (getActivity() instanceof ToolbarSettings) {
            ((ToolbarSettings) getActivity()).setToolbarTitle(R.string.auth);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        signButton.setOnClickListener(v -> {
            if (!ETemail.getText().toString().isEmpty() && !ETpassword.getText().toString().isEmpty()){
                presenter.signIn(ETemail.getText().toString().trim(), ETpassword.getText().toString().trim());
                btnEnable(false);

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(signButton.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });

        regButton.setOnClickListener(v -> {
            FragmentManager fm = getFragmentManager();
            assert fm != null;
            Fragment fragment = fm.findFragmentById(R.id.registration_fragment);
            if (fragment == null) {
                fragment = new RegistrationView();
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });

        presenter.updateUIFromPresenter();
    }

    @Override
    public void onClickRead() {
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        Fragment fragment = fm.findFragmentById(R.id.read_fragment);
        if (fragment == null) {
            fragment = new ReadFragment();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void userValid(@NotNull FirebaseUser user) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showReadFragment();
        }
    }

    @Override
    public void userNotValid() {
        Toast.makeText(getActivity(), "Authentication failed.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocalEnabled(@NotNull String name) {
        Toast.makeText(getContext(), name, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void userValidForLocalDB() {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).showReadFragment();
        }
    }

    @Override
    public void btnEnable(boolean b) {
        regButton.setEnabled(b);
        signButton.setEnabled(b);
    }
}



