package ru.filchacov.billsplittest;


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import ru.filchacov.billsplittest.User.User;

public class AuthFragment extends Fragment {
    private FirebaseAuth mAuth;
    private EditText ETemail;
    private EditText ETpassword;
    private Button buttonExit;
    private Button signButton;
    private Button btnRead;
    private Button regButton;
    private TextView textView;

    private DatabaseReference mDataBase;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.auth_fragment, container, false);
            ETemail = view.findViewById(R.id.et_email);
            ETpassword = view.findViewById(R.id.et_password);
            buttonExit = view.findViewById(R.id.button_exit);
            signButton = view.findViewById(R.id.btn_sign_in);
            btnRead = view.findViewById(R.id.btn_read);
            regButton = view.findViewById(R.id.btn_registration);
            textView = view.findViewById(R.id.text);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        buttonExit.setOnClickListener(v -> signOut());

        btnRead.setOnClickListener(v -> {
            onClickRead(v);
        });


        signButton.setOnClickListener(v -> {
            if (!ETemail.getText().toString().isEmpty() && !ETpassword.getText().toString().isEmpty())
                signIn(ETemail.getText().toString(), ETpassword.getText().toString());
        });



        regButton.setOnClickListener(v -> {
            if (!ETemail.getText().toString().isEmpty() && !ETpassword.getText().toString().isEmpty()) {
                createAccount(ETemail.getText().toString(), ETpassword.getText().toString());
            }
        });

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password){

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getActivity(), "User "  + " with password" ,
                                Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        writeNewUser(user.getUid(), user.getEmail());
                        updateUI(user);
                    } else {
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user.
                        updateUI(null);
                    }

                });
    }

    private void signOut(){
        mAuth.signOut();
        updateUI(null);
    }

    private void writeNewUser(String userId, String email) {
        User user = new User(email, userId);
        mDataBase.child("users").child(userId).setValue(user);
    }

    private void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(getActivity(), "User "  + " with password" ,
                                Toast.LENGTH_LONG).show();
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }

                    // ...
                });
    }


    private void updateUI(FirebaseUser user) {

        if(user!=null)
            textView.setText(user.getEmail());
        else textView.setText("Войдите пожалуйста");
    }

    private void onClickRead(View view) {
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



