package ru.filchacov.billsplittest.AuthMVP;

import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

import ru.filchacov.billsplittest.App;
import ru.filchacov.billsplittest.ModelDB;
import ru.filchacov.billsplittest.db.User;
import ru.filchacov.billsplittest.db.UserDB;
import ru.filchacov.billsplittest.db.UserDao;

class AuthPresenter {
    private ModelDB model = new ModelDB();
    private AuthInterface view;
    private UserDB userDB = App.getInstance().getDatabase();
    private UserDao userDao = userDB.getuserDao();
    private User currentUser;

    AuthPresenter(AuthInterface view) {
        this.view = view;
    }

    void init() {
        model.getAuthReference();
        if (userDao.getById(model.getAuth().getUid()) != null) {
            // Name, email address, and profile photo Url
            currentUser = userDao.getById(model.getAuth().getUid());
            String name = currentUser.getName(); //model.getUser().getDisplayName();
            String email = currentUser.getEmail(); //model.getUser().getEmail();
            //Uri photoUrl = model.getUser().getPhotoUrl();

               // Check if user's email is verified
            //boolean emailVerified = model.getUser().isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getId(); // model.getUser().getUid();
            view.onClickRead();
            view.onLocalEnabled(name);
        } else if (model.getUser() != null){
            // Name, email address, and profile photo Url
            String name = model.getUser().getDisplayName();
            String email = model.getUser().getEmail();
            Uri photoUrl = model.getUser().getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = model.getUser().isEmailVerified();


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = model.getUser().getUid();
            view.onClickRead();
        }

    }

    /*void createAccount(String email, String password) {

        model.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(Objects.requireNonNull(view.getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(view.getActivity(), "User " + " with password",
                                Toast.LENGTH_LONG).show();
                        writeNewUser(model.getUser().getUid(), model.getUser().getEmail());
                        updateUI(model.getUser());
                    } else {
                        Toast.makeText(view.getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        // If sign in fails, display a message to the user.
                        updateUI(null);
                    }

                });
    }*/

    void signIn(String email, String password) {
        /*if (userDao.getById(email) != null){
            view.onLocalEnabled();
        }*/
        model.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = model.getAuth().getCurrentUser();
                        // Toast.makeText(view.getActivity(), "User with password",

                        model.getUserData()
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Iterable<DataSnapshot> dataChildren = dataSnapshot.getChildren();
                                        Iterator<DataSnapshot> iter = dataChildren.iterator();
                                        HashMap map = new HashMap();
                                        while (iter.hasNext()) {
                                            DataSnapshot ds = iter.next();
                                            String userFirebaseEmail = (String) ((HashMap) ds.getValue()).get("email");
                                            if(user.getEmail().equals(userFirebaseEmail)){
                                                String name = ((HashMap) ds.getValue()).get("name").toString();
                                                String id = ((HashMap) ds.getValue()).get("id").toString();
                                                String phone = ((HashMap) ds.getValue()).get("phone").toString();
                                                User curUser = new User(email, id, name, phone);
                                                userDao.insert(curUser);
                                                Log.d("Local_DB", "signIn with Network");
                                                userDao.update(curUser);
                                            }
                                        }
                                        updateUI(user);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                        //Toast.LENGTH_LONG).show();

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.d("Local_DB", "signIn not completed");
                        updateUI(null);
                    }

                });
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            view.userValid(user);
        } else view.userNotValid();
    }

    private void updateUIForLocalDB(User user) {
        if (user != null) {
            view.userValidForLocalDB();
        } else view.userNotValid();
    }

    void updateUIFromPresenter() {
        updateUI(model.getUser());
    }




    /*
    Objects.requireNonNull(view.getActivity()), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = model.getAuth().getCurrentUser();
                        Toast.makeText(view.getActivity(), "User with password",
                                Toast.LENGTH_LONG).show();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(view.getActivity(), "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                }
     */

}
