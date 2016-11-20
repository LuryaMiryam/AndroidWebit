package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.GraphRequest;
//import com.facebook.GraphResponse;
//import com.facebook.login.LoginResult;
//import com.facebook.login.widget.LoginButton;
import com.facebook.CallbackManager;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.splunk.mint.Mint;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.BL.RegisterBL;
import webit.bthereapp.Camera.CallbackContext;
import webit.bthereapp.Camera.CameraLauncher;
import webit.bthereapp.Camera.CameraLauncherArguments;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Connection.MySingleton;
import webit.bthereapp.CustemViews.CustomDatePicker;
import webit.bthereapp.CustemViews.DetailsEt;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.MainClass;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.DownloadImageTask;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Utils.Utils;

//import com.google.android.gms.phenotype.Flag;

//import webit.bthereapp.Connection.GeneralLoader;

//import com.google.gson.Gson;
//import webit.bthereapp.Connection.GeneralLoader;

public class RegisterUser_To_Existed_Supplier_Fragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener, GoogleApiClient.OnConnectionFailedListener {

    // , GoogleApiClient.OnConnectionFailedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private boolean required = true;
    private int second = 0;
    private boolean validate = true;
    boolean isOpen = true;
    private LinearLayout cancel_ll, camera_ll, gallery_ll;
    private boolean firstContinue = true;
    private int RC_SIGN_IN = 333;
    private ScrollView registerUserScreen;
    private DetailsEt mE_mailEt, mE_phoneEt, mE_fnameEt, mE_lnameEt;
    private String mEmailTxt = "", mAdressTxt = "", mPhoneTxt = "", mfNameTxt = "", mlNameTxt = "";
    public Button mDateBtn, mOkBtn;
    View view = null;
    private boolean isNoFirst = false;
    private RelativeLayout mRegulationsTv;
    private CustomLightButton ok;
    public MainActivity activity_;
    private static TextView mE_dateTV;
    private RelativeLayout add_business;
    private Button mPic_bBtn, mLogInFaceBookB_, mGooglePlusRrgister_, sendVerificationBtn;
    //    private ImageButton mPic_cameraBtn;
    private CustomLightButton mContinueBtn;
    private ImageView mChoice1Iv, mChoice2Iv, mChoice3Iv;
    private int i1 = 0, i2 = 0, i3 = 1;
    private TextView send_second;
    private ImageButton mCloseIB;
    private LinearLayout born_date_ll;
    private static CameraLauncher cameraLauncher;
    private ImageView p_camera;
    private String mImage;
    private ImageView delete_image;
    private DatePicker datePicker;
    private Calendar calendar;
    private boolean bIsActivityResult;
    private EditText a, mVerificationET;
    String SimageByte = "";
    private int yes = 0, yes_g = 0;
    private Bitmap bitmapTemp;
    final int PIC_CROP = 1;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET = 2;

//    LoginButton mLogInFaceBookB;
//    CallbackManager callbackManager;

    SignInButton mGooglePlusRrgister;
    CallbackManager callbackManager;
    Button mOkBtnl;
    GoogleApiClient mGoogleApiClient;
    CustomDatePicker customDatePicker;
    String arr[] = new String[0];
    private ImageView img;
    String fullName;
    private RelativeLayout p_camera_ll;
    private static CameraLauncherArguments arguments;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int numTrying = 0;
    private static String date = "";
    //    private static Date date ;
    private CallbackContext callbackSignUpProfile;
    GoogleSignInOptions gso;
    SharedPreferences sharedPreferences;
    Dialog vDialod = null;
    boolean flag = true;
    private RelativeLayout mAddBusiness;
    Calendar cal;
    private ImageButton close;
    Date firstDate;
    private ImageView imgSrc;

    Camera.Size mSize;


    public RegisterUser_To_Existed_Supplier_Fragment() {
        // Required empty public constructor
    }

    public static RegisterUser_To_Existed_Supplier_Fragment instance;

