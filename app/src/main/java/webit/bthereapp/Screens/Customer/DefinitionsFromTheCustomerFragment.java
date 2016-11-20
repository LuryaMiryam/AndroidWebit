package webit.bthereapp.Screens.Customer;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessDetailsFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessPaymentFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessProfileFragment;
import webit.bthereapp.Screens.Register.BusinessRegister.ContactListFragment;
import webit.bthereapp.Screens.Register.Dialogs.PaymentDialogeFragment;
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;
import webit.bthereapp.Screens.Supplier.LanguagesToCustomerFragment;

//import webit.bthereapp.Screens.Register.MainActivity.MainActivityToEdit;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DefinitionsFromTheCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinitionsFromTheCustomerFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] definitions_txt;
    private View view;
    private String[] definitions_number = {"1", "2", "3", "4"};


    public DefinitionsFromTheCustomerFragment() {
        // Required empty public constructor
    }


    public static DefinitionsFromTheCustomerFragment instance;

    public static DefinitionsFromTheCustomerFragment getInstance() {
        if (instance == null)
            instance = new DefinitionsFromTheCustomerFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DefinitionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefinitionsFromTheCustomerFragment newInstance(  String param1, String param2) {
        DefinitionsFromTheCustomerFragment fragment = new DefinitionsFromTheCustomerFragment();
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
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_definitions_from_the_customer, container, false);
            definitions_txt = getResources().getStringArray(R.array.definitions_from_the_customer_txt);

            final GridView gridview = (GridView) view.findViewById(R.id.definitions);
            gridview.setAdapter(new TextAdapter(view.getContext()));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    Fragment fragment = null;
                    switch (position) {
                        case 0:
                            //user details
                            fragment = new RegisterUser_To_Existed_Customer_Fragment();
                            ((NavigetionLayout) getActivity()).initFragmentMain(fragment, true);
                            break;
                        case 1:
                            //alerts
                            fragment = new AlertsToCustomer();
                            ((NavigetionLayout) getActivity()).initFragmentMain(fragment, true);
                            break;
                        case 2:
                            //payment
                            fragment = new PaymentDialogeFragment();
                            ((NavigetionLayout) getActivity()).initFragmentAll(fragment, true,0);
                            break;
                        case 3:
                            //language
                            fragment = new LanguagesToCustomerFragment();
                            ((NavigetionLayout) getActivity()).initFragmentMain(fragment, true);
                            break;

                    }
                }
            });
        }

        RegisterUserFragment.removeInstance();
        BusinessDetailsFragment.removeInstance();
        BusinessGeneralData.removeInstance();
        AlertsFragment.removeInstance();
        ContactListFragment.removeInstance();
        BusinessProfileFragment.removeInstance();
        BusinessPaymentFragment.removeInstance();

        return view;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }


    public class TextAdapter extends BaseAdapter {
        private Context mContext;
        private LayoutInflater mInflater;

        public TextAdapter(Context c) {
            mInflater = LayoutInflater.from(c);
            mContext = c;
        }

        public int getCount() {
            return definitions_number.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {  // if it's not recycled,
                convertView = mInflater.inflate(R.layout.definitions_from_the_customer_item_in_gv, null);
                holder = new ViewHolder();
                holder.txt = (TextView) convertView.findViewById(R.id.txt);
                holder.mItemRl = (RelativeLayout) convertView.findViewById(R.id.item);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txt.setText(definitions_txt[position]);
            Resources r = Resources.getSystem();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, r.getDisplayMetrics());
            convertView.setLayoutParams(new GridView.LayoutParams((int) px, (int) px));

            return convertView;
        }

    }


    class ViewHolder {
        TextView number, txt;
        RelativeLayout mItemRl;
    }
}
