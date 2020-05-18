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

import ru.filchacov.billsplittest.AboutUserMVP.AboutUserView;
import ru.filchacov.billsplittest.AuthMVP.AuthFragment;
import ru.filchacov.billsplittest.Bill.Bill;
import ru.filchacov.billsplittest.DecoderActivity;
import ru.filchacov.billsplittest.MainActivityInterface;
import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.ShowFriendFragment;
import ru.filchacov.billsplittest.ToolbarSettings;
import ru.filchacov.billsplittest.db.User;

public class ReadFragment extends Fragment
        implements BillDateAdapter.OnNoteListener, ReadInterface {

    private RecyclerView recyclerView;
    private BillDateAdapter adapter;
    private FloatingActionButton btnAdd;
    private ReadPresenter presenter;
    private TextView emptyText;
    private FloatingActionButton goToAboutUser;

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
        goToAboutUser = view.findViewById(R.id.btnAboutUser);
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
        goToAboutUser.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new AboutUserView())
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivityInterface) {
            ((MainActivityInterface)getActivity()).setupDrawerContent();
        }
        if(getActivity() instanceof MainActivityInterface) {
            ((MainActivityInterface) getActivity()).navigationDrawerVisible();
        }
        if (getActivity() instanceof ToolbarSettings) {
            ((ToolbarSettings) getActivity()).setToolbarTitle(R.string.list_of_bills);
        }
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

    }

    @Override
    public void updateData() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteClick(int position) {
        presenter.onNoteClick(position);
    }

    @Override
    public void showTextEmptyList() {
        emptyText.setText("Список покупок пуст");
        emptyText.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTextEmptyList() {
        emptyText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showFriendFragment(Bill bill) {
        if (getActivity() instanceof ShowFriendFragment) {
            ((ShowFriendFragment) getActivity()).showFriendFragment(bill);
        }
    }
}
