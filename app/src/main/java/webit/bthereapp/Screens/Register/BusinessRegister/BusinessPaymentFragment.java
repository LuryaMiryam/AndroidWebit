package webit.bthereapp.Screens.Register.BusinessRegister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatDelegate;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SyncContactsRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.AddProviderDetails;
import webit.bthereapp.Entities.ProviderBuisnessDetailsObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderGeneralDetailsObj;
import webit.bthereapp.Entities.ProviderProfileObj;
import webit.bthereapp.Entities.objProviderAlertsSettings;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderServiceFragment;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.Dialogs.CreditCardFragment;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.Utils;


public class BusinessPaymentFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public BusinessPaymentFragment() {
        // Required empty public constructor
    }


    public static BusinessPaymentFragment instance;
    private CustomLightButton ok;
    private LinearLayout mCreditCardL;
    Button b;
    public static boolean is_back = false;

    public static BusinessPaymentFragment getInstance() {
        if (instance == null)
            instance = new BusinessPaymentFragment();
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
            return true;
        }
        return true;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusinessPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusinessPaymentFragment newInstance(String param1, String param2) {
        BusinessPaymentFragment fragment = new BusinessPaymentFragment();
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


        View view = inflater.inflate(R.layout.fragment_business_payment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mCreditCardL = (LinearLayout) view.findViewById(R.id.b_credit_ll);
        mCreditCardL.setOnClickListener(this);
        ok = (CustomLightButton) view.findViewById(R.id.ok);
        ok.setOnClickListener(this);

        b = (Button) view.findViewById(R.id.continue1);
        b.setOnClickListener(this);

        if (getActivity() instanceof MainActivity) {
            super.initFragmentTop(MainActivity.Num, getString(R.string.b_payment), 6, false);
        } else {
            super.initFragmentTop3(6, getString(R.string.b_payment), is_back);
            ok.setVisibility(View.VISIBLE);
            b.setVisibility(View.GONE);

        }
        is_back = false;
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok: {
                getActivity().onBackPressed();
                break;
            }
            case R.id.b_credit_ll:
                if (getActivity() instanceof MainActivity) {
                    //open credit card payment dialog
//                    CreditCardFragment creditCardFragment = new CreditCardFragment();
//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    fragmentManager.beginTransaction().add(R.id.container_main_bottom, creditCardFragment).addToBackStack(creditCardFragment.getClass().toString()).commit();
                }
                break;
            case R.id.continue1:
                b.setEnabled(false);
                //send to the function of register provider
                AddProviderDetails();
        }

    }

    private void AddProviderDetails() {

        Realm realm = Utils.getRealmInstance(getContext());

        UserRealm userRealm = realm.where(UserRealm.class).findFirst();

        SyncContactsRealm syncContactsRealm = realm.where(SyncContactsRealm.class).findFirst();
        if (syncContactsRealm != null) {
            AddProviderDetails.getInstance().setNvPhoneList(syncContactsRealm.getNvPhoneList_());
        }

        BusinessProfileRealm profileRealm = realm.where(BusinessProfileRealm.class).findFirst();
        if (profileRealm != null) {
            AddProviderDetails.getInstance().setObjProviderProfile(new ProviderProfileObj(profileRealm));
            ProviderDetailsObj.getInstance().setObjProviderProfile(new ProviderProfileObj(profileRealm));
        }

        AlertSettingsRealm alertSettingsRealm = realm.where(AlertSettingsRealm.class).findFirst();
        if (alertSettingsRealm != null) {
            AddProviderDetails.getInstance().setObjProviderAlertsSettings(new objProviderAlertsSettings(alertSettingsRealm));
            ProviderDetailsObj.getInstance().setObjProviderAlertsSettings(new objProviderAlertsSettings(alertSettingsRealm));
        }

        GeneralDetailsRealm generalDetailsRealm = realm.where(GeneralDetailsRealm.class).findFirst();
        if (generalDetailsRealm != null) {
            AddProviderDetails.getInstance().setObjProviderGeneralDetails(new ProviderGeneralDetailsObj(generalDetailsRealm));
            ProviderDetailsObj.getInstance().setObjProviderGeneralDetails(new ProviderGeneralDetailsObj(generalDetailsRealm));
        }

        ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();
        if (providerRealm != null) {
            AddProviderDetails.getInstance().setObjProviderBuisnessDetails(new ProviderBuisnessDetailsObj(providerRealm));
            ProviderDetailsObj.getInstance().setObjProviderBuisnessDetails(new ProviderBuisnessDetailsObj(providerRealm));
        }

        AddProviderDetails.getInstance().setiUserId(userRealm.getUserID());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(AddProviderDetails.getInstance().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.AddProviderAllDetails(getContext(), jsonObject, new IExecutable<Double>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(Double id) {
                        if (id != null && id != 0) {
                            ConnectionUtils.which_calendar = true;
                            if (((MainActivity) getActivity()).getFrom() == 1) {
                                OrderServiceFragment.getInstance().enter_to_existed_customer();
                                (getActivity()).finish();
                            } else
                                startActivity(new Intent(getActivity(), ExistsSuplierActivity.class));
                            RegisterUserFragment.removeInstance();
                            BusinessDetailsFragment.removeInstance();
                            BusinessGeneralData.removeInstance();
                            AlertsFragment.removeInstance();
                            BusinessPaymentFragment.removeInstance();
                            BusinessProfileFragment.removeInstance();
                            ContactListFragment.removeInstance();
                            getActivity().finish();
                        }
                        else {
                            Toast.makeText(getContext(), getResources().getString(R.string.error_in_register_to_provider), Toast.LENGTH_SHORT).show();
                            b.setEnabled(true);
                        }

                    }
                }
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        b.setEnabled(true);
    }
}