    public static RegisterUser_To_Existed_Supplier_Fragment getInstance() {
        if (instance == null)
            instance = new RegisterUser_To_Existed_Supplier_Fragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {


        if (getActivity() instanceof ExistsSuplierActivity) {
            ((ExistsSuplierActivity) getActivity()).visibleFragmentMain();
            ((ExistsSuplierActivity) getActivity()).hideFragmentTop();
            ((ExistsSuplierActivity) getActivity()).hideContainerMain();
        }
        return true;
    }

    public static RegisterUser_To_Existed_Supplier_Fragment newInstance(String param1, String param2) {
        RegisterUser_To_Existed_Supplier_Fragment fragment = new RegisterUser_To_Existed_Supplier_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        if (getArguments() != null) {

        }

    }

    // Add this to the header of your file:

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getActivity() instanceof MainActivity) {
//            this.activity_ = ((MainActivity) getContext());
            HideFragmentBottom();
            HideFragmentTop();
            AddProviderDetails.getInstance().setObjProviderProfile(null);
            AddProviderDetails.getInstance().setObjProviderBuisnessDetails(null);
            AddProviderDetails.getInstance().setObjProviderGeneralDetails(null);
            AddProviderDetails.getInstance().setNvPhoneList(null);
            AddProviderDetails.getInstance().setObjProviderAlertsSettings(null);

            ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(null);
            ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(null);
            ProviderDetailsObj.getInstance().setObjUser(null);
            ProviderDetailsObj.getInstance().setObjProviderProfile(null);
            ProviderDetailsObj.getInstance().setObjProviderAlertsSettings(null);
            ProviderDetailsObj.getInstance().setObjCustomer(null);
        }
        cal = Calendar.getInstance();
        cal.set(1950, 0, 1);
        firstDate = cal.getTime();
        if (view == null) {
            isNoFirst = false;
            view = inflater.inflate(R.layout.fragment_register_user_to_existed_supplier, container, false);
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            registerUserScreen = (ScrollView) view.findViewById(R.id.register_user_screen);
            mAddBusiness = (RelativeLayout) view.findViewById(R.id.add_business);
            mAddBusiness.setOnClickListener(this);
            mRegulationsTv = (RelativeLayout) view.findViewById(R.id.p_regulations);
            mPic_bBtn = (Button) view.findViewById(R.id.p_pic);
            mChoice1Iv = (ImageView) view.findViewById(R.id.iv_1);
            mChoice2Iv = (ImageView) view.findViewById(R.id.iv_2);
            mChoice3Iv = (ImageView) view.findViewById(R.id.iv_3);
            mE_dateTV = (TextView) view.findViewById(R.id.born_date_tv);
            mE_mailEt = (DetailsEt) view.findViewById(R.id.email);
            mE_fnameEt = (DetailsEt) view.findViewById(R.id.f_name);
            mE_lnameEt = (DetailsEt) view.findViewById(R.id.l_name);
            mE_phoneEt = (DetailsEt) view.findViewById(R.id.phone);
            p_camera_ll = (RelativeLayout) view.findViewById(R.id.p_camera_ll);
            p_camera_ll.setOnClickListener(this);
            mContinueBtn = (CustomLightButton) view.findViewById(R.id.continue1);
//            customDatePicker=new CustomDatePicker(getActivity(),null);
            customDatePicker = (CustomDatePicker) view.findViewById(R.id.cDate);
            img = (ImageView) view.findViewById(R.id.imgSrc);
            mE_mailEt.setOnClickListener(this);
            mE_mailEt.setEditTextTag("mail");
            mE_fnameEt.setOnClickListener(this);
            mE_fnameEt.setEditTextTag("f_name");
            mE_lnameEt.setOnClickListener(this);
            mE_lnameEt.setEditTextTag("l_name");
            mE_phoneEt.setOnClickListener(this);
            mE_phoneEt.setEditTextTag("phone");
            registerUserScreen.setOnTouchListener(this);
            born_date_ll = (LinearLayout) view.findViewById(R.id.born_date_ll);
            born_date_ll.setOnClickListener(this);
            mE_mailEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_phoneEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_fnameEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_lnameEt.et_get_EditText().setOnFocusChangeListener(this);
            mChoice1Iv.setOnClickListener(this);
            mChoice2Iv.setOnClickListener(this);
            mChoice3Iv.setOnClickListener(this);
            mPic_bBtn.setOnClickListener(this);
            mRegulationsTv.setOnClickListener(this);
            mContinueBtn.setOnClickListener(this);
            p_camera = (ImageView) view.findViewById(R.id.p_camera);
            mE_dateTV.setOnClickListener(this);
            imgSrc = (ImageView) view.findViewById(R.id.imgSrc);
            imgSrc.setOnClickListener(this);
            imgSrc.setClickable(false);
            mE_mailEt.et_get_EditText().setImeOptions(EditorInfo.IME_ACTION_DONE);
            mE_mailEt.et_get_EditText().setOnEditorActionListener(new EditText.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    boolean handled = false;
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        handled = true;
                        mE_mailEt.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }
                    return handled;
                }
            });
            callbackSignUpProfile = new CallbackContext() {
                @Override
                public void success(Bitmap bitmap) {
                    super.success(bitmap);
                    img.setImageBitmap(bitmap);
//                    mPic_bBtn.setText(getResources().getString(R.string.see_));
                    p_camera_ll.setClickable(false);

                    p_camera.setVisibility(View.INVISIBLE);
                    mPic_bBtn.setVisibility(View.VISIBLE);
                    mPic_bBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openToSeeDialog();
                        }
                    });
                    imgSrc.setClickable(true);
                    delete_image.setVisibility(View.VISIBLE);
                    bitmapTemp = bitmap;
                    img.setTag("change");

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    byte[] imageByte = bytes.toByteArray();
                    SimageByte = Base64.encodeToString(imageByte, Base64.DEFAULT);
                }
            };

            delete_image = (ImageView) view.findViewById(R.id.delete);
            delete_image.setOnClickListener(this);


            mE_phoneEt.et_get_EditText().setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));
