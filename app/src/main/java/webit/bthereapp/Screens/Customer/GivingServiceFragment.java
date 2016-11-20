package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;


public class GivingServiceFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ListView lv;
    Context context;
    ArrayList prgmName;
    private ArrayList<SearchResulstsObj> sales = new ArrayList<>();


    public static GivingServiceFragment instance;

    public static GivingServiceFragment getInstance() {
        if (instance == null)
            instance = new GivingServiceFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }


    public GivingServiceFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GivingServiceFragment newInstance(String param1, String param2) {
        GivingServiceFragment fragment = new GivingServiceFragment();
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
        View view = inflater.inflate(R.layout.fragment_giving_service, container, false);
        OrderDetailsFragment.setInstance(null);
        lv = (ListView) view.findViewById(R.id.my_giving_service_listView);
        lv.setAdapter(new GivingServiceAdapter(getContext(), sales));
        return view;
    }


    public void set_sales_array(ArrayList<SearchResulstsObj> sales_) {
        sales = sales_;
    }
}
