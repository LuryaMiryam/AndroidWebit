package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.SysAlertsListDM;
import webit.bthereapp.Entities.SysAlertsListItem;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.AlertsFragment;
import webit.bthereapp.Utils.Utils;

public class CustomSpinner extends LinearLayout {
    private PopupWindow popupWindow;
    private TextView mAlertTypeTv, mTextTv;
    private ListView listView;
    private int choose = -1, id_opt;
    private String[] s_;
    private Context mContext;
    private String spinner_text;
    private int spinner_opt, spinner_size;
    private LinearLayout mCustomSpinnerLl_in, mCustomSpinnerLl_in_, mCustomSpinnerLl_out;
    private LinearLayout mCustomSpinnerRl;
    RealmResults<SysAlertsListDM> results1;
    private boolean al_meet_arr[];
    private SysAlertsListItem itemsArray[], items[];
    private SysAlertsListDM s;

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int f) {
        super(context, attrs);
        init(context, attrs);
    }

    private void getData(int a) {
        for (int i = 0; i < results1.size(); i++) {
            s = results1.get(i);
            items[i] = new SysAlertsListItem();
            items[i].setItem_id(s.getiSysTableRowId());
            items[i].setItem_txt(s.getNvAletName());
        }
        itemsArray = items;
    }

    private void getData2(String[] ss, int a) {
        items = new SysAlertsListItem[ss.length];

        for (int i = 0; i < ss.length; i++) {
            items[i] = new SysAlertsListItem();
            items[i].setItem_id(i);
            items[i].setItem_txt(ss[i]);
        }
        itemsArray = items;
    }

    public int get_choose() {
        return choose;
    }

    public ArrayList<Integer> get_many_choose() {
        ArrayList<Integer> choose_many = new ArrayList<>();
        for (int i = 0; i < al_meet_arr.length; i++)
            if (al_meet_arr[i]) {
                choose_many.add(itemsArray[i].getItem_id());
            }
        return choose_many;
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custom_spinner, this);
        this.mContext = context;
        mCustomSpinnerLl_out = (LinearLayout) view.findViewById(R.id.l_out);
        mCustomSpinnerLl_in = (LinearLayout) view.findViewById(R.id.l_in);
        mCustomSpinnerLl_in_ = (LinearLayout) view.findViewById(R.id.c_s_ll);
        mCustomSpinnerRl = (LinearLayout) view.findViewById(R.id.custom_spinner_ll);
        mTextTv = (TextView) view.findViewById(R.id.al_type_txt);

        mAlertTypeTv = (TextView) view.findViewById(R.id.al_type_spinner);
        mCustomSpinnerLl_in_.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindowsort();
                popupWindow.setWidth(mCustomSpinnerLl_in.getWidth());

                popupWindow.showAsDropDown(v, 0, 0); // show popup like dropdown list

            }

        });

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomSpinner, 0, 0);
        spinner_opt = a.getInt(R.styleable.CustomSpinner_customSpinnerType, 0);
        id_opt = spinner_opt;
        spinner_size = a.getInt(R.styleable.CustomSpinner_customSpinnerSize, 0);


        //change the size of the spinner
        if (spinner_size == 0) {
            LinearLayout.LayoutParams Params1 = (LinearLayout.LayoutParams) mCustomSpinnerLl_in.getLayoutParams();
            Params1.weight = 1.0f;
            mCustomSpinnerLl_in.setLayoutParams(Params1);
            LinearLayout.LayoutParams Params2 = (LinearLayout.LayoutParams) mCustomSpinnerLl_out.getLayoutParams();
            Params2.weight = 1.0f;
            mCustomSpinnerLl_out.setLayoutParams(Params2);

        }

        Realm realm = Utils.getRealmInstance(getContext());

        //al_meet -put the option of the spinner in strings array, and
        if (spinner_opt == 0) {

            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 8).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(0);

            spinner_text = getResources().getString(R.string.al_meet_text);
            al_meet_arr = new boolean[itemsArray.length];
            if (itemsArray.length > 3)
                for (int i = 0; i < 4; i++) {

                    //CLOSE
                    al_meet_arr[i] = true;
                }

        }
        if (spinner_opt == 1) {  //type alert
            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 9).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(1);
            s_ = getResources().getStringArray(R.array.al_cus_1);
            spinner_text = getResources().getString(R.string.al_cus_1_text);
            al_meet_arr = new boolean[itemsArray.length];
            if (itemsArray.length > 2) {
                al_meet_arr[0] = false;
                al_meet_arr[1] = true;
                al_meet_arr[2] = false;
            }

        }
        if (spinner_opt == 2) {
            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 12).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(2);

            spinner_text = getResources().getString(R.string.al_cus_2_text);
        }
        if (spinner_opt == 3) {
            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 10).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(3);
            al_meet_arr = new boolean[itemsArray.length];
            if (itemsArray.length > 1) {
                al_meet_arr[0] = true;
                al_meet_arr[1] = false;
            }

            spinner_text = getResources().getString(R.string.al_up_1_text);
        }
        if (spinner_opt == 4) {
            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 12).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(4);

            spinner_text = getResources().getString(R.string.al_up_2_text);
        }
        if (spinner_opt == 5) {
            s_ = getResources().getStringArray(R.array.al_min);
            getData2(s_, 5);

            spinner_text = getResources().getString(R.string.al_min_text);
        }
        if (spinner_opt == 6) {
            s_ = getResources().getStringArray(R.array.initial_calendar_view_spinner);
            getData2(s_, 6);

            spinner_text = getResources().getString(R.string.initial_calendar_view);
        }
        if (spinner_opt == 7) {
            s_ = getResources().getStringArray(R.array.meetings_number_spinner);
            getData2(s_, 7);

            spinner_text = getResources().getString(R.string.meetings_number);
        }
        if (spinner_opt == 8) {
            s_ = getResources().getStringArray(R.array.length_of_queues_spinner);
            getData2(s_, 8);

            spinner_text = getResources().getString(R.string.length_of_queues);
        }
        if (spinner_opt == 9) {
            s_ = getResources().getStringArray(R.array.credit_card_type);
            getData2(s_, 9);

            spinner_text = getResources().getString(R.string.type_of_card);
            //change the background to transparent when its from payment credit card fragment
            mCustomSpinnerRl.setBackgroundColor(Color.TRANSPARENT);
        }
        if (spinner_opt == 10) {
            s_ = getResources().getStringArray(R.array.credit_card_num_p);
            getData2(s_, 10);

            spinner_text = getResources().getString(R.string.number_of_Payments);
            //change the background to transparent when its from payment credit card fragment
            mCustomSpinnerRl.setBackgroundColor(Color.TRANSPARENT);
        }
        if (spinner_opt == 11) {
            s_ = getResources().getStringArray(R.array.s_service);
            getData2(s_, 11);

            spinner_text = getResources().getString(R.string.s_service_text);
        }
        if (spinner_opt == 12) {
            s_ = getResources().getStringArray(R.array.s_time);
            getData2(s_, 12);

            spinner_text = getResources().getString(R.string.s_time_text);
        }
        if (spinner_opt == 13) {
            s_ = getResources().getStringArray(R.array.s_space_time);
            getData2(s_, 13);

            spinner_text = getResources().getString(R.string.s_space_time_text);
        }
        if (spinner_opt == 14) {
            s_ = getResources().getStringArray(R.array.s_discount);
            getData2(s_, 14);

            spinner_text = getResources().getString(R.string.s_discount_text);
        }
        if (spinner_opt == 17) {
            results1 = realm.where(SysAlertsListDM.class).equalTo("iTableId", 12).findAll();
            items = new SysAlertsListItem[results1.size()];
            getData(2);
            mTextTv.setTextColor(Color.BLACK);
            mCustomSpinnerLl_in_.setBackground(context.getResources().getDrawable(R.drawable.rect_to_spinner));
            mCustomSpinnerRl.setBackgroundColor(Color.TRANSPARENT);
            spinner_text = getResources().getString(R.string.s_view_calendar_text);
            choose = itemsArray[2].getItem_id();
            mAlertTypeTv.setText(itemsArray[2].getItem_txt());
        }
        mTextTv.setText(spinner_text);
        if(itemsArray.length>0) {
            if (spinner_opt == 0 || spinner_opt == 1 || spinner_opt == 2 || spinner_opt == 3 || spinner_opt == 4) {
                mAlertTypeTv.setText(itemsArray[0].getItem_txt());
                choose = itemsArray[0].getItem_id();
            }
        }
        if (spinner_opt == 1) {
            if (al_meet_arr.length > 0) {
                al_meet_arr[1] = true;
                mAlertTypeTv.setText(s_[1]);
            }
        }
        if (spinner_opt == 0) {
            for (int i = 0; i < al_meet_arr.length; i++)
                al_meet_arr[i] = true;
            String s = itemsArray[0].getItem_txt().toString();
            mAlertTypeTv.setText(s);
        }

    }

    //set the previous choose
    public void set_old(int a) {
        if (a != -1) {
            choose = a;

            for (int i = 0; i < itemsArray.length; i++)
                if (itemsArray[i].getItem_id() == a) {
                    mAlertTypeTv.setText(itemsArray[i].getItem_txt());
                }
        }
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
    }

    //set the text after choose
    public void set_txt() {
        String s = "";
        for (int i = 0; i < al_meet_arr.length; i++)
            if (al_meet_arr[i]) {
                if (!s.equals(""))
                    s += ", ";
                s += itemsArray[i].getItem_txt().toString();
                if (s != null && s.length() > 19) {
                    s = s.substring(0, 19);
                    s += "...";
                }
            }
        if (spinner_opt == 0 && al_meet_arr[0]&& al_meet_arr.length>0)
            s = itemsArray[0].getItem_txt().toString();
        mAlertTypeTv.setText(s);
    }

    //open the list popup
    private void popupWindowsort() {
        //scroll the page down if the buttom spinners clicked
        if (spinner_opt == 3 || spinner_opt == 4)
            AlertsFragment.getInstance().scroll_down();
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

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (spinner_opt == 0 && mAlertTypeTv.getText().toString().equals("")) {
                    al_meet_arr[0] = true;
                    al_meet_arr[1] = true;
                    al_meet_arr[2] = true;
                    al_meet_arr[3] = true;
                    set_txt();
                }
                if (spinner_opt == 1 && mAlertTypeTv.getText().toString().equals("")) {
                    al_meet_arr[0] = false;
                    al_meet_arr[1] = true;
                    al_meet_arr[2] = false;
                    set_txt();
                }
                if (spinner_opt == 3 && mAlertTypeTv.getText().toString().equals("")) {
                    al_meet_arr[0] = true;
                    al_meet_arr[1] = false;
                    set_txt();
                }
                //scroll the page down if the top spinners clicked
                if (spinner_opt == 3 || spinner_opt == 4)
                    AlertsFragment.getInstance().scroll_up();
            }
        });
    }

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                if (id_opt == 0 || id_opt == 1 || id_opt == 3) {
                } else {
                    //if not many choose
                    mAlertTypeTv.setText(itemsArray[position].getItem_txt());
                    choose = itemsArray[position].getItem_id();

                    popupWindow.dismiss();

                }
            }
        };
    }


    public class ListViewAdapter extends ArrayAdapter<SysAlertsListItem> {

        private SysAlertsListItem sysAlertsListItem;
        private Context context_;
        private SysAlertsListItem[] items;
        private CustomSpinner customSpinner;

        public ListViewAdapter(Context context, int resource, SysAlertsListItem[] items, CustomSpinner customSpinner) {
            super(context, resource, items);
            this.context_ = context;
            this.customSpinner = customSpinner;
            this.items = items;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.text_row_, parent, false);
            }
            final SysAlertsListItem sysAlertsListItem = items[position];
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            final ImageView imageView = (ImageView) convertView.findViewById(R.id.image);
            name.setText(sysAlertsListItem.getItem_txt());
            RelativeLayout r_all = (RelativeLayout) convertView.findViewById(R.id.r_all);
            if (al_meet_arr != null)
                if (!al_meet_arr[position])
                    imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_42);
                else
                    imageView.setImageResource(R.drawable.supplier_galaxy_icons_x1_41);
            //if many choose
            if (id_opt == 0 || id_opt == 1 || id_opt == 3) {
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

                        customSpinner.set_txt();
                        notifyDataSetChanged();
                    }
                });

            }
            return convertView;
        }

    }
}

