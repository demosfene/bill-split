package ru.filchacov.billsplittest;

import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Objects;
import ru.filchacov.billsplittest.User.User;

public class RegistrationPresenter {

    public RegistrationPresenter(RegistrationView view) {
        this.view = view;
    }

    private ModelDB model = new ModelDB();
    private RegistrationView view;
    void createAccount(String name, String email, String phone, String password) {
        model.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(view.getActivity()), (Task<AuthResult> task) -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(view.getActivity(), "Welcome " + name + "!",
                                    Toast.LENGTH_LONG).show();
                        writeNewUser(model.getUser().getUid(), name,
                                    email, phone);
                        view.updateUI(model.getUser());

                    } else {
                        Toast.makeText(view.getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user.
                        view.updateUI(null);
                    }

                });
    }

    private void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(email,  userId, name, phone);
        model.getAuthReference().child("users").child(userId).setValue(user);
    }

}
