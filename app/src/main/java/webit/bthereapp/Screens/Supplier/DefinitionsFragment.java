package  webit.bthereapp.Screens.Supplier;

import android.content.Context;
import android.content.Intent;
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
import webit.bthereapp.Screens.Register.UserRegister.RegisterUserFragment;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link DefinitionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefinitionsFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //    private String[] definitions_number = getResources().getStringArray(R.array.definitions_number);
    private String[] definitions_txt;
    private View view;
    private String[] definitions_number = {"1", "2", "3", "4", "5", "6", "7", "8"};
    private Intent intent;


    public DefinitionsFragment() {
        // Required empty public constructor
    }


    public static DefinitionsFragment instance;

    public static DefinitionsFragment getInstance() {
        if (instance == null)
            instance = new DefinitionsFragment();
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
    public static DefinitionsFragment newInstance(String param1, String param2) {
        DefinitionsFragment fragment = new DefinitionsFragment();
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
            view = inflater.inflate(R.layout.fragment_definitions, container, false);
            definitions_txt = getResources().getStringArray(R.array.definitions_txt);

            final GridView gridview = (GridView) view.findViewById(R.id.definitions);
            gridview.setAdapter(new TextAdapter(view.getContext()));
            gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                    Fragment fragment = null;
                    switch (position) {
                        case 0:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 1);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 1:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 2);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 2:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 3);
                            startActivity(intent);
                            getActivity().finish();

                            break;
                        case 3:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 4);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 4:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 5);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 5:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 6);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 6:
                            intent = new Intent(getActivity(), ExistsSuplierDefinitionsActivity.class);
                            intent.putExtra("fr", 7);
                            startActivity(intent);
                            getActivity().finish();
                            break;
                        case 7:
                            fragment = new LanguagesFragment();
                            ((ExistsSuplierActivity) getActivity()).initFragmentMain(fragment, true);
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
                convertView = mInflater.inflate(R.layout.definitions_item_in_gv, null);
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
