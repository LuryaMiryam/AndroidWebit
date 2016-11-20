package webit.bthereapp.Screens.Customer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.MainClass;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.MyLocation;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Screens.Supplier.LanguagesToCustomerFragment;
import webit.bthereapp.Utils.Utils;

public class SearchFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView mLoginUserTv;
    private LinearLayout ll_main;
    private Button mNewUserBtn;
    private RelativeLayout search_button;
    private static int SPLASH_TIME = 1000;
    private Button mSearchBtn, sendCodeBtn;
    private int numTrying = 0;
    SharedPreferences sharedPreferences;
    CustomLightEditText mPhoneET, mVerCodeET;
    CustomLightButton sendPhoneBtn;
    Dialog dialog;
    private AutoCompleteTextView autoComplete;
    private ArrayAdapter<String> adp;
    String[] data;
    private ArrayList<String> listAutoC;
    private boolean showList = true;
    private CustomLightTextView language;
    private RelativeLayout search_line_kl;

    public SearchFragment() {
    }

    public static SearchFragment instance;

    public static SearchFragment getInstance() {
        if (instance == null)
            instance = new SearchFragment();
        return instance;
    }

    public static void setInstance(SearchFragment searchFragment) {
        instance = searchFragment;
    }


    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
            HideFragmentTop();
            visibleFragmentBottom();

        }
        //get the location
        MyLocation myLocation = new MyLocation();
        myLocation.getLocation(getActivity().getApplicationContext(), MyLocation.saveLocationResult);
