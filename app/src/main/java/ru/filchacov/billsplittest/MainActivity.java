package ru.filchacov.billsplittest;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import ru.filchacov.billsplittest.aboutUserMVP.AboutUserView;
import ru.filchacov.billsplittest.addFriend.FriendItem;
import ru.filchacov.billsplittest.authMVP.AuthFragment;
import ru.filchacov.billsplittest.bill.Bill;
import ru.filchacov.billsplittest.readMVP.ReadFragment;

public class MainActivity extends AppCompatActivity implements OnClickFriendToBill, ExitFromBill, ShowFriendFragment, MainActivityInterface, ShowUpButton, ToolbarSettings {

    private DrawerLayout mDrawer;
    public Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    MainPresenter presenter;
    private TextView headerName;
    private View headerView;
    private TextView headerEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nvDrawer = findViewById(R.id.navigation);
        mDrawer = findViewById(R.id.mainActivity);


        setupDrawerContent();

        if (savedInstanceState == null) {
            showAuthFragment();
        }
        presenter = new MainPresenter(this);
        headerView = nvDrawer.getHeaderView(0);
        headerName = headerView.findViewById(R.id.header_name);
        headerEmail = headerView.findViewById(R.id.header_email);
        headerView.setOnClickListener(v -> {
            showAboutUser();
            mDrawer.closeDrawers();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void showAboutUser() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AboutUserView(), AboutUserView.TAG)
                .addToBackStack(null)
                .commit();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Создать новый фрагмент и задать фрагмент для отображения
        // на основе нажатия на элемент навигации
        switch (menuItem.getItemId()) {
            case R.id.permanent_friend:
                showPermanentFriendFragment();
                break;
            case R.id.exit:
                signOut();
                break;
            default:
                showReadFragment();

        }
        menuItem.setChecked(true);
        mDrawer.closeDrawers();
    }


    private void signOut() {
        presenter.signOut();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new AuthFragment())
                .commit();
    }




    private void showAuthFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, new AuthFragment())
                .commit();
    }

    private void showPermanentFriendFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, AddPermanentFriendView.getNewInstance(), AddPermanentFriendView.TAG)
                .addToBackStack(null)
                .commit();
    }


    private AddFriendFragment makeFragmentFriend(Bill bill) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);
        return AddFriendFragment.getNewInstance(bundle);
    }

    private void showBillForFriend(Bill bill, FriendItem friendItem) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentBill(bill, friendItem), BillListFragment.TAG)
                .addToBackStack(null)
                .commit();
    }

    private BillListFragment makeFragmentBill(Bill bill, FriendItem friendItem) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("bill", bill);
        bundle.putParcelable("friendItem", friendItem);
        return BillListFragment.getNewInstance(bundle);
    }

    @Override
    public void clickFriendToBill(@NotNull Bill bill, @NotNull FriendItem friendItem) {
        showBillForFriend(bill, friendItem);
    }

    @Override
    public void exitBill(@NotNull Bill bill) {
        getSupportFragmentManager().popBackStack();
    }

    public void showMainFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.read_fragment);
        if (fragment == null) {
            fragment = new ReadFragment();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public void showReadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.read_fragment);
        if (fragment == null) {
            fragment = new ReadFragment();
        }
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }

    @Override
    public void showFriendFragment(Bill bill) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, makeFragmentFriend(bill), AddFriendFragment.getTAG())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void navigationDrawerInvisible() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void navigationDrawerVisible() {
        drawerToggle.setDrawerIndicatorEnabled(true);
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void showUpButton(boolean b) {
        if (getSupportActionBar() != null) {
            toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
            toolbar.setNavigationOnClickListener(v -> onSupportNavigateUp());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setupDrawerContent() {
        drawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(nvDrawer);
        drawerToggle.syncState();
    }

    @Override
    public void hideDrawerIndicator() {
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void setToolbarTitle(int res) {
        toolbar.setTitle(res);
    }

    @Override
    public void setHeaderEmail(@NonNull String email, @NonNull String name) {
        headerName.setText(name);
        headerEmail.setText(email);
    }

    @Override
    public void navHeaderInit() {
        presenter.init();
    }
}