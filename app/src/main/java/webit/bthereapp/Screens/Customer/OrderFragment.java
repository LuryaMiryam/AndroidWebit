package webit.bthereapp.Screens.Customer;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.annotations.PrimaryKey;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.General.BaseFragment;
import webit.bthereapp.Utils.CalendarUtil;

public class OrderFragment extends BaseFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String idP = "param1";

    // TODO: Rename and change types of parameters
    private long providerId = -1;
    private View view = null;
    private LinearLayout mSpinnerL;
    private PopupWindow popupWindow;
    private String strings[];
    ListView listView;
    TextView mChooseTv;
    ImageView mArrorIB;
    private int provider_id;
    LinearLayout mCloseIB;

    /*static*/ ListView mServiceListLV;
    ArrayList<ProviderServices> listOfServices;
    ArrayList<ProviderServices> listOfProduct;
    ArrayList<ProviderServices> sentList;
    private SearchResulstsObj searchResulstsObj;

    AdapterService mAdapterService;
    static FragmentManager fragmentManager;

    public static OrderFragment instance;

    public static OrderFragment getInstance() {
        if (instance == null)
            instance = new OrderFragment();
        return instance;
    }

    @Override
    public boolean OnBackPress() {
        return false;
    }

    public OrderFragment() {
        // Required empty public constructor
    }


    public static OrderFragment newInstance(long id) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();
        args.putLong(idP, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            providerId = getArguments().getLong(idP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
                if (getArguments() != null) {
                    try {
                        searchResulstsObj = new Gson().fromJson(getArguments().getString("bStr", ""), SearchResulstsObj.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                view = inflater.inflate(R.layout.fragment_order, container, false);
                if (searchResulstsObj != null) {
                    if (mAdapterService == null)
                        if (searchResulstsObj.iProviderId == 0)
                            getServicesForProvider(provider_id);
                        else
                            getServicesForProvider(searchResulstsObj.iProviderId);
                }
                mCloseIB = (LinearLayout) view.findViewById(R.id.order_close);
                mCloseIB.setOnClickListener(this);
                mSpinnerL = (LinearLayout) view.findViewById(R.id.spinner);
                mSpinnerL.setOnClickListener(this);
                strings = getResources().getStringArray(R.array.ordering_list);
                mChooseTv = (TextView) view.findViewById(R.id.choose);
                mChooseTv.setText(strings[0]);

                mArrorIB = (ImageView) view.findViewById(R.id.arror);
                mServiceListLV = (ListView) view.findViewById(R.id.service_list);

                mServiceListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                });
                fragmentManager = getActivity().getSupportFragmentManager();
        }
        return view;
    }

    public void set_p_id(int id) {
        provider_id = id;
    }

    private void popupWindowsort() {
        popupWindow = new PopupWindow();
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(strings));
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), R.layout.text_row_, stringList);

        // the drop down list is a list view
        listView = new ListView(getActivity());

        // set our adapter and pass our pop up window contents
        listView.setAdapter(adapter);

        // set on item selected
        listView.setOnItemClickListener(onItemClickListener());

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setWidth(250);
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow.setContentView(listView);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
    }

    public class ListViewAdapter extends ArrayAdapter<String> {

        private Context context_;
        private ArrayList<String> strings;
        private final String TAG = ListViewAdapter.class.getSimpleName();

        public ListViewAdapter(Context context, int resource, ArrayList<String> strings) {
            super(context, resource, strings);
            this.context_ = context;
            this.strings = strings;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.spinner_row, parent, false);

            } else {
            }
            String str = strings.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            name.setText(str);
            return convertView;
        }
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                //check if the list came from the server
                if (mAdapterService != null) {
                    if (position == 1) {
                        //if the user choosed product
                        CalendarUtil.type_of_service = 2;
                        sentList.clear();
                        for (ProviderServices ps : listOfProduct)
                            sentList.add(ps);
                        mAdapterService.setListOfServices(sentList);
                        mAdapterService.setIsService(false);
                        mAdapterService.notifyDataSetChanged();
                    } else if (position == 0) {
                        //if the user choosed service
                        CalendarUtil.type_of_service = 1;
                        sentList.clear();
                        for (ProviderServices ps : listOfServices)
                            sentList.add(ps);
                        if (mAdapterService != null) {
                            mAdapterService.setListOfServices(sentList);
                            mAdapterService.setIsService(true);
                            mAdapterService.notifyDataSetChanged();
                        }
                    } else if (position == 2) {
                        //if the user choosed a series of appointments
                        if (CalendarUtil.type_of_service != 3 && CalendarUtil.type_of_service != 2) {
                            sentList.clear();
                            for (ProviderServices ps : listOfServices)
                                sentList.add(ps);
                            if (mAdapterService != null) {
                                mAdapterService.setListOfServices(sentList);
                                mAdapterService.setIsService(true);
                                mAdapterService.notifyDataSetChanged();
                            }
                        }
                        CalendarUtil.type_of_service = 3;
                    }

                    mChooseTv.setText(strings[position]);
                    popupWindow.dismiss();
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.message_not_come_from_server), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spinner:
                popupWindowsort();
                popupWindow.setWidth(mSpinnerL.getWidth());
                popupWindow.showAsDropDown(v, 0, 0); // show popup like dropdown list
                //  click = false;
                break;
            case R.id.order_close:
                getActivity().onBackPressed();
                break;
        }

    }

    private void getServicesForProvider(final long id) {
        String jsonString = "{\"iProviderId\":" + id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getProviderServicesForSupplier(getActivity(), jsonObject, new IExecutable<ArrayList<ProviderServices>>() {
            @Override
            public void onExecute(ArrayList<ProviderServices> providerServices) {
                if (providerServices != null) {
                    listOfServices = new ArrayList<>();
                    listOfProduct = new ArrayList<>();
                    sentList = new ArrayList<>();

                    for (ProviderServices ps : providerServices) {
                        if (ps.getiServiceType() == 90)
                            listOfServices.add(ps);
                        else
                            listOfProduct.add(ps);
                    }
                    sentList = new ArrayList<>(listOfServices);
                    CalendarUtil.type_of_service = 1;
                    mAdapterService = new AdapterService(getActivity(), sentList, searchResulstsObj,id);
                    mServiceListLV.setAdapter(mAdapterService);
                    //check if there is no services then so show the products(if there are)
                    if (listOfServices.size() == 0 && listOfProduct.size() > 0) {
                        CalendarUtil.type_of_service = 2;
                        // do the onclick of clicking on "products"
                        mSpinnerL.performClick();
                        listView.performItemClick(
                                listView.getAdapter().getView(1, null, null),
                                1,
                                listView.getAdapter().getItemId(1));
                    }
                } else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.no_found_services_to_this_provider), Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}