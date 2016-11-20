package webit.bthereapp.Application;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import io.realm.Realm;
import webit.bthereapp.Adapters.DrawerItemCustomAdapter;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.TriangleButton;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.ShowDayFragment;
import webit.bthereapp.Screens.Calendar.ShowWeekFragment;
import webit.bthereapp.Screens.Customer.AboutUsClientFragment;
import webit.bthereapp.Screens.Customer.AdvancedSearchFragment;
import webit.bthereapp.Screens.Customer.CalendarPrintDialogClientFragment;
import webit.bthereapp.Screens.Customer.FragmentTopUser;
import webit.bthereapp.Screens.Customer.GivingServiceFragment;
import webit.bthereapp.Screens.Customer.OrderDetailsFragment;
import webit.bthereapp.Screens.Customer.RegisterUser_To_Existed_Customer_Fragment;
import webit.bthereapp.Screens.Customer.SearchFragment;
import webit.bthereapp.Screens.Customer.SearchResultFragment;
import webit.bthereapp.Screens.General.BaseActivity;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.MainActivity.BottomFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Customer.DefinitionsFromTheCustomerFragment;
import webit.bthereapp.Utils.CalendarUtil;
import webit.bthereapp.Utils.Utils;


public class NavigetionLayout extends BaseActivity {
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    private ObjectDrawerItem[] drawerItem;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    ActionBarDrawerToggle mDrawerToggle;
    public static int N = 7;
    SharedPreferences sharedPreferences;
    DrawerItemCustomAdapter drawerItemAdapter;
    int displayView = 0;
    private Fragment fragment = null;

    String AdvanceSearchJson;
    String searchWord;
    public int from;
    public int otherFrom;
    public boolean searchByCity;
    public static Context baseContext;
    public FrameLayout mallFL;
    private TriangleButton triangle;
    private FrameLayout con_bottom;
    public boolean leftSearchResult = false;

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getAdvanceSearchJson() {
        return AdvanceSearchJson;
    }

