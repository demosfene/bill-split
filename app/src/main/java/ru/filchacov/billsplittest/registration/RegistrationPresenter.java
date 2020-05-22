package ru.filchacov.billsplittest.registration;

import com.google.firebase.auth.FirebaseUser;

import ru.filchacov.billsplittest.App;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.db.user.User;
import ru.filchacov.billsplittest.db.UserDB;
import ru.filchacov.billsplittest.db.user.UserDao;

public class RegistrationPresenter {

    private ModelDB model = new ModelDB();
    private UserAuthInterface view;
    private UserDB userDB = App.getInstance().getDatabase();
    private UserDao userDao = userDB.getUserDao();

    public RegistrationPresenter(UserAuthInterface view) {
        this.view = view;
    }

    public void createAccount(String name, String email, String phone, String password) {
        model.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information

                        writeNewUser(model.getUser().getUid(), name,
                                email, phone);
                        updateUI(model.getUser());

                    } else {

                        // If sign in fails, display a message to the user.
                        updateUI(null);
                    }
                });
    }

    private void writeNewUser(String userId, String name, String email, String phone) {
        User user = new User(email,  userId, name, phone);
        model.getAuthReference().child("users").child(userId).setValue(user);
        userDao.insert(user);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            view.userValid(user);
        } else view.userNotValid();
    }
}
