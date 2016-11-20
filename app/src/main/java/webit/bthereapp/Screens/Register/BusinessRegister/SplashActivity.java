package webit.bthereapp.Screens.Register.BusinessRegister;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.BL.RegisterBL;
import webit.bthereapp.BL.SplashBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.FieldAndCategoryDM;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.SysAlertsListDM;
import webit.bthereapp.DM.UserRealm;

import com.splunk.mint.Mint;

import webit.bthereapp.Entities.FieldAndCategory;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.SysAlertsList;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.MyLocation;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.SearchFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Utils.Utils;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME = 1000;
    String phoneNumber;
    String password;
    boolean a = false;
    int a1 = 0;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //remove system action bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        ConnectionUtils.return_from_camera=false;
//        Log.d("onStart_f1", "oncreate_in_splash" + ConnectionUtils.return_from_camera);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

//        Log.d("onStart_f2", "oncreate_in_splash" + ConnectionUtils.return_from_camera);


        Mint.initAndStartSession(SplashActivity.this, "e4c7c8ba");
        Mint.addExtraData("isLive", String.valueOf(ConnectionUtils.isLive));

        //select loction
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getApplicationContext(), MyLocation.saveLocationResult);

        setLanguages();

        init();
        ConnectionUtils.return_from_camera = 2;
    }


    private void init() {

        ConnectionUtils.which_calendar = false;
        Log.d("aaaaaaa", "mm2" + ConnectionUtils.return_from_camera);
        if (ConnectionUtils.return_from_camera==1) {
            return_from_the_camera_();
        }
        else {
            //select user from realm
            RegisterUserFragment.removeInstance();
            Realm realm = Utils.getRealmInstance(this);
            realm.beginTransaction();

            UserRealm userRealm = realm.where(UserRealm.class).findFirst();
            if (userRealm == null) {
                realm.where(UserRealm.class).findAll().deleteAllFromRealm();
//                realm.createObject(UserRealm.class);
            }
            realm.commitTransaction();
            //check network
            if (ConnectionUtils.isNetworkAvailable(this)) {
                getInformation();
                getSysAlertsList();
                if (!CheckSharedPreference())
                    passPage(false);
                else
                    login();
            } else {
                //no connect to network
                ifNoConnection();
            }
        }
    }

    private void setLanguages() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("DETAILS", MODE_WORLD_READABLE);
        String lan = sharedPreferences.getString("Languages", null);
        if (lan != null) {
            ConnectionUtils.setLanguage(SplashActivity.this, lan);
        }
    }

    private void passPage(final boolean flag) {

        //delete realm
        Realm realm = Utils.getRealmInstance(getBaseContext());
        realm.beginTransaction();
        realm.where(UserRealm.class).findAll().deleteAllFromRealm();
        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
        realm.commitTransaction();

        //splash
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent();
                mainIntent.setClass(SplashActivity.this, MainActivity.class);
                mainIntent.putExtra("fragmentFlag", flag);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_TIME);
    }

    private void getProviderAllDetails(final double id) {

        String jsonString = "{\"iUserId\":" + id + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getProviderAllDetails(this, jsonObject, new IExecutable<ProviderDetailsObj>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(ProviderDetailsObj providerDetails) {
                        ProviderDetailsObj.getInstance().setObjUser(null);
                        ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(null);
                        ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(null);
                        ProviderDetailsObj.getInstance().setObjProviderAlertsSettings(null);
                        ProviderDetailsObj.getInstance().setObjCustomer(null);
                        ProviderDetailsObj.getInstance().setObjProviderProfile(null);

                        if (providerDetails != null && providerDetails.getObjProviderGeneralDetails() != null) {

                            ProviderDetailsObj.getInstance().setObjUser(providerDetails.getObjUser());
                            ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(providerDetails.getObjProviderBuisnessDetails());
                            ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(providerDetails.getObjProviderGeneralDetails());
                            ProviderDetailsObj.getInstance().setObjProviderAlertsSettings(providerDetails.getObjProviderAlertsSettings());
                            ProviderDetailsObj.getInstance().setObjCustomer(providerDetails.getObjCustomer());
                            ProviderDetailsObj.getInstance().setObjProviderProfile(providerDetails.getObjProviderProfile());


                            Realm realm = Utils.getRealmInstance(getBaseContext());
                            realm.beginTransaction();
                            ProviderBuisnessDetailsObj.setInstance(ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails());
                            realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails() != null) {
                                realm.copyToRealm(new ProviderRealm(ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails()));
                            }

                            realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails() != null) {
                                realm.copyToRealm(new GeneralDetailsRealm(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails()));
                            }

                            if (ProviderDetailsObj.getInstance().getObjProviderAlertsSettings() != null) {
                                realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                                realm.copyToRealm(new AlertSettingsRealm(ProviderDetailsObj.getInstance().getObjProviderAlertsSettings()));
                            }

                            realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderProfile() != null) {
                                realm.copyToRealm(new BusinessProfileRealm(ProviderDetailsObj.getInstance().getObjProviderProfile()));
                            }

                            realm.commitTransaction();

                        } else {
                            //if the user stopped in the middle of the registion, it open where he stopped
                            Realm realm = Utils.getRealmInstance(getBaseContext());


                            SyncContactsRealm syncContactsRealm = realm.where(SyncContactsRealm.class).findFirst();

                            BusinessProfileRealm profileRealm = realm.where(BusinessProfileRealm.class).findFirst();

                            AlertSettingsRealm alertSettingsRealm = realm.where(AlertSettingsRealm.class).findFirst();

                            GeneralDetailsRealm generalDetailsRealm = realm.where(GeneralDetailsRealm.class).findFirst();

                            ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();


                            if (syncContactsRealm != null) {
                                a = true;
                                a1 = 6;
                            } else if (profileRealm != null) {
                                a = true;
                                a1 = 5;
                            } else if (alertSettingsRealm != null) {
                                a = true;
                                a1 = 4;
                            } else if (generalDetailsRealm != null) {

                                a = true;
                                a1 = 3;
                            } else if (providerRealm != null) {
                                a = true;
                                a1 = 2;

                            }


                        }
                        if (!a) {
                            if (ConnectionUtils.return_from_camera==1) {
                                ConnectionUtils.which_calendar = false;
                                Intent mainIntent = new Intent();
                                mainIntent.setClass(SplashActivity.this, NavigetionLayout.class);
                                SplashActivity.this.startActivity(mainIntent);
                                SplashActivity.this.finish();
                            } else
                                //if the user stopped in the middle of the registion, it open where he stopped)
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        ConnectionUtils.which_calendar = false;
                                        Intent mainIntent = new Intent();
                                        mainIntent.setClass(SplashActivity.this, NavigetionLayout.class);
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    }
                                }, SPLASH_TIME);
                        } else {
                            if (ConnectionUtils.return_from_camera==1) {
                                Intent mainIntent = new Intent();
                                mainIntent.setClass(SplashActivity.this, MainActivity.class);
                                mainIntent.putExtra("fragmentNum", a1);
                                SplashActivity.this.startActivity(mainIntent);
                                SplashActivity.this.finish();
                            } else {
                                //if the user didnt bugun the registion, it open where in beginning
                                SearchFragment.removeInstance();
                                RegisterUserFragment.removeInstance();
                                BusinessDetailsFragment.removeInstance();
                                BusinessGeneralData.removeInstance();
                                AlertsFragment.removeInstance();
                                ContactListFragment.removeInstance();
                                BusinessPaymentFragment.removeInstance();
                                BusinessProfileFragment.removeInstance();

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent mainIntent = new Intent();
                                        mainIntent.setClass(SplashActivity.this, MainActivity.class);
                                        mainIntent.putExtra("fragmentNum", a1);
                                        SplashActivity.this.startActivity(mainIntent);
                                        SplashActivity.this.finish();
                                    }
                                }, SPLASH_TIME);
                            }
                        }

                    }
                }
        );
    }

    public void saveDetailsCustomers(UserObj objUser, double id) {

        objUser.setInstance(objUser);
        Realm realm = Utils.getRealmInstance(this);
        realm.beginTransaction();
//        realm.where(UserRealm.class).findAll().deleteAllFromRealm();
        if (objUser.getInstance() != null) {
            realm.copyToRealm(new UserRealm(objUser.getInstance()));
        }

        UserObj.getInstance().setiUserId(UserObj.getObjUser().getiUserId());
        ProviderDetailsObj.getInstance().setObjUser(UserObj.getInstance());
        realm.commitTransaction();

        getProviderAllDetails(id);
    }

    private void GetCustomerDetails(final double id) {
        String jsonString = "{\"iUserId\":" + id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCustomerDetails(getBaseContext(), jsonObject, new IExecutable<UserObj>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(UserObj objUser) {
                        if (objUser != null) {
                            saveDetailsCustomers(objUser, id);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent();
                                    mainIntent.setClass(SplashActivity.this, MainActivity.class);
                                    mainIntent.putExtra("fragmentFlag", false);
                                    SplashActivity.this.startActivity(mainIntent);
                                    SplashActivity.this.finish();
                                }
                            }, SPLASH_TIME);
                        }
                    }
                }
        );
    }


    private void passPage_cal(double id) {
        GetCustomerDetails(id);
    }

    private boolean CheckSharedPreference() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("DETAILS", MODE_WORLD_READABLE);
        phoneNumber = sharedPreferences.getString("phoneNumber", null);
        password = sharedPreferences.getString("password", null);
        if (phoneNumber != null && password != null) {
            Mint.setUserIdentifier(phoneNumber);
            return true;
        }
        return false;
    }

    public void login() {
        ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(null);
        if (ProviderDetailsObj.getInstance().getObjUser() != null)
            ProviderDetailsObj.getInstance().getObjUser().setiUserId(null);
        UserObj.setInstance(null);

        String jsonString = "{\"nvPhone\":\"" + phoneNumber + "\",\"nvVerCode\":\"" + password + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RegisterBL.loginUser(getBaseContext(), jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                if (id != 0) {
                    UserObj.getInstance().setiUserId(id);
                    ProviderDetailsObj.getInstance().setObjUser(UserObj.getInstance());
                    insertIntoUserRealm(id);
                    passPage_cal(id);
                } else {
                    deleteSharedPreference();
                    passPage(false);
                }
            }
        });
    }

    private void ifNoConnection() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.no_connection);
        builder.setMessage(R.string.no_connection_try_again);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                init();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }


    private void insertIntoUserRealm(Double userId) {
        Realm realm = Utils.getRealmInstance(this);
        realm.beginTransaction();
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        userRealm.setUserID(userId);
        realm.commitTransaction();
    }


    private void getInformation() {
        String jsonString = "{}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SplashBL.getFieldsAndCategories(this, jsonObject, new IExecutable<List<FieldAndCategory>>() {
            @Override
            public void onExecute(List<FieldAndCategory> fieldAndCategoryList) {
                Realm realm = null;
                if (fieldAndCategoryList != null) {
                    realm = Utils.getRealmInstance(SplashActivity.this);
                    RealmResults<FieldAndCategoryDM> results = realm.where(FieldAndCategoryDM.class).findAll();
                    realm.beginTransaction();
                    results.deleteAllFromRealm();
                    realm.commitTransaction();
                    for (FieldAndCategory fieldAndCategory : fieldAndCategoryList) {
                        realm.beginTransaction();
                        FieldAndCategoryDM fieldAndCategoryDM = realm.createObject(FieldAndCategoryDM.class);
                        fieldAndCategoryDM.setiFieldRowId(fieldAndCategory.getiFieldRowId());
                        fieldAndCategoryDM.setNvFieldName(fieldAndCategory.getNvFieldName());
                        fieldAndCategoryDM.setiCategoryRowId(fieldAndCategory.getiCategoryRowId());
                        fieldAndCategoryDM.setNvCategoryName(fieldAndCategory.getNvCategoryName());
                        realm.commitTransaction();
                    }
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.error_in_send), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getSysAlertsList() {
        String jsonString = "{}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SplashBL.GetSysAlertsList(this, jsonObject, new IExecutable<List<SysAlertsList>>() {
            @Override
            public void onExecute(List<SysAlertsList> sysAlertsListList) {
                Realm realm = null;
                if (sysAlertsListList != null) {
                    realm = Utils.getRealmInstance(SplashActivity.this);
                    RealmResults<SysAlertsListDM> results = realm.where(SysAlertsListDM.class).findAll();
                    realm.beginTransaction();
                    results.deleteAllFromRealm();
                    realm.commitTransaction();
                    for (SysAlertsList sysAlertsList : sysAlertsListList) {
                        realm.beginTransaction();
                        SysAlertsListDM sysAlertsListDM = realm.createObject(SysAlertsListDM.class);
                        sysAlertsListDM.setiTableId(sysAlertsList.getiTableId());
                        sysAlertsListDM.setiSysTableRowId(sysAlertsList.getiSysTableRowId());
                        sysAlertsListDM.setNvAletName(sysAlertsList.getNvAletName());
                        sysAlertsListDM.setNvSysTableNameEng(sysAlertsList.getNvSysTableNameEng());
                        realm.commitTransaction();

                    }

                } else {
                    Toast.makeText(getBaseContext(), getResources().getString(R.string.error_in_send), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteSharedPreference() {
        sharedPreferences = getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public void return_from_the_camera_() {
        ProviderDetailsObj.setInstance(new ProviderDetailsObj());
        UserObj.setInstance(new UserObj());

        Realm realm = Utils.getRealmInstance(this);
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm == null || userRealm.getNvFirstName() == null || userRealm.getNvFirstName().equals("")) {
            realm.beginTransaction();
            realm.where(UserRealm.class).findAll().deleteAllFromRealm();
            realm.createObject(UserRealm.class);
            realm.commitTransaction();
            Intent intent = new Intent();
            intent.putExtra("fragmentFlag2", 0);
            intent.setClass(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            UserObj.setInstance(new UserObj(userRealm));
            getProviderAllDetails(userRealm.getUserID());
        }
//        ConnectionUtils.return_from_camera = false;

    }
}
