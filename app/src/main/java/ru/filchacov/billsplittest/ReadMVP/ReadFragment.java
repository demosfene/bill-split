package ru.filchacov.billsplittest.ReadMVP;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import ru.filchacov.billsplittest.AddPermanentFriendView;
import ru.filchacov.billsplittest.AuthMVP.AuthFragment;
import ru.filchacov.billsplittest.DecoderActivity;
import ru.filchacov.billsplittest.R;

public class ReadFragment extends Fragment implements BillDateAdapter.OnNoteListener {
    private RecyclerView recyclerView;
    private BillDateAdapter adapter;
    private FloatingActionButton btnAdd;
    private FloatingActionButton btnAddNewFriend;
    private Button buttonExit;
    private ReadPresenter presenter;

    private TextView emptyText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read, container, false);
        recyclerView = view.findViewById(R.id.user_list);
        emptyText = view.findViewById(R.id.text_no_data);
        btnAdd = view.findViewById(R.id.addBill);
        btnAddNewFriend = view.findViewById(R.id.addPermanentFriend);
        buttonExit = view.findViewById(R.id.button_exit);
        init();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DecoderActivity.class);
            startActivity(intent);
        });
        btnAddNewFriend.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AddPermanentFriendView())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private void init() {
        presenter = new ReadPresenter(this);
        adapter = new BillDateAdapter(presenter.result, this);
        presenter.initPresenter();
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);

        buttonExit.setOnClickListener(v -> {
            presenter.signOut();
            FragmentManager fm = getFragmentManager();
            assert fm != null;
            Fragment fragment = fm.findFragmentById(R.id.auth_fragment);
            if (fragment == null) {
                fragment = new AuthFragment();
            }
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.addToBackStack(null);
            ft.commit();
        });
    }

    void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(int position) {
        presenter.onNoteClick(position);
    }


    void showTextEmptyList() {
        emptyText.setText("Список покупок пуст");
        emptyText.setVisibility(View.VISIBLE);
    }

    void hideTextEmptyList() {
        emptyText.setVisibility(View.INVISIBLE);
    }
}
