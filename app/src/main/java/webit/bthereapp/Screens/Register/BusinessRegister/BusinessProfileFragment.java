package webit.bthereapp.Screens.Register.BusinessRegister;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

//import com.facebook.FacebookSdk;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Camera.CallbackContext;
import webit.bthereapp.Camera.CameraLauncher;
import webit.bthereapp.Camera.CameraLauncherArguments;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderProfileObj;

import webit.bthereapp.Entities.HelpWorkingHours;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Entities.WorkingHours;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.ChosenServiceFragment;
import webit.bthereapp.Screens.Customer.OrderDetailsFragment;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierDefinitionsActivity;
import webit.bthereapp.Utils.Utils;

public class BusinessProfileFragment extends BaseFragment implements View.OnClickListener, OnMapReadyCallback {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private GoogleMap mMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ImageView mImageViewLogo;
    private Dialog vDialod;
    boolean c = false;
    private LinearLayout close_sale;
    private boolean[] Days = new boolean[7], DaysBreaks = new boolean[7];
    private CustomBoldTextView nameCompany;
    private CustomLightTextView sloganCompany;
    private int id_ = 0, d = 0;
    private int id;
    private String b_name = "", b_adress = "";
    private boolean bIsActivityResult;
    private Button mButtonEnter;
    private CustomLightButton to_order_b1, to_order_b2;
    private CustomLightTextView provider_name;
    private RelativeLayout relativeLayoutInformation;
    private CallbackContext callbackSignUpProfile;
    private CustomLightButton ok;
    int ss = 0;
    private CustomLightEditText aboutET;
    private Dialog dialog;
    private int a = 0;
    ProviderRealm businessRealm;
    private Bitmap bitmapTemp;
    private ImageView imageView, mImageView3, mImageView4, makeClick;
    private ImageView mImageView2;
    private static CameraLauncher cameraLauncher;
    private static int s = 0;
    private ImageView imageViewRaking;
    private ImageView mImageButtonCancel, mCancelInformationIB;
    private RelativeLayout mInformation;
    private int alertDialogImage;
    private int alertDialogEdit;
    private Context context;
    private View view;
    private boolean flag = false;
    private ScrollView scroll;
    String SimageByte = "";
    String logoString = "";
    String sloganString = "";
    private LinearLayout ll;
    public static boolean is_back = false;
    String headerString = "";
    String footerString = "";
    private LinearLayout slogan_ll;
    public ProgressDialog progressDialog;
    String campaignString = "";
    String aboutText = "";
    private static CameraLauncherArguments arguments;
    TextView mLocation;
    Button button;
    private CustomBoldTextView read;
    CustomLightTextView textHourTV;
    private TextView mAboutET;

    public BusinessProfileFragment() {
    }

    public static BusinessProfileFragment instance;

    public static BusinessProfileFragment getInstance() {
        if (instance == null)
            instance = new BusinessProfileFragment();
        return instance;
    }


    public static void removeInstance() {
        instance = null;
    }

    // TODO: Rename and change types and number of parameters
    public static BusinessProfileFragment newInstance(String param1, String param2) {
        BusinessProfileFragment fragment = new BusinessProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getActivity() instanceof MainActivity) {
            HideFragmentBottom();
        }
        id_ = 0;
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_business_profile, container, false);
            mLocation = (TextView) view.findViewById(R.id.textViewLocation);
            imageViewRaking = (ImageView) view.findViewById(R.id.imageViewRaking);
            to_order_b1 = (CustomLightButton) view.findViewById(R.id.to_order_b1);
            to_order_b2 = (CustomLightButton) view.findViewById(R.id.to_order_b2);
            to_order_b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof NavigetionLayout)
                        to_oredr();
                }
            });
            to_order_b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getActivity() instanceof NavigetionLayout)
                        to_oredr();
                }
            });
            relativeLayoutInformation = (RelativeLayout) view.findViewById(R.id.relativeLayoutInformation);
            relativeLayoutInformation.setOnClickListener(this);
            scroll = (ScrollView) view.findViewById(R.id.scroll_);
            close_sale = (LinearLayout) view.findViewById(R.id.close_sale);
            close_sale.setOnClickListener(this);
            slogan_ll = (LinearLayout) view.findViewById(R.id.slogan);
            slogan_ll.setOnClickListener(this);
            mAboutET = (TextView) view.findViewById(R.id.about);
            mImageViewLogo = (ImageView) view.findViewById(R.id.imageViewLogo);
            mImageViewLogo.setOnClickListener(this);
            mImageView2 = (ImageView) view.findViewById(R.id.imageView2);
            mImageView2.setOnClickListener(this);
            mImageView3 = (ImageView) view.findViewById(R.id.imageView3);
            mImageView3.setOnClickListener(this);
            provider_name = (CustomLightTextView) view.findViewById(R.id.provider_name);
            ok = (CustomLightButton) view.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().onBackPressed();
                }
            });
            mImageView4 = (ImageView) view.findViewById(R.id.imageView4);
            mImageView4.setOnClickListener(this);
            textHourTV = (CustomLightTextView) view.findViewById(R.id.textViewDayOne);
            read = (CustomBoldTextView) view.findViewById(R.id.read);
            read.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openToReadMoreDialog();
                }
            });
            if (!(getActivity() instanceof NavigetionLayout)) {
                if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails() != null && ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours() != null) {
                    String s2 = setTextHour(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours(), 3, Days);
                    String s1 = setTextHour((ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getWorkingHours()), 2, DaysBreaks);
                    if (!(s1.equals("")))
                        textHourTV.setText(s2 + "\n" + getString(R.string.break_, s1) + "\n");
                    else
                        textHourTV.setText(s2);

                }
            }
            ll = (LinearLayout) view.findViewById(R.id.ll);
            if (s > 2) {
                s = s - 2;
                s *= 32;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) ll.getLayoutParams();
                params.height += s;
                ll.setLayoutParams(params);
            }
            scroll.scrollTo(0, 500);
            if (getActivity() instanceof MainActivity)
                ((MainActivity) getActivity()).scroll(500);
            //when return from the camera after take picture
            callbackSignUpProfile = new CallbackContext() {
                @Override
                public void success(Bitmap bitmap) {
                    super.success(bitmap);
                    imageView.setImageBitmap(bitmap);
                    Log.d("mainac_", "callback");
                    bitmapTemp = bitmap;
                    imageView.setTag("change");

                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmapTemp.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                    byte[] imageByte = bytes.toByteArray();
                    SimageByte = Base64.encodeToString(imageByte, Base64.DEFAULT);
                    //save the string of the image
                    a();
//                    if (c)
                    //open the next dialog
                    next_step();

                }
            };
            sloganCompany = (CustomLightTextView) view.findViewById(R.id.sloganCompany);
            if (ProviderProfileObj.getInstance().getNvSlogen() != null)
                if (ProviderProfileObj.getInstance().getNvSlogen().length() > 48)
                    sloganCompany.setText(ProviderProfileObj.getInstance().getNvSlogen().toString().substring(0, 48) + "...");
            sloganCompany.setText(ProviderProfileObj.getInstance().getNvSlogen());

