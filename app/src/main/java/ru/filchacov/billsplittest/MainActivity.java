package ru.filchacov.billsplittest;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import ru.filchacov.billsplittest.AddFriend.FriendItem;
import ru.filchacov.billsplittest.AuthMVP.AuthFragment;
import ru.filchacov.billsplittest.Bill.Bill;
import ru.filchacov.billsplittest.ReadMVP.ReadFragment;
import ru.filchacov.billsplittest.db.User;
import ru.filchacov.billsplittest.db.UserDB;

public class MainActivity extends AppCompatActivity implements OnClickFriendToBill, ExitFromBill, ShowFriendFragment, MainActivityInterface, ShowUpButton, ToolbarSettings {

    private DrawerLayout mDrawer;
    public Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    MainPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UserDB db = App.getInstance().getDatabase();
        //User userDB = db.getuserDao().getByEmail(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail());


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nvDrawer = findViewById(R.id.navigation);
        mDrawer = findViewById(R.id.mainActivity);


        setupDrawerContent();

        if (savedInstanceState == null) {
            showAuthFragment();
        }
        presenter = new MainPresenter();
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
}