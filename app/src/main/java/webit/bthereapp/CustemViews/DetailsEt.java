package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Utils.Utils;

/**
 * Created by 1 on 2/16/2016.
 */
public class DetailsEt extends LinearLayout implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private boolean flag = true;
    private boolean flagRequired = true;
    private boolean serverValidate = true;
    private Context mContext;
    private EditText mDetailsEt;
    private Boolean isrequired;
    private Boolean isvalidate;
    private String text;
    private String patternEt;
    private String errorEt;
    private String serverError;
    private int input_typeEt;
    private LinearLayout details_ll;
    private View view;
    AddOnClickListener addOnClickListener = null;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE_STREET = 2;


    @Override
    public void onClick(View v) {
        mDetailsEt.requestFocus();
        if (addOnClickListener != null)
            addOnClickListener.addOnClick();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private enum Format {
        enum_name_one(0), enum_name_n(666);
        int id;

        Format(int id) {
            this.id = id;
        }

        static Format fromId(int id) {
            for (Format f : values()) {
                if (f.id == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

    public DetailsEt(Context context, AttributeSet attrs) {
        super(context, attrs);
        flag = true;
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        if (view == null) {
            view = View.inflate(context, R.layout.details_et, this);
            this.mContext = context;
            mDetailsEt = (EditText) view.findViewById(R.id.detalis_et);

            this.details_ll = (LinearLayout) view.findViewById(R.id.details_ll);
            this.details_ll.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDetailsEt.requestFocus();
                    InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.showSoftInput(mDetailsEt, 0);
                }
            });
            view.setOnClickListener(this);
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DetailsEt, 0, 0);
            text = a.getText(R.styleable.DetailsEt_et_hint).toString();
            isrequired = a.getBoolean(R.styleable.DetailsEt_et_is_required, false);
            isvalidate = a.getBoolean(R.styleable.DetailsEt_et_validate, false);
            patternEt = a.getString(R.styleable.DetailsEt_et_pattern);
            errorEt = a.getString(R.styleable.DetailsEt_et_error);
            input_typeEt = a.getInt(R.styleable.DetailsEt_et_input_type, 0);
            if (input_typeEt == 0) {
                this.mDetailsEt.setInputType(InputType.TYPE_CLASS_TEXT);
                max_lenth_30();
            }
            if (input_typeEt == 1)
                this.mDetailsEt.setInputType(InputType.TYPE_CLASS_NUMBER);
            if (input_typeEt == 2)
                this.mDetailsEt.setInputType(InputType.TYPE_CLASS_PHONE);
            if (input_typeEt == 7)
                this.mDetailsEt.setInputType(InputType.TYPE_CLASS_PHONE);
            if (input_typeEt == 3)
                this.mDetailsEt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            if (input_typeEt == 4)
                this.mDetailsEt.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
            if (input_typeEt == 5)
                this.mDetailsEt.setInputType(InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            if (input_typeEt == 6)
                this.mDetailsEt.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
            if (isrequired)
                mDetailsEt.setHint(text + "*");
            else
                mDetailsEt.setHint(" " + text);
            mDetailsEt.setHintTextColor(Color.GRAY);

        }
    }


    private void max_lenth_30() {

        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(30);
        mDetailsEt.setFilters(filterArray);

    }

    public EditText et_get_EditText() {
        return mDetailsEt;
    }

    public void et_set_text(String text) {
        this.mDetailsEt.setText(text);
    }

    public void setEditTextText(String t) {
        mDetailsEt.setText(t);
    }

    public String getEditTextText() {
        return (mDetailsEt.getText()).toString();
    }

    public void setEditTextTag(String tag) {
        mDetailsEt.setTag(tag);
    }

    public void setServerError(String error) {
        this.serverError = error;
        mDetailsEt.setError(error);
    }

    public boolean isServerValidate() {
        return serverValidate;
    }

    public void setServerValidate(boolean serverValidate) {
        this.serverValidate = serverValidate;
    }

    //-------------//
    public boolean is_validate() {
        //not needed because the validation server is seperated
//        if (!serverValidate)
//            return false;
        if (!flag)
            return false;
        return true;
    }

    //set error on the edittext if no correct
    public void setErrors(boolean noError) {
        if (noError)
            mDetailsEt.setError(null);
        else if (!flagRequired)
            mDetailsEt.setError(getResources().getString(R.string.error_empty));
        else if (!serverValidate) {
            if (serverError != null)
                mDetailsEt.setError(serverError);
        } else if (!flag)
            mDetailsEt.setError(errorEt);
        else mDetailsEt.setError(null);
    }

    //if(the field is required check it
    public boolean requiredField() {
        if (!isrequired) {
            flagRequired = true;
        } else if (mDetailsEt.getText().toString().length() != 0)
            flagRequired = true;
        else
            flagRequired = false;
        setErrors(false);
        return flagRequired;
    }


    public void validateOnFocusChange() {
        BusinessDetailsFragment.check = true;
        if (mDetailsEt.getText().length() == 0)
            flag = true;
        else {
            flagRequired = true;
            mDetailsEt.setError(null);
            if (patternEt != null)
//            {
                valid();

            //check validity the web site
//            if (text.contains(getResources().getString(R.string.site)) && flag) {
            if (text.contains(getResources().getString(R.string.site))) {
                flag=true;
                Log.d("stop", "ggg" + flag);
                int count = 0;
                String str = mDetailsEt.getText().toString();
                for (int i = 0; i < str.length(); i++) {
                    if (!Character.toString(str.charAt(i)).equals(".") && !((str.charAt(i)) >= 'A' && (str.charAt(i)) <= 'Z')&& !((str.charAt(i)) >= 'a' && (str.charAt(i)) <= 'z')&& !((str.charAt(i)) >= '0' && (str.charAt(i)) <= '9')) {
                        flag = false;
                    }
                    if (Character.toString(str.charAt(i)).equals("."))
                        count++;
                }
                if (count < 1 || count > 2)
                    flag = false;
                if (Character.toString(str.charAt(0)).equals(".") || Character.toString(str.charAt(str.length() - 1)).equals("."))
                    flag = false;
                Log.d("stop", "ggg" + flag);

//                    String str = mDetailsEt.getText().toString();
//                    String end = str.substring(str.lastIndexOf(".") + 1);
//                    String siteSuffix[] = {"com", "net", "org", "edu", "biz", "gov", "mil", "info", "name", "me", "tv", "us", "mobi"};
//                    Boolean rightSuffix = false;
//                    for (String s : siteSuffix) {
//                        if (end.equals(s))
//                            rightSuffix = true;
//                    }
//                    if (!rightSuffix)
//                        flag = false;


                //check validity the phone
            } else if (text.contains(getResources().getString(R.string.phone)) && flag) {
                String str = mDetailsEt.getText().toString();
                if (!str.startsWith("05"))
                    flag = false;
                //check validity the phone
            } else if (text.contains(getResources().getString(R.string.business_fax)) && flag) {
                String str = mDetailsEt.getText().toString();
                if (!str.startsWith("0"))
                    flag = false;
                //check validity the tz
            } else if (text.contains(getResources().getString(R.string.business_ch_p)) && flag) {
                String str = mDetailsEt.getText().toString();
                if (!ValidateID(str))
                    flag = false;
            }
//            }
            //check validity the bussines name
            if (mDetailsEt.getText().length() > 0 && text.contains(getResources().getString(R.string.business_name))) {
                flag = true;
                int count = 0;
                for (int i = 0; i < mDetailsEt.getText().toString().length(); i++) {
                    if (String.valueOf(mDetailsEt.getText().toString().charAt(i)).equals(" ")) {
                        count++;
                    }
                }
                if ((mDetailsEt.getText().toString().length() - count) < 2)
                    flag = false;
                else {

                    String[] parts = mDetailsEt.getText().toString().split(" ");
                    for (int i = 0; i < parts.length && flag; i++) {
                        if (parts[i].length() < 2)
                            flag = false;
                    }
                }
                //check validity the name
            } else if (mDetailsEt.getText().length() > 0 && (text.contains(getResources().getString(R.string.first_name)))) {
                int count = 0;
                for (int i = 0; i < mDetailsEt.getText().toString().length(); i++) {
                    if (String.valueOf(mDetailsEt.getText().toString().charAt(i)).equals(" ")) {
                        count++;
                    }
                }
                if ((mDetailsEt.getText().toString().length() - count) < 2)
                    flag = false;
                else {
                    Pattern p;
                    Matcher m;
                    p = Pattern.compile("^[\\p{L} .'-]+$");
                    m = p.matcher(mDetailsEt.getText().toString());
                    if (!m.matches()) {
                        flag = false;
                    } else {
                        flag = true;
                    }

                }
                String[] parts = mDetailsEt.getText().toString().split(" ");
                for (int i = 0; i < parts.length && flag; i++) {
                    if (parts[i].length() < 2)
                        flag = false;
                }

            }

        }
        setErrors(false);
    }

    //check if need to validate edittext and put in flag the answer
    public void valid() {
        if (mDetailsEt.getText().toString().trim().length() == 0)
            flag = false;
        else if (patternEt != null) {
            Pattern p;
            Matcher m;
            p = Pattern.compile(patternEt);
            m = p.matcher(mDetailsEt.getText().toString());
            if (!m.matches()) {
                flag = false;
            } else {
                flag = true;
            }
        } else
            flag = true;
    }

    //check validity if 9 digits
    private boolean ValidateID(String str) {
        if (str.length() < 9)
            return false;
        return true;

    }
}