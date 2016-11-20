package webit.bthereapp.Screens.General;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.SplashActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;

public class BaseActivity extends AppCompatActivity {
    private ActionBar mActionBar;
    private ImageButton actionTitle;
    private LinearLayout l_name;
    private CustomLightTextView name;
    private ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        setActionBar();
        if (ConnectionUtils.return_from_camera==0) {
            ConnectionUtils.return_from_camera=1;
            Intent mStartActivity = new Intent(this, SplashActivity.class);
            int mPendingIntentId = 123456;
            PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
            System.exit(0);
        }
    }

    private void setActionBar() {
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayUseLogoEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        View mActionBarView = getLayoutInflater().inflate(R.layout.custome_action_bar_layout, null);
        getSupportActionBar().setElevation(0);
        l_name = (LinearLayout) mActionBarView.findViewById(R.id.l_name);
        l_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails() != null && ConnectionUtils.which_calendar == false) {
                    ConnectionUtils.which_calendar = true;
                    Intent mainIntent = new Intent();

                    mainIntent.setClass(getBaseContext(), ExistsSuplierActivity.class);
                    startActivity(mainIntent);
                } else if (ConnectionUtils.which_calendar) {
                    ConnectionUtils.which_calendar = false;
                    Intent mainIntent = new Intent();
                    mainIntent.setClass(getBaseContext(), NavigetionLayout.class);
                    startActivity(mainIntent);
                }
            }
        });
        actionTitle = (ImageButton) mActionBarView.findViewById(R.id.actionTitle);
        name = (CustomLightTextView) mActionBarView.findViewById(R.id.name);
        mActionBar.setCustomView(mActionBarView);
        logout = (ImageView) mActionBarView.findViewById(R.id.logout);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
        Toolbar parent = (Toolbar) mActionBarView.getParent();
        parent.setContentInsetsAbsolute(0, 0);
    }

    public void existsSuplierActionBar(final DrawerLayout mDrawerLayout, final ListView mDrawerList, boolean color_) {

        actionTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(mDrawerList))
                    mDrawerLayout.closeDrawer(mDrawerList);
                else
                    mDrawerLayout.openDrawer(mDrawerList);
            }
        });
        l_name.setVisibility(View.VISIBLE);


        if (!color_) {
            name.setTextColor(getResources().getColor(R.color.color3));
            logout.setImageResource(R.drawable.client_galaxy_icons1_03);
//            name.setText(UserObj.getInstance().getNvFirstName());
            Realm realm = Utils.getRealmInstance(this);
            UserRealm userRealm = realm.where(UserRealm.class).findFirst();
            if (userRealm != null)
                name.setText(userRealm.getNvFirstName().toString());

        } else {
            name.setTextColor(getResources().getColor(R.color.color4));
            logout.setImageResource(R.drawable.supplier_galaxy_icons_x1_04);
            if (ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails() != null)
                name.setText(ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails().getNvSupplierName());
        }
    }

    public void gone_icon() {
        actionTitle.setVisibility(View.GONE);

    }

    public void set_name_new_c() {
        name.setTextColor(getResources().getColor(R.color.color3));
        logout.setImageResource(R.drawable.client_galaxy_icons1_03);
//        name.setText(UserObj.getInstance().getNvFirstName());
        Realm realm = Utils.getRealmInstance(this);
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null)
            name.setText(userRealm.getNvFirstName().toString());
    }

    public void gone_name() {
        l_name.setVisibility(View.GONE);
    }

    public void visible_icon() {
        actionTitle.setVisibility(View.VISIBLE);

    }

    //visible the name in top in existed user
    public void visible_name() {
        l_name.setVisibility(View.VISIBLE);
    }

    //update the name in top in existed user after change in definitions
    public void set_fname_after_update() {
//        name.setText(UserObj.getInstance().getNvFirstName());
        Realm realm = Utils.getRealmInstance(this);
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null)
            name.setText(userRealm.getNvFirstName().toString());
    }

}