//            mAdress.setFilter(setInputFilter(30, getResources().getString(R.string.too_long_adress)));

        } else {
            mE_fnameEt.requestFocus();
            isNoFirst = true;


//            mfNameTxtError = mE_fnameEt.et_get_EditText().getError().toString();
//            mlNameTxtError = mE_lnameEt.et_get_EditText().getError().toString();
//            mEmailTxtError = mE_mailEt.et_get_EditText().getError().toString();
//            mPhoneTxtError = mE_phoneEt.et_get_EditText().getError().toString();
//            mAdressTxtError = mAdress.et_get_EditText().getError().toString();

            mfNameTxt = mE_fnameEt.et_get_EditText().getText().toString();
            mlNameTxt = mE_lnameEt.et_get_EditText().getText().toString();
            mEmailTxt = mE_mailEt.et_get_EditText().getText().toString();
            mPhoneTxt = mE_phoneEt.et_get_EditText().getText().toString();

            //setPayment(yes);
            setReadRegulations(i1);
        }
        if (!(getActivity() instanceof MainActivity)) {
//            ok.setVisibility(View.VISIBLE);
//            mContinueBtn.setVisibility(View.GONE);
            super.HideFragmentTop2();
        }
        date="";
        mE_dateTV.setText(getResources().getString(R.string.born_date));
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            mE_fnameEt.et_get_EditText().setText(userRealm.getNvFirstName());
            mE_lnameEt.et_get_EditText().setText(userRealm.getNvLastName());
            mE_phoneEt.et_get_EditText().setText(userRealm.getNvPhone());
            mE_mailEt.et_get_EditText().setText(userRealm.getNvMail());
            if (userRealm.getdBirthdate() != null) {
//                Date date_b = ConnectionUtils.convertJsonDate_(userRealm.getdBirthdate());
//                Date date_b = userRealm.getdBirthdate();
                Date date_b = ConnectionUtils.JsonDateToDate(userRealm.getdBirthdate());

//                String s1 = "", s2 = "";
//                if ((userRealm.getdBirthdate()).contains("/")) {
//                    s1 = (userRealm.getdBirthdate()).replace('/', '.');
//                    s1 = s1.replace("\\", "");
//                    mE_dateTV.setText(s1);
//                } else {
//                    s1 = (userRealm.getdBirthdate()).substring(0, 4);
//                    s2 = (userRealm.getdBirthdate()).substring(4, 8);
//                    mE_dateTV.setText(s2.substring(2, 4) + "." + s2.substring(0, 2) + "." + s1);
//                }
                SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yyyy");

                mE_dateTV.setText(mFormatter.format(date_b));
                mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
            }
            if (userRealm.getNvImageFilePath() != null&&!(userRealm.getNvImageFilePath().equals("")) ) {
                byte[] decodedString = Base64.decode(userRealm.getNvImageFilePath(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                img.setImageBitmap(decodedByte);
            }
//           if(userRealm.isbDataDownloadApproval())
            if (userRealm.isbAdvertisingApproval()) {
                mChoice2Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                i2 = 1;
            } else {
                mChoice2Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                i2 = 0;
            }
//           if(userRealm.isbAutomaticUpdateApproval())
            if (userRealm.isbTermOfUseApproval()) {
                mChoice1Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                i1 = 1;
            } else {
                mChoice1Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                i1 = 0;
            }
            if (userRealm.isbIsGoogleCalendarSync()) {
                mChoice3Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                i3 = 1;
            } else {
                mChoice3Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                i3 = 0;
            }

        }
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getActivity() instanceof MainActivity)
            activity_ = (MainActivity) activity;
    }


    @Override
    public void onStart() {
        super.onStart();

        if (isNoFirst) {

            mE_fnameEt.et_get_EditText().setError(null);
            mE_lnameEt.et_get_EditText().setError(null);
            mE_mailEt.et_get_EditText().setError(null);
            mE_phoneEt.et_get_EditText().setError(null);


            mE_fnameEt.et_get_EditText().setText(mfNameTxt);
            mE_lnameEt.et_get_EditText().setText(mlNameTxt);
            mE_mailEt.et_get_EditText().setText(mEmailTxt);
            mE_phoneEt.et_get_EditText().setText(mPhoneTxt);


            mE_phoneEt.et_get_EditText().setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        mGooglePlusRrgister_.setEnabled(true);

        firstContinue = true;
        mContinueBtn.setEnabled(true);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                if (acct.getDisplayName() != null) {
                    arr = (acct.getDisplayName()).split(" ", 2);
                    mE_fnameEt.et_get_EditText().requestFocus();
                    mE_fnameEt.et_set_text(arr[0]);
                    if (arr.length > 1)
                        mE_lnameEt.et_set_text(arr[1]);
                    else
                        mE_lnameEt.et_set_text("");
                }
                if (acct.getEmail() != null) {
                    mE_mailEt.et_get_EditText().requestFocus();
                    mE_mailEt.et_set_text(acct.getEmail());
                }
                if (acct.getPhotoUrl() != null) {
                    Toast.makeText(getContext(), acct.getPhotoUrl().toString(), Toast.LENGTH_LONG).show();

                    Uri personPhoto = acct.getPhotoUrl();
                    Bitmap bitmap = null;
                    try {
                        bitmap = new DownloadImageTask().execute(personPhoto.toString()).get();
                        img.setImageBitmap(bitmap);
                        img.setClickable(true);
                        delete_image.setVisibility(View.VISIBLE);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    SimageByte = ConnectionUtils.convertBitmapToBase64(bitmap);
                }

                Bundle args = new Bundle();
                args.putString("id", acct.getId());
            }
        } else {
            Toast.makeText(getActivity(),getString(R.string.there_is_error), Toast.LENGTH_LONG).show();
        }
    }

    public void removeYourFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (RegisterUser_To_Existed_Supplier_Fragment.this != null) {
            transaction.remove(RegisterUser_To_Existed_Supplier_Fragment.this);
            transaction.commit();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            getActivity().getSupportFragmentManager().popBackStack();
//            yourFragment = null;
        }
    }

    public void continueToUserRegister() {
        ConnectionUtils.which_calendar = false;
        Intent intent = new Intent(getActivity(), NavigetionLayout.class);
        startActivity(intent);

    }

    private void contiue_to_next() {
        vDialod.dismiss();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        RegisterUser_To_Existed_Supplier_Fragment.removeInstance();
        BusinessDetailsFragment.removeInstance();
        BusinessGeneralData.removeInstance();
        AlertsFragment.removeInstance();
        BusinessPaymentFragment.removeInstance();
        BusinessProfileFragment.removeInstance();
        ContactListFragment.removeInstance();

        continueToUserRegister();
    }

    private void contiue_to_next_s() {
        removeYourFragment();

        vDialod.dismiss();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        initFragmentMain(new BusinessDetailsFragment(), true, true, 1);
    }

    public void saveDetails(Double id) {

        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm user = realm.where(UserRealm.class).findFirst();
        Double userId = user.getUserID();
        realm.beginTransaction();
        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
        ProviderRealm providerRealm = new ProviderRealm();
        providerRealm.setiBuisnessId(id);
        providerRealm.setiUserId(userId);
        realm.copyToRealm(providerRealm);
        realm.commitTransaction();
    }

    private void supplierRegistration() {
//        if (firstSend) {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm user = realm.where(UserRealm.class).findFirst();
        Double userId = user.getUserID();
        String jsonString = "{\"iUserId\":" + userId + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getDetails(getContext(), ConnectionUtils.AddProviderUser, jsonObject, new IExecutable<Double>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(Double id) {
                        if (id != 0) {

                            MainClass.getInstance().setBussinessId(id);
                            saveDetails(id);
                        } else

                            Toast.makeText(getContext(), R.string.general_error_msg, Toast.LENGTH_LONG).show();
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                    }
                }
        );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.ok: {
//                getActivity().onBackPressed();
//                break;
//            }
            case R.id.delete: {
                imgSrc.setClickable(false);
                imgSrc.setImageBitmap(null);
                p_camera.setVisibility(View.VISIBLE);
//                p_camera.setClickable(true);
                mPic_bBtn.setVisibility(View.GONE);
                delete_image.setVisibility(View.GONE);
                p_camera_ll.setClickable(true);
                break;
            }
            case R.id.born_date_tv: {
                mE_mailEt.clearFocus();
                new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(firstDate)
                        .build()
                        .show();
                break;
            }
            case R.id.born_date_ll: {
                mE_mailEt.clearFocus();
                new SlideDateTimePicker.Builder(getActivity().getSupportFragmentManager())
                        .setListener(listener)
                        .setInitialDate(firstDate)
                        .build()
                        .show();
                break;
            }
            //Save image
            case R.id.p_pic: {
//                Toast.makeText(getActivity(), getResources().getString(R.string.no_prepared), Toast.LENGTH_SHORT).show();
                String s = mPic_bBtn.getText().toString();
                if (s.equals(getResources().getString(R.string.personal_picture)))
                    openCameraDialog();
                else
                    openToSeeDialog();
//                getProfileImg_g();
//                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                intent.setType("image/*");
//                startActivityForResult(Intent.createChooser(intent, "select_ File"), 222);

                break;
            }
            //Retrieving user information from FACEBOOK
            case R.id.login_button: {
                break;
            }
            //The user will be marked as read the Terms of Use
            case R.id.iv_1: {
                if (i1 == 0) {
                    mChoice1Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                    i1 = 1;
                } else {
                    mChoice1Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                    i1 = 0;
                }
//                mChoice1Iv.setImageResource(R.drawable.client_galaxy_icons1_22);

                break;
            }
            //Highlighted when the user agree to receive promotional information
            case R.id.iv_2: {
                if (i2 == 0) {
                    mChoice2Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                    i2 = 1;
                } else {
                    mChoice2Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                    i2 = 0;
                }
                break;
            }
            case R.id.iv_3: {
                if (i3 == 0) {
                    mChoice3Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
                    i3 = 1;
                } else {
                    mChoice3Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
                    i3 = 0;
                }
                break;
            }
            case R.id.imgSrc: {
//                openToSeeDialog();
                openCameraDialog();
                break;
            }
            case R.id.login_button_:

//                mLogInFaceBookB.performClick();
                break;


            //Registration confirmation and data integrity checking
            case R.id.continue1: {
//                mContinueBtn.setEnabled(false);
//                contiue_to_next();
//                check();
                if (!required && i1 == 0)
                    Toast.makeText(getContext(), R.string.errRegulations, Toast.LENGTH_LONG).show();
                else if (!required)
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                else if (!validate)
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
//If the details are correct, the user is enrolled in
//            if (required && validate) {
                else if (validate && required) {
                    mContinueBtn.setEnabled(false);
                    mAddBusiness.setEnabled(false);
                    numTrying = 0;
                    getActivity().onBackPressed();
                }

            }
        }
    }

    private boolean is_valid_name() {

        if (!mE_fnameEt.getEditTextText().contains(" "))
            return false;
        if (mE_fnameEt.getEditTextText().split(" ")[0] != null && mE_fnameEt.getEditTextText().split(" ")[0].length() < 2)
            return false;
        if (mE_fnameEt.getEditTextText().split(" ")[1] != null && mE_fnameEt.getEditTextText().split(" ")[1].length() < 2)
            return false;

        if (mE_lnameEt.getEditTextText() == null)
            return false;
        if (mE_lnameEt.getEditTextText().length() > 1 && String.valueOf(mE_lnameEt.getEditTextText().charAt(0)).equals(" "))
            return false;
        if (!mE_lnameEt.getEditTextText().contains(" "))
            return false;
        if (mE_lnameEt.getEditTextText().split(" ")[0] != null && mE_lnameEt.getEditTextText().split(" ")[0].length() < 2)
            return false;
        if (mE_lnameEt.getEditTextText().split(" ")[1] != null && mE_lnameEt.getEditTextText().split(" ")[1].length() < 2)
            return false;
        return true;
    }

    private void check() {
        required = true;
        validate = true;
        mE_mailEt.validateOnFocusChange();
        CheckMailValidity();
//                //Check to see if the user read the Terms of Use
////                if (i1 == 0)
////                    required = false;
        if (i1 == 0) {
            required = false;
        }
        mE_fnameEt.valid();
        mE_lnameEt.valid();
        mE_phoneEt.valid();

        mE_mailEt.valid();
        mE_phoneEt.validateOnFocusChange();
        mE_fnameEt.validateOnFocusChange();
        mE_lnameEt.validateOnFocusChange();
        if (!mE_fnameEt.requiredField()) {
            mE_fnameEt.et_get_EditText().requestFocus();
            required = false;
        }

        if (!mE_lnameEt.requiredField()) {
            mE_lnameEt.et_get_EditText().requestFocus();
            required = false;
        }
        if (mE_fnameEt.getEditTextText() == null) {
            validate = false;
            mE_fnameEt.et_get_EditText().requestFocus();

        }
        if (!mE_phoneEt.requiredField()) {
            mE_phoneEt.et_get_EditText().requestFocus();
            required = false;
        }

        if (!mE_mailEt.requiredField()) {
            mE_mailEt.et_get_EditText().requestFocus();
            required = false;
        }
//                if (!mE_nameEt.is_validate())
//                    validate = false;
        if (!mE_phoneEt.is_validate()) {
            mE_phoneEt.et_get_EditText().requestFocus();
            validate = false;
        }
        if (!mE_mailEt.is_validate()) {
            mE_mailEt.et_get_EditText().requestFocus();
            validate = false;
        }
        if (!mE_fnameEt.is_validate()) {
            mE_mailEt.et_get_EditText().requestFocus();
            validate = false;
        }
        if (!mE_lnameEt.is_validate()) {
            mE_mailEt.et_get_EditText().requestFocus();
            validate = false;
        }
        if (!mE_phoneEt.isServerValidate()) {
            mE_phoneEt.et_get_EditText().requestFocus();
            validate = false;
        }
        if (!mE_mailEt.isServerValidate()) {
            mE_mailEt.et_get_EditText().requestFocus();
            validate = false;
        }


    }

    public void userInitialization(double verificationId, int b) {
        UserObj.getInstance().setNvUserName(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvFirstName(mE_fnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvLastName(mE_lnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvMail(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvPhone(mE_phoneEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvPassword(String.valueOf(verificationId));
        UserObj.getInstance().setNvVerification(String.valueOf(verificationId));
        // TODO: 07/03/2016 change the next lines  according to user answers
        if (i2 == 1)
            UserObj.getInstance().setbAutomaticUpdateApproval(true);
        else
            UserObj.getInstance().setbAutomaticUpdateApproval(false);
        UserObj.getInstance().setbDataDownloadApproval(true);
        UserObj.getInstance().setbTermOfUseApproval(true);
        if (i1 == 1)
            UserObj.getInstance().setbAdvertisingApproval(true);
        else
            UserObj.getInstance().setbAdvertisingApproval(false);
        UserObj.getInstance().setiUserStatusType(24);
        UserObj.getInstance().setiUserType(1);
        if (i3 == 1)
            UserObj.getInstance().setbIsGoogleCalendarSync(true);
        else
            UserObj.getInstance().setbIsGoogleCalendarSync(false);
//        UserObj.getInstance().setbIsGoogleCalendarSync(true);
        UserObj.getInstance().setNvImage(SimageByte);
        UserObj.getInstance().setiCreatedByUserId(0);
        UserObj.getInstance().setiCreatedByUserId(1);
        UserObj.getInstance().setiLastModifyUserId(1);
        UserObj.getInstance().setiSysRowStatus(1);

        insertIntoUserRealm();
    }
    private void insertIntoUserRealm() {
        Realm realm = Utils.getRealmInstance(getContext());
        realm.beginTransaction();

        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
//        userRealm.setNvUserName(UserObj.getInstance().getNvUserName());
        userRealm.setNvFirstName(UserObj.getInstance().getNvFirstName());
        userRealm.setNvLastName(UserObj.getInstance().getNvLastName());
        userRealm.setdBirthdate(UserObj.getInstance().getdBirthdate());
        userRealm.setNvMail(UserObj.getInstance().getNvMail());
//        userRealm.setNvAdress(UserObj.getInstance().getNvAdress());
        userRealm.setNvPhone(UserObj.getInstance().getNvPhone());
        userRealm.setNvPassword(UserObj.getInstance().getNvPassword());
        userRealm.setNvVerification(UserObj.getInstance().getNvVerification());
//        userRealm.setbAutomaticUpdateApproval(UserObj.getInstance().isbAutomaticUpdateApproval());
//        userRealm.setbDataDownloadApproval(UserObj.getInstance().isbDataDownloadApproval());
//        userRealm.setbTermOfUseApproval(UserObj.getInstance().isbTermOfUseApproval());
//        userRealm.setbAdvertisingApproval(UserObj.getInstance().isbAdvertisingApproval());
//        userRealm.setiUserStatusType(UserObj.getInstance().getiUserStatusType());
        userRealm.setbIsGoogleCalendarSync(UserObj.getInstance().isbIsGoogleCalendarSync());
        userRealm.setNvImageFilePath(UserObj.getInstance().getNvImage());
//        userRealm.setiCreatedByUserId(UserObj.getInstance().getiCreatedByUserId());
//        userRealm.setiLastModifyUserId(UserObj.getInstance().getiLastModifyUserId());
//        userRealm.setiSysRowStatus(UserObj.getInstance().getiSysRowStatus());
        realm.commitTransaction();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGooglePlusRrgister_.setEnabled(true);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            try {
                if (cameraLauncher != null) {
                    cameraLauncher.onActivityResult(requestCode, resultCode, data);
                    bIsActivityResult = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);

        }
        //Marking the event that the user read the Terms of Use
        if (requestCode == 150) {
            mChoice1Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
            i1 = 0;
        }
    }
    public void saveImageFile(Bitmap bitmap) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight()), new RectF(0, 0, 50, 50), Matrix.ScaleToFit.CENTER);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);

    }


    public void setReadRegulations(int d) {
        if (d == 1) {
            mChoice1Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
            i1 = 1;
        } else {
            mChoice1Iv.setImageResource(R.drawable.client_galaxy_icons1_34);
            i1 = 0;
        }
    }


//    public void setPayment(int d) {
//        if (d == 1) {
//            mYesBtn.setImageResource(R.drawable.client_galaxy_icons1_39);
//            mNOBtn.setImageResource(R.drawable.client_galaxy_icons1_36);
//            yes = 1;
//        } else {
//            mYesBtn.setImageResource(R.drawable.client_galaxy_icons1_37);
//            mNOBtn.setImageResource(R.drawable.client_galaxy_icons1_38);
//            yes = 2;
//        }
//    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getTag().toString()) {
            case "f_name":
                if (mE_fnameEt.et_get_EditText().getText().length() != 0) {
                    fullName = mE_fnameEt.et_get_EditText().getText().toString();
//                    arr = fullName.split(" ", 2);
//                    arr[0]=fullName;
                    mE_fnameEt.validateOnFocusChange();
                }
                break;
            case "l_name":
                if (mE_lnameEt.et_get_EditText().getText().length() != 0) {
                    fullName = mE_lnameEt.et_get_EditText().getText().toString();
//                    arr = fullName.split(" ", 2);
//                    arr[0]=fullName;
                    mE_lnameEt.validateOnFocusChange();
                }
                break;
            case "phone":
                if (mE_phoneEt.et_get_EditText().getText().length() != 0) {
                    String s = mE_phoneEt.et_get_EditText().getText().toString();
                    s = s.replace("-", "");
                    mE_phoneEt.et_set_text(s);
                    mE_phoneEt.validateOnFocusChange();
                    CheckPhoneValidity();
                }
                break;
            case "mail":
                if (mE_mailEt.et_get_EditText().getText().length() != 0) {
                    mE_mailEt.validateOnFocusChange();
                    CheckMailValidity();
                }
                break;
        }
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.register_user_screen:
                mE_fnameEt.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(registerUserScreen.getWindowToken(), 0);
                return false;
        }
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    public void verificationCode_second() {
        String jsonString = "{\"nvPhoneNumber\":\"" + mE_phoneEt.et_get_EditText().getText() + "\"}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RegisterBL.verificationCode(getContext(), jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double verificationId) {
                if (verificationId != 0) {
                    //// TODO: 10/04/2016 open verification dialog, and if it's right registerUser  function will be
                    Toast.makeText(getContext(), "verificationCode" + verificationId, Toast.LENGTH_LONG).show();
//                    verificationDialog(verificationId);
                } else {
                    Toast.makeText(getContext(), getString(R.string.there_is_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SaveLog(String log) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ProjectName", "BThere");
            jsonObject.put("Json", log);
        } catch (Exception ex) {
        }
        MySingleton.getInstance(getActivity()).addToRequestQueue(new JsonObjectRequest(Request.Method.POST,
                "http://qa.webit-track.com/WebitLogs/LogService.svc/WriteLog",
                jsonObject, null, null));
    }

    private void verificationDialog(final double verificationId, final int a) {
        second = 0;
        vDialod = new Dialog(getContext());
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.fragment_verification_emai_dialog);
        vDialod.setCancelable(true);
        SaveLog("verificationDialog");
        mCloseIB = (ImageButton) vDialod.findViewById(R.id.close);
        mCloseIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContinueBtn.setEnabled(true);
                mAddBusiness.setEnabled(true);
                vDialod.dismiss();
            }
        });
        send_second = (TextView) vDialod.findViewById(R.id.send_second);
        send_second.setPaintFlags(send_second.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        send_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                second++;
                if (second == 1)
                    verificationCode_second();
                else {
                    vDialod.dismiss();
                    mE_phoneEt.setEditTextText("");
                    mE_phoneEt.requestFocus();
                    mContinueBtn.setEnabled(true);
                    mAddBusiness.setEnabled(true);
//                    mContinueBtn.setEnabled(true);
                }
            }
        });
        mVerificationET = (EditText) vDialod.findViewById(R.id.et_verification_mail);
        sendVerificationBtn = (Button) vDialod.findViewById(R.id.OK_send);
        sendVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                numTrying++;
                SaveLog("user  = " + mE_fnameEt.getEditTextText());
                SaveLog("numTrying  = " + numTrying);
                if (!mVerificationET.getText().toString().equals("")) {
                    if (Double.parseDouble(mVerificationET.getText().toString()) == (int) verificationId) {
                        SaveLog("verificationId  = " + verificationId);
                        SaveLog("mVerificationET.getText()  = " + mVerificationET.getText().toString());
                        sendVerificationBtn.setEnabled(false);
                        userInitialization(verificationId, a);
                    } else {
                        if (numTrying == 3) {
                            System.exit(0);
                        }
                        if (numTrying == 1) {
                            SaveLog("numTrying == 1");
                            Toast.makeText(getContext(), R.string.verification_error_1, Toast.LENGTH_LONG).show();
                        } else {
                            SaveLog("numTrying == 2");
                            Toast.makeText(getContext(), R.string.verification_error, Toast.LENGTH_LONG).show();
                        }
                        mVerificationET.setText("");
                    }
                } else {
                    if (numTrying == 3) {
                        System.exit(0);
                    }
                    Toast.makeText(getContext(), R.string.verification_empty, Toast.LENGTH_LONG).show();
                    mVerificationET.setText("");
                }
            }
        });
        vDialod.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                mContinueBtn.setEnabled(true);
                mAddBusiness.setEnabled(true);
                sendVerificationBtn.setEnabled(true);
            }
        });
        vDialod.show();
    }

    public void userRegistration(final int a) {
//        // TODO: 4/13/2016
        String jsonString = "{ \"objUser\":" + new Gson().toJson(UserObj.getInstance()) + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RegisterBL.RegisterUser(getContext(), jsonObject, new IExecutable<Double>() {
            @Override
            public void onExecute(Double id) {

//                if(id==0)
//                    id= Double.valueOf(1);
                if (id != 0) {
                    AddProviderDetails.getInstance().setiUserId(id);
                    UserObj.getInstance().setiUserId(id);
                    insertIntoUserRealm(id);
                    saveInSharedPreference();
                    if (a == 1)
                        contiue_to_next();
                    else
                        contiue_to_next_s();
                } else {
                    Toast.makeText(getContext(), R.string.error_msg, Toast.LENGTH_SHORT).show();
                    mContinueBtn.setEnabled(true);
                    mAddBusiness.setEnabled(true);
                    sendVerificationBtn.setEnabled(true);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    vDialod.dismiss();
                }
            }
        });
    }

    public void CheckPhoneValidity() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            if (!(mE_phoneEt.et_get_EditText().getText().toString().equals(userRealm.getNvPhone()))) {
                String jsonString = "{\"nvPhone\":\"" + mE_phoneEt.et_get_EditText().getText() + "\"}";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RegisterBL.CheckPhoneValidity(getActivity(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double isValid) {
                        if (isValid == 1) {
                            mE_phoneEt.setServerValidate(true);
                            mE_phoneEt.setErrors(false);
                        } else {
                            mE_phoneEt.setServerValidate(false);
                            mE_phoneEt.setServerError(getResources().getString(R.string.phone_error));
                        }
                    }
                });
            }
        }
    }
    public void CheckMailValidity() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            if (!(mE_mailEt.et_get_EditText().getText().toString().equals(userRealm.getNvMail()))) {
//        String jsonString = new Gson().toJson(new objUser());
                String jsonString = "{\"nvEmail\":\"" + mE_mailEt.et_get_EditText().getText() + "\"}";
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(jsonString);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                RegisterBL.checkMailValidity(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double isValid) {
                        if (isValid == 1) {
                            mE_mailEt.setServerValidate(true);
                            mE_mailEt.setErrors(false);
                        } else {
                            mE_mailEt.setServerValidate(false);
                            mE_mailEt.setServerError(getResources().getString(R.string.mail_error));
                        }
                    }
                });
            }
        }
    }

    private void saveInSharedPreference() {
        sharedPreferences = getActivity().getSharedPreferences("DETAILS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("phoneNumber", UserObj.getInstance().getNvPhone());
        editor.putString("password", UserObj.getInstance().getNvPassword());
        editor.commit();
        Mint.setUserIdentifier(UserObj.getInstance().getNvPhone());
    }

    private void insertIntoUserRealm(Double userId) {
//        Realm realm = Realm.getInstance(getContext());
        Realm realm = Utils.getRealmInstance(getContext());
        realm.beginTransaction();
//        realm.copyToRealm(new ProviderDetailsObjRealm());


        realm.where(UserRealm.class).findAll().deleteAllFromRealm();
        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
        realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();

        UserRealm userRealm = new UserRealm();
        userRealm.setUserID(userId);
        userRealm.setNvUserName(UserObj.getInstance().getNvUserName());
        userRealm.setNvFirstName(UserObj.getInstance().getNvFirstName());
        userRealm.setNvLastName(UserObj.getInstance().getNvLastName());
        userRealm.setdBirthdate(UserObj.getInstance().getdBirthdate());
        userRealm.setNvMail(UserObj.getInstance().getNvMail());
        userRealm.setNvAdress(UserObj.getInstance().getNvAdress());
//        userRealm.setiCityType(UserObj.getInstance().getiCityType());
        userRealm.setNvPhone(UserObj.getInstance().getNvPhone());
        userRealm.setNvPassword(UserObj.getInstance().getNvPassword());
        userRealm.setNvVerification(UserObj.getInstance().getNvVerification());
        userRealm.setbAutomaticUpdateApproval(UserObj.getInstance().isbAutomaticUpdateApproval());
        userRealm.setbDataDownloadApproval(UserObj.getInstance().isbDataDownloadApproval());
        userRealm.setbTermOfUseApproval(UserObj.getInstance().isbTermOfUseApproval());
        userRealm.setbAdvertisingApproval(UserObj.getInstance().isbAdvertisingApproval());
        userRealm.setiUserStatusType(UserObj.getInstance().getiUserStatusType());
        userRealm.setbIsGoogleCalendarSync(UserObj.getInstance().isbIsGoogleCalendarSync());
        userRealm.setNvImageFilePath(UserObj.getInstance().getNvImage());
        userRealm.setiCreatedByUserId(UserObj.getInstance().getiCreatedByUserId());
        userRealm.setiLastModifyUserId(UserObj.getInstance().getiLastModifyUserId());
        userRealm.setiSysRowStatus(UserObj.getInstance().getiSysRowStatus());
//        userRealm_=userRealm;
//        realm.where(ProviderDetailsObjRealm.class).findFirst().setiUserId(UserObj.getInstance().getiUserId());
//        realm.where(ProviderDetailsObjRealm.class).findFirst().setiUserId(UserObj.getInstance().getiUserId());
        realm.copyToRealm(userRealm);
        realm.commitTransaction();
    }

    private void enterWithGooglePlus() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
// Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void getProfileImg_c() {

        cameraLauncher = new CameraLauncher(this);

        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.CAMERA);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());

    }

    private void getProfileImg_g() {


        cameraLauncher = new CameraLauncher(this);

//        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.CAMERA);
//        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());

        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.GALLERY);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        private SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yyyy");
        private SimpleDateFormat mFormatter_2 = new SimpleDateFormat("yyyyMMdd");

        @Override
        public void onDateTimeSet(Date date) {
            mE_dateTV.setText(mFormatter.format(date));
//            UserObj.getInstance().setdBirthdate((java.sql.Date) date);
            SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
            UserObj.getInstance().setdBirthdate(ConnectionUtils.convertStringToDate(format3.format(date)));
            mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
        }
    };

    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
