package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.YesOrNo_White;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.CustomerAlertsSettingsObj;
import webit.bthereapp.Entities.ProviderAlertsSettingsObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Utils.Utils;

/**
 * to handle interaction events.
 * Use the {@link AlertsToCustomer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlertsToCustomer extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CustomLightButton alerts_continue;
    private YesOrNo_White al_c_90_yn, al_c_20_yn, al_c_ok_b_yn, al_c_ok_wait_yn, al_c_new_yn;

    public AlertsToCustomer() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static AlertsToCustomer newInstance(String param1, String param2) {
        AlertsToCustomer fragment = new AlertsToCustomer();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alerts_to_customer, container, false);

        al_c_90_yn = (YesOrNo_White) view.findViewById(R.id.al_c_90_yn);
        al_c_20_yn = (YesOrNo_White) view.findViewById(R.id.al_c_20_yn);
        al_c_ok_b_yn = (YesOrNo_White) view.findViewById(R.id.al_c_ok_b_yn);
        al_c_ok_wait_yn = (YesOrNo_White) view.findViewById(R.id.al_c_ok_wait_yn);
        al_c_new_yn = (YesOrNo_White) view.findViewById(R.id.al_c_new_yn);

        al_c_90_yn.change_status(true);
        al_c_20_yn.change_status(true);
        al_c_ok_b_yn.change_status(true);
        al_c_ok_wait_yn.change_status(true);
        al_c_new_yn.change_status(true);

        GetAlertSettingsForCustomer();

        alerts_continue = (CustomLightButton) view.findViewById(R.id.alerts_continue);
        alerts_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerAlertsSettingsObj.getInstance().setB20minutesBeforeService(al_c_20_yn.getYes_no_state_b());
                CustomerAlertsSettingsObj.getInstance().setB90thAlertTime(al_c_90_yn.getYes_no_state_b());
                CustomerAlertsSettingsObj.getInstance().setbOrderInWaiting(al_c_ok_wait_yn.getYes_no_state_b());
                CustomerAlertsSettingsObj.getInstance().setbPermissionsFromBusinesses(al_c_ok_b_yn.getYes_no_state_b());
                CustomerAlertsSettingsObj.getInstance().setbUpdatesAndNews(al_c_new_yn.getYes_no_state_b());

                Realm realm = Utils.getRealmInstance(getContext());
                UserRealm userRealm = realm.where(UserRealm.class).findFirst();
                CustomerAlertsSettingsObj.getInstance().setiCustomerUserId((int) userRealm.getUserID());
                AddAlertSettingsForCustomer();
            }
        });

        return view;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    //get the definitions
    private void GetAlertSettingsForCustomer() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        String jsonString = "{\"iUserId\":" + userRealm.getUserID() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetAlertSettingsForCustomer(getContext(), jsonObject, new IExecutable<CustomerAlertsSettingsObj>() {
                    @Override
                    public void onExecute(CustomerAlertsSettingsObj customerAlertsSettingsObj) {
                        if (customerAlertsSettingsObj != null) {
                            //put the previous state in the fragment
//                            Toast.makeText(getContext(), getResources().getString(R.string.success_in_alerts_get), Toast.LENGTH_LONG).show();

                            CustomerAlertsSettingsObj.setInstance(customerAlertsSettingsObj);

                            if (customerAlertsSettingsObj.isB20minutesBeforeService())
                                al_c_20_yn.change_status(true);
                            else
                                al_c_20_yn.change_status(false);

                            if (customerAlertsSettingsObj.isB90thAlertTime())
                                al_c_90_yn.change_status(true);
                            else
                                al_c_90_yn.change_status(false);

                            if (customerAlertsSettingsObj.isbOrderInWaiting())
                                al_c_ok_wait_yn.change_status(true);
                            else
                                al_c_ok_wait_yn.change_status(false);

                            if (customerAlertsSettingsObj.isbPermissionsFromBusinesses())
                                al_c_ok_b_yn.change_status(true);
                            else
                                al_c_ok_b_yn.change_status(false);

                            if (customerAlertsSettingsObj.isbUpdatesAndNews())
                                al_c_new_yn.change_status(true);
                            else
                                al_c_new_yn.change_status(false);

                        } else {
                            CustomerAlertsSettingsObj.setInstance(null);
                            Toast.makeText(getContext(), getResources().getString(R.string.error_in_alerts), Toast.LENGTH_LONG).show();

                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == -1 || integer == -2 /*|| integer == -3*/) {
                            Toast.makeText(getContext(), getResources().getString(R.string.error_in_alerts), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }
//save the definitions
    private void AddAlertSettingsForCustomer() {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        int id = (int) userRealm.getUserID();
        String jsonString = "{\"customerAlertsSettingsObj\":" + CustomerAlertsSettingsObj.getInstance().toString() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.AddAlertSettingsForCustomer(getContext(), jsonObject, new IExecutable<Double>() {
                    @Override
                    public void onExecute(Double d) {
                        if (d != 0) {
                            Toast.makeText(getContext(), getResources().getString(R.string.success_in_alerts), Toast.LENGTH_LONG).show();
                            getActivity().onBackPressed();
                        } else {
                            Toast.makeText(getContext(), getResources().getString(R.string.error_in_alerts_in), Toast.LENGTH_LONG).show();
                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {
                        if (integer == -1 || integer == -2) {
                            Toast.makeText(getContext(), getResources().getString(R.string.error_in_alerts_in), Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

}
