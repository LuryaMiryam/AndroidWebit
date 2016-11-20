package webit.bthereapp.Screens.Supplier;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.Adapters.DrawerItemCustomAdapter;
import webit.bthereapp.Application.ObjectDrawerItem;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.NextFragment;
import webit.bthereapp.Screens.Customer.SearchFragment;
import webit.bthereapp.Screens.Customer.SearchResultFragment;
import webit.bthereapp.Screens.General.BaseActivity;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.MainActivity.BottomFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Register.MainActivity.TopFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegulationsFragment;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;

public class ExistsSuplierActivity extends BaseActivity implements View.OnFocusChangeListener, RegulationsFragment.OnFragmentInteractionListener {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    private ObjectDrawerItem[] drawerItem;
    private CharSequence mDrawerTitle;
    private int a = 0;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    public static int N = 8;
    public static int mSelectedItem;
    DrawerItemCustomAdapter drawerItemAdapter;
    private int fr;
    int displayView = 0;
    private FrameLayout container_Top;
    public static Context baseContext;
    SharedPreferences sharedPreferences;
    public static FrameLayout mallFL;
    Dialog dialog;
    FrameLayout mContainerTopFL, mContainerFrameFL, container_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        baseContext = getBaseContext();
        setContentView(R.layout.activity_exists_suplier);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        displayView = getIntent().getIntExtra("ShowDifferentView", 0);
        fr = getIntent().getIntExtra("fr", 0);
        mallFL = (FrameLayout) findViewById(R.id.content_all);
        mContainerTopFL = (FrameLayout) findViewById(R.id.container_Top);
        ViewGroup.LayoutParams params = mContainerTopFL.getLayoutParams();
        a = params.height;
        mContainerFrameFL = (FrameLayout) findViewById(R.id.content_frame);
        container_main = (FrameLayout) findViewById(R.id.container_main);
        CalendarUtil.taskCalanderListFree = null;

