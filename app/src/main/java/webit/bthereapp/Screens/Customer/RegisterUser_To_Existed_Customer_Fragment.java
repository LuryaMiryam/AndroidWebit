package webit.bthereapp.Screens.Customer;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
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
import webit.bthereapp.CustemViews.CustomSpinner;
import webit.bthereapp.CustemViews.DetailsEt;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.YesOrNo_White;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.General.DateTimePicker.DatePicker;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Utils.Utils;


public class RegisterUser_To_Existed_Customer_Fragment extends BaseFragment implements View.OnClickListener, View.OnFocusChangeListener, View.OnTouchListener, GoogleApiClient.OnConnectionFailedListener {

    private boolean required = true;
    private int second = 0;
    private boolean validate = true;
    private Date birth_date, marriage_date;
    private LinearLayout cancel_ll, camera_ll, gallery_ll;
    private ScrollView registerUserScreen;
    private Bitmap decodedByte;
    private DetailsEt mE_mailEt, mE_phoneEt, mE_fnameEt, mE_lnameEt;
    View view = null;
    private static int f_date = 0;
    private String phone_p = "";
    private static TextView mE_dateTV, mE_date_marriageTV;
    private Button sendVerificationBtn;
    private ImageButton mPic_cameraBtn;
    private CustomSpinner view_calendar;
    private TextView send_second;
    private ImageButton mCloseIB;
    private LinearLayout born_date_ll, born_date_marriage_ll;
    private static CameraLauncher cameraLauncher;
    private boolean bIsActivityResult;
    private EditText mVerificationET;
    String SimageByte = "";
    private YesOrNo_White al_yn2;
    private Bitmap bitmapTemp;
    private CustomBoldTextView codeDialogTitle;
    private RelativeLayout image_full, image_empty;
    CustomDatePicker customDatePicker;
    String arr[] = new String[0];
    String fullName;
    private static CameraLauncherArguments arguments;
    private DatePicker datePicker;
    private int numTrying = 0;
    private CallbackContext callbackSignUpProfile;
    private Calendar c = Calendar.getInstance();
    Dialog vDialod = null;
    Calendar cal;
    Date firstDate;
    private ImageView imgSrc;
    private ImageView img;
    private CustomLightButton save;
    private CircleImageView profile_image;

    public RegisterUser_To_Existed_Customer_Fragment() {
        // Required empty public constructor
    }

    public static RegisterUser_To_Existed_Customer_Fragment instance;

