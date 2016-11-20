package webit.bthereapp.Screens.Register.BusinessRegister;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.CustomSpinner;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.YesOrNo_White;
import webit.bthereapp.DM.AlertSettingsRealm;

import webit.bthereapp.DM.RealmInt;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.objProviderAlertsSettings;
import webit.bthereapp.Intreface.AddOnClickListener;

import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AlertsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertsFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static boolean is_back = false;
    private CustomLightButton ok;
    private int mState1 = 0, mState2 = 0, mState3 = 0, mState4 = 0;
    private RelativeLayout mMeetRl, mCustRl, mUpdateRl;
    private CustomSpinner mMeet, mCust1, mUpdate1, mCust2, mUpdate2;
    private YesOrNo_White yesOrNo1, yesOrNo2, yesOrNo3, yesOrNo4;
    private Button mContinueBtn;
    private View view;

    public AlertsFragment() {
    }

    public static AlertsFragment instance;

    public static AlertsFragment getInstance() {
        if (instance == null)
            instance = new AlertsFragment();
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


    // TODO: Rename and change types and number of parameters
    public static AlertsFragment newInstance(String param1, String param2) {
        AlertsFragment fragment = new AlertsFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getActivity() instanceof MainActivity) {
            HideFragmentBottom();
        }

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_alerts, container, false);
            mMeet = (CustomSpinner) view.findViewById(R.id.al_meet);
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            mCust1 = (CustomSpinner) view.findViewById(R.id.al_cus_1);
            mCust2 = (CustomSpinner) view.findViewById(R.id.al_cus_2);
            mUpdate1 = (CustomSpinner) view.findViewById(R.id.al_up_1);
            mUpdate2 = (CustomSpinner) view.findViewById(R.id.al_up_2);

            ok = (CustomLightButton) view.findViewById(R.id.ok);
            ok.setOnClickListener(this);
