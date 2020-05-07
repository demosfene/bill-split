package ru.filchacov.billsplittest.Registration;

import com.google.firebase.auth.FirebaseUser;

import ru.filchacov.billsplittest.App;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.db.User;
import ru.filchacov.billsplittest.db.UserDB;
import ru.filchacov.billsplittest.db.UserDao;

public class RegistrationPresenter {

    public RegistrationPresenter(UserAuthInterface view) {
        this.view = view;
    }

    private ModelDB model = new ModelDB();
    private UserAuthInterface view;
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
        UserDB userDB = App.getInstance().getDatabase();
        UserDao userDao = userDB.getuserDao();
        userDao.insert(user);
        model.getAuthReference().child("users").child(userId).setValue(user);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            view.userValid(user);
        } else view.userNotValid();
    }
}