    public static RegisterUser_To_Existed_Customer_Fragment getInstance() {
        if (instance == null)
            instance = new RegisterUser_To_Existed_Customer_Fragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {

        return true;
    }

    public static RegisterUser_To_Existed_Customer_Fragment newInstance(String param1, String param2) {
        RegisterUser_To_Existed_Customer_Fragment fragment = new RegisterUser_To_Existed_Customer_Fragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cal = Calendar.getInstance();
        cal.set(1950, 0, 1);
        firstDate = cal.getTime();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_register_user_to_existed_customer, container, false);
            getActivity().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            registerUserScreen = (ScrollView) view.findViewById(R.id.register_user_screen);
            mPic_cameraBtn = (ImageButton) view.findViewById(R.id.p_camera);
            mE_dateTV = (TextView) view.findViewById(R.id.born_date_tv);
            mE_date_marriageTV = (TextView) view.findViewById(R.id.marriage_date);
            mE_mailEt = (DetailsEt) view.findViewById(R.id.email);
            mE_fnameEt = (DetailsEt) view.findViewById(R.id.f_name);
            mE_lnameEt = (DetailsEt) view.findViewById(R.id.l_name);
            mE_phoneEt = (DetailsEt) view.findViewById(R.id.phone);
            al_yn2 = (YesOrNo_White) view.findViewById(R.id.al_yn2);
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
            born_date_marriage_ll = (LinearLayout) view.findViewById(R.id.date_of_marriage_ll);
            born_date_marriage_ll.setOnClickListener(this);
            mE_mailEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_phoneEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_fnameEt.et_get_EditText().setOnFocusChangeListener(this);
            mE_lnameEt.et_get_EditText().setOnFocusChangeListener(this);
            mPic_cameraBtn.setOnClickListener(this);
            mE_dateTV.setOnClickListener(this);
            mE_date_marriageTV.setOnClickListener(this);
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
                    profile_image.setImageBitmap(bitmap);
                    decodedByte = bitmap;
                    image_empty.setVisibility(View.INVISIBLE);
                    image_full.setVisibility(View.VISIBLE);
                    imgSrc.setClickable(true);
                    bitmapTemp = bitmap;
                    img.setTag("change");
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    byte[] imageByte = bytes.toByteArray();
                    SimageByte = Base64.encodeToString(imageByte, Base64.DEFAULT);
                }
            };


            mE_phoneEt.et_get_EditText().setFilters(setInputFilter(10, getResources().getString(R.string.too_long_phone)));

        } else {
            mE_fnameEt.requestFocus();
        }
        image_full = (RelativeLayout) view.findViewById(R.id.image_full);
        image_empty = (RelativeLayout) view.findViewById(R.id.image_empty);
        profile_image = (CircleImageView) view.findViewById(R.id.profile_image);
        save = (CustomLightButton) view.findViewById(R.id.save);
        save.setOnClickListener(this);
        view_calendar = (CustomSpinner) view.findViewById(R.id.view_calendar);

        mE_dateTV.setText(getResources().getString(R.string.born_date));
        mE_date_marriageTV.setText(getResources().getString(R.string.date_of_marriage));
        image_empty.setVisibility(View.VISIBLE);
        image_full.setVisibility(View.INVISIBLE);

        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            mE_fnameEt.et_get_EditText().setText(userRealm.getNvFirstName());
            mE_lnameEt.et_get_EditText().setText(userRealm.getNvLastName());
            phone_p = userRealm.getNvPhone();
            mE_phoneEt.et_get_EditText().setText(userRealm.getNvPhone());
            mE_mailEt.et_get_EditText().setText(userRealm.getNvMail());
            if (userRealm.getdBirthdate() != null) {
                Date date_b = ConnectionUtils.convertJsonDate_(userRealm.getdBirthdate());
                SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yyyy");
                mE_dateTV.setText(mFormatter.format(date_b));
                birth_date = date_b;
                mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
            }
            if (userRealm.getdMarriageDate() != null) {
                Date date_b = ConnectionUtils.convertJsonDate_(userRealm.getdMarriageDate());
                SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yyyy");
                mE_date_marriageTV.setText(mFormatter.format(date_b));
                mE_date_marriageTV.setTextColor(getResources().getColor(R.color.color1));
            }
            view_calendar.set_old(userRealm.getiCalendarViewType());

            if (userRealm.getNvImageFilePath() != null && !(userRealm.getNvImageFilePath().equals(""))) {
                byte[] decodedString = Base64.decode(userRealm.getNvImageFilePath(), Base64.DEFAULT);
                decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                image_full.setVisibility(View.VISIBLE);
                image_empty.setVisibility(View.INVISIBLE);
                img.setImageBitmap(decodedByte);
                profile_image.setImageBitmap(decodedByte);
                SimageByte=userRealm.getNvImageFilePath();
            }
            if (userRealm.isbIsGoogleCalendarSync()) {
                al_yn2.change_status(true);
            } else {
                al_yn2.change_status(false);
            }
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.born_date_tv: {
                mE_mailEt.clearFocus();
                openDialogDate(1);
                break;
            }
            case R.id.born_date_ll: {
                mE_mailEt.clearFocus();
                openDialogDate(1);
                break;
            }
            case R.id.marriage_date: {
                openDialogDate(2);
                break;
            }
            case R.id.date_of_marriage_ll: {
                openDialogDate(2);
                break;
            }
            case R.id.close: {
                getActivity().onBackPressed();

                break;
            }
            case R.id.save: {
                save.setEnabled(false);
                //check all the validation(except the server)
                check();
                if (!required)
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                else if (!validate)
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
           //if the details are correct, so check the server validation
                else if (validate && required) {
                    Log.d("eee","regular validation are correct");
                       CheckPhoneValidity();
                }
                save.setEnabled(true);
                break;
            }
            //The user will be marked as read the Terms of Use
            case R.id.imgSrc: {
                openCameraDialog();
                break;
            }
            case R.id.p_camera: {
                openCameraDialog();
                break;
            }

            //Registration confirmation and data integrity checking
            case R.id.continue1: {
                save.setEnabled(false);
                check();
                if (!required)
                    Toast.makeText(getContext(), R.string.empty_fields, Toast.LENGTH_LONG).show();
                else if (!validate)
                    Toast.makeText(getContext(), R.string.error_register, Toast.LENGTH_LONG).show();
                else if (validate && required) {
                    numTrying = 0;
                    verificationCode();
                }
            }
        }
    }

    private void check() {
        required = true;
        validate = true;
        mE_mailEt.validateOnFocusChange();
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
//        if (!mE_phoneEt.isServerValidate()) {
//            mE_phoneEt.et_get_EditText().requestFocus();
//            validate = false;
//        }
//        if (!mE_mailEt.isServerValidate()) {
//            mE_mailEt.et_get_EditText().requestFocus();
//            validate = false;
//        }
    }

    public void verificationCode() {
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
                    verificationDialog(verificationId);
                } else {
                    Toast.makeText(getContext(),getString(R.string.there_is_error), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //open dialog verification
    private void verificationDialog(final double verificationId) {
        second = 0;
        vDialod = new Dialog(getContext());
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setCanceledOnTouchOutside(false);
        vDialod.setContentView(R.layout.fragment_verification_emai_dialog);
        codeDialogTitle = (CustomBoldTextView) vDialod.findViewById(R.id.title_message);
        sendVerificationBtn = (Button) vDialod.findViewById(R.id.OK_send);
//        if (a == 1) {
        codeDialogTitle.setTextColor(getResources().getColor(R.color.pink));
        sendVerificationBtn.setBackgroundColor(getResources().getColor(R.color.pink));
//        }
        vDialod.setCancelable(true);
        SaveLog("verificationDialog");
        mCloseIB = (ImageButton) vDialod.findViewById(R.id.close);
        mCloseIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save.setEnabled(true);
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
                    save.setEnabled(true);
                }
            }
        });
        mVerificationET = (EditText) vDialod.findViewById(R.id.et_verification_mail);

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
                        userInitialization();
                    } else {
                        if (numTrying == 3) {
                            vDialod.dismiss();
                            save.setEnabled(true);
                            mE_phoneEt.requestFocus();
                        }

                        SaveLog("numTrying == 2");
                        Toast.makeText(getContext(), R.string.verification_error, Toast.LENGTH_LONG).show();
                        mVerificationET.setText("");
                    }
                } else {
                    if (numTrying == 3) {
                        vDialod.dismiss();
                        mE_phoneEt.requestFocus();
                        save.setEnabled(true);
                    }
                    Toast.makeText(getContext(), R.string.verification_empty, Toast.LENGTH_LONG).show();
                    mVerificationET.setText("");
                }
            }
        });
        vDialod.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                save.setEnabled(true);
                sendVerificationBtn.setEnabled(true);
            }
        });
        vDialod.show();
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

    public void userInitialization() {
        UserObj.getInstance().setNvUserName(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvFirstName(mE_fnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvLastName(mE_lnameEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvMail(mE_mailEt.et_get_EditText().getText().toString());
        UserObj.getInstance().setNvPhone(mE_phoneEt.et_get_EditText().getText().toString());
//        UserObj.getInstance().setNvPassword(String.valueOf(verificationId));
//        UserObj.getInstance().setNvVerification(String.valueOf(verificationId));
        UserObj.getInstance().setbDataDownloadApproval(true);
        UserObj.getInstance().setbTermOfUseApproval(true);

        UserObj.getInstance().setiUserStatusType(24);
        UserObj.getInstance().setiUserType(1);
//        if (i3 == 1)
        if (al_yn2.getYes_no_state() == 1)
            UserObj.getInstance().setbIsGoogleCalendarSync(true);
        else
            UserObj.getInstance().setbIsGoogleCalendarSync(false);
        UserObj.getInstance().setNvImage(SimageByte);
        UserObj.getInstance().setiCreatedByUserId(0);
        UserObj.getInstance().setiCreatedByUserId(1);
        UserObj.getInstance().setiLastModifyUserId(1);
        UserObj.getInstance().setiSysRowStatus(1);
        UserObj.getInstance().setiCalendarViewType(view_calendar.get_choose());
        UpdateUser();
//        insertIntoUserRealm();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK) {
            try {
                if (cameraLauncher != null) {
                    cameraLauncher.onActivityResult(requestCode, resultCode, data);
//                    bIsActivityResult = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);
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
                if (!phone_p.equals(mE_phoneEt.getEditTextText().toString()))
                    if (mE_phoneEt.et_get_EditText().getText().length() != 0) {
                        String s = mE_phoneEt.et_get_EditText().getText().toString();
                        s = s.replace("-", "");
                        mE_phoneEt.et_set_text(s);
                        mE_phoneEt.validateOnFocusChange();
                        CheckPhoneValidityForFocus();
                    }
                break;
            case "mail":
                if (mE_mailEt.et_get_EditText().getText().length() != 0) {
                    mE_mailEt.validateOnFocusChange();
                    CheckMailValidityForFocus();
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
                    Toast.makeText(getContext(), "verificationCode" + verificationId, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getContext(), getString(R.string.there_is_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void CheckPhoneValidityForFocus() {
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
                    mE_phoneEt.setErrors(true);
                } else {
                    mE_phoneEt.setServerError(getResources().getString(R.string.phone_error));
                }
            }
        });
    }

    //check validate server for phone at the end
    public void CheckPhoneValidity() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            //check that the phone that is being checked is not the phone of this user
            if (!(mE_phoneEt.et_get_EditText().getText().toString().equals(userRealm.getNvPhone().toString()))) {
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
                            Log.d("eee", "phone is correct");
                            mE_phoneEt.setErrors(true);
                            //the phone is correct,so check the mail
                            CheckMailValidity();
                        } else {
                            Log.d("eee", "phone is not correct");
                            mE_phoneEt.setServerError(getResources().getString(R.string.phone_error));
                        }
                    }
                });
            }
             else
                CheckMailValidity();

        }}

    public void CheckMailValidityForFocus() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            //check that the mail that is being checked is not the mail of this user
            if (!(mE_mailEt.et_get_EditText().getText().toString().equals(userRealm.getNvMail().toString()))) {
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
                            mE_mailEt.setErrors(true);
                        } else {
                            mE_mailEt.setServerError(getResources().getString(R.string.mail_error));
                        }
                    }
                });
            }
        }
    }

    //check validate server for mail at the end,if correct so save all the details
    public void CheckMailValidity() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        if (userRealm != null) {
            //check that the mail that is being checked is not the mail of this user
            if (!(mE_mailEt.et_get_EditText().getText().toString().equals(userRealm.getNvMail()))) {
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
                            mE_mailEt.setErrors(true);
                       //all the validation are correct,so save the details
                            saveNewDetails();
                        }
                         else {
                            Log.d("eee","mail is not correct");
                            mE_mailEt.setServerError(getResources().getString(R.string.mail_error));
                        }
                    }
                });
            }
            else {
                mE_mailEt.setErrors(true);
                //all the validation are correct,so save the details
                saveNewDetails();
            }
        }
    }

    private void saveNewDetails(){
        //all the validation are correct,so save the details
        Log.d("eee","evreything  is correct");
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        String phone = userRealm.getNvPhone();
        if (!(mE_phoneEt.getEditTextText().equals(phone))) {
            numTrying = 0;
            verificationCode();
        } else
            userInitialization();
    }

    private void insertIntoUserRealm() {
        Realm realm = Utils.getRealmInstance(getContext());
        realm.beginTransaction();
        realm.where(UserRealm.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(new UserRealm(UserObj.getInstance()));
        realm.commitTransaction();

//        UpdateUser();
    }

    private void getProfileImg_c() {
        cameraLauncher = new CameraLauncher(this);
        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.CAMERA);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

    private void getProfileImg_g() {
        cameraLauncher = new CameraLauncher(this);
        arguments = new CameraLauncherArguments(190, 190, true, CameraLauncherArguments.ESourceType.GALLERY);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

    private SlideDateTimeListener listener = new SlideDateTimeListener() {
        private SimpleDateFormat mFormatter = new SimpleDateFormat("dd.MM.yyyy");

        @Override
        public void onDateTimeSet(Date date) {
            SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
            if (f_date == 1) {
                birth_date = date;
                mE_dateTV.setText(mFormatter.format(date));
                UserObj.getInstance().setdBirthdate(ConnectionUtils.convertStringToDate(format3.format(date)));
                mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
            }
            if (f_date == 2) {
                marriage_date = date;
                if (birth_date != null && marriage_date.before(birth_date)) {
                    Toast.makeText(getContext(), getContext().getResources().getString(R.string.enter_marriage), Toast.LENGTH_SHORT).show();

                } else {
                    mE_date_marriageTV.setText(mFormatter.format(date));
                    mE_date_marriageTV.setTextColor(getResources().getColor(R.color.color1));
                    UserObj.getInstance().setdMarriageDate(ConnectionUtils.convertStringToDate(format3.format(date)));
                }
            }
        }
    };

    private InputFilter[] setInputFilter(int max, final String message) {
        InputFilter[] inputFilter = new InputFilter[]{
                new InputFilter.LengthFilter(max) {
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        CharSequence res = super.filter(source, start, end, dest, dstart, dend);
                        if (res != null) { // Overflow
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

    private void openDialogDate(final int a) {
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
                        if (a == 1) {
                            UserObj.getInstance().setdBirthdate(ConnectionUtils.convertStringToDate(format3.format(c.getTime())));
                            mE_dateTV.setTextColor(getResources().getColor(R.color.color1));
                            mE_dateTV.setText(datePicker.getDay() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        } else {
                            UserObj.getInstance().setdMarriageDate(ConnectionUtils.convertStringToDate(format3.format(c.getTime())));
                            mE_date_marriageTV.setTextColor(getResources().getColor(R.color.color1));
                            mE_date_marriageTV.setText(datePicker.getDay() + "." + (datePicker.getMonth() + 1) + "." + datePicker.getYear());
                        }
                        vDialod.dismiss();
                    } else {
                        Toast.makeText(getActivity(), getResources().getString(R.string.enter_valid_date), Toast.LENGTH_LONG).show();
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
                save.setEnabled(true);
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

//update the details of the user
    private void UpdateUser() {
        String jsonString = "{ \"objUser\":" + new Gson().toJson(UserObj.getInstance()) + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.UpdateUser(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double id) {
                        if (id != null) {
                            Toast.makeText(getActivity(), getResources().getString(R.string.update_user_s), Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                            if (vDialod != null && vDialod.isShowing())
                                vDialod.dismiss();
                            save.setEnabled(true);
                            insertIntoUserRealm();
                            if (getActivity() instanceof NavigetionLayout)
                                //update the name in top in existed user
                                ((NavigetionLayout) getActivity()).set_name_after_update();
                        } else {
                            Toast.makeText(getActivity(), getResources().getString(R.string.update_user_not_s), Toast.LENGTH_LONG).show();
                            save.setEnabled(true);
                        }
                    }
                }
        );
    }
}
