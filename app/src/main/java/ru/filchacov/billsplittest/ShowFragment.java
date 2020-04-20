package ru.filchacov.billsplittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class ShowFragment extends Fragment {

    private TextView tvEmail, tvId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        tvEmail = view.findViewById(R.id.tv_email);
        tvId = view.findViewById(R.id.tv_id);
        tvEmail.setText(getArguments().getString(Constant.USER_EMAIL));
        tvId.setText(getArguments().getString(Constant.USER_ID));

        return view;
    }

}
