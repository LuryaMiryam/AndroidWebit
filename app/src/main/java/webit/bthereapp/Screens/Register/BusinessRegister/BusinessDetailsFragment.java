package webit.bthereapp.Screens.Register.BusinessRegister;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.RegisterBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomAutoCompleteTextView;
import webit.bthereapp.CustemViews.DetailsEt;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Utils.Utils;


public class BusinessDetailsFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int RC_SIGN_IN = 333;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET = 2;
    public static boolean is_back = false;
    private Dialog dialog;
    private static boolean b = true;
    private ImageButton close;
    public Button mOkBtn;
    private View view;
    private boolean isNoFirst = false;
    private CustomLightButton ok;
    private boolean required = true;
    private boolean validate = true;
    private ScrollView businessDetailsScreen;
    private Button mCont_to_gBtn;
    private CustomAutoCompleteTextView mAdress;
    private DetailsEt mCh_p, mFax, mMail, mName, mPhone, mSite;
    private String mAdressTxt, mCityTxt, mCh_pTxt, mFaxTxt = "", mMailTxt, mNameTxt, mPhoneTxt, mSiteTxt;
    public static boolean check = true;
    private GoogleApiClient mGoogleApiClient;

    public BusinessDetailsFragment() {
        // Required empty public constructor
    }

    public static BusinessDetailsFragment instance;

    public static BusinessDetailsFragment getInstance() {
        if (instance == null) {
            instance = new BusinessDetailsFragment();
            b = true;
        } else
            b = true;
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        check = false;
        return true;
    }


    public static BusinessDetailsFragment newInstance(String param1, String param2) {
        BusinessDetailsFragment fragment = new BusinessDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showDialogOpen() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);


        dialog.setContentView(R.layout.fragment_enter_user_dialog);
        close = (ImageButton) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectionUtils.which_calendar = false;
                Intent intent = new Intent(getActivity(), NavigetionLayout.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        mOkBtn = (Button) dialog.findViewById(R.id.welcome_ok);
        mOkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.light_gray_9);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
            HideFragmentBottom();
        }

        if (view == null) {
            Realm realm = Utils.getRealmInstance(getContext());
            ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();
            if (providerRealm == null)
                if (getActivity() instanceof MainActivity){
                    showDialogOpen();
                }

            isNoFirst = false;
            view = inflater.inflate(R.layout.fragment_business_details, container, false);
            mCont_to_gBtn = (Button) view.findViewById(R.id.bus_det_continue);
            mAdress = (CustomAutoCompleteTextView) view.findViewById(R.id.bus_det_adress);
            mCh_p = (DetailsEt) view.findViewById(R.id.bus_det_ch_p);
            mFax = (DetailsEt) view.findViewById(R.id.bus_det_fax);
            mMail = (DetailsEt) view.findViewById(R.id.bus_det_mail);
            mName = (DetailsEt) view.findViewById(R.id.bus_det_name);
            mPhone = (DetailsEt) view.findViewById(R.id.bus_det_phone);
            mSite = (DetailsEt) view.findViewById(R.id.bus_det_site);
            businessDetailsScreen = (ScrollView) view.findViewById(R.id.business_details_screen);
            businessDetailsScreen.setOnTouchListener(this);
            ok = (CustomLightButton) view.findViewById(R.id.ok);
            ok.setOnClickListener(this);

            mAdress.setOnClickListener(this);
            mCh_p.setOnClickListener(this);
            mFax.setOnClickListener(this);
            mMail.setOnClickListener(this);
            mName.setOnClickListener(this);
            mPhone.setOnClickListener(this);
            mSite.setOnClickListener(this);
            mMail.et_get_EditText().setTag("mail");

            mFax.et_get_EditText().setTag("fax");
            mName.et_get_EditText().setTag("business_name");
            mCh_p.et_get_EditText().setTag("ch_p");

            mPhone.et_get_EditText().setTag("phone");
            mSite.et_get_EditText().setTag("site");
            mAdress.setTag("address");

            mCh_p.et_get_EditText().setOnFocusChangeListener(this);
            mFax.et_get_EditText().setOnFocusChangeListener(this);
            mMail.et_get_EditText().setOnFocusChangeListener(this);
            mName.et_get_EditText().setOnFocusChangeListener(this);
            mPhone.et_get_EditText().setOnFocusChangeListener(this);
            mSite.et_get_EditText().setOnFocusChangeListener(this);

            mCont_to_gBtn.setOnClickListener(this);

            if (providerRealm != null) {
                mName.et_get_EditText().setText(providerRealm.getNvSupplierName());
                mCh_p.et_get_EditText().setText(providerRealm.getNvBusinessIdentity());
                mAdress.setTextAdress(providerRealm.getNvAddress());
                mAdress.setTextCity(providerRealm.getNvCity());
                mPhone.et_get_EditText().setText(providerRealm.getNvPhone());
                mFax.et_get_EditText().setText(providerRealm.getNvFax());
                mMail.et_get_EditText().setText(providerRealm.getNvEmail());
                mSite.et_get_EditText().setText(providerRealm.getNvSiteAddress());

                mAdressTxt = mAdress.getTextAdress();
                mCityTxt = mAdress.getTextCity();
                mCh_pTxt = mCh_p.et_get_EditText().getText().toString();
                mFaxTxt = mFax.et_get_EditText().getText().toString();
                mMailTxt = mMail.et_get_EditText().getText().toString();
                mNameTxt = mName.et_get_EditText().getText().toString();
                mPhoneTxt = mPhone.et_get_EditText().getText().toString();
                mSiteTxt = mSite.et_get_EditText().getText().toString();
            }
        } else {
            mFax.et_get_EditText().setFilters(new InputFilter[]{});
            mName.et_get_EditText().setFilters(new InputFilter[]{});
            mCh_p.et_get_EditText().setFilters(new InputFilter[]{});


            isNoFirst = true;


        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).change_background_to_supplier();

        if (getActivity() instanceof MainActivity) {

            if (is_back)
                super.initFragmentTop(MainActivity.Num, getString(R.string.business_the_datails_title), 1, true);
            else
                super.initFragmentTop(MainActivity.Num, getString(R.string.business_the_datails_title), 1, false);


        } else {

            super.initFragmentTop3(1, getString(R.string.business_the_datails_title), is_back);
            ok.setVisibility(View.VISIBLE);
            mCont_to_gBtn.setVisibility(View.GONE);
        }

        is_back = false;
        return view;
    }


    public boolean isB() {
        return b;
    }

    public void setB(boolean b) {
        this.b = b;
    }
    @Override
    public void onStart() {
        super.onStart();

        if (isNoFirst && mNameTxt != "") {

            mAdress.setTextAdress(mAdressTxt);
            mAdress.setTextCity(mCityTxt);
            mCh_p.et_get_EditText().setText(mCh_pTxt);
            mFax.et_get_EditText().setText(mFaxTxt);
            mMail.et_get_EditText().setText(mMailTxt);
            mName.et_get_EditText().setText(mNameTxt);
            mPhone.et_get_EditText().setText(mPhoneTxt);
            mSite.et_get_EditText().setText(mSiteTxt);

        }
        mName.et_get_EditText().setFilters(setInputFilter(30, getResources().getString(R.string.too_long_service_name)));
        mCh_p.et_get_EditText().setFilters(setInputFilter(9, getResources().getString(R.string.too_long_chet_pe)));
        mFax.et_get_EditText().setFilters(setInputFilter(9, getResources().getString(R.string.too_long_chet_pe)));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET) {
            if (resultCode == getActivity().RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this.getActivity(), data);
//                streetId = place.getId();
                mAdress.setTextAdress(place.getAddress().toString());
                mAdress.setError(null);
                mAdress.setTextCity(place.getAddress().toString());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this.getActivity(), data);
            } else if (resultCode == getActivity().RESULT_CANCELED) {
            }
        }
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.ok: {
                getActivity().onBackPressed();
                break;
            }

            case R.id.bus_det_continue:
                required = true;
                validate = true;
                mName.valid();
                mCh_p.valid();
                mAdress.valid();
                mPhone.valid();
                mMail.valid();
                mName.validateOnFocusChange();
                mSite.validateOnFocusChange();
                mFax.validateOnFocusChange();
                mCh_p.validateOnFocusChange();


                if (!mName.requiredField()) {
                    mName.et_get_EditText().requestFocus();
                    required = false;
                }
                if (!mCh_p.requiredField()) {
                    mCh_p.et_get_EditText().requestFocus();
                    required = false;
                }
                if (!mAdress.valid()) {
                    required = false;
                }
                if (!mPhone.requiredField()) {
                    mPhone.et_get_EditText().requestFocus();
                    required = false;
                }
                if (!mMail.requiredField()) {
                    mMail.et_get_EditText().requestFocus();
                    required = false;
                }
                if (!mName.is_validate()) {
                    mName.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!mPhone.is_validate()) {
                    mPhone.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!mMail.is_validate()) {
                    mMail.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!mSite.is_validate()) {
                    mSite.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!mCh_p.is_validate()) {
                    mCh_p.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!mFax.is_validate()) {
                    mCh_p.et_get_EditText().requestFocus();
                    validate = false;
                }
                if (!required) {
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                }
                else if (!validate) {
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
                }
                    //If the details are correct, the user is enrolled in
                else if (validate && required) {
                    mCont_to_gBtn.setEnabled(false);
                    if (!ConnectionUtils.if_server) {
                        initFragmentMain(BusinessGeneralData.getInstance(), true, true, 2);
                    } else {
                        Realm realm = Utils.getRealmInstance(getContext());
                        ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();//
                        realm.beginTransaction();
                        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
                        providerRealm = realm.createObject(ProviderRealm.class);
                        providerRealm.setiUserId(providerRealm.getiUserId());
                        providerRealm.setNvCity(mAdress.getTextCity());
                        providerRealm.setiCityType(1);
                        providerRealm.setNvAddress(mAdress.getTextAdress());
                        providerRealm.setNvBusinessIdentity(mCh_p.et_get_EditText().getText().toString());
                        providerRealm.setNvEmail(mMail.et_get_EditText().getText().toString());
                        String str = mPhone.et_get_EditText().getText().toString().replaceAll("\\D+", "");
                        providerRealm.setNvPhone(str);
                        providerRealm.setNvFax(mFax.et_get_EditText().getText().toString());
                        providerRealm.setNvSiteAddress(mSite.et_get_EditText().getText().toString());
                        providerRealm.setNvSupplierName(mName.et_get_EditText().getText().toString());
                        realm.commitTransaction();
                        mFax.et_get_EditText().setFilters(new InputFilter[]{});
                        mName.et_get_EditText().setFilters(new InputFilter[]{});
                        mCh_p.et_get_EditText().setFilters(new InputFilter[]{});

                        if (dialog != null)
                            dialog.dismiss();

                        mAdressTxt = mAdress.getTextAdress();
                        mCityTxt = mAdress.getTextCity();
                        mCh_pTxt = mCh_p.et_get_EditText().getText().toString();
                        mFaxTxt = mFax.et_get_EditText().getText().toString();
                        mMailTxt = mMail.et_get_EditText().getText().toString();
                        mNameTxt = mName.et_get_EditText().getText().toString();
                        mPhoneTxt = mPhone.et_get_EditText().getText().toString();
                        mSiteTxt = mSite.et_get_EditText().getText().toString();

                        initFragmentMain(BusinessGeneralData.getInstance(), true, true, 2);
                    }
                }
                break;
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {

            } else {
                // Signed out, show unauthenticated UI.
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        switch (v.getTag().toString()) {
            case "ch_p":
                mCh_p.validateOnFocusChange();
                break;
            case "business_name":
                mName.validateOnFocusChange();
                break;
            case "phone":
                if (mPhone.et_get_EditText().getText().length() != 0) {
                    mPhone.validateOnFocusChange();
                }
                break;
            case "fax":
                mFax.validateOnFocusChange();
                break;
            case "mail":
                if (mMail.et_get_EditText().getText().length() != 0) {
                    mMail.validateOnFocusChange();
                }
                break;
            case "site":
                mSite.validateOnFocusChange();
                break;
            case "address":
                mAdress.valid();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.business_details_screen:
                mName.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(businessDetailsScreen.getWindowToken(), 0);
                return false;
        }
        return false;

    }


    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null && check) { // Overflow
                            final Toast toast = Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
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

    @Override
    public void onResume() {
        super.onResume();
        mCont_to_gBtn.setEnabled(true);
        mCh_p.clearFocus();
    }

}
