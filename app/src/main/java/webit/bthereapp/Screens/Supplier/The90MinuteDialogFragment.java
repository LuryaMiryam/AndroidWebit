package  webit.bthereapp.Screens.Supplier;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.DM.AlertSettingsRealm;
import webit.bthereapp.DM.BusinessProfileRealm;
import webit.bthereapp.DM.GeneralDetailsRealm;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;
import webit.bthereapp.Utils.Utils;


/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link The90MinuteDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class The90MinuteDialogFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RelativeLayout rr;
    private ListView listView;
    private List<Sale90> sale_90_list = new ArrayList<>();
    private ArrayList<CouponObj> sales=new ArrayList<CouponObj>();
    private CouponObj couponObj;
    private Sale90 sale90;
    private LinearLayout mAddL;
    private int a = 0;
    private ImageButton close;
//    private ArrayList<CouponObj> sales;

    public The90MinuteDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment The90MinuteDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static The90MinuteDialogFragment newInstance(String param1, String param2) {
        The90MinuteDialogFragment fragment = new The90MinuteDialogFragment();
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
        View view = inflater.inflate(R.layout.fragment_the90_minute_dialog, container, false);
        close = (ImageButton) view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mAddL = (LinearLayout) view.findViewById(R.id.cus);
        mAddL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentManager fm = getActivity().getSupportFragmentManager();
                AddNewOperation addNewOperation = new AddNewOperation();
                addNewOperation.show(fm, "dialog");
            }
        });

        rr = (RelativeLayout) view.findViewById(R.id.rr);

        Realm realm = Utils.getRealmInstance(getContext());
        ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();
        couponObj=new CouponObj();
        couponObj.setdDate("26.1.16");
//       couponObj.setNvCouponName(getString(R.string.coupon1));
        if (providerRealm != null)
            couponObj.setiSupplierServiceId(providerRealm.getiBuisnessId());
        sales.add(couponObj);


        couponObj=new CouponObj();
        couponObj.setdDate("26.1.16");
//       couponObj.setNvCouponName(getString(R.string.coupon2));
        if (providerRealm != null)
            couponObj.setiSupplierServiceId(providerRealm.getiBuisnessId());
        sales.add(couponObj);

        couponObj=new CouponObj();
        couponObj.setdDate("2.2.16");
//       couponObj.setNvCouponName(getString(R.string.coupon3));
        if (providerRealm != null)
            couponObj.setiSupplierServiceId(providerRealm.getiBuisnessId());
        sales.add(couponObj);

        couponObj=new CouponObj();
        couponObj.setdDate("26.1.16");
//       couponObj.setNvCouponName(getString(R.string.coupon1));
        if (providerRealm != null)
            couponObj.setiSupplierServiceId(providerRealm.getiBuisnessId());
        sales.add(couponObj);

        couponObj=new CouponObj();
        couponObj.setdDate("3.2.16");
//       couponObj.setNvCouponName(getString(R.string.coupon2));
        if (providerRealm != null)
            couponObj.setiSupplierServiceId(providerRealm.getiBuisnessId());
        sales.add(couponObj);

        listView = (ListView) view.findViewById(R.id.qweues_list);

        for (CouponObj couponObj : sales)
            a++;

        listView = (ListView) view.findViewById(R.id.the_90_minute_list);
        GetCouponsForProvider();
        return view;
    }

    private void GetCouponsForProvider() {
        Realm realm = Utils.getRealmInstance(getContext());
        ProviderRealm providerRealm = realm.where(ProviderRealm.class).findFirst();
        double d;
        if (providerRealm != null)
            d = providerRealm.getiBuisnessId();
        else
            d = 0;
        String jsonString = "{\"iProviderId\":" + d + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetCouponsForProvider(getContext(), jsonObject, new IExecutable<ArrayList<CouponObj>>() {
                    @Override
                    public void onExecute(ArrayList<CouponObj> list) {

                        if (list != null) {
                            sales=new ArrayList<CouponObj>(list);
                        } else {
                            Toast.makeText(getContext(),getResources().getString(R.string.there_are_no_coupons_to_this_provider),Toast.LENGTH_LONG).show();
                        }

                        ViewGroup.LayoutParams params_ = rr.getLayoutParams();
                        ViewGroup.LayoutParams params = listView.getLayoutParams();
                        TheCouponObjListAdapter adapter = new TheCouponObjListAdapter(getActivity(), R.layout.qweues_to_cus_item, sales);
                        params.height = a * (adapter.getA()) + params_.height;
                        listView.setLayoutParams(params);
                        listView.requestLayout();

                        listView.setDivider(null);

                        listView.setAdapter(adapter);
                    }
                }
        );
    }

}
