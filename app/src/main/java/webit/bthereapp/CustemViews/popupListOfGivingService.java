package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.R;

/**
 * Created by user on 15/06/2016.
 */
public class popupListOfGivingService extends LinearLayout {
    Context mContext;
    LinearLayout mSpinner;
    private PopupWindow popupWindow;
    private String strings[];
    ListView listView;
    TextView mChooseTv;

    public popupListOfGivingService(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(final Context context) {
        this.mContext = context;
        if(ProviderDetailsObj.getInstance().getObjProviderGeneralDetails()!=null&&ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders()!=null)
        if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().size() > 0) {
            strings = new String[ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().size() + 1];
            int j = 0;
            strings[j++] = getResources().getStringArray(R.array.staff_member_list)[0];
        } else
            strings = getResources().getStringArray(R.array.staff_member_list);
        final View view = View.inflate(context, R.layout.popup_list_giving_service, this);
        mSpinner = (LinearLayout) view.findViewById(R.id.spinner);
        mChooseTv = (TextView) view.findViewById(R.id.choose);
        mSpinner.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowsort();
                popupWindow.setWidth(mSpinner.getWidth());
                popupWindow.showAsDropDown(v, 0, 0);
            }
        });

    }

    //open the list popup
    private void popupWindowsort() {
        popupWindow = new PopupWindow();
        ArrayList<String> stringList = new ArrayList<String>(Arrays.asList(strings));

        ListViewAdapter adapter = new ListViewAdapter(mContext, R.layout.text_row_, stringList);

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
                mChooseTv.setText(strings[position]);
                popupWindow.dismiss();
            }
        };
    }
}