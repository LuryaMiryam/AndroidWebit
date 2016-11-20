package webit.bthereapp.CustemViews;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;

public class CustomSpinnerWhite extends LinearLayout {
    private CustomLightTextView mtv;
    private PopupWindow popupWindow;
    private AddOnClickListener addOnClickListener = null;
    private TextView mAlertTypeTv, mTextTv;
    private ListView listView;
    private Spinner spinner;
    private int position_ = -1;
    private Context mContext;
    private String spinner_text;
    private int spinner_opt, spinner_size;
    private LinearLayout mCustomSpinnerLl_in, mCustomSpinnerLl_in_;
    private LinearLayout mCustomSpinnerRl;
    private String[] strings;

    public CustomSpinnerWhite(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinnerWhite(Context context, AttributeSet attrs, int f) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custom_spinner_white, this);
        this.mContext = context;
        mtv = (CustomLightTextView) view.findViewById(R.id.al_type_spinner_);
        mCustomSpinnerLl_in_ = (LinearLayout) view.findViewById(R.id.c_s_ll_);
        mCustomSpinnerLl_in = (LinearLayout) view.findViewById(R.id.c_s_ll3_);
        mCustomSpinnerRl = (LinearLayout) view.findViewById(R.id.custom_spinner_ll_);
//        mCustomSpinnerRl.SE
        mTextTv = (TextView) view.findViewById(R.id.al_type_txt_);

        mAlertTypeTv = (TextView) view.findViewById(R.id.al_type_spinner_);
        mCustomSpinnerLl_in_.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindowsort();
                popupWindow.setWidth(mCustomSpinnerLl_in_.getWidth());

                popupWindow.showAsDropDown(v, 0, 0); // show popup like dropdown list

            }

        });


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomSpinner, 0, 0);

//        spinner_text = a.getText(R.styleable.CustomSpinner_customSpinnerText).toString();
        spinner_opt = a.getInt(R.styleable.CustomSpinner_customSpinnerType, 0);
        spinner_size = a.getInt(R.styleable.CustomSpinner_customSpinnerSize, 0);

//change the size of the spinner
        if (spinner_size == 0) {

            LayoutParams Params1 = (LayoutParams) mCustomSpinnerLl_in.getLayoutParams();
            Params1.weight = 2.0f;
            mCustomSpinnerLl_in.setLayoutParams(Params1);

        }


        //al_meet -put the option of the spinner in strings array, and
        if (spinner_opt == 0) {
            strings = getResources().getStringArray(R.array.al_meet);
            spinner_text = getResources().getString(R.string.al_meet_text);
        }
        if (spinner_opt == 1) {
            strings = getResources().getStringArray(R.array.al_cus_1);
            spinner_text = getResources().getString(R.string.al_cus_1_text);
        }
        if (spinner_opt == 2) {
            strings = getResources().getStringArray(R.array.al_cus_2);
            spinner_text = getResources().getString(R.string.al_cus_2_text);
        }
        if (spinner_opt == 3) {
            strings = getResources().getStringArray(R.array.al_up_1);
            spinner_text = getResources().getString(R.string.al_up_1_text);
        }
        if (spinner_opt == 4) {
            strings = getResources().getStringArray(R.array.al_up_2);
            spinner_text = getResources().getString(R.string.al_up_2_text);
        }
        if (spinner_opt == 5) {
            strings = getResources().getStringArray(R.array.al_min);
            spinner_text = getResources().getString(R.string.al_min_text);
        }
        if (spinner_opt == 6) {
            strings = getResources().getStringArray(R.array.initial_calendar_view_spinner);
            spinner_text = getResources().getString(R.string.initial_calendar_view);
            mAlertTypeTv.setText(strings[0]);
        }
        if (spinner_opt == 7) {
            strings = getResources().getStringArray(R.array.meetings_number_spinner);
            spinner_text = getResources().getString(R.string.meetings_number);
        }
        if (spinner_opt == 8) {
            strings = getResources().getStringArray(R.array.length_of_queues_spinner);
            spinner_text = getResources().getString(R.string.length_of_queues);
        }
        if (spinner_opt == 9) {
            strings = getResources().getStringArray(R.array.credit_card_type);
            spinner_text = getResources().getString(R.string.type_of_card);
            //change the background to transparent when its from payment credit card fragment
            mCustomSpinnerRl.setBackgroundColor(Color.TRANSPARENT);
        }
        if (spinner_opt == 10) {
            strings = getResources().getStringArray(R.array.credit_card_num_p);
            spinner_text = getResources().getString(R.string.number_of_Payments);
            //change the background to transparent when its from payment credit card fragment
            mCustomSpinnerRl.setBackgroundColor(Color.TRANSPARENT);
        }
        if (spinner_opt == 11) {
            strings = getResources().getStringArray(R.array.s_service);
            spinner_text = getResources().getString(R.string.s_service_text);

        }
        if (spinner_opt == 12) {
            strings = getResources().getStringArray(R.array.s_time);
            spinner_text = getResources().getString(R.string.s_time_text);
        }
        if (spinner_opt == 13) {
            strings = getResources().getStringArray(R.array.s_space_time);
            spinner_text = getResources().getString(R.string.s_space_time_text);
        }
        if (spinner_opt == 14) {
            strings = getResources().getStringArray(R.array.s_discount);
            spinner_text = getResources().getString(R.string.s_discount_text);
        }
        if (spinner_opt == 16) {
            strings = getResources().getStringArray(R.array.product_or_service);
            spinner_text = getResources().getString(R.string.product_or_service_text);
            mAlertTypeTv.setText(strings[0]);
        }
        mTextTv.setText(spinner_text);
//        mAlertTypeTv.setText(strings[0]);
        if (spinner_opt != 6 && spinner_opt != 16)
            mAlertTypeTv.setText(getResources().getString(R.string.choose));
    }

    //open list popup
    private void popupWindowsort() {

        popupWindow = new PopupWindow(this);

        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(strings));
        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_white, stringList);

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
//        popupWindow.setFocusable(true);
//        return popupWindow;
    }

    public void setAddOnClickListener(AddOnClickListener addOnClickListener) {
        this.addOnClickListener = addOnClickListener;
    }


    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                position_ = position;
                mAlertTypeTv.setText(strings[position]);
                popupWindow.dismiss();
                if (addOnClickListener != null)
                    addOnClickListener.addOnClick();
            }
        };
    }

    public int get_position() {
        return position_;
    }

    public void put_position(int position) {
        position_ = position;
        mAlertTypeTv.setText(strings[position]);
        if (addOnClickListener != null)
            addOnClickListener.addOnClick();
    }


    public class ListViewAdapter extends ArrayAdapter<String> {

        private ArrayList<String> strings;

        public ListViewAdapter(Context context, int resource, ArrayList<String> strings) {
            super(context, resource, strings);
            this.strings = strings;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.text_row_white, parent, false);

            } else {
            }
            String str = strings.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.type_text_);
            name.setText(str);
            return convertView;
        }
    }
}

