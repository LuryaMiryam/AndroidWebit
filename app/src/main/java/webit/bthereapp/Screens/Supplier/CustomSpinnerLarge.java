package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.BL.MainBL;
import webit.bthereapp.CustemViews.CustomSpinner;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.CustemViews.Fonts.CustomRegularEditText;
import webit.bthereapp.CustemViews.Fonts.CustomRegularTextView;
import webit.bthereapp.DM.ProviderRealm;
import webit.bthereapp.DM.SysAlertsListDM;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.Entities.ProviderServices;
import webit.bthereapp.Entities.SysAlertsListItem;
import webit.bthereapp.Intreface.IExecutable;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

public class CustomSpinnerLarge extends LinearLayout {
    private CustomLightTextView mtv, txt;
    private PopupWindow popupWindow;
    private boolean click = true;
    private CustomLightTextView mAlertTypeTv;
    private ListView listView;
    private int choose = -1;
    private String[] s_;
    private int id_opt;
    private Context mContext;
    private boolean al_meet_arr[] = {false, false, false, false};

    private String spinner_text;
    private int spinner_opt;
    private RelativeLayout mCustomSpinnerLl_in_, width_all;

    private ObjInSpinner s_item, itemsArray[], items[];
    private SysAlertsListDM s;

    public CustomSpinnerLarge(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinnerLarge(Context context, AttributeSet attrs, int f) {
        super(context, attrs);
        init(context, attrs);
    }


    public int get_choose() {
        return choose;
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_custom_spinner_large, this);
        this.mContext = context;
        width_all = (RelativeLayout) view.findViewById(R.id.width_all);


        mCustomSpinnerLl_in_ = (RelativeLayout) view.findViewById(R.id.r_all);
        txt = (CustomLightTextView) view.findViewById(R.id.txt_type);
        mAlertTypeTv = (CustomLightTextView) view.findViewById(R.id.txt);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomSpinnerLarge, 0, 0);


        spinner_opt = a.getInt(R.styleable.CustomSpinnerLarge_customSpinnerLargeType, 0);
        id_opt = spinner_opt;

        //give_service
        if (spinner_opt == 0) {
            s_item = new ObjInSpinner();
            items = new ObjInSpinner[1];

            items[0] = new ObjInSpinner();
            items[0].setId(0);
            items[0].setTxt(getResources().getStringArray(R.array.staff_member_list)[0]);

            if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders() != null){

                for (int i = 0; i < ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().size() + 1; i++) {
                    items[i+1] = new ObjInSpinner();
                    items[i+1].setId((int) ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().get(i).getObjUsers().getiUserId());
                    items[i+1].setTxt(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().get(i).getObjUsers().getNvFirstName() + " ");
                    items[i+1].setTxt(items[i+1].getTxt() + ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().get(i).getObjUsers().getNvLastName());
                }
        }
            itemsArray = items;
            spinner_text = getResources().getString(R.string.det_app_give_service_txt);

        }
//type_service
        if (spinner_opt == 1) {
            s_item = new ObjInSpinner();
            items = new ObjInSpinner[ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().size()];

            for (int i = 0; i < ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().size(); i++) {
                items[i] = new ObjInSpinner();
                items[i].setId(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().get(i).getIProviderServiceId());
                items[i].setTxt(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().get(i).getNvServiceName());
            }
            itemsArray = items;

                getProviderServicesForSupplier((int)ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails().getiBuisnessDetailsId());
            spinner_text = getResources().getString(R.string.det_app_type_service_txt);
        }
//date
        if (spinner_opt == 2) {
            s_ = getResources().getStringArray(R.array.det_app_date);
            getData2(s_);

            spinner_text = getResources().getString(R.string.det_app_date_txt);
        }
//hour
        if (spinner_opt == 3) {
            s_ = getResources().getStringArray(R.array.det_app_hour);
            getData2(s_);

            spinner_text = getResources().getString(R.string.det_app_hour_txt);
        }
//customer
        if (spinner_opt == 4) {
            s_ = getResources().getStringArray(R.array.new_cus_name);
            getData2(s_);

            spinner_text = getResources().getString(R.string.new_cus_name_txt);
        }
//customer
        if (spinner_opt == 5) {
            s_ = getResources().getStringArray(R.array.new_operation);
            getData2(s_);

            spinner_text = getResources().getString(R.string.new_operation_txt);
        }

        txt.setText(spinner_text);
//num of customer
        if (spinner_opt == 6) {
            s_ = getResources().getStringArray(R.array.num_customers);
            getData2(s_);

            spinner_text = getResources().getString(R.string.num_customers_txt);
        }
        //type of service
        if (spinner_opt == 7) {
            s_ = getResources().getStringArray(R.array.det_app_type_service);
            getData2(s_);
                getProviderServicesForSupplier((int)ProviderDetailsObj.getInstance().getObjProviderBuisnessDetails().getiBuisnessDetailsId());

            spinner_text = getResources().getString(R.string.det_app_type_service_txt);
        }
