package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import webit.bthereapp.R;

public class CreditCardValidity extends LinearLayout {

    private PopupWindow popupWindow_m, popupWindow_y;
    private boolean click = true;
    private TextView mValidityTv_m, mValidityTv_y;
    private ListView listView_m, listView_y;
    private Context mContext;
    private LinearLayout mYearLl, mMonthLl;
    private String[] strings1, strings2;


    public CreditCardValidity(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_credit_card_validity, this);
        this.mContext = context;

        function_Validity_year();
        mMonthLl = (LinearLayout) view.findViewById(R.id.c_s_ll_);
        mValidityTv_m = (TextView) view.findViewById(R.id.validity_spinner_m);
        mMonthLl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindowMonth();
                popupWindow_m.setWidth(mMonthLl.getWidth());
                popupWindow_m.showAsDropDown(v, 0, 0); // show popup like dropdown list
                click = false;
            }

        });
        mYearLl = (LinearLayout) findViewById(R.id.c_s_ll);
        mValidityTv_y = (TextView) view.findViewById(R.id.validity_spinner_y);
        mYearLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowYear();
                popupWindow_y.setWidth(mYearLl.getWidth());
                popupWindow_y.showAsDropDown(v, 0, 0); // show popup like dropdown list
                click = false;
            }
        });

        strings1 = getResources().getStringArray(R.array.months);
        mValidityTv_m.setText(R.string.month);
        mValidityTv_y.setText(R.string.year);

    }

    private void function_Validity_year() {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        strings2 = new String[10];
        for (int i = 0; i < 10; i++) {
            strings2[i] = String.valueOf(year + i);
        }
    }

    private void popupWindowMonth() {

        popupWindow_m = new PopupWindow(this);
        ArrayList<String> stringList1 = new ArrayList<String>(Arrays.asList(strings1));
        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_, stringList1);

        // the drop down list is a list view
        listView_m = new ListView(mContext);

        // set our adapter and pass our pop up window contents
        listView_m.setAdapter(adapter);

        // set on item selected
        listView_m.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mValidityTv_m.setText(strings1[position]);
                popupWindow_m.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow_m.setFocusable(true);
        popupWindow_m.setWidth(250);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow_m.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow_m.setContentView(listView_m);
        popupWindow_m.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_m.setOutsideTouchable(true);

    }


    private void popupWindowYear() {

        popupWindow_y = new PopupWindow(this);
        ArrayList<String> stringList2 = new ArrayList<String>(Arrays.asList(strings2));
        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_, stringList2);

        // the drop down list is a list view
        listView_y = new ListView(mContext);

        // set our adapter and pass our pop up window contents
        listView_y.setAdapter(adapter);

        // set on item selected
//        listView_y.setOnItemClickListener(onItemClickListener());
        listView_y.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mValidityTv_y.setText(strings2[position]);
                popupWindow_y.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow_y.setFocusable(true);
        popupWindow_y.setWidth(250);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow_y.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        // set the listview as popup content
        popupWindow_y.setContentView(listView_y);
        popupWindow_y.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_y.setOutsideTouchable(true);

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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.text_row_, parent, false);
            }
            String str = strings.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            name.setText(str);
            return convertView;
        }
    }
}