    public void setAdvanceSearchJson(String advanceSearchJson) {
        AdvanceSearchJson = advanceSearchJson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        baseContext = getBaseContext();
        setContentView(R.layout.activity_navigetion_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        displayView = getIntent().getIntExtra("ShowDifferentView", 0);
        setSearchWord(getIntent().getStringExtra("searchWord"));
        setAdvanceSearchJson(getIntent().getStringExtra("advanceSearchObject"));
        from = getIntent().getIntExtra("sentFrom", 0);
        searchByCity = getIntent().getBooleanExtra("searchByCity", false);

        SearchFragment.removeInstance();

        mallFL = (FrameLayout) findViewById(R.id.content_all);

        initFragmentBottom(new BottomFragment());
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        drawerItem = new ObjectDrawerItem[N];
        drawerItem[0] = new ObjectDrawerItem(R.drawable.diary, mNavigationDrawerItemTitles[0]);
        drawerItem[1] = new ObjectDrawerItem(R.drawable.service_providers, mNavigationDrawerItemTitles[1]);
        drawerItem[2] = new ObjectDrawerItem(R.drawable.definitions, mNavigationDrawerItemTitles[2]);
        drawerItem[3] = new ObjectDrawerItem(R.drawable.about, mNavigationDrawerItemTitles[3]);
        drawerItem[4] = new ObjectDrawerItem(R.drawable.supplier, mNavigationDrawerItemTitles[4]);
        drawerItem[5] = new ObjectDrawerItem(R.drawable.print, mNavigationDrawerItemTitles[5]);
        drawerItem[6] = new ObjectDrawerItem(R.drawable.log_out, mNavigationDrawerItemTitles[6]);

        drawerItemAdapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem, R.color.orange, mDrawerList);
        mDrawerList.setAdapter(drawerItemAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mTitle = mDrawerTitle = getTitle();
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
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
        mDrawerLayout.closeDrawer(mDrawerList);
        drawerItemAdapter.setSelectedItem(-1);
        triangle = (TriangleButton) findViewById(R.id.triangle);

        String Language = Locale.getDefault().getDisplayLanguage();
        if (Language.equals("עברית")) {
            triangle.setBackground(this.getResources().getDrawable(R.drawable.triangle_blue_2));
        } else {
            triangle.setBackground(this.getResources().getDrawable(R.drawable.triangle_blue_2_en));
        }
        super.existsSuplierActionBar(mDrawerLayout, mDrawerList, false);
        if (displayView == 0) {
            SearchFragment.setInstance(null);
            initFragmentMain(SearchFragment.getInstance(), true);
        } else {
            super.gone_icon();
            super.gone_name();
            SearchResultFragment.removeInstance();
            initFragmentMain(SearchResultFragment.getInstance(), true);
            initFragmentBottom(new BottomFragment());
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            triangle.setVisibility(View.GONE);
        }
        con_bottom = (FrameLayout) findViewById(R.id.con_bottom_);
    }


    public void hideFragmentBottom() {
        con_bottom.setVisibility(View.GONE);
    }


    public void visibleFragmentBottom() {
        con_bottom.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.content_all);

        if (uploadType != null) {
            uploadType.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            view.setBackgroundColor(getResources().getColor(R.color.orange));
            selectItem(position);
        }

        private void selectItem(int position) {
            fragment = null;
            drawerItemAdapter.setSelectedItem(position);
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            switch (position) {
                case 1:
                    //my giving service
                    if (!(currentFragment instanceof GivingServiceFragment)) {
                        GivingServiceFragment.removeInstance();
                        GetProviderListForCustomer();
                    }
                    break;
                case 2:
                    //definitions
                    if (!(currentFragment instanceof DefinitionsFromTheCustomerFragment)) {
                        DefinitionsFromTheCustomerFragment.removeInstance();
                        fragment = DefinitionsFromTheCustomerFragment.getInstance();
                    }
                    break;
                case 3:
                    //about us information
                    if (!(currentFragment instanceof AboutUsClientFragment)) {
                        AboutUsClientFragment.removeInstance();
                        fragment = AboutUsClientFragment.getInstance();
                    }
                    break;
                case 4:
                    //to be supplier
                    //if the user is already a supplier

                    if (ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails() != null) {
                        Toast.makeText(getBaseContext(), getResources().getString(R.string.provider_exists), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent2 = new Intent();
                        intent2.putExtra("fragmentNum", 1);
                        Realm realm = Utils.getRealmInstance(getBaseContext());
                        realm.beginTransaction();
                        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                        realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                        realm.commitTransaction();
                        BusinessDetailsFragment.removeInstance();
                        BusinessGeneralData.removeInstance();
                        AlertsFragment.removeInstance();
                        BusinessProfileFragment.removeInstance();
                        ContactListFragment.removeInstance();
                        BusinessPaymentFragment.removeInstance();
                        intent2.setClass(NavigetionLayout.this, MainActivity.class);
                        startActivity(intent2);
                    }
                    break;
                case 5:
                    //to print the calendar
                    FragmentManager fm = getSupportFragmentManager();
                    CalendarPrintDialogClientFragment newFragment = new CalendarPrintDialogClientFragment();
                    newFragment.show(fm, "dialog");
                    mDrawerLayout.closeDrawers();
                    break;
                case 6:
                    //to out
                    CalendarUtil.type_of_service = 0;
                    CalendarUtil.is_calendar_of_provider = false;
                    CalendarUtil.tasksCalandersfree_ = new ArrayList<>();
                    CalendarUtil.taskCalanderListFree = new ArrayList<>();
                    CalendarUtil.OrderDetailsObj_ = new ArrayList<>();
                    CalendarUtil.serviceProviders = new ArrayList<>();
                    saveInSharedPreference();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    Intent intent = new Intent();
                    intent.putExtra("fragmentFlag2", 0);
                    Realm realm2 = Utils.getRealmInstance(getBaseContext());
                    realm2.beginTransaction();
                    realm2.where(UserRealm.class).findAll().deleteAllFromRealm();
                    realm2.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                    realm2.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                    realm2.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                    realm2.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                    realm2.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                    realm2.commitTransaction();
                    UserObj.removeInstance();
                    RegisterUserFragment.removeInstance();
                    BusinessDetailsFragment.removeInstance();
                    BusinessGeneralData.removeInstance();
                    AlertsFragment.removeInstance();
                    BusinessProfileFragment.removeInstance();
                    ContactListFragment.removeInstance();
                    BusinessPaymentFragment.removeInstance();
                    ProviderDetailsObj.removeInstance();
                    intent.setClass(NavigetionLayout.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    //bussines search
                    FragmentTopUser.removeInstance();
                    fragment = FragmentTopUser.getInstance();
                    break;
            }
            if (fragment != null && position != 1) {
                initFragmentMain(fragment, true);
            }
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        }
    }

    private void GetProviderListForCustomer() {
        Realm realm = Utils.getRealmInstance(getBaseContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        double d;
        if (userRealm != null)
            d = userRealm.getUserID();
        else
            d = 0;
        String jsonString = "{\"iUserId\":" + d + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetProviderListForCustomer(getBaseContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
                    @Override
                    public void onExecute(ArrayList<SearchResulstsObj> list) {

                        if (list != null && list.size() > 0) {
                            GivingServiceFragment.getInstance().set_sales_array(list);
                            fragment = GivingServiceFragment.getInstance();
                            initFragmentMain(fragment, true);
                        } else {
                            Toast.makeText(getBaseContext(), getResources().getString(R.string.there_are_no_providers_to_this_customer), Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    public void enter_to_existed_customer() {
        initFragmentMain(OrderDetailsFragment.getInstance(), true);
        SearchFragment.setInstance(null);
        //visible the incon and the name in top in existed user
        visible_icon();
        visible_name();
        set_name_new_c();
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        triangle.setVisibility(View.VISIBLE);
        visibleFragmentBottom();
    }

    //update the name in top in existed user after change in definitions
    public void set_name_after_update() {
        set_fname_after_update();
    }

    private void saveInSharedPreference() {
        sharedPreferences = this.getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phoneNumber", null);
        editor.putString("password", null);
        editor.commit();
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }

    public void initFragmentMain(Fragment fragment, boolean isAddToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (isAddToBackStack) {
            //looks like checks if the fragment is already added, but FragmentTopUser is added a few times with another content frame so took it off...
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            beginTransaction.replace(R.id.content_frame, fragment, fragment.getClass().toString()).addToBackStack(fragment.getClass().toString());
        } else {
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            beginTransaction.replace(R.id.content_frame, fragment, fragment.getClass().toString());
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public void initFragmentAll(Fragment fragment, boolean isAddToBackStack, int hight) {
        mallFL.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) mallFL.getLayoutParams();
        params1.setMargins(0, hight, 0, 0);
        mallFL.setLayoutParams(params1);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (isAddToBackStack) {
            if (fragmentManager.findFragmentByTag(fragment.getClass().toString()) == null) {
                beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                beginTransaction.replace(R.id.content_all, fragment, fragment.getClass().toString()).addToBackStack(fragment.getClass().toString());
            }
        } else {
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
            beginTransaction.replace(R.id.content_all, fragment);
        }
        beginTransaction.commitAllowingStateLoss();
    }

    public void initFragmentBottom(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.con_bottom_, fragment).commit();
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        Fragment currentAllFragment = getSupportFragmentManager().findFragmentById(R.id.content_all);
        // check if the change place dialog is not shown
        if ((currentFragment instanceof SearchResultFragment) && currentAllFragment == null) {
            switch (from) {
                case 1:
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("showAdvanceSearch", true);
                    startActivity(intent);
                    this.finish();
                    break;
                case 2:
                    beginTransaction.replace(R.id.content_frame, AdvancedSearchFragment.getInstance());
//                    initFragmentMain(AdvancedSearchFragment.getInstance(), true);
                    break;
                case 3:
                    Intent intent1 = new Intent(this, MainActivity.class);
                    startActivity(intent1);
                    this.finish();
                    break;
                case 4:
                    SearchFragment.setInstance(null);
                    beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                    drawerItemAdapter.setSelectedItem(-1);
                    drawerItemAdapter.no_choice();
                    break;
            }
        }
//      else if (currentFragment instanceof OrderDetailsFragment && otherFrom == 10) {
//        }
        else {
            if (currentFragment instanceof OrderDetailsFragment && (otherFrom == 5 || otherFrom == 10)) {
                ShowDayFragment.getInstance().notify_data();
                ShowWeekFragment.getInstance().notify_data();
            }
            Fragment fragment;
            if (getSupportFragmentManager() != null && getSupportFragmentManager().getBackStackEntryCount() != 0) {
                FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                String str = backEntry.getName();
                if (!(backEntry instanceof MainActivity)) {
                    currentFragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
                    if (currentFragment instanceof GivingServiceFragment) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                        // check if the payment dialog is not shown
                    } else if (currentFragment instanceof DefinitionsFromTheCustomerFragment && currentAllFragment == null) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                    } else if (currentFragment instanceof AdvancedSearchFragment) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                    } else if (currentFragment instanceof BusinessProfileFragment) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchResultFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                    } else if (currentFragment instanceof AboutUsClientFragment) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                    } else if (currentFragment instanceof FragmentTopUser) {
                        SearchFragment.setInstance(null);
                        beginTransaction.replace(R.id.content_frame, SearchFragment.getInstance());
                        drawerItemAdapter.setSelectedItem(-1);
                        drawerItemAdapter.no_choice();
                    } else {
                        if (currentAllFragment != null) {
                            fragment = getSupportFragmentManager().findFragmentByTag(str);
                            if (fragment instanceof BaseFragment)
                                ((BaseFragment) fragment).OnBackPress();
                            getSupportFragmentManager().popBackStack();
                        } else if (!(currentFragment instanceof SearchFragment)) {
                            fragment = getSupportFragmentManager().findFragmentByTag(str);
                            if (fragment instanceof BaseFragment)
                                ((BaseFragment) fragment).OnBackPress();
                            if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                                getSupportFragmentManager().popBackStack();
                        } else {
                            Utils.exitAppAlert(this);
                        }
                    }
                }
            }
        }

        beginTransaction.commitAllowingStateLoss();

    }

    public void set_no_choose() {
        drawerItemAdapter.setSelectedItem(-1);
        drawerItemAdapter.no_choice();
    }
}


