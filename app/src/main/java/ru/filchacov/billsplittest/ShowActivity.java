package ru.filchacov.billsplittest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    private TextView tvEmail, tvId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentMain();
    }

    private void init() {
        tvEmail = findViewById(R.id.tv_email);
        tvId = findViewById(R.id.tv_id);
    }

    private void getIntentMain() {
        Intent i = getIntent();
        if (i != null) {
            tvEmail.setText(i.getStringExtra(Constant.USER_EMAIL));
            tvId.setText(i.getStringExtra(Constant.USER_ID));
        }
    }
}