//type of service
        if (spinner_opt == 8) {
            s_item = new ObjInSpinner();
            items = new ObjInSpinner[ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().size()];

            for (int i = 0; i < ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().size(); i++) {
                items[i] = new ObjInSpinner();
                items[i].setId(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().get(i).getIProviderServiceId());
                items[i].setTxt(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServices().get(i).getNvServiceName());
            }
            itemsArray = items;
//                getProviderServicesForSupplier();

            spinner_text = getResources().getString(R.string.det_app_type_service_txt);
        }
        txt.setText(spinner_text);


        mCustomSpinnerLl_in_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindowsort();
                popupWindow.setWidth(width_all.getWidth());

                popupWindow.showAsDropDown(width_all, 0, 0); // show popup like dropdown list
                click = false;

            }

        });



        mAlertTypeTv.setText(getResources().getString(R.string.choose));
    }

private void getProviderServicesForSupplier(long s) {
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
    MainBL.getProviderServicesForSupplier(getContext(), jsonObject, new IExecutable<ArrayList<ProviderServices>>() {
        @Override
        public void onExecute(ArrayList<ProviderServices> providerServices) {

            if (providerServices != null) {
                if (providerServices.size() > 0) {
                    s_item = new ObjInSpinner();
                    items = new ObjInSpinner[providerServices.size()];

                    for (int i = 0; i < providerServices.size(); i++) {
                        items[i] = new ObjInSpinner();
                        items[i].setId((int)providerServices.get(i).getIProviderServiceId());
                        items[i].setTxt(providerServices.get(i).getNvServiceName());
                    }
                    itemsArray = items;
                }
            } else {
                Toast.makeText(getContext(),getResources().getString(R.string.no_found_services_to_this_provider), Toast.LENGTH_LONG).show();
            }
        }
    });
}

    private void getData2(String[] ss) {
        s_item = new ObjInSpinner();
        items = new ObjInSpinner[ss.length];

        for (int i = 0; i < ss.length; i++) {
            items[i] = new ObjInSpinner();
            items[i].setId(i);
            items[i].setTxt(ss[i]);
        }
        itemsArray = items;

    }

    private void popupWindowsort() {

        popupWindow = new PopupWindow(this);
        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_large, itemsArray, this);

        // the drop down list is a list view
        listView = new ListView(mContext);

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
//set text when chose many
    public void set_txt() {
        String s = "";

        for (int i = 0; i < al_meet_arr.length; i++)
            if (al_meet_arr[i]) {
                if (!s.equals(""))
                    s += ", ";
                s += itemsArray[i].getTxt().toString();
                if (s != null && s.length() > 19) {
                    s = s.substring(0, 19);
                    s += "...";
                }
            }
        mAlertTypeTv.setText(s);
        if (mAlertTypeTv.getText().equals(""))
            mAlertTypeTv.setText(getResources().getString(R.string.choose));
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (id_opt == 7) {
                } else {
                    mAlertTypeTv.setText(itemsArray[position].getTxt());
                    int ii_n;
                    choose = itemsArray[position].getId();

                    popupWindow.dismiss();
                }
            }
        };
    }


    public class ListViewAdapter extends ArrayAdapter<ObjInSpinner> {

        private ObjInSpinner objInSpinner;
        private Context context_;
        private ObjInSpinner[] items;
        private final String TAG = ListViewAdapter.class.getSimpleName();
        private CustomSpinnerLarge customSpinner;


        public ListViewAdapter(Context context, int resource, ObjInSpinner[] items, CustomSpinnerLarge customSpinner) {
            super(context, resource, items);
            this.context_ = context;
            this.items = items;
            this.customSpinner = customSpinner;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // If holder not exist then locate all view from UI file.
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.text_row_, parent, false);
            }
            ObjInSpinner objInSpinner = items[position];
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            name.setText(objInSpinner.getTxt());


            final ObjInSpinner sysAlertsListItem = items[position];
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            RelativeLayout r_all = (RelativeLayout) convertView.findViewById(R.id.r_all);

            if (!al_meet_arr[position])
                imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_42);
            else
                imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_41);


            if (id_opt == 7) {
                imageView.setVisibility(VISIBLE);
                r_all.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (al_meet_arr[position]) {
                            al_meet_arr[position] = false;
                            imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_42);
                        } else {
                            al_meet_arr[position] = true;
                            imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_41);
                        }
                        customSpinner.set_txt();
                    }
                });
            }
            return convertView;
        }
    }

    public class ObjInSpinner {
        int id;
        String txt;

        public ObjInSpinner() {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }
    }
}

