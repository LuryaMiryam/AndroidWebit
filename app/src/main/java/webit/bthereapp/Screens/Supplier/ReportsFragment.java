package  webit.bthereapp.Screens.Supplier;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;


public class ReportsFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button mToAllReportsB;
    private LinearLayout mOperationalUtilizationR_l, pay_in_app_l, qweues_l, customers_l;
private CustomLightTextView operational_utilization_txt,customers_txt,qweues_txt,pay_in_app_txt;

    public static ReportsFragment instance;

    public static ReportsFragment getInstance() {
        if (instance == null)
            instance = new ReportsFragment();
        return instance;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    public static void removeInstance() {
        instance = null;
    }

    public ReportsFragment() {
        // Required empty public constructor
    }


    public static ReportsFragment newInstance(String param1, String param2) {
        ReportsFragment fragment = new ReportsFragment();
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
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        mToAllReportsB = (Button) view.findViewById(R.id.to_all_reports);
        mToAllReportsB.setOnClickListener(this);
        mOperationalUtilizationR_l = (LinearLayout) view.findViewById(R.id.operational_utilization_L);
        mOperationalUtilizationR_l.setOnClickListener(this);

        customers_l = (LinearLayout) view.findViewById(R.id.customers_l);
        customers_l.setOnClickListener(this);

        qweues_l = (LinearLayout) view.findViewById(R.id.appointments_l);
        qweues_l.setOnClickListener(this);

        pay_in_app_l = (LinearLayout) view.findViewById(R.id.pay_in_app_l);
        pay_in_app_l.setOnClickListener(this);

        operational_utilization_txt= (CustomLightTextView) view.findViewById(R.id.operational_utilization_txt);
        customers_txt= (CustomLightTextView) view.findViewById(R.id.customers_txt);
        qweues_txt= (CustomLightTextView) view.findViewById(R.id.qweues_txt);
        pay_in_app_txt= (CustomLightTextView) view.findViewById(R.id.pay_in_app_txt);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.operational_utilization_L: {
                ((ExistsSuplierActivity) getActivity()).initFragmentMain(ReportOperationalUtilizationFragment.newInstance(operational_utilization_txt.getText().toString(), ""), true);
                break;
            }
            case R.id.customers_l: {
                ((ExistsSuplierActivity) getActivity()).initFragmentMain(ReportOperationalUtilizationFragment.newInstance(customers_txt.getText().toString(), ""), true);
                break;
            }
            case R.id.appointments_l: {
                ((ExistsSuplierActivity) getActivity()).initFragmentMain(ReportOperationalUtilizationFragment.newInstance(qweues_txt.getText().toString(), ""), true);
                break;
            }
            case R.id.pay_in_app_l: {
                ((ExistsSuplierActivity) getActivity()).initFragmentMain(ReportOperationalUtilizationFragment.newInstance(pay_in_app_txt.getText().toString(), ""), true);
                break;
            }
            case R.id.to_all_reports:

                FragmentManager fm = getFragmentManager();
                ReportsDialogFragment newFragment = new ReportsDialogFragment();
                newFragment.show(fm, "dialog");
                break;

        }
    }
}