//            alert_space = (CustomLightButton) view.findViewById(R.id.alerts__);
            mMeetRl = (RelativeLayout) view.findViewById(R.id.al_meet_en);
            mMeetRl.setOnClickListener(this);
            mCustRl = (RelativeLayout) view.findViewById(R.id.al_cus_en);
            mCustRl.setOnClickListener(this);
            mUpdateRl = (RelativeLayout) view.findViewById(R.id.al_up_en);
            mUpdateRl.setOnClickListener(this);

            mContinueBtn = (Button) view.findViewById(R.id.alerts_continue);
            mContinueBtn.setOnClickListener(this);

            yesOrNo1 = (YesOrNo_White) view.findViewById(R.id.al_yn1);
            yesOrNo2 = (YesOrNo_White) view.findViewById(R.id.al_yn2);
            yesOrNo3 = (YesOrNo_White) view.findViewById(R.id.al_yn3);
            yesOrNo4 = (YesOrNo_White) view.findViewById(R.id.al_yn4);

            //add function that close the option when the user click on x
            setAddOnClicks();

            mMeet.setOnClickListener(this);
            mCust1.setOnClickListener(this);
            mUpdate1.setOnClickListener(this);
            mCust2.setOnClickListener(this);
            mUpdate2.setOnClickListener(this);

            yesOrNo1.setOnClickListener(this);
            yesOrNo3.setOnClickListener(this);
            yesOrNo4.setOnClickListener(this);
            yesOrNo1.change_status(true);
            yesOrNo2.change_status(true);
            yesOrNo3.change_status(true);
            yesOrNo4.change_status(true);

            //get the details if the user stopped in middle of the registion
            Realm realm = Utils.getRealmInstance(getContext());

            AlertSettingsRealm alertSettingsRealm = realm.where(AlertSettingsRealm.class).findFirst();

            if (alertSettingsRealm != null) {
                if (alertSettingsRealm.getiIncomingAlertsId() != null) {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    for (RealmInt results : alertSettingsRealm.getiIncomingAlertsId()) {
                        arrayList.add(results.getVal());
                    }
                    yesOrNo1.change_status(true);
                    mMeet.setVisibility(View.VISIBLE);
                    mState1 = 1;
                    mMeet.set_old_array(arrayList);
                } else
                    yesOrNo1.change_status(false);


                if (alertSettingsRealm.isB10minutesBeforeService())
                    yesOrNo2.change_status(true);
                else
                    yesOrNo2.change_status(false);

                if (alertSettingsRealm.getiCustomerResvId().size() != 0 || alertSettingsRealm.getiCustomerResvFreqId() != 0) {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    for (RealmInt results : alertSettingsRealm.getiCustomerResvId()) {
                        arrayList.add(results.getVal());
                    }
                    yesOrNo3.change_status(true);
                    mCust1.setVisibility(View.VISIBLE);
                    mCust2.setVisibility(View.VISIBLE);
                    mState3 = 1;
                    mCust1.set_old_array(arrayList);
                } else
                    yesOrNo3.change_status(false);
                mCust2.set_old(alertSettingsRealm.getiCustomerResvFreqId());

                if (alertSettingsRealm.getiCustomerEventsId().size() != 0 || alertSettingsRealm.getiCustomerEventsFreqId() != 0) {
                    yesOrNo4.change_status(true);
                    mUpdate1.setVisibility(View.VISIBLE);
                    mUpdate2.setVisibility(View.VISIBLE);
                    mState4 = 1;
                } else
                    yesOrNo4.change_status(false);
                if (alertSettingsRealm.getiCustomerEventsId() != null) {
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    for (RealmInt results : alertSettingsRealm.getiCustomerEventsId()) {
                        arrayList.add(results.getVal());
                    }
                    mUpdate1.set_old_array(arrayList);
                    mUpdate2.set_old(alertSettingsRealm.getiCustomerEventsFreqId());
                }

            }
        }
        if (getActivity() instanceof MainActivity) {
            if (is_back)
                super.initFragmentTop(MainActivity.Num, getString(R.string.alerts), 3, true);
            else
                super.initFragmentTop(MainActivity.Num, getString(R.string.alerts), 3, false);
        } else {
            super.initFragmentTop3(3, getString(R.string.alerts), is_back);
            ok.setVisibility(View.VISIBLE);
            mContinueBtn.setVisibility(View.GONE);
        }


        is_back = false;
        return view;

    }

    //function that close the option when the user click on x
    private void setAddOnClicks() {

        yesOrNo1.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mMeet.setVisibility(View.VISIBLE);
                mState1 = 1;
            }
        });
        yesOrNo1.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mMeet.setVisibility(View.GONE);
                mState1 = 0;
            }
        });
        yesOrNo3.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mCust1.setVisibility(View.VISIBLE);
                mCust2.setVisibility(View.VISIBLE);
                mState3 = 1;
            }
        });
        yesOrNo2.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                objProviderAlertsSettings.getInstance().setB10minutesBeforeService(false);
            }
        });

        yesOrNo2.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                objProviderAlertsSettings.getInstance().setB10minutesBeforeService(true);

            }
        });
        yesOrNo3.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mCust1.setVisibility(View.GONE);
                mCust2.setVisibility(View.GONE);
                mState3 = 0;
            }
        });

        yesOrNo4.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mUpdate1.setVisibility(View.VISIBLE);
                mUpdate2.setVisibility(View.VISIBLE);
                mState4 = 1;

            }
        });
        yesOrNo4.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mUpdate1.setVisibility(View.GONE);
                mUpdate2.setVisibility(View.GONE);
                mState4 = 0;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: {
                getActivity().onBackPressed();
                break;
            }
            case R.id.al_meet_en: {
                if (mState1 == 0) {
                    mMeet.setVisibility(View.VISIBLE);
                    yesOrNo1.change_status(true);
                    mState1 = 1;

                } else {
                    mMeet.setVisibility(View.GONE);
                    yesOrNo1.change_status(false);
                    mState1 = 0;
                }
                break;
            }
            case R.id.al_up_en: {
                if (mState4 == 0) {
                    mUpdate1.setVisibility(View.VISIBLE);
                    mUpdate2.setVisibility(View.VISIBLE);
                    yesOrNo4.change_status(true);

                    mState4 = 1;
                } else {
                    mUpdate1.setVisibility(View.GONE);
                    mUpdate2.setVisibility(View.GONE);
                    yesOrNo4.change_status(false);

                    mState4 = 0;
                }
                break;
            }


            case R.id.al_cus_en: {
                if (mState2 == 0) {
                    mCust1.setVisibility(View.VISIBLE);
                    mCust2.setVisibility(View.VISIBLE);
                    yesOrNo3.change_status(true);
                    mState2 = 1;
                } else {
                    mCust1.setVisibility(View.GONE);
                    mCust2.setVisibility(View.GONE);
                    yesOrNo3.change_status(false);
                    mState2 = 0;
                }
                break;
            }

            //save the details when continue to the next page
            case R.id.alerts_continue: {
//enter the data from the fragment to the object
                mContinueBtn.setEnabled(false);

                if (yesOrNo2.getYes_no_state() != 0)
                    objProviderAlertsSettings.getInstance().setB10minutesBeforeService(true);
                else
                    objProviderAlertsSettings.getInstance().setB10minutesBeforeService(false);

                if (yesOrNo1.getYes_no_state() != 0)
                    objProviderAlertsSettings.getInstance().setiIncomingAlertsId(mMeet.get_many_choose());
                if (yesOrNo3.getYes_no_state() != 0) {
                    objProviderAlertsSettings.getInstance().setiCustomerResvId(mCust1.get_many_choose());
                    objProviderAlertsSettings.getInstance().setiCustomerResvFreqId(mCust2.get_choose());
                }
                if (yesOrNo4.getYes_no_state() != 0) {
                    objProviderAlertsSettings.getInstance().setiCustomerEventsId(mUpdate1.get_many_choose());
                    objProviderAlertsSettings.getInstance().setiCustomerEventsFreqId(mUpdate2.get_choose());
                }
                Realm realm = Utils.getRealmInstance(getContext());
                UserRealm userRealm = realm.where(UserRealm.class).findFirst();

                double d;
                if (userRealm != null) {
                    d = userRealm.getUserID();
                } else
                    d = 0;

                objProviderAlertsSettings.getInstance().setiProviderId(d);

                realm.beginTransaction();
                AlertSettingsRealm detailsRealm = new AlertSettingsRealm(objProviderAlertsSettings.getInstance());
                realm.where(AlertSettingsRealm.class).findAll().deleteAllFromRealm();
                realm.copyToRealm(detailsRealm);
                realm.commitTransaction();


                if (!ConnectionUtils.if_server)
                    initFragmentMain(BusinessProfileFragment.getInstance(), true, true, 4);

                else {
                    initFragmentMain(BusinessProfileFragment.getInstance(), true, true, 4);

                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mContinueBtn.setEnabled(true);
    }

    //scroll the page down if the buttom spinners clicked
    public void scroll_down() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).scroll((int) (mContinueBtn.getY()));
    }

    //scroll the page down if the up spinners clicked
    public void scroll_up() {
        if (getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).scroll((int) (mMeetRl.getY()));
    }
}


