package webit.bthereapp.Screens.Register.UserRegister;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.format.Formatter;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.firstparty.shared.FACLConfig;
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
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.RegisterBL;
import webit.bthereapp.Camera.CallbackContext;
import webit.bthereapp.Camera.CameraLauncher;
import webit.bthereapp.Camera.CameraLauncherArguments;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomDatePicker;
import webit.bthereapp.CustemViews.DetailsEt;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.CalendarPropertiesRealm;
import webit.bthereapp.DM.FieldAndCategory;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.CalendarProperties;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.Entities.CouponTypeObj;
import webit.bthereapp.Entities.CustomerAlertsSettingsObj;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.Error;
import webit.bthereapp.Entities.GetFreeDaysForServiceProvider;
import webit.bthereapp.Entities.HelpWorkingHours;
import webit.bthereapp.Entities.MainClass;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.Provider;
import webit.bthereapp.Entities.ProviderAlertsSettingsObj;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderHourObj;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.ServiceProviders;
import webit.bthereapp.Entities.SysAlertsList;
import webit.bthereapp.Entities.SysAlertsListItem;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WaitingListObj;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.General.DateTimePicker.DatePicker;
import webit.bthereapp.General.DownloadImageTask;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderServiceFragment;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;


public class RegisterUserFragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener, GoogleApiClient.OnConnectionFailedListener {

    private boolean required = true;
    private int second = 0;
    private boolean validate = true;
    public static boolean is_back = false;
    private LinearLayout cancel_ll, camera_ll, gallery_ll;
    private int RC_SIGN_IN = 333;
    private ScrollView registerUserScreen;
    private DetailsEt mE_mailEt, mE_phoneEt, mE_fnameEt, mE_lnameEt;
    private String mEmailTxt = "", mPhoneTxt = "", mfNameTxt = "", mlNameTxt = "";
    View view = null;
    private boolean isNoFirst = false;
    private RelativeLayout mRegulationsTv;
    private CustomLightButton ok;
    public MainActivity activity_;
    private static TextView mE_dateTV;
    private Button mContinueBtn, mLogInFaceBookB_, mGooglePlusRrgister_, sendVerificationBtn;
    private ImageView mChoice1Iv, mChoice2Iv, mChoice3Iv;
    private int i1 = 0, i2 = 0, i3 = 1;
    private TextView send_second, codeDialogTitle;
    private ImageButton mCloseIB;
    private LinearLayout born_date_ll;
    private static CameraLauncher cameraLauncher;
//    private static CameraLauncher cameraLauncher;
    private RelativeLayout delete_image;
    private DatePicker datePicker;
    private EditText mVerificationET;
    String SimageByte = "";
    private Bitmap bitmapTemp, bitmap_;
    LoginButton mLogInFaceBookB;
    CallbackManager callbackManager;
    SignInButton mGooglePlusRrgister;
    GoogleApiClient mGoogleApiClient;
    CustomDatePicker customDatePicker;
    String arr[] = new String[0];
    private ImageView img;
    String fullName;
    private RelativeLayout p_camera_ll;
    private static CameraLauncherArguments arguments;
    // TODO: Rename and change types of parameters
    private int numTrying = 0;
    private CallbackContext callbackSignUpProfile;
    SharedPreferences sharedPreferences;
    Dialog vDialod = null;
    private RelativeLayout mAddBusiness;
    Calendar cal;
    private static final long KILOBYTE = 1024;
    Date firstDate;
    private Calendar c = Calendar.getInstance();
    long fb_id = -1;


    public RegisterUserFragment() {
        // Required empty public constructor
    }

    public static RegisterUserFragment instance;

    public static RegisterUserFragment getInstance() {
        if (instance == null)
            instance = new RegisterUserFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        if (getActivity() instanceof ExistsSuplierActivity) {
            ((ExistsSuplierActivity) getActivity()).visibleFragmentMain();
            ((ExistsSuplierActivity) getActivity()).hideFragmentTop();
            ((ExistsSuplierActivity) getActivity()).hideContainerMain();
        }
        return true;
    }