//            if (UserObj.getInstance().getNvFirstName() != null)
//                provider_name.setText(" " + UserObj.getInstance().getNvFirstName());
            Realm realm = Utils.getRealmInstance(getContext());
            UserRealm userRealm = realm.where(UserRealm.class).findFirst();
            if (userRealm != null)
                if (userRealm.getNvFirstName() != null)
                    provider_name.setText(" " + userRealm.getNvFirstName());


            nameCompany = (CustomBoldTextView) view.findViewById(R.id.nameCompany);


            mButtonEnter = (Button) view.findViewById(R.id.buttonEnter);
            mButtonEnter.setOnClickListener(this);
            mInformation = (RelativeLayout) view.findViewById(R.id.relativeLayoutInformation);
            mInformation.setOnClickListener(this);
            alertDialogImage = R.layout.alertdialog_image_custom;
            alertDialogEdit = R.layout.alertdialog_information_custom;
            SupportMapFragment mapFragment = new SupportMapFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.map_frame, mapFragment).commit();
            mapFragment.getMapAsync(this);
            if (!(getActivity() instanceof NavigetionLayout)) {
                //put the perious details if stopped at mittle the registion
                realm = Utils.getRealmInstance(getContext());

                BusinessProfileRealm businessProfileRealm = realm.where(BusinessProfileRealm.class).findFirst();

                //if stopped in middle the registion,
                //put the details from the realm in the fragment
                if (businessProfileRealm != null) {

                    if (businessProfileRealm.getNvAboutComment() != null && (!businessProfileRealm.getNvAboutComment().equals(""))) {
                        aboutText = businessProfileRealm.getNvAboutComment().toString();
                        mAboutET.setVisibility(View.VISIBLE);

                        String[] lines = aboutText.split("\r\n|\r|\n");
                        if (lines.length>2) {
                            read.setVisibility(View.VISIBLE);
                            mAboutET.setText(aboutText);
                        }
                    }
                    if (businessProfileRealm.getNvCampaignImage() != null && (!businessProfileRealm.getNvCampaignImage().equals(""))) {
                        campaignString = businessProfileRealm.getNvCampaignImage().toString();
                        byte[] decodedString = Base64.decode(campaignString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mImageView3.setImageBitmap(decodedByte);
                    }
//                    else {
//                        mImageView3.setVisibility(View.GONE);
//                        close_sale.setVisibility(View.GONE);
//                    }
                    if (businessProfileRealm.getNvFooterImage() != null && (!businessProfileRealm.getNvFooterImage().equals(""))) {
                        footerString = businessProfileRealm.getNvFooterImage().toString();
                        byte[] decodedString = Base64.decode(footerString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mImageView4.setImageBitmap(decodedByte);
                    }
                    if (businessProfileRealm.getNvHeaderImage() != null && (!businessProfileRealm.getNvHeaderImage().equals(""))) {
                        headerString = businessProfileRealm.getNvHeaderImage().toString();
                        byte[] decodedString = Base64.decode(headerString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mImageView2.setImageBitmap(decodedByte);
                    }
                    if (businessProfileRealm.getNvILogoImage() != null && (!businessProfileRealm.getNvILogoImage().equals(""))) {
                        logoString = businessProfileRealm.getNvILogoImage().toString();
                        byte[] decodedString = Base64.decode(logoString, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mImageViewLogo.setImageBitmap(decodedByte);
                    }
                    if (businessProfileRealm.getNvSlogen() != null && (!businessProfileRealm.getNvSlogen().equals(""))) {
                        sloganString = businessProfileRealm.getNvSlogen().toString();
                        if (sloganString.length() > 48) {
                            sloganCompany.setText(sloganString.toString().substring(0, 48) + "...");
                        } else
                            sloganCompany.setText(sloganString);
                    }
                }
            }

        } else
            Log.d("mainac_", "oncreateview no null");
        Realm realm = Utils.getRealmInstance(getContext());
        businessRealm = realm.where(ProviderRealm.class).findFirst();
//put the map according the adress of the bussines
        if (businessRealm != null) {
            if (businessRealm.getNvAddress() != null)
                mLocation.setText(businessRealm.getNvAddress());
            if (businessRealm.getNvSupplierName() != null)
                nameCompany.setText(businessRealm.getNvSupplierName());
        }
        SupportMapFragment mapFragment = new SupportMapFragment();
        getChildFragmentManager().beginTransaction().replace(R.id.map_frame, mapFragment).
                commit();
        mapFragment.getMapAsync(this);

        if (getActivity() instanceof MainActivity) {
            if (is_back)
                initFragmentTop(MainActivity.Num, getString(R.string.title_fragment_business_profile), 4, true);
            else
                initFragmentTop(MainActivity.Num, getString(R.string.title_fragment_business_profile), 4, false);
            is_back = false;
        }


        if (getActivity() instanceof ExistsSuplierDefinitionsActivity) {
            super.initFragmentTop3(4, getString(R.string.title_fragment_business_profile), is_back);
            mButtonEnter.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);

            ((ExistsSuplierDefinitionsActivity) getActivity()).hideFragmentMain();
            ((ExistsSuplierDefinitionsActivity) getActivity()).visibleContainerMain();
            is_back = false;
        }

        if (getActivity() instanceof NavigetionLayout) {
            mButtonEnter.setVisibility(View.GONE);
            ok.setVisibility(View.VISIBLE);
            provider_name.setVisibility(View.VISIBLE);
            //put the datails of the provider
            setLastProviderProfile();
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.dismiss();
                }
            }, 1500);
        }
        return view;
    }

    private void a() {

        switch (a) {
            case 1:
                logoString = SimageByte;
                break;
            case 3:
                headerString = SimageByte;
                break;
            case 5:
                campaignString = SimageByte;
                break;
            case 6:
                footerString = SimageByte;
                break;
        }

    }

    //open the next dialog
    private void next_step() {
        id_++;
        if (id_ == 1) {
            //if not edited
            if (logoString.equals("")) {
                //open the dialog of image of the logo
                imageView = mImageViewLogo;
                makeClick = mImageViewLogo;
                int[] viewLocation = new int[2];
                mImageViewLogo.getLocationInWindow(viewLocation);

                int[] viewLocation2 = new int[2];
                imageViewRaking.getLocationInWindow(viewLocation2);
                viewLocation[1] = viewLocation2[1];
                scroll.fullScroll(View.FOCUS_FORWARD);
                a = 1;
                setAlertDialog(getString(R.string.logo), viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        } else if (id_ == 2) {
            //if not edited
            if (sloganString.equals("")) {
                //open the dialog of the slogan
                a = 2;
                int[] viewLocation = new int[2];
                mImageViewLogo.getLocationInWindow(viewLocation);
                int b = (int) (Math.round(mImageViewLogo.getY()));
                scroll.scrollTo(0, b);

                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).scroll(b);
                viewLocation[1] = viewLocation[1] + (slogan_ll.getHeight() / 2);

                setAlertDialogSlogan(viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        } else if (id_ == 3) {
            //if not edited
            if (headerString.equals("")) {
                //open the dialog of image
                a = 3;
                imageView = mImageView2;
                makeClick = mImageView2;

                int[] viewLocation = new int[2];

                mImageView2.getLocationInWindow(viewLocation);
                viewLocation[1] = viewLocation[1] + (mImageView2.getHeight() / 2);
                setAlertDialog(getString(R.string.camera), viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        } else if (id_ == 4) {
            //if not edited
            if (aboutText.equals("")) {
                a = 4;
                int[] viewLocation = new int[2];

                int b = (int) (Math.round(relativeLayoutInformation.getY()));
                scroll.scrollTo(0, b);
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).scroll(b);
                relativeLayoutInformation.getLocationInWindow(viewLocation);
                viewLocation[1] = viewLocation[1] + (relativeLayoutInformation.getHeight() / 2);
                setAlertDialogInformation(viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        } else if (id_ == 5) {
            //if not edited
            if (campaignString.equals("")) {
                //open the dialog of image of the sale
                a = 5;
                imageView = mImageView3;
                makeClick = mImageView3;
                int[] viewLocation = new int[2];

                int b = (int) (Math.round(mImageView3.getY()));

                b = (int) (Math.round(relativeLayoutInformation.getY()));
                scroll.scrollTo(0, b);
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).scroll(b);
                relativeLayoutInformation.getLocationInWindow(viewLocation);
                viewLocation[1] = (int) (viewLocation[1] + (mImageView3.getHeight() * (0.8)));
                setAlertDialog(getString(R.string.sale), viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        } else if (id_ == 6) {
            //if not edited
            if (footerString.equals("")) {
                flag = true;
                //open the dialog of image
                a = 6;
                imageView = mImageView4;
                makeClick = mImageView4;

                int[] viewLocation = new int[2];

                int b = (int) ((Math.round(relativeLayoutInformation.getY())));
                scroll.scrollTo(0, b);
                if (getActivity() instanceof MainActivity)
                    ((MainActivity) getActivity()).scroll(b);
                mImageView4.getLocationInWindow(viewLocation);
                viewLocation[1] = (int) (viewLocation[1] + (mImageView3.getHeight() * (0.5)));
                setAlertDialog(getString(R.string.image), viewLocation, true);
            } else {
//                id_++;
                next_step();
            }
        }


    }

    //open dialog to enter image
    public void setAlertDialog(String text, int[] viewLocation, final boolean b) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.LEFT;
        wmlp.x = 40;  //x position
        wmlp.y = viewLocation[1];//y position

        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.setContentView(R.layout.alertdialog_image_custom);
        TextView edit_model = (TextView) dialog.findViewById(R.id.textViewTitle);
        edit_model.setText(text);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        LinearLayout linearLayoutVisible;
        LinearLayout linearLayoutInVisible;
        if (flag) {
            wmlp.y = viewLocation[1] - 320;
            linearLayoutInVisible = (LinearLayout) dialog.findViewById(R.id.LinearLayoutAboutOne);
            linearLayoutVisible = (LinearLayout) dialog.findViewById(R.id.linearLayoutEnd);
        } else {
            linearLayoutInVisible = (LinearLayout) dialog.findViewById(R.id.linearLayoutEnd);
            linearLayoutVisible = (LinearLayout) dialog.findViewById(R.id.LinearLayoutAboutOne);
        }
        linearLayoutInVisible.setVisibility(View.GONE);
        linearLayoutVisible.setVisibility(View.VISIBLE);
        dialog.getWindow().setAttributes(wmlp);
        RelativeLayout relativeLayoutGalary = (RelativeLayout) dialog.findViewById(R.id.Line1);
        relativeLayoutGalary.setOnClickListener(this);
        RelativeLayout relativeLayoutCamera = (RelativeLayout) dialog.findViewById(R.id.Line2);
        relativeLayoutCamera.setOnClickListener(this);
        mImageButtonCancel = (ImageView) dialog.findViewById(R.id.imageButtonCancel);
        mImageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if (b && c)
                next_step();
            }
        });
        flag = false;
    }

    //open dialog to input text
    public void setAlertDialogInformation(int[] viewLocation, final boolean b) {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        wmlp.x = 15;  //x position
        wmlp.y = viewLocation[1];//y position
        dialog.getWindow().setAttributes(wmlp);
        dialog.show();
        dialog.setContentView(R.layout.alertdialog_information_custom);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //makes the dialog go on top the key board
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dialog.show();
        aboutET = (CustomLightEditText) dialog.findViewById(R.id.editTextInformation);
        if (aboutText != null)
            aboutET.setText(aboutText);
        mCancelInformationIB = (ImageView) dialog.findViewById(R.id.imageButtonCancel);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        Button buttonSave = (Button) dialog.findViewById(R.id.buttonSaveEnter);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the key board
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(aboutET.getWindowToken(), 0);
                ////save edit text
                aboutText = aboutET.getText().toString();
                mAboutET.setVisibility(View.VISIBLE);

                String[] lines = aboutText.split("\r\n|\r|\n");
                if (lines.length>2) {
                    read.setVisibility(View.VISIBLE);
                    mAboutET.setText(aboutText);
                }

                dialog.dismiss();
//                if (b && c)
                next_step();

            }
        });
        mCancelInformationIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if (b && c)
                next_step();
            }
        });

        dialog = dialog;
    }

    //open dialog to input text to slogan
    public void setAlertDialogSlogan(int[] viewLocation, final boolean b) {
//        Display display = getActivity().getWindowManager().getDefaultDisplay();
//        int width = display.getWidth();  // deprecate
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER;
        wmlp.x = 15;  //x position
        wmlp.y = viewLocation[1];//y position

        dialog.getWindow().setAttributes(wmlp);
        dialog.show();
        dialog.setContentView(R.layout.alertdialog_slogan_custom);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        //makes the dialog go on top the key board
//        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.show();
        aboutET = (CustomLightEditText) dialog.findViewById(R.id.editTextInformation);
        if (sloganString != "")
            aboutET.setText(sloganString);
        aboutET.setFilters(setInputFilter(120, getResources().getString(R.string.too_long_slogen)));
        aboutET.setSelection(0);


        mCancelInformationIB = (ImageView) dialog.findViewById(R.id.imageButtonCancel);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        Button buttonSave = (Button) dialog.findViewById(R.id.buttonSaveEnter);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //close the key board
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(aboutET.getWindowToken(), 0);
                ////save edit text
                sloganString = aboutET.getText().toString();

                if (aboutET.getText().length() > 48) {
                    sloganCompany.setText(aboutET.getText().toString().substring(0, 48) + "...");
                } else
                    sloganCompany.setText(sloganString);

                dialog.dismiss();
//                if (b && c)
                next_step();
            }
        });
        mCancelInformationIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                if (b && c)
                next_step();
            }
        });
