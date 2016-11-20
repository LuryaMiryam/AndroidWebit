package webit.bthereapp.Screens.Register.MainActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.AdvancedSearchFragment;
import webit.bthereapp.Screens.Customer.SearchFragment;
import webit.bthereapp.Screens.General.BaseActivity;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.Dialogs.CreditCardFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegulationsFragment;
import webit.bthereapp.Utils.Utils;


public class MainActivity extends BaseActivity implements View.OnFocusChangeListener, RegulationsFragment.OnFragmentInteractionListener {
    FrameLayout mContainerTopFL, mContainerBottomFL;
    public static int Num;
    public boolean return_from_image = false;
    public int fragmentNum;

    public int getFrom() {
        return from;
    }


    private int from;
    public boolean showAdvanceSearch = false;
    private boolean is_back = false;

    public ScrollView getScrollView() {
        return scrollView;
    }

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();

        fragmentNum = intent.getIntExtra("fragmentNum", 0);
        from = getIntent().getIntExtra("sentFrom", 0);

        super.gone_icon();
        super.gone_name();

        SearchFragment.removeInstance();

        showAdvanceSearch = getIntent().getBooleanExtra("showAdvanceSearch", false);

        scrollView = (ScrollView) findViewById(R.id.scroll_main);
        mContainerTopFL = (FrameLayout) findViewById(R.id.container_Top);
        mContainerBottomFL = (FrameLayout) findViewById(R.id.container_bottom);

        //if the user in middle registion, he send to the fragment when he stopped
        if (fragmentNum == 0) {
            if (!showAdvanceSearch)
                initFragmentMain(SearchFragment.getInstance(), false, true);
            else
                initFragmentMain(AdvancedSearchFragment.getInstance(), false, true);
        } else if (fragmentNum == 1)

            initFragmentMainNotInOrder(new BusinessDetailsFragment(), false, true, 1);

