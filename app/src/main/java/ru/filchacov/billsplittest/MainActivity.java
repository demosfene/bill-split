package ru.filchacov.billsplittest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    AuthFragment authFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        authFragment = new AuthFragment();


        showAuthFragment();
    }

    private void showAuthFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainActivity, authFragment)
                .commit();
    }












}