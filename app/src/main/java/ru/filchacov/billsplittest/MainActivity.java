package ru.filchacov.billsplittest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.filchacov.billsplittest.AuthMVP.AuthFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            showAuthFragment();
        }
    }

    private void showAuthFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new AuthFragment())
                .commit();
    }












}