        else if (fragmentNum == 2) {

            initFragmentMain(BusinessDetailsFragment.getInstance(), false, true);
            initFragmentMainNotInOrder(new BusinessGeneralData(), false, true, 2);
        } else if (fragmentNum == 3) {

            initFragmentMain(new BusinessDetailsFragment(), false, true);
            initFragmentMain(new BusinessGeneralData(), false, true);
            initFragmentMainNotInOrder(new AlertsFragment(), false, true, 3);

        } else if (fragmentNum == 4) {
            initFragmentMain(new BusinessDetailsFragment(), false, true);
            initFragmentMain(new BusinessGeneralData(), false, true);
            initFragmentMain(new AlertsFragment(), false, true);
            initFragmentMainNotInOrder(BusinessProfileFragment.getInstance(), false, true, 4);

        } else if (fragmentNum == 5) {
            initFragmentMain(new BusinessDetailsFragment(), false, true);
            initFragmentMain(new BusinessGeneralData(), false, true);
            initFragmentMain(new AlertsFragment(), false, true);
            initFragmentMain(new BusinessProfileFragment(), false, true);
            initFragmentMainNotInOrder(new ContactListFragment(), false, true, 5);

        } else if (fragmentNum == 6) {
            initFragmentMain(new BusinessDetailsFragment(), false, true);
            initFragmentMain(new BusinessGeneralData(), false, true);
            initFragmentMain(new AlertsFragment(), false, true);
            initFragmentMain(new BusinessProfileFragment(), false, true);
            initFragmentMain(new ContactListFragment(), false, true);
            initFragmentMainNotInOrder(new BusinessPaymentFragment(), false, true, 6);

        } else if (fragmentNum == 7) {
//            RegisterUserFragment.removeInstance();
            initFragmentMain(RegisterUserFragment.getInstance(), false, true);

        }
//        }
        initFragmentBottom(new BottomFragment());
//        initFragmentBottom(BottomFragment.getInstance());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        }
    }


    //init top Fragment
    public void initFragmentTop(int position, String title) {
        mContainerTopFL.setVisibility(View.VISIBLE);
        TopFragment topFragment = TopFragment.newInstance(position, title);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_Top, topFragment, topFragment.getClass().toString()).commit();
    }

    public void initFragmentTop(int position, String title, int place, boolean is_back) {
        mContainerTopFL.setVisibility(View.VISIBLE);
        TopFragment topFragment = TopFragment.newInstance(position, title, place);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (is_back == false)
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            beginTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        beginTransaction.replace(R.id.container_Top, topFragment, topFragment.getClass().toString()).commit();
    }


    public void hideFragmentTop() {
        if (mContainerTopFL != null)
            mContainerTopFL.setVisibility(View.GONE);
    }

    public void hideFragmentBottom() {
        if (mContainerBottomFL != null)
            mContainerBottomFL.setVisibility(View.GONE);
    }

    public void visibleFragmentBottom() {
        if (mContainerBottomFL != null)
            mContainerBottomFL.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment uploadType = getSupportFragmentManager().findFragmentById(R.id.container_main);
        if (uploadType != null) {
            uploadType.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void change_background_to_supplier() {
        (findViewById(R.id.Container_all)).setBackgroundResource(R.drawable.bg_pic_supplier_x1);
    }

    public void scroll(int b) {
        (findViewById(R.id.scroll_main)).scrollTo(0, b);
    }

    public void initFragmentMainNotInOrder(Fragment fragment, boolean isTop, boolean isAddToBackStack, int num) {
        if (num > this.Num)
            this.Num = num;
        initFragmentMain(fragment, isTop, isAddToBackStack);
    }

    public void initFragmentMainYesInOrder(Fragment fragment, boolean isTop, boolean isAddToBackStack) {
        this.Num = -1;
        initFragmentMain(fragment, isTop, isAddToBackStack);
    }

    //init main Fragment
    public void initFragmentMain(Fragment fragment, boolean isTop, boolean isAddToBackStack) {


        //hide the keyboard
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        mContainerTopFL.setVisibility(View.VISIBLE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (is_back == false)
            beginTransaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
        else
            beginTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//            beginTransaction.setCustomAnimations(R.anim.exit_to_left,R.anim.enter_from_right,R.anim.exit_to_right,R.anim.enter_from_left);

        if (isAddToBackStack) {
            //first way when the numbers are not click remove the slashes(add the if and else)
            //     if (fragmentManager.findFragmentByTag(fragment.getClass().toString()) == null) {

            beginTransaction.replace(R.id.container_main, fragment, fragment.getClass().toString()).addToBackStack(fragment.getClass().toString());

            //  } else
            //    beginTransaction.replace(R.id.container_main, fragment);
        } else {
            if (fragmentManager.findFragmentByTag(fragment.getClass().toString()) == null) {
                beginTransaction.replace(R.id.container_main, fragment, fragment.getClass().toString());
            } else {
                beginTransaction.replace(R.id.container_main, fragment);
            }
        }
        beginTransaction.commitAllowingStateLoss();
        if (!isTop)
            mContainerTopFL.setVisibility(View.GONE);
        is_back = false;
    }

    //    //init bottom fragment
    public void initFragmentBottom(Fragment fragment) {
        mContainerTopFL.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_bottom, fragment, fragment.getClass().toString()).commit();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

    }


    @Override
    public void onFragmentInteraction(int b) {
        ((RegisterUserFragment) getSupportFragmentManager().findFragmentByTag(RegisterUserFragment.class.toString())).setReadRegulations(b);
    }

    @Override
    public void onBackPressed() {
        is_back = true;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container_main);
        Fragment creditCardFragment = getSupportFragmentManager().findFragmentById(R.id.container_main_bottom);
        if (currentFragment instanceof SearchFragment) {
            Utils.exitAppAlert(this);
            is_back = false;
        } else {

            if (currentFragment instanceof BusinessDetailsFragment && BusinessDetailsFragment.getInstance().isB()) {
                exitToCustomer(this);
                is_back = false;
            } else if (currentFragment instanceof BusinessGeneralData) {
                BusinessDetailsFragment.is_back = true;
                initFragmentMain(BusinessDetailsFragment.getInstance(), true, true);
            } else if (currentFragment instanceof RegisterUserFragment) {
//                SearchFragment.is_back = true;
                initFragmentMain(SearchFragment.getInstance(), false, true);
            } else if (currentFragment instanceof AlertsFragment) {
                BusinessGeneralData.is_back = true;
                initFragmentMain(BusinessGeneralData.getInstance(), true, true);
            } else if (currentFragment instanceof BusinessProfileFragment) {
                AlertsFragment.is_back = true;
                initFragmentMain(AlertsFragment.getInstance(), true, true);
            } else if (currentFragment instanceof ContactListFragment) {
                BusinessProfileFragment.is_back = true;
                initFragmentMain(BusinessProfileFragment.getInstance(), true, true);
            } else if (creditCardFragment != null) {
                if (creditCardFragment instanceof CreditCardFragment) {
                    getSupportFragmentManager().popBackStack();
                }
            } else if (currentFragment instanceof BusinessPaymentFragment) {
                ContactListFragment.is_back = true;
                initFragmentMain(ContactListFragment.getInstance(), true, true);
            } else if (currentFragment instanceof RegulationsFragment) {
                initFragmentMain(RegisterUserFragment.getInstance(), true, true);

            } else if (currentFragment instanceof AdvancedSearchFragment)
                initFragmentMain(SearchFragment.getInstance(), true, true);

            else {
                Fragment fragment;
                int i = getSupportFragmentManager().getBackStackEntryCount() - 1;

                if (i != -1) {
                    FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1);
                    String str = backEntry.getName();

                    fragment = getSupportFragmentManager().findFragmentByTag(str);
                    if (fragment instanceof BaseFragment)
                        ((BaseFragment) fragment).OnBackPress();
                    if (getSupportFragmentManager().getBackStackEntryCount() > 1)
                        getSupportFragmentManager().popBackStack();
                    if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                        if (from == 1)
                            this.finish();
                        else
                            Utils.exitAppAlert(this);
                    }
                }

            }
        }

    }

    //if the user pass back when he want to out from the registion
    public void exitToCustomer(final Context context/* ,final Fragment fragment*/) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(R.string.exit);
        alertDialogBuilder
                .setMessage(R.string.exit_to_customer_question)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConnectionUtils.which_calendar = false;
                        Intent intent = new Intent(context, NavigetionLayout.class);
                        startActivity(intent);

                        Realm realm = Utils.getRealmInstance(getBaseContext());
                        realm.beginTransaction();
                        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                        realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();
                        realm.commitTransaction();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void go_in_middle2() {


        //if the user stopped in the middle of the registion, it open where he stopped
        Realm realm = Utils.getRealmInstance(getBaseContext());
        SyncContactsRealm syncContactsRealm = realm.where(SyncContactsRealm.class).findFirst();
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        BusinessProfileRealm profileRealm = realm.where(BusinessProfileRealm.class).findFirst();
        AlertSettingsRealm alertSettingsRealm = realm.where(AlertSettingsRealm.class).findFirst();
        GeneralDetailsRealm generalDetailsRealm = realm.where(GeneralDetailsRealm.class).findFirst();
        ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();
        fragmentNum = 0;
        if (userRealm != null)
            fragmentNum = 1;
        if (syncContactsRealm != null) {
            fragmentNum = 6;
        } else if (profileRealm != null) {
            fragmentNum = 5;
        } else if (alertSettingsRealm != null) {
            fragmentNum = 4;
        } else if (generalDetailsRealm != null) {
            fragmentNum = 3;
        } else if (providerRealm != null) {
            fragmentNum = 2;
        }
    }

}