//                            if (ConnectionUtils.if_try)
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

    private boolean see_the_image(ImageView image) {
        // Find the last picture
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        final Cursor cursor = getContext().getContentResolver()
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

// Put it in the image view
        if (cursor.moveToFirst()) {
            String imageLocation = cursor.getString(1);
            File imageFile = new File(imageLocation);
            if (imageFile.exists()) {   // TODO: is there a better way to do this?
                Bitmap bm = BitmapFactory.decodeFile(imageLocation);
                image.setImageBitmap(bm);
                return true;
            } else
                Toast.makeText(getContext(), getString(R.string.the_image_didnt_load), Toast.LENGTH_LONG).show();
        }
        return false;
    }

    private void openCameraDialog() {
        vDialod = new Dialog(getContext());
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.fragment_open_camera_dialog);
        vDialod.setCancelable(true);
        vDialod.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        vDialod.getWindow().setLayout(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);

        cancel_ll = (LinearLayout) vDialod.findViewById(R.id.cancel_ll);
        camera_ll = (LinearLayout) vDialod.findViewById(R.id.camera_ll);
        cancel_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
            }
        });
        gallery_ll = (LinearLayout) vDialod.findViewById(R.id.gallery_ll);
        gallery_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
                getProfileImg_g();
            }
        });
        camera_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
                getProfileImg_c();
            }
        });
        vDialod.show();
    }

    private void openToSeeDialog() {
        vDialod = new Dialog(getContext());
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.big_image);
        vDialod.setCancelable(true);
        vDialod.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView imageView = (ImageView) vDialod.findViewById(R.id.big_image);
        if (see_the_image(imageView))
            vDialod.show();
        else
            Toast.makeText(getContext(), getString(R.string.the_image_didnt_load), Toast.LENGTH_LONG).show();
    }
}
