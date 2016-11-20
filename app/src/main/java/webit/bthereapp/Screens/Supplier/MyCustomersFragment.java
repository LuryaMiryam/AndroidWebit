package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.CustomerObj;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MyCustomersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCustomersFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private LinearLayout rr;
    private ListView listView;

    private LinearLayout add_customer;
    private CustomLightEditText search_linek, et;
    private ArrayList<CustomerObj> customers_list;
    public static MyCustomersFragment instance;

    public static MyCustomersFragment getInstance() {
        if (instance == null)
            instance = new MyCustomersFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    public MyCustomersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCustomersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCustomersFragment newInstance(String param1, String param2) {
        MyCustomersFragment fragment = new MyCustomersFragment();
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
        View view = inflater.inflate(R.layout.fragment_my_customers, container, false);

        customers_list = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.customers_list);

        add_customer = (LinearLayout) view.findViewById(R.id.add_customer);
        add_customer.setOnClickListener(this);

        if (ProviderDetailsObj.getInstance().getObjCustomer()==null || ProviderDetailsObj.getInstance().getObjCustomer().size() == 0){

            customers_list = new ArrayList<>();
            CustomerObj customerObj = new CustomerObj();
            UserObj userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("יסמין גנזי");
            customerObj.getUserObj().setNvLastName("בן לולו");
            customers_list.add(customerObj);

            customerObj = new CustomerObj();
            userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("קארין");
            customerObj.getUserObj().setNvLastName("אופיר");
            customers_list.add(customerObj);

            customerObj = new CustomerObj();
            userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("משה");
            customerObj.getUserObj().setNvLastName("סבן");
            customers_list.add(customerObj);

            customerObj = new CustomerObj();
            userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("לירון");
            customerObj.getUserObj().setNvLastName("חזיז");
            customers_list.add(customerObj);


            customerObj = new CustomerObj();
            userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("דודו");
            customerObj.getUserObj().setNvLastName("טסה");
            customers_list.add(customerObj);

            customerObj = new CustomerObj();
            userObj = new UserObj();
            customerObj.setUserObj(userObj);
            customerObj.getUserObj().setNvFirstName("חיה");
            customerObj.getUserObj().setNvLastName("בן שושן");
            customers_list.add(customerObj);

        }
        else
            customers_list = ProviderDetailsObj.getInstance().getObjCustomer();

        listView.setDivider(null);

        int a = 0;


        CusInMyCustomersAdapter adapter = new CusInMyCustomersAdapter(getActivity(), R.layout.customer_in_list, customers_list);
        listView.setAdapter(adapter);

        et = (CustomLightEditText) view.findViewById(R.id.et);
        search_linek = (CustomLightEditText) view.findViewById(R.id.search_linek);

        rr = (LinearLayout) view.findViewById(R.id.rr);
        search_linek.clearFocus();


        rr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et.requestFocus();
                hideKeyboard(v);
                search_linek.clearFocus();
            }
        });


        return view;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_customer: {
                FragmentManager fm = getFragmentManager();
                AddNewCustomerDialogFragment newFragment = new AddNewCustomerDialogFragment();
                newFragment.show(fm, "dialog");
                break;
            }
        }
    }
}