    public static RegisterUserFragment newInstance(String param1, String param2) {
        RegisterUserFragment fragment = new RegisterUserFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            if (getActivity() instanceof MainActivity) {
                this.activity_ = ((MainActivity) getContext());
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
            if (getActivity() instanceof NavigetionLayout)
                HideFragmentBottom_n();
            cal = Calendar.getInstance();
            cal.set(1950, 0, 1);
            firstDate = cal.getTime();
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_register_user, container, false);
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                isNoFirst = false;
                registerUserScreen = (ScrollView) view.findViewById(R.id.register_user_screen);
                mAddBusiness = (RelativeLayout) view.findViewById(R.id.add_business);
                mAddBusiness.setOnClickListener(this);
                mRegulationsTv = (RelativeLayout) view.findViewById(R.id.p_regulations);
                mChoice1Iv = (ImageView) view.findViewById(R.id.iv_1);
                mChoice2Iv = (ImageView) view.findViewById(R.id.iv_2);
                mChoice3Iv = (ImageView) view.findViewById(R.id.iv_3);
                mE_dateTV = (TextView) view.findViewById(R.id.born_date_tv);
                mE_mailEt = (DetailsEt) view.findViewById(R.id.email);
                ok = (CustomLightButton) view.findViewById(R.id.ok);
                ok.setOnClickListener(this);
                mE_fnameEt = (DetailsEt) view.findViewById(R.id.f_name);
                mE_lnameEt = (DetailsEt) view.findViewById(R.id.l_name);
                mE_phoneEt = (DetailsEt) view.findViewById(R.id.phone);
                p_camera_ll = (RelativeLayout) view.findViewById(R.id.p_camera_ll);
                p_camera_ll.setOnClickListener(this);

                mContinueBtn = (Button) view.findViewById(R.id.continue1);
                customDatePicker = (CustomDatePicker) view.findViewById(R.id.cDate);
                img = (ImageView) view.findViewById(R.id.imgSrc);
                img.setOnClickListener(this);
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

                mRegulationsTv.setOnClickListener(this);
                mContinueBtn.setOnClickListener(this);
                mE_dateTV.setOnClickListener(this);
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

                //after take photo
                callbackSignUpProfile = new CallbackContext() {
                    @Override
                    public void success(Bitmap bitmap) {
                        super.success(bitmap);
                        after_camera(bitmap);
                    }
                };

                mGooglePlusRrgister_ = (Button) view.findViewById(R.id.sign_in_button_);
                mGooglePlusRrgister_.setOnClickListener(this);
                mGooglePlusRrgister = (SignInButton) view.findViewById(R.id.sign_in_button);
                mGooglePlusRrgister.setOnClickListener(this);

                delete_image = (RelativeLayout) view.findViewById(R.id.delete);
                delete_image.setOnClickListener(this);
                enterWithGooglePlus();

                mLogInFaceBookB_ = (Button) view.findViewById(R.id.login_button_);
                mLogInFaceBookB_.setOnClickListener(this);

                callbackManager = CallbackManager.Factory.create();

                mLogInFaceBookB = (LoginButton) view.findViewById(R.id.login_button);
                mLogInFaceBookB.setOnClickListener(this);

                mLogInFaceBookB.setReadPermissions("email", "public_profile");
                mLogInFaceBookB.setFragment(this);

                mLogInFaceBookB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {
                        setFacebookData(loginResult);
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {

                    }
                });

