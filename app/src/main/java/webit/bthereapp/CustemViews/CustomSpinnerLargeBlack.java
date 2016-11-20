package webit.bthereapp.CustemViews;

/**
 * Created by User on 31/05/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.UserRealm;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SearchResulstsObj;
import webit.bthereapp.Entities.SysAlertsListItem;
import webit.bthereapp.Entities.UserObj;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;


public class CustomSpinnerLargeBlack extends LinearLayout {
    private CustomLightTextView mtv, txt;
    private PopupWindow popupWindow;
    private boolean click = true;
    private CustomLightTextView mAlertTypeTv;
    private ListView listView;
    private int choose = -1, id_opt;
    private String[] s_;
    private boolean al_meet_arr[] = new boolean[0];
    public static int provider_id = 0, give_service_id = 0;
    private ArrayList<Integer> choose_many = new ArrayList<>();
    private Context mContext;
    private String spinner_text;
    private int spinner_opt;
    private RelativeLayout mCustomSpinnerLl_in_, width_all;
    SysAlertsListItem s_item;
    private SysAlertsListItem itemsArray[] = new SysAlertsListItem[0], items[];
    public static ArrayList<UserObj> userObjs = new ArrayList<>();
    public static ArrayList<ProviderServices> providerServices = new ArrayList<>();
    public static boolean if_providerServices_changed = false;
    public static boolean if_userObjs_changed = false;
    private ArrayList<SearchResulstsObj> list = new ArrayList<>();
    public String adress = "";

    public CustomSpinnerLargeBlack(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinnerLargeBlack(Context context, AttributeSet attrs, int f) {
        super(context, attrs);
        init(context, attrs);
    }

    public int get_provider_id() {
        return provider_id;
    }

    public int get_give_service_id() {
        return give_service_id;
    }

    public int get_choose() {
        return choose;
    }

    public String get_choose_txt() {
        return mAlertTypeTv.getText().toString();
    }

    public void set_choose(ArrayList<Integer> c) {
        if (c.size() == 1)
            choose = c.get(0);
    }

    public int[] get_many_choose() {
        choose_many = new ArrayList<>();
        if (spinner_opt == 4) {
            if (al_meet_arr.length > 0)
                if (al_meet_arr[0]) {
                    for (int i = 1; i < al_meet_arr.length; i++)
                        choose_many.add(itemsArray[i].getItem_id());
                } else {
                    for (int i = 1; i < al_meet_arr.length; i++)
                        if (al_meet_arr[i]) {
                            choose_many.add(itemsArray[i].getItem_id());
                        }
                }

        } else {
            for (int i = 0; i < al_meet_arr.length; i++)
                if (al_meet_arr[i]) {
                    choose_many.add(itemsArray[i].getItem_id());
                }
        }
        int arr_[] = new int[0];
        if (choose_many.size() > 0)
            arr_ = new int[choose_many.size()];
        for (int i = 0; i < choose_many.size(); i++)
            arr_[i] = choose_many.get(i);
        return arr_;
    }


    public ArrayList<SysAlertsListItem> get_many_choose_with_txt() {
        ArrayList<SysAlertsListItem> arr_ = new ArrayList<>();
        if (spinner_opt == 4) {
            if (al_meet_arr.length > 0)
                if (al_meet_arr[0]) {
                    for (int i = 1; i < al_meet_arr.length; i++) {
                        arr_.add(itemsArray[i]);
                    }
                } else {
                    for (int i = 1; i < al_meet_arr.length; i++)
                        if (al_meet_arr[i]) {
                            arr_.add(itemsArray[i]);
                        }
                }

        } else {
            for (int i = 0; i < al_meet_arr.length; i++)
                if (al_meet_arr[i]) {
                    arr_.add(itemsArray[i]);
                }
        }
        return arr_;
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_custom_spinner_large_black, this);
        this.mContext = context;
        width_all = (RelativeLayout) view.findViewById(R.id.width_all);
        mCustomSpinnerLl_in_ = (RelativeLayout) view.findViewById(R.id.r_all);
        txt = (CustomLightTextView) view.findViewById(R.id.txt_type);
        mAlertTypeTv = (CustomLightTextView) view.findViewById(R.id.txt);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomSpinnerLargeBlack, 0, 0);
        spinner_opt = a.getInt(R.styleable.CustomSpinnerLargeBlack_customSpinnerLargeBlackType, 0);
        id_opt = spinner_opt;
        if (spinner_opt == 4) {
            if (userObjs.size() > 0) {
                s_item = new SysAlertsListItem();
                items = new SysAlertsListItem[userObjs.size() + 1];
                items[0] = new SysAlertsListItem();
                items[0].setItem_id(0);
                items[0].setItem_txt(getResources().getStringArray(R.array.staff_member_list)[0]);

                for (int i = 1; i < userObjs.size() + 1; i++) {
                    items[i] = new SysAlertsListItem();
                    items[i].setItem_id((int) userObjs.get(i - 1).getiUserId());
                    items[i].setItem_txt(userObjs.get(i - 1).getNvFirstName() + " " + userObjs.get(i - 1).getNvLastName());
                }
                itemsArray = items;
                al_meet_arr = new boolean[itemsArray.length];
                for (int i = 0; i < itemsArray.length; i++) {
                    al_meet_arr[i] = false;
                }
            } else
                itemsArray = new SysAlertsListItem[0];
            spinner_text = getResources().getString(R.string.det_app_give_service_txt);
        }

        if (spinner_opt == 1) {
            if (providerServices.size() > 0) {
                s_item = new SysAlertsListItem();
                items = new SysAlertsListItem[providerServices.size()];
                for (int i = 0; i < providerServices.size(); i++) {
                    items[i] = new SysAlertsListItem();
                    items[i].setItem_id((int) providerServices.get(i).getIProviderServiceId());
                    items[i].setItem_txt(providerServices.get(i).getNvServiceName());
                }
                itemsArray = items;
                al_meet_arr = new boolean[itemsArray.length];
                for (int i = 0; i < itemsArray.length; i++) {
                    al_meet_arr[i] = false;
                }
            } else
                itemsArray = new SysAlertsListItem[0];


            spinner_text = getResources().getString(R.string.det_app_type_service_txt);
        }

        if (spinner_opt == 2) {
            s_ = getResources().getStringArray(R.array.det_app_date);
            getData2(s_);

            spinner_text = getResources().getString(R.string.det_app_date_txt);
        }

        if (spinner_opt == 3) {
            s_ = getResources().getStringArray(R.array.det_app_hour);
            getData2(s_);

            spinner_text = getResources().getString(R.string.det_app_hour_txt);
        }
        if (spinner_opt == 5) {
            GetProviderListForCustomer(this);
            spinner_text = getResources().getString(R.string.new_app_provider_txt);
        }

        txt.setText(spinner_text);

        if (spinner_opt == 6) {
            s_ = getResources().getStringArray(R.array.num_customers);
            getData2(s_);

            spinner_text = getResources().getString(R.string.num_customers_txt);
        }

        txt.setText(spinner_text);


        mCustomSpinnerLl_in_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (spinner_opt == 1 && give_service_id == 0)
                    //if the user choose service before choose give service
                    new AlertDialog.Builder(mContext)
                            .setMessage(getResources().getString(R.string.choose_provider_))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                else if (spinner_opt == 4 && provider_id == 0)
                    //if the user choose give service before choose provider
                    new AlertDialog.Builder(mContext)
                            .setMessage(getResources().getString(R.string.choose_give_service_))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                else {
                    popupWindowsort();
                    if (popupWindow != null) {
                        popupWindow.setWidth(width_all.getWidth());
                        popupWindow.showAsDropDown(width_all, 0, 0); // show popup like dropdown list
                        click = false;
                    }
                }
            }

        });
    }

    //set the previous choose
    public void set_old(int a) {
        if (a != -1) {
            choose = a;

            for (int i = 0; i < itemsArray.length; i++)
                if (itemsArray[i].getItem_id() == a) {
                    mAlertTypeTv.setText(itemsArray[i].getItem_txt());
                    al_meet_arr[i] = true;
                }

        }
        if_userObjs_changed = false;
    }

    public void set_zero() {
        choose = -1;
        al_meet_arr = new boolean[]{};
        mAlertTypeTv.setText("");
        itemsArray = new SysAlertsListItem[]{};
        items = new SysAlertsListItem[]{};
        userObjs = new ArrayList<>();
        providerServices = new ArrayList<>();
        if_providerServices_changed = true;
    }


    //set the previous many choose
    public void set_old_array(ArrayList<Integer> a) {
        int arr[] = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            arr[i] = a.get(i);
        }


        if (arr != null) {
            for (int i = 0; i < al_meet_arr.length; i++) {
                al_meet_arr[i] = false;
            }
            for (int j = 0; j < arr.length; j++) {
                for (int i = 0; i < itemsArray.length; i++)
                    if (itemsArray[i].getItem_id() == arr[j]) {
                        al_meet_arr[i] = true;
                    }
            }
            this.set_txt();
        }
        if_providerServices_changed = false;
    }

    private void getData2(String[] ss) {
        s_item = new SysAlertsListItem();
        items = new SysAlertsListItem[ss.length];

        for (int i = 0; i < ss.length; i++) {
            items[i] = new SysAlertsListItem();
            items[i].setItem_id(i);
            items[i].setItem_txt(ss[i]);
        }
        itemsArray = items;
    }

    //open the list popup
    private void popupWindowsort() {
        if (spinner_opt == 1) {
            if (providerServices.size() > 0 && if_providerServices_changed) {
                s_item = new SysAlertsListItem();
                items = new SysAlertsListItem[providerServices.size()];

                for (int i = 0; i < providerServices.size(); i++) {
                    items[i] = new SysAlertsListItem();
                    items[i].setItem_id((int) providerServices.get(i).getIProviderServiceId());
                    items[i].setItem_txt(providerServices.get(i).getNvServiceName());
                }
                itemsArray = items;
                al_meet_arr = new boolean[itemsArray.length];
                for (int i = 0; i < itemsArray.length; i++) {
                    al_meet_arr[i] = false;
                }

            } else if (providerServices.size() == 0)
                itemsArray = new SysAlertsListItem[0];
            if_providerServices_changed = false;
        }
        if (spinner_opt == 4) {
            if (userObjs.size() > 0 && if_userObjs_changed) {
                s_item = new SysAlertsListItem();
                items = new SysAlertsListItem[userObjs.size() + 1];
                items[0] = new SysAlertsListItem();
                items[0].setItem_id(0);
                items[0].setItem_txt(getResources().getStringArray(R.array.staff_member_list)[0]);

                for (int i = 1; i < userObjs.size() + 1; i++) {
                    items[i] = new SysAlertsListItem();
                    items[i].setItem_id((int) userObjs.get(i - 1).getiUserId());
                    items[i].setItem_txt(userObjs.get(i - 1).getNvFirstName() + " " + userObjs.get(i - 1).getNvLastName());
                }
                itemsArray = items;
                al_meet_arr = new boolean[itemsArray.length];
                for (int i = 0; i < itemsArray.length; i++) {
                    al_meet_arr[i] = false;
                }
            } else if (userObjs.size() == 0)
                itemsArray = new SysAlertsListItem[0];
            if_userObjs_changed = false;
        }
        if (itemsArray != null && itemsArray.length > 0) {
            popupWindow = new PopupWindow(this);
            ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_, itemsArray, this);
            // the drop down list is a list view
            listView = new ListView(mContext);

            // set our adapter and pass our pop up window contents
            listView.setAdapter(adapter);

            // set on item selected
            listView.setOnItemClickListener(onItemClickListener());

            // some other visual settings for popup window
            popupWindow.setFocusable(true);
            popupWindow.setWidth(250);
            // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
            popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

            // set the listview as popup content
            popupWindow.setContentView(listView);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);
        }
    }


    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                mAlertTypeTv.setText(itemsArray[position].getItem_txt());
                choose = itemsArray[position].getItem_id();
                popupWindow.dismiss();
                if (spinner_opt == 5) {
//                    ConnectionUtils.set_zero_in_new_order_dialog();
                    provider_id = choose;
                    for (SearchResulstsObj searchResulstsObj : list)
                        if (searchResulstsObj.iProviderId == provider_id)
                            adress = searchResulstsObj.nvAdress;
                    getServicesProviderForSupplier();
                    getProviderServicesForSupplier();
                }
                if (spinner_opt == 4) {
                    give_service_id = choose;
                }
            }
        };
    }


    private void getServicesProviderForSupplier() {
        String jsonString = "{\"iProviderId\":" + provider_id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getServicesProviderForSupplier(mContext, jsonObject, new IExecutable<ArrayList<UserObj>>() {
            @Override
            public void onExecute(ArrayList<UserObj> userObjs_) {
                if (userObjs_ != null) {
                    userObjs = userObjs_;
                    if_userObjs_changed = true;
                } else {
                    userObjs = new ArrayList<UserObj>();
                }

            }
        });

    }

    private void getProviderServicesForSupplier() {

        String jsonString = "{\"iProviderId\":" + provider_id + "}";
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.getProviderServicesForSupplier(getContext(), jsonObject, new IExecutable<ArrayList<ProviderServices>>() {
            @Override
            public void onExecute(ArrayList<ProviderServices> providerServices_) {

                if (providerServices_ != null) {
                    providerServices = providerServices_;
                    if_providerServices_changed = true;
                } else
                    providerServices = new ArrayList<>();

            }
        });
    }

    private void GetProviderListForCustomer(final CustomSpinnerLargeBlack customSpinnerLargeBlack) {
        Realm realm = Utils.getRealmInstance(getContext());
        UserRealm userRealm = realm.where(UserRealm.class).findFirst();
        double d;
        if (userRealm != null)
            d = userRealm.getUserID();
        else
            d = 0;
        String jsonString = "{\"iUserId\":" + d + "}";

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MainBL.GetProviderListForCustomer(getContext(), jsonObject, new IExecutable<ArrayList<SearchResulstsObj>>() {
                    @Override
                    public void onExecute(ArrayList<SearchResulstsObj> list_) {

                        if (list_ != null) {
                            list = list_;
                            s_item = new SysAlertsListItem();
                            items = new SysAlertsListItem[list_.size()];

                            for (int i = 0; i < list_.size(); i++) {
                                items[i] = new SysAlertsListItem();
                                items[i].setItem_id((int) list_.get(i).iProviderId/*.getiUserId()*/);
                                items[i].setItem_txt(list_.get(i).nvProviderName) /*+ " " + list.get(i).getNvLastName())*/;
                            }
                            itemsArray = items;
                            if (provider_id != 0)
                                for (int i = 0; i < itemsArray.length; i++)
                                    if (itemsArray[i].getItem_id() == provider_id) {
                                        mAlertTypeTv.setText(itemsArray[i].getItem_txt());
                                    }

                        } else {
                            list = new ArrayList<SearchResulstsObj>();
                            Toast.makeText(getContext(), getResources().getString(R.string.there_are_no_providers_to_this_customer), Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    //set the text after choose
    public void set_txt() {
        String s = "";
        for (int i = 0; i < al_meet_arr.length; i++)
            if (id_opt == 4 && i != 0 && al_meet_arr[0]) {

            } else if (al_meet_arr[i]) {
                if (!s.equals(""))
                    s += ", ";
                s += itemsArray[i].getItem_txt().toString();
                if (s != null && s.length() > 19) {
                    s = s.substring(0, 19);
                    s += "...";
                }
            }
        if (spinner_opt == 0 && al_meet_arr[0])
            s = itemsArray[0].getItem_txt().toString();
        mAlertTypeTv.setText(s);


    }


    public class ListViewAdapter extends ArrayAdapter<SysAlertsListItem> {

        private SysAlertsListItem sysAlertsListItem;
        private Context context_;
        private SysAlertsListItem[] items;
        private final String TAG = ListViewAdapter.class.getSimpleName();
        private CustomSpinnerLargeBlack customSpinner;

        public ListViewAdapter(Context context, int resource, SysAlertsListItem[] items, CustomSpinnerLargeBlack customSpinner) {
            super(context, resource, items);
            this.context_ = context;
            this.customSpinner = customSpinner;
            this.items = items;
        }

        //public void change_to_choose()
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.text_row_, parent, false);
            } else {
            }

            final SysAlertsListItem sysAlertsListItem = items[position];
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            name.setText(sysAlertsListItem.getItem_txt());
            RelativeLayout r_all = (RelativeLayout) convertView.findViewById(R.id.r_all);
            if (al_meet_arr != null)
                if (al_meet_arr.length > 0) {

                    if (!al_meet_arr[position])
                        imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_42);
                    else
                        imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_41);

                    if (id_opt == 4 || id_opt == 1) {
                        //in many choose
                        imageView.setVisibility(VISIBLE);
                        r_all.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (al_meet_arr[position]) {
                                    al_meet_arr[position] = false;
                                    imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_42);
                                    if (position != 0 && id_opt == 0 && al_meet_arr[0]) {
                                        al_meet_arr[0] = false;
                                    }
                                } else {
                                    al_meet_arr[position] = true;
                                    imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_41);
                                    if (position == 0 && id_opt == 0) {
                                        al_meet_arr[1] = true;
                                        al_meet_arr[2] = true;
                                        al_meet_arr[3] = true;
                                    }
                                }
                                if (id_opt == 4) {
                                    give_service_id = 0;
                                    for (int i = 0; i < al_meet_arr.length; i++)
                                        if (al_meet_arr[i])
                                            give_service_id = 1;
                                }
                                customSpinner.set_txt();
                                notifyDataSetChanged();
                            }
                        });

                    }
                }
            return convertView;
        }
    }
}