        SearchFragment.removeInstance();
        initFragmentBottom(new BottomFragment());
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.supplier_navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        drawerItem = new ObjectDrawerItem[N];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.diary, mNavigationDrawerItemTitles[0]);
        drawerItem[1] = new ObjectDrawerItem(R.drawable.supplier_bussnis_page, mNavigationDrawerItemTitles[1]);
        drawerItem[2] = new ObjectDrawerItem(R.drawable.service_providers, mNavigationDrawerItemTitles[2]);
        drawerItem[3] = new ObjectDrawerItem(R.drawable.definitions, mNavigationDrawerItemTitles[3]);
        drawerItem[4] = new ObjectDrawerItem(R.drawable.supplier_reports, mNavigationDrawerItemTitles[4]);
        drawerItem[5] = new ObjectDrawerItem(R.drawable.about, mNavigationDrawerItemTitles[5]);
        drawerItem[6] = new ObjectDrawerItem(R.drawable.print, mNavigationDrawerItemTitles[6]);
        drawerItem[7] = new ObjectDrawerItem(R.drawable.log_out, mNavigationDrawerItemTitles[7]);

        drawerItemAdapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem, R.color.color4, mDrawerList);
        mDrawerList.setAdapter(drawerItemAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
//R.drawable
                R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyCustomersFragment.removeInstance();

        if (displayView == 0 && fr != 2) {

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

            NextFragment.removeInstance();
            beginTransaction.replace(R.id.content_frame, NextFragment.getInstance());
            beginTransaction.commitAllowingStateLoss();
        } else if (displayView != 0 && fr != 2) {
            SearchResultFragment.removeInstance();
            initFragmentMain(SearchResultFragment.getInstance(), false);
        }

        mDrawerList.setItemChecked(0, true);
        mDrawerList.setSelection(0);
        setTitle(mNavigationDrawerItemTitles[0]);
        mDrawerLayout.closeDrawer(mDrawerList);
        showDialogOpen();
        DefinitionsFragment.removeInstance();
        ReportsFragment.removeInstance();
        super.existsSuplierActionBar(mDrawerLayout, mDrawerList, true);
        if (fr == 2)
            initFragmentMain(DefinitionsFragment.getInstance(), false);

        visibleFragmentMain();
        hideFragmentTop();
        hideContainerMain();
    }


    public void initFragmentMain(Fragment fragment, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (isAddToBackStack) {

            if (fragmentManager.findFragmentByTag(fragment.getClass().toString()) == null) {
                beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);


                beginTransaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());
            }
        } else {
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);

            beginTransaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());
        }
        beginTransaction.commitAllowingStateLoss();
        visibleFragmentMain();
        hideFragmentTop();
        hideContainerMain();
    }

    private void saveInSharedPreference() {
        sharedPreferences = this.getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phoneNumber", null);
        editor.putString("password", null);
        editor.commit();

    }

    //init top Fragment
    public void initFragmentTop(int position, String title, boolean is_back) {
        mContainerTopFL.setVisibility(View.VISIBLE);
        TopFragment topFragment = TopFragment.newInstance(position, title);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (is_back == false)
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            beginTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        beginTransaction.replace(R.id.container_Top, topFragment, topFragment.getClass().toString()).commit();

        container_Top = (FrameLayout) findViewById(R.id.container_Top);
        ViewGroup.LayoutParams params = container_Top.getLayoutParams();
        params.height = (a / 3) * 2;
        container_Top.setLayoutParams(params);
        container_Top.requestLayout();

    }

    public void hideFragmentTop() {
        if (mContainerTopFL != null)
            mContainerTopFL.setVisibility(View.GONE);
    }

    public void hideContainerMain() {
        if (container_main != null)
            container_main.setVisibility(View.GONE);
    }

    public void visibleFragmentMain() {
        mContainerFrameFL.setVisibility(View.VISIBLE);

    }


    public void initFragmentBottom(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.con_bottom, fragment).commit();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }

    @Override
    public void onFragmentInteraction(int b) {
        ((RegisterUserFragment) getSupportFragmentManager().findFragmentByTag(RegisterUserFragment.class.toString())).setReadRegulations(b);
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setBackgroundColor(getResources().getColor(R.color.light_blue));
            selectItem(position);

            if (mContainerFrameFL.getVisibility() == View.GONE) {
                visibleFragmentMain();
                hideFragmentTop();
                hideContainerMain();
            }
        }

        private void selectItem(int position) {
            drawerItemAdapter.setSelectedItem(position);
            Fragment fragment = null;
            switch (position) {
                case 0:
                    NextFragment.removeInstance();
                    fragment = NextFragment.getInstance();
                    break;
                case 1:
                    Intent intent = new Intent(ExistsSuplierActivity.this, ExistsSuplierDefinitionsActivity.class);
                    intent.putExtra("fr", 5);
                    startActivity(intent);
                    finish();

                    fragment = null;

                    break;
                case 2:
                    fragment = MyCustomersFragment.getInstance();
                    break;
                case 3:
                    fragment = DefinitionsFragment.getInstance();
                    break;
                case 4:
                    fragment = ReportsFragment.getInstance();
                    break;
                case 5:
                    fragment = AboutUsFragment.getInstance();
                    break;
                case 6:
                    FragmentManager fm = getSupportFragmentManager();
                    CalendarPrintDialogFragment newFragment = new CalendarPrintDialogFragment();
                    newFragment.show(fm, "dialog");
                    mDrawerLayout.closeDrawers();
                    break;
                case 7:
                    CalendarUtil.type_of_service = 0;
                    CalendarUtil.is_calendar_of_provider = false;
                    CalendarUtil.tasksCalandersfree_ = new ArrayList<>();
                    CalendarUtil.taskCalanderListFree = new ArrayList<>();
                    CalendarUtil.OrderDetailsObj_ = new ArrayList<>();
                    CalendarUtil.serviceProviders = new ArrayList<>();

                    saveInSharedPreference();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    intent = new Intent();
                    intent.putExtra("fragmentFlag2", 0);
                    Realm realm = Utils.getRealmInstance(getBaseContext());
                    realm.beginTransaction();

                    realm.where(UserRealm.class).findAll().deleteAllFromRealm();
                    realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                    realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                    realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                    realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                    realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                    realm.commitTransaction();

                    RegisterUserFragment.removeInstance();
                    BusinessDetailsFragment.removeInstance();
                    BusinessGeneralData.removeInstance();
                    AlertsFragment.removeInstance();
                    BusinessProfileFragment.removeInstance();
                    ContactListFragment.removeInstance();
                    BusinessPaymentFragment.removeInstance();

                    ProviderDetailsObj.removeInstance();

                    intent.setClass(ExistsSuplierActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    NextFragment.removeInstance();
                    fragment = NextFragment.getInstance();
                    break;
            }
            if (fragment != null && position != 1) {
                initFragmentMain(fragment, false);
            }
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);


        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    private void showDialogOpen() {
        if (AddProviderDetails.getInstance() != null && AddProviderDetails.getInstance().getObjProviderGeneralDetails() != null && AddProviderDetails.getInstance().getObjProviderGeneralDetails().getServiceProviders() != null && AddProviderDetails.getInstance().getObjProviderGeneralDetails().getServiceProviders().size() > 0) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    FragmentManager fm = getSupportFragmentManager();
                    WelcomeToCusDialogFragment newFragment_ = new WelcomeToCusDialogFragment();
                    newFragment_.show(fm, "dialog");
                }
            }, 600);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if (!(currentFragment instanceof NextFragment)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction beginTransaction = fragmentManager.beginTransaction();

            beginTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
            beginTransaction.replace(R.id.content_frame, NextFragment.getInstance());

            beginTransaction.commitAllowingStateLoss();
        } else {
            Utils.exitAppAlert(this);
        }
    }

    public void changeColor(View topCalander) {
        topCalander.setBackgroundColor(getResources().getColor(R.color.light_blue));
    }

}
