package  webit.bthereapp.Screens.Supplier;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.realm.Realm;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.SysAlertsListDM;
import webit.bthereapp.R;
import webit.bthereapp.Utils.Utils;

public class CustomSpinnerPhone extends LinearLayout {


    private PopupWindow popupWindow;
    private CustomLightTextView mAlertTypeTv;
    private ListView listView;
    private int choose = -1;
    private String[] s_;
    private Context mContext;
    private RelativeLayout  width_all;
    private EditText phone;
    private ObjInSpinner  itemsArray[], items[];

    public CustomSpinnerPhone(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CustomSpinnerPhone(Context context, AttributeSet attrs, int f) {
        super(context, attrs);
        init(context, attrs);
    }


    public int get_choose() {
        return choose;
    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_custom_spinner_phone, this);
        this.mContext = context;
        width_all = (RelativeLayout) view.findViewById(R.id.r_all);
        mAlertTypeTv = (CustomLightTextView) view.findViewById(R.id.txt);
        phone = (EditText) view.findViewById(R.id.ed_edit);
        s_ = getResources().getStringArray(R.array.phone_start);
        getData2(s_);


        width_all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                popupWindowsort();
                popupWindow.setWidth(width_all.getWidth());
                popupWindow.showAsDropDown(width_all, 0, 0); // show popup like dropdown list
            }

        });
    }


    private void getData2(String[] ss) {
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
        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_large, itemsArray);

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

    private AdapterView.OnItemClickListener onItemClickListener() {
        return new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                mAlertTypeTv.setText(itemsArray[position].getTxt());
                int ii_n;
                choose = itemsArray[position].getId();

                popupWindow.dismiss();
            }
        };
    }

    public boolean isValid() {
        if (phone != null && phone.length() < 7) {
            phone.setError(mContext.getString(R.string.error_empty));
            return false;
        }
        return true;
    }


    public class ListViewAdapter extends ArrayAdapter<ObjInSpinner> {

        private ObjInSpinner objInSpinner;
        private Context context_;
        private ObjInSpinner[] items;
        private final String TAG = ListViewAdapter.class.getSimpleName();


        public ListViewAdapter(Context context, int resource, ObjInSpinner[] items) {
            super(context, resource, items);
            this.context_ = context;
            this.items = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // inflate layout from xml
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            // If holder not exist then locate all view from UI file.
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.text_row_, parent, false);
            }
            ObjInSpinner objInSpinner = items[position];
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            name.setText(objInSpinner.getTxt());
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