                mE_phoneEt.et_get_EditText().setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));

            } else {
                mE_fnameEt.requestFocus();
                isNoFirst = true;
                mfNameTxt = mE_fnameEt.et_get_EditText().getText().toString();
                mlNameTxt = mE_lnameEt.et_get_EditText().getText().toString();
                mEmailTxt = mE_mailEt.et_get_EditText().getText().toString();
                mPhoneTxt = mE_phoneEt.et_get_EditText().getText().toString();
                setReadRegulations(i1);
            }
            if (!(getActivity() instanceof MainActivity)) {
                if (!(getActivity() instanceof NavigetionLayout)) {
                    ok.setVisibility(View.VISIBLE);
                    mContinueBtn.setVisibility(View.GONE);
                    super.HideFragmentTop2();
                }
            }

            is_back = false;

            BusinessDetailsFragment.removeInstance();
            BusinessGeneralData.removeInstance();
            AlertsFragment.removeInstance();
            BusinessPaymentFragment.removeInstance();
            BusinessProfileFragment.removeInstance();
            ContactListFragment.removeInstance();
        return view;
    }

    public void after_camera(Bitmap bitmap) {
        bitmap_ = bitmap;
        img.setImageBitmap(bitmap_);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openToSeeDialog();
            }
        });
        img.setClickable(true);
        delete_image.setVisibility(View.VISIBLE);
        bitmapTemp = bitmap;
        img.setTag("change");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
        byte[] imageByte = bytes.toByteArray();
        SimageByte = Base64.encodeToString(imageByte, Base64.DEFAULT);
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
        Log.d("onStart_f","register");

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
        Log.d("memmm","onresume");
        mGooglePlusRrgister_.setEnabled(true);
        mContinueBtn.setEnabled(true);
    }

    private void setFacebookData(final LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            String email = response.getJSONObject().getString("email");
                            mE_mailEt.requestFocus();
                            mE_mailEt.et_set_text(email);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String firstName = response.getJSONObject().getString("first_name");
                            mE_fnameEt.requestFocus();
                            mE_fnameEt.et_set_text(firstName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String lastName = response.getJSONObject().getString("last_name");
                            mE_lnameEt.requestFocus();
                            mE_lnameEt.et_set_text(lastName);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String bday = response.getJSONObject().getString("birthday");
                            mE_dateTV.requestFocus();
                            mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
                            mE_dateTV.setText(bday);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            fb_id = object.getLong("id");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (Profile.getCurrentProfile() != null) {
                            Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            Bitmap bitmap = null;
                            try {
                                bitmap = new DownloadImageTask().execute(Profile.getCurrentProfile().getProfilePictureUri(200, 200).toString()).get();
                                after_camera(bitmap);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
        //log out from facebook, so that next time when want to sign in would not show a message
        logoutFromFacebook();

    }

    private void logoutFromFacebook() {
        try {
            if (AccessToken.getCurrentAccessToken() == null) {
                return; // already logged out
            }
            //fb_id -not sure is needed,because sometimes stays -1  and it still logs out
            GraphRequest graphRequest = new GraphRequest(AccessToken.getCurrentAccessToken(), "/ " + fb_id + "/permissions/", null,
                    HttpMethod.DELETE, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse graphResponse) {
                    LoginManager.getInstance().logOut();
                }
            });

            graphRequest.executeAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
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
                    Uri personPhoto = acct.getPhotoUrl();
                    Bitmap bitmap = null;
                    try {
                        bitmap = new DownloadImageTask().execute(personPhoto.toString()).get();
                        after_camera(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    SimageByte = ConnectionUtils.convertBitmapToBase64(bitmap);
                }

                Bundle args = new Bundle();
                args.putString("id", acct.getId());
            } else {
                // Signed out, show unauthenticated UI.
            }
        } else {
            Toast.makeText(getActivity(), getResources().getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
        }
    }

    public void removeYourFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (RegisterUserFragment.this != null) {
            transaction.remove(RegisterUserFragment.this);
            transaction.commit();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    public void continueToUserRegister() {
        if (getActivity() instanceof MainActivity) {
            if (((MainActivity) getActivity()).getFrom() == 1) {
                OrderServiceFragment.getInstance().enter_to_existed_customer();
                getActivity().finish();
            } else {
                ConnectionUtils.which_calendar = false;
                Intent intent = new Intent(getActivity(), NavigetionLayout.class);
                startActivity(intent);
            }

        } else if (getActivity() instanceof NavigetionLayout) {
            ((NavigetionLayout) getActivity()).enter_to_existed_customer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("onstop_f","register");
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
    }


    private void contiue_to_next() {
        vDialod.dismiss();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        RegisterUserFragment.removeInstance();
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
        if (getActivity() instanceof MainActivity)
            initFragmentMain(new BusinessDetailsFragment(), true, true, 1);
        if (getActivity() instanceof NavigetionLayout) {
            ConnectionUtils.which_calendar = false;
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: {
                mGoogleApiClient.stopAutoManage(getActivity());
                mGoogleApiClient.disconnect();
                getActivity().onBackPressed();
                break;
            }
            case R.id.delete: {
                img.setImageBitmap(null);
                SimageByte = "";
                delete_image.setVisibility(View.GONE);
                break;
            }

            case R.id.born_date_tv: {
                mE_mailEt.clearFocus();
                openDialogDate();
                break;
            }
            case R.id.born_date_ll: {
                mE_mailEt.clearFocus();
                openDialogDate();
                break;
            }

            //Profile photo photo
            case R.id.p_camera_ll: {
//                File path = Environment.getDataDirectory();
//                StatFs stat = new StatFs(path.getPath());
//                long blockSize = stat.getBlockSize();
//                long availableBlocks = stat.getAvailableBlocks();
//                long freeMemory = availableBlocks * blockSize;
//                String freeMemory = Formatter.formatFileSize(getContext(), availableBlocks * blockSize);
//                Log.d("memory", blockSize + " " + (blockSize / 10) + " " + freeMemory + " " + availableBlocks);
//                if ((blockSize / 10) < freeMemory)
//                if (checkIfThereIsFreeMemory())
                openCameraDialog();
//                else
//                    Toast.makeText(getContext(), R.string.no_enough_space, Toast.LENGTH_LONG).show();
                break;
            }
            //Link regulations
            case R.id.p_regulations: {
                mE_phoneEt.et_get_EditText().setFilters(new InputFilter[]{});
                if (getActivity() instanceof MainActivity)
                    super.initFragmentMain(new RegulationsFragment(), true, true);
                else
                    ((NavigetionLayout) getContext()).initFragmentMain(new RegulationsFragment(), false);
                break;
            }
            case R.id.add_business: {
                checkNew();
                if (!required && i1 == 0)
                    Toast.makeText(getContext(), R.string.errRegulations, Toast.LENGTH_LONG).show();
                else if (!required)
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                else if (!validate)
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
                    //If the details are correct, the user is enrolled in
                else if (validate && required) {
                    CheckPhoneValidityNew(2);
                }
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
                openCameraDialog();
                break;
            }
            case R.id.login_button_:

                mLogInFaceBookB.performClick();
                break;
            case R.id.sign_in_button:

                break;
            case R.id.sign_in_button_:
                mGooglePlusRrgister_.setEnabled(false);
                mGooglePlusRrgister.performClick();
                if (!mGoogleApiClient.isConnecting()) {
                    enterWithGooglePlus();
                }
                if (mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.clearDefaultAccountAndReconnect();
                    signIn();
                }
                break;


            //Registration confirmation and data integrity checking
            case R.id.continue1: {
                checkNew();
                if (!required && i1 == 0)
                    Toast.makeText(getContext(), R.string.errRegulations, Toast.LENGTH_LONG).show();
                else if (!required)
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                else if (!validate)
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
//if the details are correct, the user is enrolled in
                else if (validate && required) {
                    CheckPhoneValidityNew(1);
                }
            }
        }
    }

    public boolean checkIfThereIsFreeMemory() {

//        Here is how you get internal storage sizes:
//        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
//        Here is how you get external storage sizes (SD card size):
//        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());

        StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());//אחסון פנימי

        long blockSize = statFs.getBlockSize();
        long totalSize = statFs.getBlockCount() * blockSize;
        long availableSize = statFs.getAvailableBlocks() * blockSize;
        long freeSize = statFs.getFreeBlocks() * blockSize;

        ActivityManager actManager = (ActivityManager) getContext().getSystemService(getContext().ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem/ 1048576L;
        long totalMemory2 = memInfo.availMem/ 1048576L;


        final Runtime runtime = Runtime.getRuntime();
        final long usedMemInMB=(runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
        final long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;






        StatFs internalStatFs = new StatFs( Environment.getRootDirectory().getAbsolutePath() );
        long internalTotal;
        long internalFree;

        StatFs externalStatFs = new StatFs( Environment.getExternalStorageDirectory().getAbsolutePath() );
        long externalTotal;
        long externalFree;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            internalTotal = ( internalStatFs.getBlockCountLong() * internalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            internalFree = ( internalStatFs.getAvailableBlocksLong() * internalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            externalTotal = ( externalStatFs.getBlockCountLong() * externalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
            externalFree = ( externalStatFs.getAvailableBlocksLong() * externalStatFs.getBlockSizeLong() ) / ( KILOBYTE * KILOBYTE );
        }
        else {
            internalTotal = ( (long) internalStatFs.getBlockCount() * (long) internalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            internalFree = ( (long) internalStatFs.getAvailableBlocks() * (long) internalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            externalTotal = ( (long) externalStatFs.getBlockCount() * (long) externalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
            externalFree = ( (long) externalStatFs.getAvailableBlocks() * (long) externalStatFs.getBlockSize() ) / ( KILOBYTE * KILOBYTE );
        }

        long total = internalTotal + externalTotal;
        long free = internalFree + externalFree;
        long used = total - free;


        if ((getTotalInternalMemorySizeInt() / 8.5) < getAvailableInternalMemorySizeInt())
            return true;
        return false;
    }
    private MemorySize getMemorySize() {
        final Pattern PATTERN = Pattern.compile("([a-zA-Z]+):\\s*(\\d+)");

        MemorySize result = new MemorySize();
        String line;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            while ((line = reader.readLine()) != null) {
                Matcher m = PATTERN.matcher(line);
                if (m.find()) {
                    String name = m.group(1);
                    String size = m.group(2);

                    if (name.equalsIgnoreCase("MemTotal")) {
                        result.total = Long.parseLong(size);
                    } else if (name.equalsIgnoreCase("MemFree") || name.equalsIgnoreCase("Buffers") ||
                            name.equalsIgnoreCase("Cached") || name.equalsIgnoreCase("SwapFree")) {
                        result.free += Long.parseLong(size);
                    }
                }
            }
            reader.close();

            result.total *= 1024;
            result.free *= 1024;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class MemorySize {
        public long total = 0;
        public long free = 0;
    }

    public String getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return formatSize(availableBlocks * blockSize);
    }

    public String getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        Log.d("memory31", blockSize + " ");
        Log.d("memory3", totalBlocks + " ");
        ActivityManager actManager = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long totalMemory = memInfo.totalMem;
        Log.d("memory111", bytesToHuman(totalMemory) + " ");
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        totalMemory  = (statFs.getBlockCount() * statFs.getBlockSize());
        Log.d("memory112", bytesToHuman(totalMemory) + " ");
        double percentAvail = memInfo.availMem / memInfo.totalMem;
        Log.d("memory113", percentAvail + " ");
        Log.d("memory4", getTotalRAM() + " ");

        Log.d("memory100",   bytesToHuman(TotalMemory()) + " ");
        Log.d("memory100",  bytesToHuman(FreeMemory()) + " ");
        Log.d("memory100", bytesToHuman(BusyMemory()) + " ");

        return formatSize(totalBlocks * blockSize);
    }
    public long TotalMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ( (long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        return Total;
    }

    public long FreeMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Free   = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return Free;
    }

    public long BusyMemory()
    {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long   Total  = ((long) statFs.getBlockCount() * (long) statFs.getBlockSize());
        long   Free   = (statFs.getAvailableBlocks()   * (long) statFs.getBlockSize());
        long   Busy   = Total - Free;
        return Busy;
    }

    public static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }


    public static String bytesToHuman (long size)
    {
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " Kb";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " Mb";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " Gb";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " Tb";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " Pb";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " Eb";

        return "???";
    }
    public String getTotalRAM() {

        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;

            double mb = totRam / 1024.0;
            double gb = totRam / 1048576.0;
            double tb = totRam / 1073741824.0;

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }



        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }

        return lastValue;
    }
    public static long getAvailableInternalMemorySizeInt() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static long getTotalInternalMemorySizeInt() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }


    public static String formatSize(long size) {
        String suffix = null;

        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }

        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }

        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }


    private void openDialogDate() {
        if (getActivity() != null) {
            vDialod = new Dialog(getActivity());
            vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
            vDialod.setCanceledOnTouchOutside(false);
            vDialod.setContentView(R.layout.date_picker_dialog);
            datePicker = (DatePicker) vDialod.findViewById(R.id.datePicker);
            final Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, 1950);
            calendar.set(Calendar.MONTH, 0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            datePicker.setCalendar(calendar);
            Button btnOk = (Button) vDialod.findViewById(R.id.ok);
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = Calendar.getInstance();
                    c.set(Calendar.YEAR, datePicker.getYear());
                    c.set(Calendar.MONTH, datePicker.getMonth());
                    c.set(Calendar.DAY_OF_MONTH, datePicker.getDay());
                    if (!c.after(Calendar.getInstance())) {
                        SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
                        UserObj.getInstance().setdBirthdate(ConnectionUtils.convertStringToDate(format3.format(c.getTime())));
                        mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
                        mE_dateTV.setText(datePicker.getDay() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        vDialod.dismiss();
                    } else {
                        Toast.makeText(getActivity(), getString(R.string.enter_valid_date), Toast.LENGTH_LONG).show();
                    }
                }
            });
            Button btnBack = (Button) vDialod.findViewById(R.id.back);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    vDialod.dismiss();
                }
            });
            vDialod.show();
        }
    }

    // check validation for all the fields (except server validation)
    private void checkNew() {
        required = true;
        validate = true;
        mE_mailEt.validateOnFocusChange();
        mE_phoneEt.validateOnFocusChange();
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
    }

    public void userInitialization(double verificationId, int b) {
        UserObj.getInstance().setNvUserName(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvFirstName(mE_fnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvLastName(mE_lnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvMail(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvPhone(mE_phoneEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvPassword(String.valueOf(verificationId));

        UserObj.getInstance().setNvVerification(String.valueOf(verificationId));
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

        userRegistration(b);
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);

        }

        if (requestCode == 150) {
            mChoice1Iv.setImageResource(R.drawable.supplier_galaxy_icons_x1_30);
            i1 = 0;
        }
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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getTag().toString()) {
            case "f_name":
                if (mE_fnameEt.et_get_EditText().getText().length() != 0) {
                    fullName = mE_fnameEt.et_get_EditText().getText().toString();
                    mE_fnameEt.validateOnFocusChange();
                }
                break;
            case "l_name":
                if (mE_lnameEt.et_get_EditText().getText().length() != 0) {
                    fullName = mE_lnameEt.et_get_EditText().getText().toString();
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


    //return verification Code first time
    public void verificationCode(final int a) {
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
//                    Toast.makeText(getContext(), "verificationCode " + verificationId, Toast.LENGTH_LONG).show();
                    verificationDialog(verificationId, a);
                } else /*if (ConnectionUtils.if_try)*/ {
                    Toast.makeText(getContext(), getResources().getString(R.string.there_is_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //return verification Code second time
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
                    Toast.makeText(getContext(), getResources().getString(R.string.there_is_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //open dialog verification
    private void verificationDialog(final double verificationId, final int a) {
        second = 0;
        vDialod = new Dialog(getContext());
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.fragment_verification_emai_dialog);
        codeDialogTitle = (TextView) vDialod.findViewById(R.id.title_message);
        sendVerificationBtn = (Button) vDialod.findViewById(R.id.OK_send);
        if (a == 1) {
            codeDialogTitle.setTextColor(getResources().getColor(R.color.pink));
            sendVerificationBtn.setBackgroundColor(getResources().getColor(R.color.pink));
        }
        vDialod.setCancelable(true);
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
                }
            }
        });
        mVerificationET = (EditText) vDialod.findViewById(R.id.et_verification_mail);

        sendVerificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                numTrying++;
                if (!mVerificationET.getText().toString().equals("")) {
                    if (Double.parseDouble(mVerificationET.getText().toString()) == (int) verificationId) {
                        sendVerificationBtn.setEnabled(false);
                        userInitialization(verificationId, a);
                    } else {
                        if (numTrying == 3) {
                            System.exit(0);
                        }
                        if (numTrying == 1) {
                            Toast.makeText(getContext(), R.string.verification_error_1, Toast.LENGTH_LONG).show();
                        } else {
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

    //send to the server to user Registration
    public void userRegistration(final int a) {
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

    //check if the phone exists in the server (for leaving the focus)
    public void CheckPhoneValidity() {
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
                    //mE_phoneEt.setServerValidate(true);
                    if (getActivity() instanceof MainActivity)
                        mE_phoneEt.setErrors(false);
                } else {
                    // mE_phoneEt.setServerValidate(false);
                    if (getActivity() instanceof MainActivity)
                        mE_phoneEt.setServerError(getResources().getString(R.string.phone_error));
                }
            }
        });
    }

    //validate server for phone for checking at the end
    public void CheckPhoneValidityNew(final int kindOfRegister) {
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
                    //phone correct,so check mail server
                    CheckMailValidityNew(kindOfRegister);
                } else {
                    mE_phoneEt.setServerError(getResources().getString(R.string.phone_error));
                }
            }
        });
    }

    //check if the mail exists in the server (for leaving the focus)
    public void CheckMailValidity() {
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

                    // mE_mailEt.setServerValidate(true);
                    mE_mailEt.setErrors(true);

                } else {
                    if (isAdded()) {
                        mE_mailEt.setServerError(getResources().getString(R.string.mail_error));
                    }
                }
            }
        });
    }

    //validate server for mail for checking at the end after everything else is correct
    public void CheckMailValidityNew(final int kindOfRegister) {
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
                    Log.d("rrr", "all correct");
                    // when all the validations are correct
                    mContinueBtn.setEnabled(false);
                    mAddBusiness.setEnabled(false);
                    numTrying = 0;
                    mGoogleApiClient.stopAutoManage(getActivity());
                    mGoogleApiClient.disconnect();
//                    if (ConnectionUtils.if_server) ??
                    verificationCode(kindOfRegister);

//                    mE_mailEt.setServerValidate(true);
//                    mE_mailEt.setErrors();
                } else {
                    if (isAdded()) {
                        mE_mailEt.setServerError(getResources().getString(R.string.mail_error));
                    }
                }
            }
        });
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
        Realm realm = Utils.getRealmInstance(getContext());
        realm.beginTransaction();
        ProviderProfileObj.removeInstance();

        realm.where(UserRealm.class).findAll().deleteAllFromRealm();
        realm.where(ProviderRealm.class).findAll().deleteAllFromRealm();
        realm.where(GeneralDetailsRealm.class).findAll().deleteAllFromRealm();
        realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
        realm.where(SyncContactsRealm.class).findAll().deleteAllFromRealm();

        UserObj.getInstance().setiUserId(userId);
        realm.copyToRealm(new UserRealm(UserObj.getInstance()));
        realm.commitTransaction();
    }

    private void enterWithGooglePlus() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to GoogleSignIn.API and the options above.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), this)
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    //get Profile Image frof the camera
    private void getProfileImg_c() {
        cameraLauncher = new CameraLauncher(this);
        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.CAMERA);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

    //get Profile Image frof the gallery
    private void getProfileImg_g() {
        cameraLauncher = new CameraLauncher(this);
        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.GALLERY);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

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


    //open dialog to take photo
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
        //get profile image from the gallery
        gallery_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
                getProfileImg_g();
            }
        });
        //get profile image from the camera
        camera_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
                if (checkIfThereIsFreeMemory()) {
                    Log.d("mem","enough");
                    getProfileImg_c();
                }
                else {
                    Log.d("mem","no_enough");
//                    Toast.makeText(getContext(), R.string.no_enough_space, Toast.LENGTH_LONG).show();
                    getProfileImg_c();
                }
            }
        });
        vDialod.show();
    }

    //open dialog to show the image
    private void openToSeeDialog() {
        vDialod = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.big_image);
        vDialod.setCancelable(true);
        vDialod.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView imageView = (ImageView) vDialod.findViewById(R.id.big_image);

        if (bitmap_ != null) {
            imageView.setImageBitmap(bitmap_);
            vDialod.show();
        } else
            Toast.makeText(getContext(), getResources().getString(R.string.the_image_didnt_load), Toast.LENGTH_LONG).show();
    }

    //check validate all the fields,not used
    private void check() {
        required = true;
        validate = true;
        mE_mailEt.validateOnFocusChange();
        mE_phoneEt.validateOnFocusChange();
        CheckMailValidity();
        CheckPhoneValidity();
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


    public void continueWhenValidateForUser() {
        mContinueBtn.setEnabled(false);
        mAddBusiness.setEnabled(false);
        numTrying = 0;
        mGoogleApiClient.stopAutoManage(getActivity());
        mGoogleApiClient.disconnect();
        if (ConnectionUtils.if_server) {
            verificationCode(1);
        } else
            contiue_to_next();
    }

}