//        dialog = dialog;
    }


    @Override
    public boolean OnBackPress() {
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
        if (getActivity() instanceof ExistsSuplierActivity) {
            ((ExistsSuplierActivity) getActivity()).visibleFragmentMain();
            ((ExistsSuplierActivity) getActivity()).hideFragmentTop();
            ((ExistsSuplierActivity) getActivity()).hideContainerMain();
        }
        return true;
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

    @Override
    public void onClick(View v) {
        int b;
        if (!(getActivity() instanceof NavigetionLayout)) {
            int[] viewLocation = new int[2];
            v.getLocationInWindow(viewLocation);

            switch (v.getId()) {
                case R.id.close_sale: {
                    if (d == 1) {
                        mImageView3.setVisibility(View.VISIBLE);
                        d = 0;
                    } else {
                        mImageView3.setVisibility(View.GONE);
                        d = 1;
                    }

                    break;
                }
                case R.id.imageViewLogo:

                    int[] viewLocation2 = new int[2];
                    imageViewRaking.getLocationInWindow(viewLocation2);
                    viewLocation[1] = viewLocation2[1];

                    c = false;
                    setAlertDialog(getString(R.string.logo), viewLocation, false);
                    a = 1;
                    imageView = mImageViewLogo;
                    id_ = 1;
//                    next_step();
                    break;
                case R.id.buttonEnter:

                    mButtonEnter.setEnabled(false);
                    if (ConnectionUtils.if_server)
                        saveBusinessProfile();
                    if (dialog != null)
                        dialog.dismiss();
                    initFragmentMain(ContactListFragment.getInstance(), true, true, 5);
                    break;
                case R.id.Line2:
                    getProfileImg_c();
                    if (dialog != null)
                        dialog.dismiss();
                    break;
                case R.id.Line1:
                    getProfileImg_g();
                    if (dialog != null)
                        dialog.dismiss();

                    break;
                case R.id.imageView3:
                    c = false;
                    a = 5;

                    b = (int) (Math.round(relativeLayoutInformation.getY()));
                    scroll.scrollTo(0, b);
                    if (getActivity() instanceof MainActivity)
                        ((MainActivity) getActivity()).scroll(b);
                    relativeLayoutInformation.getLocationInWindow(viewLocation);
                    viewLocation[1] = (int) (viewLocation[1] + (mImageView3.getHeight() * (0.8)));

                    setAlertDialog(getString(R.string.sale), viewLocation, false);
                    imageView = mImageView3;
                    id_ = 5;
//                    next_step();
                    break;
                case R.id.imageView2:
                    a = 3;
                    c = false;
                    viewLocation[1] = viewLocation[1] + (mImageView2.getHeight() / 2);
                    setAlertDialog(getString(R.string.camera), viewLocation, false);
                    imageView = mImageView2;
                    id_ = 3;
//                    next_step();
                    break;
                case R.id.imageView4:
                    a = 6;
                    c = false;
                    flag = true;
                    b = (int) ((Math.round(relativeLayoutInformation.getY())));
                    scroll.scrollTo(0, b);
                    if (getActivity() instanceof MainActivity)
                        ((MainActivity) getActivity()).scroll(b);
                    mImageView4.getLocationInWindow(viewLocation);
                    viewLocation[1] = (int) (viewLocation[1] + (mImageView3.getHeight() * (0.5)));

                    setAlertDialog(getString(R.string.image), viewLocation, false);
                    imageView = mImageView4;
                    id_ = 6;
//                    next_step();
                    break;
                case R.id.relativeLayoutInformation:
                    a = 4;
                    c = false;

                    b = (int) (Math.round(relativeLayoutInformation.getY()));
                    scroll.scrollTo(0, b);
                    if (getActivity() instanceof MainActivity)
                        ((MainActivity) getActivity()).scroll(b);
                    relativeLayoutInformation.getLocationInWindow(viewLocation);
                    viewLocation[1] = viewLocation[1] + (relativeLayoutInformation.getHeight() / 2);
//                    viewLocation[1]=viewLocation[1]+(mImageView2.getHeight()/3);
                    setAlertDialogInformation(viewLocation, false);
                    id_ = 4;
//                    next_step();
                    break;
                case R.id.slogan:
                    a = 2;
                    c = false;
                    viewLocation[1] = viewLocation[1] + (slogan_ll.getHeight() / 2);
                    setAlertDialogSlogan(viewLocation, false);
                    id_ = 2;
//                    next_step();
                    break;


            }
        }
    }

    private void getProfileImg_c() {
        cameraLauncher = new CameraLauncher(this);

        int height = imageView.getHeight();
        int width = imageView.getWidth();
        arguments = new CameraLauncherArguments(width, height, true, CameraLauncherArguments.ESourceType.CAMERA);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());

    }

    private void getProfileImg_g() {
        cameraLauncher = new CameraLauncher(this);

        int height = imageView.getHeight();
        int width = imageView.getWidth();
        arguments = new CameraLauncherArguments(width, height, true, CameraLauncherArguments.ESourceType.GALLERY);
        cameraLauncher.execute(arguments, callbackSignUpProfile, getActivity().getApplicationContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == getActivity().RESULT_OK) {

            try {
                if (cameraLauncher != null) {
                    cameraLauncher.onActivityResult(requestCode, resultCode, data);
                    bIsActivityResult = true;

                    if (button != null)
                        button.setVisibility(View.GONE);
                    if (makeClick != null)
                        makeClick.setClickable(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            super.onActivityResult(requestCode, resultCode, data);

        }

        if (button != null)
            button.setVisibility(View.GONE);
        if (makeClick != null)
            makeClick.setClickable(true);
    }

    private void saveBusinessProfile() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        ProviderProfileObj.getInstance().setiProviderUserId(userRealm.getUserID());

        ProviderProfileObj.getInstance().setNvAboutComment(aboutText);
        ProviderProfileObj.getInstance().setNvCampaignImage(campaignString);
        ProviderProfileObj.getInstance().setNvFooterImage(footerString);
        ProviderProfileObj.getInstance().setNvHeaderImage(headerString);
        ProviderProfileObj.getInstance().setNvILogoImage(logoString);
        ProviderProfileObj.getInstance().setNvSlogen(sloganString);

        realm.beginTransaction();
        BusinessProfileRealm businessProfileRealm = new BusinessProfileRealm(ProviderProfileObj.getInstance());
        realm.where(BusinessProfileRealm.class).findAll().deleteAllFromRealm();
        realm.copyToRealm(businessProfileRealm);
        realm.commitTransaction();
    }


    private List<HelpWorkingHours> sortByDays(List<WorkingHours> workingHoursList) {
        List<HelpWorkingHours> helpWorkingHoursList = new ArrayList<>();
        HelpWorkingHours help;
        int i, j;
        int m = -1, m2 = -1;

        WorkingHours work1 = new WorkingHours(0, "", "");
        help = new HelpWorkingHours(work1, work1);
        helpWorkingHoursList.add(0, help);

        for (i = 1; i < 8; i++) {
            m = -1;
            m2 = -1;
            for (j = 0; j < workingHoursList.size(); j++) {
                if (workingHoursList.get(j).getiDayInWeekType() == i && workingHoursList.get(j).getNum() == 0) {
                    m = j;
                }
                if (workingHoursList.get(j).getiDayInWeekType() == i && workingHoursList.get(j).getNum() == 1) {
                    m2 = j;
                }

            }
            if (m != -1) {
                if (m2 != -1) {
                    help = new HelpWorkingHours(workingHoursList.get(m), workingHoursList.get(m2));
                    helpWorkingHoursList.add(i, help);
                } else {
                    work1 = new WorkingHours(i, "", "");
                    help = new HelpWorkingHours(workingHoursList.get(m), work1);
                    helpWorkingHoursList.add(i, help);
                }
            }
//            && m2 == -1
            if (m == -1) {
                work1 = new WorkingHours(i, "", "");
                help = new HelpWorkingHours(work1, work1);
                helpWorkingHoursList.add(i, help);
            }
        }
        return helpWorkingHoursList;
    }

    //get hours from the realm
    private List<HelpWorkingHours> sortByDays2(List<WorkingHours> workingHoursList) {
        List<HelpWorkingHours> helpWorkingHoursList = new ArrayList<>();
        HelpWorkingHours help;
        int i, j, a = 0;
        int m[] = {-1, -1};

        WorkingHours work1 = new WorkingHours(0, "", "");
        help = new HelpWorkingHours(work1, work1);
        helpWorkingHoursList.add(0, help);

        for (i = 1; i < 8; i++) {
            m[0] = -1;
            m[1] = -1;
            a = 0;
            for (j = 0; j < workingHoursList.size(); j++) {
                if (workingHoursList.get(j).getiDayInWeekType() == i) {
                    m[a++] = j;
                }
            }
            if (m[0] != -1) {
                if (m[1] != -1) {
                    help = new HelpWorkingHours(workingHoursList.get(m[0]), workingHoursList.get(m[1]));
                    helpWorkingHoursList.add(i, help);
                } else {
                    work1 = new WorkingHours(i, "", "");
                    help = new HelpWorkingHours(workingHoursList.get(m[0]), work1);
                    helpWorkingHoursList.add(i, help);
                }
            } else {
                work1 = new WorkingHours(i, "", "");
                help = new HelpWorkingHours(work1, work1);
                helpWorkingHoursList.add(i, help);
            }
        }
        return helpWorkingHoursList;
    }

    @Override
    public void onStart() {
        super.onStart();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                c = true;
                if (!(getActivity() instanceof NavigetionLayout) && id_ == 0 && a == 0)
                    next_step();
            }
        }, 600);
    }


    public List<HelpWorkingHours> getBreaks(List<HelpWorkingHours> workingHours) {
        List<HelpWorkingHours> array = new ArrayList<>();
        HelpWorkingHours help = null;
        WorkingHours workingHours1;
        WorkingHours workingHours2;

        for (int i = 0; i < 7; i++) {
            workingHours2 = new WorkingHours(i, "", "");
            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker1().getiDayInWeekType() == i) {
                workingHours1 = new WorkingHours(i, workingHours.get(i).getWorker1().getNvToHour(), workingHours.get(i).getWorker2().getNvFromHour());

                help = new HelpWorkingHours(workingHours1, workingHours2);
                if (i > 0)
                    DaysBreaks[i - 1] = true;
            } else
                help = new HelpWorkingHours(workingHours2, workingHours2);


            array.add(i, help);
        }

        return array;
    }

    //get hours of activity from the list
    public List<HelpWorkingHours> getHours(List<HelpWorkingHours> workingHours) {
        List<HelpWorkingHours> array = new ArrayList<>();
        HelpWorkingHours help = null;
        WorkingHours workingHours1;
        WorkingHours workingHours2;

        for (int i = 0; i < 7; i++) {
            workingHours2 = new WorkingHours(i, "", "");

            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                workingHours1 = new WorkingHours(i, workingHours.get(i).getWorker1().getNvFromHour(), workingHours.get(i).getWorker2().getNvToHour());
                help = new HelpWorkingHours(workingHours1, workingHours2);
                if (i > 0) {
                    Days[i - 1] = true;
                }
            }

            if (!workingHours.get(i).getWorker1().getNvToHour().equals("") && workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours.get(i).getWorker1(), workingHours2);

                if (i > 0)
                    Days[i - 1] = true;
            }

            if (workingHours.get(i).getWorker1().getNvToHour().equals("") && !workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours2, workingHours.get(i).getWorker2());
                if (i > 0)
                    Days[i - 1] = true;
            }

            if (workingHours.get(i).getWorker1().getNvToHour().equals("") && workingHours.get(i).getWorker2().getNvToHour().equals("") && workingHours.get(i).getWorker2().getiDayInWeekType() == i) {
                help = new HelpWorkingHours(workingHours2, workingHours2);
            }
            if (workingHours.get(i).getWorker2().getiDayInWeekType() != i)
                help = new HelpWorkingHours(workingHours2, workingHours2);
            array.add(i, help);
        }

        return array;
    }

    //set the hours of the bussines
    public String setTextHour(List<WorkingHours> workingHours, int r, boolean[] Days) {
        boolean fl = false;
        boolean b = false;
        final boolean[] workDays = new boolean[7];
        Arrays.fill(workDays, true);
        final boolean[] breakDays = new boolean[7];
        Arrays.fill(breakDays, true);
        boolean m = false;
        String text_day = "";
        String newString = "";
        String hours = "";
        List<HelpWorkingHours> workingHoursList = new ArrayList<>();
        if (r == 1)
            workingHoursList = sortByDays(workingHours);
        if (r == 2)
            //put break from the realm
            workingHoursList = getBreaks(sortByDays2(workingHours));
        if (r == 3)
            //put hours from the realm
            workingHoursList = getHours(sortByDays2(workingHours));

        for (int i = 1; i <= 6; i++) {
            if (Days[i - 1]) {
                m = false;
                hours = "";
                text_day = "";
                if (workingHoursList.get(i) != null && (workDays[i])) {
                    text_day = text_day + getNameDayFromNumber(i);
                    m = true;
                    if (workingHoursList.get(i).getWorker1().getNvFromHour() == "" && workingHoursList.get(i).getWorker2().getNvFromHour() == "") {
                        hours = "";
                    } else {
                        if (workingHoursList.get(i).getWorker1() != null && workingHoursList.get(i).getWorker1().getNvFromHour() != ""/*&& Days[i-1]*/)
                            hours = hours + workingHoursList.get(i).getWorker1().getNvFromHour().substring(0, 5) + "-" + workingHoursList.get(i).getWorker1().getNvToHour().substring(0, 5);
                        if (workingHoursList.get(i).getWorker2() != null && workingHoursList.get(i).getWorker2().getNvFromHour() != "") {
                            hours = hours + ", ";
                            hours = hours + workingHoursList.get(i).getWorker2().getNvFromHour().substring(0, 5) + "-" + workingHoursList.get(i).getWorker2().getNvToHour().substring(0, 5);
                        }
                    }
                    for (int j = i + 1; j <= 6; j++) {
                        if (Days[j - 1]) {
                            if (workDays[j]/*&& Days[j-1]*/)
                                if (workingHoursList.get(i).getWorker1() != null && workingHoursList.get(j).getWorker1() != null)

                                    if (workingHoursList.get(j).getWorker1().getNvFromHour().equals(workingHoursList.get(i).getWorker1().getNvFromHour()))

                                        if (workingHoursList.get(j).getWorker1().getNvToHour().equals(workingHoursList.get(i).getWorker1().getNvToHour())) {
                                            if (workingHoursList.get(i).getWorker2() != null && workingHoursList.get(j).getWorker2() != null) {

                                                if (workingHoursList.get(j).getWorker2().getNvFromHour().equals(workingHoursList.get(i).getWorker2().getNvFromHour())) {

                                                    if (workingHoursList.get(j).getWorker2().getNvToHour().equals(workingHoursList.get(i).getWorker2().getNvToHour())) {
                                                        text_day = text_day + ", " + getNameDayFromNumber(j);
                                                        workDays[j] = false;
                                                    }
                                                }
                                            } else if (workingHoursList.get(i).getWorker2() == null && workingHoursList.get(j).getWorker2() == null) {
                                                text_day = text_day + ", " + getNameDayFromNumber(j);
                                                workDays[j] = false;
                                            }
                                        }
                        }
                    }
                }
                if (m && !hours.equals("")) {
                    if (b)
                        newString += "," + text_day + " - " + hours;
                    else {
                        newString += text_day + " - " + hours;
                        b = true;
                    }
                }

            }
        }
        return newString;
    }


    private String getNameDayFromNumber(int i) {

        switch (i) {
            case 1:
                return getString(R.string.sun);
            case 2:
                return getString(R.string.mon);
            case 3:
                return getString(R.string.tues);
            case 4:
                return getString(R.string.wed);
            case 5:
                return getString(R.string.thur);
            case 6:
                return getString(R.string.fir);
        }
        return "";
    }

    @Override
    public void onResume() {
        super.onResume();
        mButtonEnter.setEnabled(true);
        Log.d("mainac_", "onResume_in_pr");

    }

    public void set_progressDialog(ProgressDialog progressDialog_) {
        if (progressDialog_ != null) {
            progressDialog = progressDialog_;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        if (getActivity() instanceof NavigetionLayout) {
            LatLng latlng = getLocationFromAddress(getContext(), b_adress);
            mMap.addMarker(new MarkerOptions().position(latlng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
        } else if (businessRealm != null && businessRealm.getNvAddress() != null) {
            LatLng latlng = getLocationFromAddress(getContext(), businessRealm.getNvAddress());
            mMap.addMarker(new MarkerOptions().position(latlng));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17));
        }
    }

    public void put_providerid(int id__, String b_name, String b_adress) {
        this.id = id__;
        this.b_name = b_name;
        this.b_adress = b_adress;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude());
            if (p1 == null)
                p1 = new LatLng(0, 0);
        } catch (Exception ex) {
            p1 = new LatLng(0, 0);
            ex.printStackTrace();
        }

        return p1;
    }

    //put the datails of the provider
    private void setLastProviderProfile() {

        if (ProviderProfileObj.getInstance().getNvAboutComment() != null && (!ProviderProfileObj.getInstance().getNvAboutComment().equals(""))) {
            aboutText = ProviderProfileObj.getInstance().getNvAboutComment().toString();
            mAboutET.setVisibility(View.VISIBLE);

            String[] lines = aboutText.split("\r\n|\r|\n");
            if (lines.length>2) {
                read.setVisibility(View.VISIBLE);
                mAboutET.setText(aboutText);
            }
        }
        if (ProviderProfileObj.getInstance().getNvCampaignImage() != null && (!ProviderProfileObj.getInstance().getNvCampaignImage().equals(""))) {
            campaignString = ProviderProfileObj.getInstance().getNvCampaignImage().toString();
            byte[] decodedString = Base64.decode(campaignString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            mImageView3.setImageBitmap(decodedByte);
        } else {
            mImageView3.setVisibility(View.GONE);
            close_sale.setVisibility(View.GONE);
        }
        if (ProviderProfileObj.getInstance().getNvFooterImage() != null && (!ProviderProfileObj.getInstance().getNvFooterImage().equals(""))) {
            footerString = ProviderProfileObj.getInstance().getNvFooterImage().toString();
            byte[] decodedString = Base64.decode(footerString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            mImageView4.setImageBitmap(decodedByte);
        }
        if (ProviderProfileObj.getInstance().getNvHeaderImage() != null && (!ProviderProfileObj.getInstance().getNvHeaderImage().equals(""))) {
            headerString = ProviderProfileObj.getInstance().getNvHeaderImage().toString();
            byte[] decodedString = Base64.decode(headerString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            mImageView2.setImageBitmap(decodedByte);
        }
        if (ProviderProfileObj.getInstance().getNvILogoImage() != null && (!ProviderProfileObj.getInstance().getNvILogoImage().equals(""))) {
            logoString = ProviderProfileObj.getInstance().getNvILogoImage().toString();
            byte[] decodedString = Base64.decode(logoString, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            mImageViewLogo.setImageBitmap(decodedByte);
        }
        if (ProviderProfileObj.getInstance().getNvSlogen() != null && (!ProviderProfileObj.getInstance().getNvSlogen().equals(""))) {
            sloganString = ProviderProfileObj.getInstance().getNvSlogen().toString();
            if (sloganString.length() > 48)
                sloganCompany.setText(sloganString.toString().substring(0, 48) + "...");
            else
                sloganCompany.setText(sloganString);
        }
        if (!(b_adress.equals("")))
            mLocation.setText(b_adress);
        if (!(b_name.equals("")))
            nameCompany.setText(b_name);

        if (ProviderProfileObj.getInstance().getObjWorkingHours() != null) {
            String s2 = setTextHour(ProviderProfileObj.getInstance().getObjWorkingHours(), 3, Days);
            String s1 = setTextHour((ProviderProfileObj.getInstance().getObjWorkingHours()), 2, DaysBreaks);
            if (!(s1.equals("")))
                textHourTV.setText(s2 + "\n" + getString(R.string.break_, s1) + "\n");
            else
                textHourTV.setText(s2);

        }
    }

    //in NavigetionLayout activity, take to orders
    private void to_oredr() {
        Bundle bundle = new Bundle();
        bundle.putString("business", new Gson().toJson(SearchResulstsObj.getInstance()));
        OrderDetailsFragment.setInstance(null);
        //enter the details to fragment of the order details
        ChosenServiceFragment chosenServiceFragment = new ChosenServiceFragment();
        chosenServiceFragment.set_provider_id(id);
        chosenServiceFragment.setArguments(bundle);
        ((NavigetionLayout) getActivity()).initFragmentMain(/*ChosenServiceFragment.newInstance(getItemId(position))*/chosenServiceFragment, true);
    }

    //open dialog to read more of about
    private void openToReadMoreDialog() {
        vDialod = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        vDialod.requestWindowFeature(Window.FEATURE_NO_TITLE);
        vDialod.setContentView(R.layout.read_more);
        vDialod.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        CustomLightTextView txt = (CustomLightTextView) vDialod.findViewById(R.id.read_more_txt);
        CustomLightButton ok = (CustomLightButton) vDialod.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vDialod.dismiss();
            }
        });
        txt.setText(aboutText);
        vDialod.show();
    }
}
