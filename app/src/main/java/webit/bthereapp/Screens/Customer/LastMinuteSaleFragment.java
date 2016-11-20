package webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.R;


public class LastMinuteSaleFragment extends DialogFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView mSalesLv;
    private ArrayList<CouponObj> couponList = new ArrayList<>();
    AdapterLastMinuteSale mAdapterLastMinuteSale;
    ImageButton closeIB;

    public LastMinuteSaleFragment() {
        // Required empty public constructor
    }

    public static LastMinuteSaleFragment instance;

    public static LastMinuteSaleFragment getInstance() {
        if (instance == null)
            instance = new LastMinuteSaleFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    public static LastMinuteSaleFragment newInstance(String param1, String param2) {
        LastMinuteSaleFragment fragment = new LastMinuteSaleFragment();
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
        View view = inflater.inflate(R.layout.fragment_last_minute_sale, container, false);
        closeIB = (ImageButton) view.findViewById(R.id.close_last_minute_sales);
        closeIB.setOnClickListener(this);
        mSalesLv = (ListView) view.findViewById(R.id.sales_list);
        mAdapterLastMinuteSale = new AdapterLastMinuteSale(getActivity(),couponList);
        mSalesLv.setAdapter(mAdapterLastMinuteSale);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.close_last_minute_sales:
                getActivity().onBackPressed();
                break;
        }
    }

    public void setCouponList(ArrayList<CouponObj> couponList_) {
        couponList = couponList_;
    }
}
