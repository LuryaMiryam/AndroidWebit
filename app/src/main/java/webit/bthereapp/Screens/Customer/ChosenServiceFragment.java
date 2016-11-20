package webit.bthereapp.Screens.Customer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.CouponObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Utils.CalendarUtil;


public class ChosenServiceFragment extends BaseFragment {
    private static final String id = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private long providerId = -1;
    static FrameLayout mTopFL;
    String str = "";
    private View view;
    private int id_p;


    public ChosenServiceFragment() {
        // Required empty public constructor
    }

    public static ChosenServiceFragment instance;

    public static ChosenServiceFragment getInstance() {
        if (instance == null)
            instance = new ChosenServiceFragment();
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // to know not to show the autocmplete list in search result when come back to there
            ((NavigetionLayout) getActivity()).leftSearchResult = true;
            if (getArguments() != null) {
                try {
                    str = getArguments().getString("business", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (view == null) {
                view = inflater.inflate(R.layout.fragment_chosen, container, false);
                OrderDetailsFragment.setInstance(null);
                mTopFL = (FrameLayout) view.findViewById(R.id.service_chosen);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                if (!str.equals("")) {
                    OrderFragment orderFragment = new OrderFragment();
                    ChosenPicFragment chosenPicFragment = new ChosenPicFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("bStr", str);
                    orderFragment.setArguments(bundle);
                    orderFragment.set_p_id(id_p);
                    chosenPicFragment.setArguments(bundle);
                    beginTransaction.replace(R.id.order_service, orderFragment);
                    beginTransaction.replace(R.id.service_chosen, chosenPicFragment);
                    beginTransaction.commitAllowingStateLoss();
                }
            }
        return view;
    }

    public void set_provider_id(int id) {
        id_p = id;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }


}