//        RegisterUserFragment.removeInstance();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        view = inflater.inflate(R.layout.fragment_search, container, false);
        language = (CustomLightTextView) view.findViewById(R.id.language);
        language.setOnClickListener(this);
        mLoginUserTv = (TextView) view.findViewById(R.id.user_login_tv);
        mLoginUserTv.setOnClickListener(this);
        mNewUserBtn = (Button) view.findViewById(R.id.sign_up_btn);
        mNewUserBtn.setOnClickListener(this);
        mSearchBtn = (Button) view.findViewById(R.id.advanced_search_btn);
        mSearchBtn.setOnClickListener(this);
        search_button = (RelativeLayout) view.findViewById(R.id.search_button);
        search_button.setOnClickListener(this);
        autoComplete = (AutoCompleteTextView) view.findViewById(R.id.search_linek);
        search_line_kl = (RelativeLayout) view.findViewById(R.id.search_line_kl);
        autoComplete.setFilters(setInputFilter(120, getResources().getString(R.string.too_long_slogen)));

        listAutoC = new ArrayList<>();

        autoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 1)
                    initAutoComplete(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        //gets the event of picking a item from the autocomplete list
        autoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                showList = false;
            }
        });

        ll_main = (LinearLayout) view.findViewById(R.id.ll_main);
        if (getActivity() instanceof NavigetionLayout) {
            language.setVisibility(View.GONE);
            mLoginUserTv.setVisibility(View.GONE);
            mNewUserBtn.setVisibility(View.GONE);
            ll_main.setVisibility(View.GONE);
        } else
            language.setVisibility(View.VISIBLE);
        return view;
    }




    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
                            final Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
                            toast.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    toast.cancel();
                                }
                            }, 600);
                        }
                        return res;
                    }
                }
        };
        return inputFilter;
    }

    public void initAutoComplete(String str) {
        String jsonString = "{\"nvKeyWord\":\"" + str + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.SearchWordCompletion(getContext(), jsonObject, new IExecutable<ArrayList<String>>() {
            @Override
            public void onExecute(ArrayList<String> strings) {
                if (strings != null) {
                    if (adp == null) {
//                        //get strings for autocomplate
//                        adp = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, strings);
//                        autoComplete.setAdapter(adp);
                        search_line_kl.post(new Runnable() {
                            @Override
                            public void run() {
                                autoComplete.showDropDown();
                            }
                        });

                    }
//                    else  {
                    //refresh strings for autocomplate
                    else
                        adp = null;
                    data = strings.toArray(new String[strings.size()]);
                    adp = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
                    autoComplete.setAdapter(adp);
                    adp.notifyDataSetChanged();

//                        adp.clear();
//////                        for(String s:strings)
//////                                adp.add(s);
//                        adp.addAll(data);
//                       adp.notifyDataSetChanged();
//                        autoComplete.showDropDown();

                    //if a item from the list was picked not to show the list and remove the keyboard
                    if (!showList) {
                        autoComplete.dismissDropDown();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(autoComplete.getWindowToken(), 0);
                        showList = true;
                        // }
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.language: {
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).initFragmentMain(new LanguagesToCustomerFragment(), false, true);
                break;
            }
            case R.id.search_button: {
                InputMethodManager imm = (InputMethodManager) this.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                if (autoComplete.getText().toString().trim().length() > 0) {
                    SearchByKeyWord();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_word_for_search), Toast.LENGTH_SHORT).show();
                }

                break;
            }
            case R.id.user_login_tv: {
                //onclick on exists user
                numTrying = 0;
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setLayout(800, 600);
                dialog.setContentView(R.layout.exsisted_user_dialoge);
                mPhoneET = (CustomLightEditText) dialog.findViewById(R.id.phone_number);
                mPhoneET.setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));
                sendPhoneBtn = (CustomLightButton) dialog.findViewById(R.id.send_phone_number);
                sendPhoneBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ifInsertPhone()) {
                            CheckPhoneValidity();
                            dialog.dismiss();
                        }
                    }
                });
                dialog.show();
                break;
            }
            case R.id.advanced_search_btn:
                AdvancedSearchFragment.removeInstance();
                if (getActivity() instanceof MainActivity)
                    // AdvancedSearchFragment.getInstance() stop's when comes from another activity
                    initFragmentMain(new AdvancedSearchFragment(), false, true);
                else
                    ((NavigetionLayout) getActivity()).initFragmentMain(AdvancedSearchFragment.getInstance(), true);
                break;
            case R.id.sign_up_btn: {
                initFragmentMain(RegisterUserFragment.getInstance(), false, true);
                break;
            }
        }
    }

    private boolean ifInsertPhone() {
        if (mPhoneET.getText().toString().length() == 0) {
            mPhoneET.setError(getResources().getString(R.string.error_empty));
            return false;
        } else {
            mPhoneET.setError(null);
            return true;
        }
    }

    // check - is phone number exists ?
    public void CheckPhoneValidity() {
        String jsonString = "{\"nvPhone\":\"" + mPhoneET.getText().toString() + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getDetails(getActivity(), ConnectionUtils.CheckPhoneValidity, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double isValid) {
                if (isValid == 0) {
                    restoreVerificationCode();
                } else {
                    Toast.makeText(getContext(), R.string.phone_exists_error, Toast.LENGTH_LONG).show();

                }
            }
        }, false);
    }

    public void restoreVerificationCode() {
        String jsonString = "{\"nvPhone\":\"" + mPhoneET.getText().toString() + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getDetails(getActivity(), ConnectionUtils.RestoreVerCode, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double verCode) {
                if (verCode == 0) {
                    Toast.makeText(getContext(), R.string.details_not_exist, Toast.LENGTH_LONG).show();
                } else {
                    openVerDialog();
                }
            }
        }, true);
    }

    public void login() {
        String jsonString = "{\"nvPhone\":\"" + mPhoneET.getText().toString() + "\",\"nvVerCode\":\"" + mVerCodeET.getText().toString() + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getDetails(getContext(), ConnectionUtils.LoginUser, jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {
                Toast.makeText(getContext(), "loginUser:" + id, Toast.LENGTH_SHORT);
                if (id != 0) {
                    dialog.dismiss();
                    UserObj.getInstance().setiUserId(id);
                    saveInSharedPreference();
                    GetCustomerDetails(id);
                } else {
                    if (numTrying == 3) {
                        System.exit(0);
                    }
                    Toast.makeText(getContext(), R.string.verification_error, Toast.LENGTH_LONG).show();
                }
            }
        }, true);
    }

    public void saveDetailsCustomers(UserObj objUser) {
        objUser.setInstance(objUser);
        Realm realm = Utils.getRealmInstance(getActivity());
        realm.beginTransaction();

        if (objUser != null) {
            realm.where(UserRealm.class).findAll().deleteAllFromRealm();
            realm.where(UserRealm.class).findAll().deleteAllFromRealm();
            realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
            realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
            realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
            realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
            realm.copyToRealm(new UserRealm(objUser.getInstance()));
        }
        realm.commitTransaction();
    }

    private void getProviderAllDetails(final double id) {
        String jsonString = "{\"iUserId\":" + id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getProviderAllDetails(getContext(), jsonObject, new IExecutable<ProviderDetailsObj>() {
                    @Override
                    public void onExecute(ProviderDetailsObj providerDetails) {

//if the user is provider, we save the details in the realm
                        if (providerDetails != null && providerDetails.getObjProviderGeneralDetails() != null && providerDetails.getObjProviderAlertsSettings() != null && providerDetails.getObjProviderBuisnessDetails() != null && providerDetails.getObjProviderProfile() != null) {
                            ProviderDetailsObj.getInstance().setObjUser(providerDetails.getObjUser());
                            ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(providerDetails.getObjProviderBuisnessDetails());
                            ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(providerDetails.getObjProviderGeneralDetails());
                            ProviderDetailsObj.getInstance().setObjProviderAlertsSettings(providerDetails.getObjProviderAlertsSettings());
                            ProviderDetailsObj.getInstance().setObjCustomer(providerDetails.getObjCustomer());
                            ProviderDetailsObj.getInstance().setObjProviderProfile(providerDetails.getObjProviderProfile());
                            Realm realm = Utils.getRealmInstance(getContext());
                            realm.beginTransaction();

                            //save Obj Provider Buisness Details in the realm
                            realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails() != null) {
                                realm.copyToRealm(new ProviderRealm(ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails()));
                            }
                            //save Obj Provider General Details in the realm
                            realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails() != null) {
                                realm.copyToRealm(new GeneralDetailsRealm(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails()));
                            }
                            //save Obj Provider Alert Settings in the realm
                            if (ProviderDetailsObj.getInstance().getObjProviderAlertsSettings() != null) {
                                realm.where(AlertSettingsRealm.class);
                                realm.copyToRealm(new AlertSettingsRealm(ProviderDetailsObj.getInstance().getObjProviderAlertsSettings()));
                            }
                            //save Obj Provider Profile in the realm
                            realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
                            if (ProviderDetailsObj.getInstance().getObjProviderProfile() != null) {
                                realm.copyToRealm(new BusinessProfileRealm(ProviderDetailsObj.getInstance().getObjProviderProfile()));
                            }

                            realm.commitTransaction();
                            //to Exists Suplier
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ConnectionUtils.which_calendar = true;
                                    Intent mainIntent = new Intent();
                                    mainIntent.setClass(getActivity(), ExistsSuplierActivity.class);
                                    getActivity().startActivity(mainIntent);
                                    getActivity().finish();
                                }
                            }, SPLASH_TIME);

                        } else {
                            //to Exists Customer
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ConnectionUtils.which_calendar = false;
                                    Intent mainIntent = new Intent();
                                    mainIntent.setClass(getActivity(), NavigetionLayout.class);
                                    getActivity().startActivity(mainIntent);
                                    getActivity().finish();
                                }
                            }, SPLASH_TIME);
                        }
                    }
                }
        );
    }

    //get the details of the user
    private void GetCustomerDetails(final double id) {
        String jsonString = "{\"iUserId\":" + id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCustomerDetails(getContext(), jsonObject, new IExecutable<UserObj>() {
                    @Override
                    public void onExecute(UserObj objUser) {
                        if (objUser != null) {
                            saveDetailsCustomers(objUser);
                            getProviderAllDetails(id);
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent mainIntent = new Intent();
                                    mainIntent.setClass(((MainActivity) getActivity()), MainActivity.class);
                                    mainIntent.putExtra("fragmentFlag", false);
                                    getActivity().startActivity(mainIntent);
                                    getActivity().finish();
                                }
                            }, SPLASH_TIME);
                        }
                    }
                }
        );
    }

    //open dialog verification code
    private void openVerDialog() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(800, 600);
        dialog.setContentView(R.layout.send_code);
        mVerCodeET = (CustomLightEditText) dialog.findViewById(R.id.et_verification_mail);
        sendCodeBtn = (Button) dialog.findViewById(R.id.OK_send);
        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numTrying++;
                if (!mVerCodeET.getText().toString().equals(""))
                    login();
            }
        });
        dialog.show();
    }


    private void saveInSharedPreference() {
        sharedPreferences = getActivity().getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phoneNumber", mPhoneET.getText().toString());
        editor.putString("password", mVerCodeET.getText().toString());
        editor.commit();
    }
