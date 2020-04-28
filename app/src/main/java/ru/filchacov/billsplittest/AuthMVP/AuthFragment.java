package ru.filchacov.billsplittest.AuthMVP;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.ReadMVP.ReadFragment;

public class AuthFragment extends Fragment {
    private EditText ETemail;
    private EditText ETpassword;
    private Button signButton;
    private Button regButton;
    TextView textView;

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
        textView = view.findViewById(R.id.text);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        signButton.setOnClickListener(v -> {
            if (!ETemail.getText().toString().isEmpty() && !ETpassword.getText().toString().isEmpty())
                presenter.signIn(ETemail.getText().toString().trim(), ETpassword.getText().toString().trim());
        });

        regButton.setOnClickListener(v -> {
            if (!ETemail.getText().toString().isEmpty() && !ETpassword.getText().toString().isEmpty()) {
                presenter.createAccount(ETemail.getText().toString().trim(), ETpassword.getText().toString().trim());
            }
        });

        presenter.updateUIFromPresenter();
    }

    void onClickRead() {
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        Fragment fragment = fm.findFragmentById(R.id.read_fragment);
        if (fragment == null) {
            fragment = new ReadFragment();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}



