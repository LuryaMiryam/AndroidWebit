package  webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomBoldTextView;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderServiceDetailsObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.FragmentTopUser;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CancelQweueDialogFromCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CancelQweueDialogFromCustomerFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OrderDetailsObj orderDetailsObj;


    private CustomLightButton cancel_ok;
    private ImageButton close;
    private CustomBoldTextView s_name;
    private CustomLightTextView service;
    private java.util.Date date;
    private CustomLightTextView date_;
    private CustomLightTextView hour;
    private Calendar calendar = Calendar.getInstance();

    public CancelQweueDialogFromCustomerFragment() {
        // Required empty public constructor
    }

    public CancelQweueDialogFromCustomerFragment(OrderDetailsObj orderDetailsObj) {
        // Required empty public constructor
        this.orderDetailsObj = orderDetailsObj;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CancelQweueDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CancelQweueDialogFromCustomerFragment newInstance(String param1, String param2) {
        CancelQweueDialogFromCustomerFragment fragment = new CancelQweueDialogFromCustomerFragment();
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
        setStyle(STYLE_NO_TITLE, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_cancel_qweue_from_customer_dialog, container, false);
            cancel_ok = (CustomLightButton) view.findViewById(R.id.cancel_ok);
            cancel_ok.setOnClickListener(this);

            close = (ImageButton) view.findViewById(R.id.close);
            close.setOnClickListener(this);

            s_name = (CustomBoldTextView) view.findViewById(R.id.s_name);
            s_name.setText(orderDetailsObj.getNvSupplierName());

            service = (CustomLightTextView) view.findViewById(R.id.service);
            date_ = (CustomLightTextView) view.findViewById(R.id.date);
            hour = (CustomLightTextView) view.findViewById(R.id.hour);

            date = ConnectionUtils.convertJsonDate_2(orderDetailsObj.getDtDateOrder());
            calendar.setTime(date);
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy");
            date_.setText(format.format(calendar.getTime()));
            SimpleDateFormat mFormatter_2 = new SimpleDateFormat("HH:mm");

            hour.setText(mFormatter_2.format(calendar.getTime()));

            String s = "";
            for (ProviderServiceDetailsObj providerServiceDetailsObj : orderDetailsObj.getObjProviderServiceDetails())
                if (s.equals(""))
                    s += providerServiceDetailsObj.getNvServiceName();
                else
                    s += (", " + providerServiceDetailsObj.getNvServiceName());

            service.setText(s);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //if the user check to cancel the queue
            case R.id.cancel_ok: {
                CancelOrder();
                break;
            }
            case R.id.close: {
                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
                dismiss();
                break;
            }
        }
    }

    private void CancelOrder() {
        String jsonString = "{\"iCoordinatedServiceId\":" + orderDetailsObj.getiCoordinatedServiceId() + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.CancelOrder(getContext(), jsonObject, new IExecutable<Double>() {
                    //            ProviderBL.AddProviderUser(mContext,jsonObject,new IExecutable<Double>()
                    @Override
                    public void onExecute(Double id) {
                        if (id != null) {
                            if (id!=0) {
                                //close all the opened dialogs

                                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                                if (prev != null) {
                                    DialogFragment df = (DialogFragment) prev;
                                    df.dismiss();
                                }
                                Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
                                if (prev2 != null) {
                                    DialogFragment df = (DialogFragment) prev2;
                                    df.dismiss();
                                }
                                Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                                if (prev3 != null) {
                                    DialogFragment df = (DialogFragment) prev3;
                                    df.dismiss();
                                }
                                Toast.makeText(getContext(), R.string.qweue_canceled, Toast.LENGTH_LONG).show();
                                FragmentTopUser.removeInstance();
                                ((NavigetionLayout) getActivity()).initFragmentMain(FragmentTopUser.getInstance(), true);
                                dismiss();
                            } else {
                                Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag("dialog");
                                if (prev != null) {
                                    DialogFragment df = (DialogFragment) prev;
                                    df.dismiss();
                                }
                                Fragment prev2 = getActivity().getSupportFragmentManager().findFragmentByTag("UpdateAppointmentFromCustomer");
                                if (prev2 != null) {
                                    DialogFragment df = (DialogFragment) prev2;
                                    df.dismiss();
                                }
                                Fragment prev3 = getActivity().getSupportFragmentManager().findFragmentByTag("dialogMyQweues");
                                if (prev3 != null) {
                                    DialogFragment df = (DialogFragment) prev3;
                                    df.dismiss();
                                }
                                Toast.makeText(getContext(), R.string.general_error_msg, Toast.LENGTH_LONG).show();
                                dismiss();
                            }
                        }
                    }
                }, new IExecutable<Integer>() {
                    @Override
                    public void onExecute(Integer integer) {

                    }
                }
        );
    }
}