//0575454755

    private void SearchByKeyWord() {
        String nvLatitude = "", nvLongitude = "";
        if (ConnectionUtils.CurrentLatLng != null) {
            nvLatitude = String.valueOf(ConnectionUtils.CurrentLatLng.latitude);
            nvLongitude = String.valueOf(ConnectionUtils.CurrentLatLng.longitude);
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nvKeyWord", autoComplete.getText().toString());
            if (!nvLongitude.equals(""))
                jsonObject.put("nvlong", nvLongitude);
            if (!nvLatitude.equals(""))
                jsonObject.put("nvlat", nvLatitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.SearchByKeyWord(getContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
            @Override
            public void onExecute(ArrayList<SearchResulstsObj> searchResulstsObjs) {
                if (searchResulstsObjs != null) {
                    ConnectionUtils.resulstsObjs = searchResulstsObjs;
                    if (getActivity() instanceof MainActivity) {
                        Intent intent = new Intent(getActivity(), NavigetionLayout.class);
                        intent.putExtra("ShowDifferentView", 1);
                        //send the search word that will be displayed at the searchResult page
                        intent.putExtra("searchWord", autoComplete.getText().toString());
                        intent.putExtra("sentFrom", 3);
                        startActivity(intent);
                        getActivity().finish();
                    } else {  
                        if (getActivity() instanceof NavigetionLayout) {
                            ((NavigetionLayout) getActivity()).from = 4;
                            // enable to sort also by distance (this option isn't enabled when search by city)
                            ((NavigetionLayout) getActivity()).searchByCity = false;

                            ((NavigetionLayout) getActivity()).setSearchWord(autoComplete.getText().toString());
                            SearchResultFragment.removeInstance();
                            ((NavigetionLayout) getActivity()).initFragmentMain(SearchResultFragment.getInstance(), true);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_results_to_this_word), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}

