package ru.filchacov.billsplittest.ReadMVP;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.filchacov.billsplittest.Bill.Bill;
import ru.filchacov.billsplittest.Constant;
import ru.filchacov.billsplittest.DecoderActivity;
import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.ShowFragment;
import ru.filchacov.billsplittest.User.User;
import ru.filchacov.billsplittest.User.UserAdapter;

public class ReadFragment extends Fragment implements UserAdapter.OnNoteListener {
    private RecyclerView recyclerView;
    public UserAdapter adapter;
    private FloatingActionButton btnAdd;
    ReadPresenter presenter;

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
        init();
        /*updateList();
        checkIfEmpty();*/
        //getDataFromDB();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), DecoderActivity.class);
            startActivity(intent);
        });
    }

    private void init() {
        presenter = new ReadPresenter(this);
        adapter = new UserAdapter(presenter.result, this);
        presenter.initPresenter();
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(llm);


        /*database = FirebaseDatabase.getInstance();
        reference = database.getReference("users");*/

    }

    /*@Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                presenter.addUser(item.getGroupId());
                break;

            case 1:
                presenter.removeUser(item.getGroupId());
                break;
        }

        return super.onContextItemSelected(item);
    }*/

    public void updateData() {
        adapter.notifyDataSetChanged();
    }
    public void removeItem(int index) {
        adapter.notifyItemRemoved(index);
    }

    void checkIfEmpty() {
        if (presenter.result.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onNoteClick(int position) {
        String bf = presenter.listTemp.get(position);
        FragmentManager fm = getFragmentManager();
        assert fm != null;
        Fragment fragment = fm.findFragmentById(R.id.show_fragment);

        if (fragment == null) {
            fragment = new ShowFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.USER_EMAIL, bf);
            fragment.setArguments(bundle);
        }

        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


}
