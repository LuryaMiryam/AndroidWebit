package webit.bthereapp.Screens.Customer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.firebase.auth.api.model.StringList;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightButton;
import webit.bthereapp.Entities.GetFreeDaysForServiceProvider;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.orderObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.ShowDateFragment;
import webit.bthereapp.Screens.Calendar.ShowDayFragment;
import webit.bthereapp.Screens.Calendar.ShowWeekFragment;
import webit.bthereapp.Utils.CalendarUtil;


public class OrderServiceFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RelativeLayout mSpinnerL;
    private ArrayList<UserObj> userObjs_ = new ArrayList<>();
    private PopupWindow popupWindow;
    private String strings[];
    private ImageView logo;
    ListView listView;
    private TextView mTextSpinner;
    private CustomLightButton mDayB, mWeekB, mMonth, mPickedB;
    private View view = null;
    private int arr[];
    Toast toast;
    private ArrayList<ProviderFreeDaysObj> providerFreeDaysObjs;
    private long id;
    private TextView srv_name, srv_type;
    private SearchResulstsObj searchResulstsObj;
    private Calendar dayForWeek;
    private String srvType;

    public OrderServiceFragment() {
        // Required empty public constructor
    }


    public static OrderServiceFragment newInstance(String param1, long param2) {
        OrderServiceFragment fragment = new OrderServiceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putLong(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static OrderServiceFragment instance;

    public static OrderServiceFragment getInstance() {
        if (instance == null) {
            instance = new OrderServiceFragment();
        }

        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            id = getArguments().getLong(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
            CalendarUtil.is_calendar_of_provider = true;
            if (view == null) {

                if (getArguments() != null && getArguments().getString("SearchResulstsObj") != null) {
                    try {
                        searchResulstsObj = new Gson().fromJson(getArguments().getString("SearchResulstsObj", ""), SearchResulstsObj.class);
                        srvType = getArguments().getString("srvType", "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                view = inflater.inflate(R.layout.fragment_order_service, container, false);
                mSpinnerL = (RelativeLayout) view.findViewById(R.id.spiner_Staff_member);
                mSpinnerL.setVisibility(View.GONE);
                mSpinnerL.setOnClickListener(this);
                srv_name = (TextView) view.findViewById(R.id.srv_name);
                srv_type = (TextView) view.findViewById(R.id.srv_type);
                logo = (ImageView) view.findViewById(R.id.logo);
                srv_type.setText(srvType);
                //set logo image of the bussines
                if (searchResulstsObj != null && !searchResulstsObj.equals("")) {
                    srv_name.setText(searchResulstsObj.nvProviderName);
                    if (searchResulstsObj.nvProviderLogo != null) {
                        Bitmap bitmap = ConnectionUtils.convertStringToBitmap(searchResulstsObj.nvProviderLogo);
                        logo.setImageBitmap(bitmap);
                    }
                }
                getServicesProviderForSupplier(id);
                mTextSpinner = (TextView) view.findViewById(R.id.text_spinner);
                mMonth = (CustomLightButton) view.findViewById(R.id.month);
                mDayB = (CustomLightButton) view.findViewById(R.id.day);
                mWeekB = (CustomLightButton) view.findViewById(R.id.week);
                ShowDateFragment.removeInstance();
                ShowDateFragment.getInstance().put_fr(this);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                mMonth.setOnClickListener(this);
                mDayB.setOnClickListener(this);
                mWeekB.setOnClickListener(this);
                changePressed(mMonth);
                if (mPickedB != null)
                    changeBack(mPickedB);
                mPickedB = mMonth;

            }
            ((NavigetionLayout) getActivity()).otherFrom = 5;
        return view;
    }


    public void setProviderId(long d) {
        id = d;
    }

    public void enter_to_existed_customer() {
        ((NavigetionLayout) getActivity()).enter_to_existed_customer();
    }

    private void getServicesProviderForSupplier(double d) {

        List<UserObj> userObjs = CalendarUtil.GetServiceProviders();
        if (userObjs != null) {
            if (userObjs.size() > 0) {
                mSpinnerL.setVisibility(View.VISIBLE);
                userObjs_ = new ArrayList<UserObj>(userObjs);
                strings = new String[userObjs_.size() + 1];
                int j = 0;
                strings[j++] = getResources().getStringArray(R.array.staff_member_list)[0];
                for (int i = 0; i < userObjs_.size(); i++) {
                    strings[j++] = (userObjs_.get(i).getNvFirstName() + " " + userObjs_.get(i).getNvLastName());
                }

            } else {
                userObjs_ = new ArrayList<UserObj>(userObjs);
                strings = new String[0];
                Toast.makeText(getContext(), "no found giving service to this provider", Toast.LENGTH_LONG).show();

            }

        }
    }

    private void GetFreeDaysForServiceProvider() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage(getResources().getString(R.string.first_name));

        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        orderObj.getInstance().setiProviderServiceId(GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId());

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(GetFreeDaysForServiceProvider.getInstance().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetFreeDaysForServiceProvider(getContext(), jsonObject, new IExecutable<ArrayList<ProviderFreeDaysObj>>() {
            @Override
            public void onExecute(ArrayList<ProviderFreeDaysObj> providerFreeDaysObj_) {

                if (providerFreeDaysObj_ != null) {
                    if (providerFreeDaysObj_.size() > 0) {
                        mSpinnerL.setVisibility(View.VISIBLE);
                        providerFreeDaysObjs = new ArrayList<ProviderFreeDaysObj>(providerFreeDaysObj_);
                    } else
                        providerFreeDaysObjs = new ArrayList<ProviderFreeDaysObj>();
                    CalendarUtil.taskCalanderListFree = providerFreeDaysObj_;
                    ShowDateFragment.removeInstance();
                    ShowDateFragment.getInstance().put_fr(OrderServiceFragment.this);
                    //open the calendar of the provider with month view
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                    changePressed(mMonth);
                    if (mPickedB != null)
                        changeBack(mPickedB);
                    mPickedB = mMonth;
                    alertDialog.dismiss();
                } else {
                    CalendarUtil.taskCalanderListFree = new ArrayList<ProviderFreeDaysObj>();
                    Toast.makeText(getContext(), getResources().getString(R.string.no_found_free_days_to_this_service_provider), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.spiner_Staff_member:
                popupWindowsort();
                // popupWindow.setWidth(mSpinnerL.getWidth());
                popupWindow.showAsDropDown(v, 0, 0);
                break;
            //day view
            case R.id.day:
                if (mPickedB != mDayB) {
                    changePressed(mDayB);
                    changeBack(mPickedB);
                    mPickedB = mDayB;
                    ShowDayFragment.removeInstance();
                    ShowDayFragment.getInstance().put_fr(this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
                }
                break;

            //month view
            case R.id.month:
                if (mPickedB != mMonth) {
                    changePressed(mMonth);
                    changeBack(mPickedB);
                    mPickedB = mMonth;
                    ShowDateFragment.removeInstance();
                    ShowDateFragment.getInstance().put_fr(OrderServiceFragment.this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDateFragment.getInstance()).commit();
                }
                break;
            //week view
            case R.id.week:
                boolean b = false;
                if (mPickedB != mWeekB) {
                    if (mPickedB == mDayB) {
                        if (dayForWeek != null) {
                            b = true;
                        }
                    }
                    changePressed(mWeekB);
                    changeBack(mPickedB);
                    mPickedB = mWeekB;
                    if (!b)
                        dayForWeek = Calendar.getInstance();
                    ShowWeekFragment.removeInstance();
                    ShowWeekFragment.getInstance().put_cal_and_fr(dayForWeek, this);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowWeekFragment.getInstance()).commit();
                }
                break;


        }
    }

    public void changePressed(CustomLightButton b) {
        b.setBackgroundResource(R.color.transparent);
        b.ChangeFont(1);
        b.setPaintFlags(b.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void changeBack(CustomLightButton b) {
        int color = 0;
        switch (b.getId()) {
            case R.id.day:
                color = getResources().getColor(R.color.light_gray_9);
                break;
            case R.id.week:
                color = getResources().getColor(R.color.light_gray_8);
                break;
            case R.id.month:
                color = getResources().getColor(R.color.dark_gray_7);
                break;
            case R.id.list:
                color = getResources().getColor(R.color.dark_gray_6);
                break;
        }
        b.setBackgroundColor(color);
        b.ChangeFont(0);
        b.setPaintFlags(b.getPaintFlags() & (~Paint.UNDERLINE_TEXT_FLAG));
    }

    //open popup to choice
    private void popupWindowsort() {
        ArrayList<String> stringList = new ArrayList<String>();
        popupWindow = new PopupWindow();
        stringList = new ArrayList<String>(Arrays.asList(strings));
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), R.layout.text_row_, stringList);
        // the drop down list is a list view
        listView = new ListView(getActivity());

        // set our adapter and pass our pop up window contents
        listView.setAdapter(adapter);

        // set on item selected
        listView.setOnItemClickListener(onItemClickListener());

        // some other visual settings for popup window
        popupWindow.setFocusable(true);
        popupWindow.setWidth(mSpinnerL.getWidth());
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
                mTextSpinner.setText(strings[position]);
                popupWindow.dismiss();
                //when change provider name in the popup, change the popup of the service providers and the services
                if (position == 0) {
                    arr = new int[strings.length - 1];
                    for (int i = 0; i < userObjs_.size(); i++)
                        arr[i] = (int) userObjs_.get(i).getiUserId();
                } else {
                    arr = new int[1];
                    arr[0] = (int) userObjs_.get(position - 1).getiUserId();
                }
                GetFreeDaysForServiceProvider.getInstance().setlServiseProviderId(arr);
                orderObj.getInstance().setiProviderServiceId(GetFreeDaysForServiceProvider.getInstance().getlProviderServiceId());
                GetFreeDaysForServiceProvider();
            }
        };
    }

    //if the user want to pass from month view in specific date to day view
    public void setDisplayDay(Calendar calendar) {
        changePressed(mDayB);
        changeBack(mPickedB);
        mPickedB = mDayB;
        ShowDayFragment.removeInstance();
        ShowDayFragment.getInstance().put_cal_and_fr(calendar, this);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
    }

    public void setDayForWeek(Calendar dayForWeek) {
        this.dayForWeek = dayForWeek;
    }
}