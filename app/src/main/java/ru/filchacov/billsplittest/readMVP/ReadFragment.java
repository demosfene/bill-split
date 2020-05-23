package ru.filchacov.billsplittest.readMVP;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.DecoderActivity;
import ru.filchacov.billsplittest.MainActivityInterface;
import ru.filchacov.billsplittest.R;
import ru.filchacov.billsplittest.ShowFriendFragment;
import ru.filchacov.billsplittest.ToolbarSettings;
import ru.filchacov.billsplittest.bill.Bill;

public class ReadFragment extends Fragment
        implements BillDateAdapter.OnNoteListener, ReadInterface {

    private RecyclerView recyclerView;
    private BillDateAdapter adapter;
    private FloatingActionButton btnAdd;
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
//        init();
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

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() instanceof MainActivityInterface)
            ((MainActivityInterface) getActivity()).navHeaderInit();
        if (getActivity() instanceof MainActivityInterface) {
            ((MainActivityInterface) getActivity()).setupDrawerContent();
        }
        if (getActivity() instanceof MainActivityInterface) {
            ((MainActivityInterface) getActivity()).navigationDrawerVisible();
        }
        if (getActivity() instanceof ToolbarSettings) {
            ((ToolbarSettings) getActivity()).setToolbarTitle(R.string.list_of_bills);
        }
//        init();
    }

    private void init() {
        presenter = new ReadPresenter(this);
        presenter.initPresenter();
        adapter = new BillDateAdapter(presenter.getListTemp(), this);
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(getContext(), 2);
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

    @Override
    public void addItemToAdapter(@NotNull Bill bill) {
        adapter.add(bill);
    }
}
