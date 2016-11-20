package webit.bthereapp.CustemViews;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import webit.bthereapp.CustemViews.Fonts.CustomLightEditText;
import webit.bthereapp.CustemViews.Fonts.CustomLightTextView;
import webit.bthereapp.DM.CalendarPropertiesRealm;
import webit.bthereapp.Entities.CalendarProperties;
import webit.bthereapp.Intreface.AddOnClickListener;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Register.BusinessRegister.BusinessGeneralData;
import webit.bthereapp.Screens.Register.MainActivity.MainActivity;

public class Bus_G_CalendarSet extends LinearLayout implements View.OnClickListener {
    Context mContext;
    private YesOrNo_Black limitNumQueuesYN, googleDailyYN;
    CustomLightEditText mNumMeetings, mQueueLength;
    private boolean py = false, limitNumQueues = false;
    LinearLayout mDairyL;
    int dailyViewPosition = 1;
    private static String date = "";

    public Bus_G_CalendarSet(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public void init(final Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.activity_bus_g_calendar_set, this);
        this.mContext = context;

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        mNumMeetings = (CustomLightEditText) view.findViewById(R.id.calendar_num_meetings);
        mQueueLength = (CustomLightEditText) view.findViewById(R.id.calendar_queues_length);
        mDairyL = (LinearLayout) view.findViewById(R.id.dairy_calendar);
        mDairyL.setVisibility(LinearLayout.GONE);
        googleDailyYN = (YesOrNo_Black) view.findViewById(R.id.cal_yn2);
        limitNumQueuesYN = (YesOrNo_Black) view.findViewById(R.id.cal_yn1);

        limitNumQueues = true;
        mDairyL.setVisibility(LinearLayout.VISIBLE);
        limitNumQueuesYN.change_status(true);

        limitNumQueuesYN.setAddOnClickListenerX(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mDairyL.setVisibility(LinearLayout.GONE);
                py = !py;
                limitNumQueues = false;
            }
        });
        limitNumQueuesYN.setAddOnClickListenerV(new AddOnClickListener() {
            @Override
            public void addOnClick() {
                mDairyL.setVisibility(LinearLayout.VISIBLE);
                py = !py;
                limitNumQueues = true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public boolean checkAllFields() {
        boolean flag = true;
        if (limitNumQueuesYN.getYes_no_state() == -1)
            flag = false;
        return flag;
    }

    public CalendarProperties getCalendarProperties() {
        CalendarProperties calendarProperties = new CalendarProperties();
        calendarProperties.setiFirstCalendarViewType(dailyViewPosition);

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_WEEK);
        String mm = String.valueOf(month);
        String dd = String.valueOf(day);
        date = String.valueOf(year) + (month < 10 ? "0" + mm : mm) + (day < 10 ? "0" + dd : dd);

        calendarProperties.setDtCalendarOpenDate(date);
        if (limitNumQueues) {
            calendarProperties.setbLimitSeries(true);
            if (!(mNumMeetings.getText().toString() .equals("")))
                calendarProperties.setiMaxServiceForCustomer(Integer.valueOf(mNumMeetings.getText().toString()));
            else
                calendarProperties.setiMaxServiceForCustomer(0);
            int i=0;
            if (!(mQueueLength.getText().toString().equals("")))
               i=Integer.valueOf(mQueueLength.getText().toString());
                calendarProperties.setiPeriodInWeeksForMaxServices(i);
        } else {
            calendarProperties.setbLimitSeries(false);
            calendarProperties.setiMaxServiceForCustomer(0);
            calendarProperties.setiPeriodInWeeksForMaxServices(0);
        }
        return calendarProperties;
    }

    public void setCalendarProperties(CalendarPropertiesRealm calendarPropertiesRealm) {
        if(calendarPropertiesRealm.isbLimitSeries()) {
            mNumMeetings.setText(calendarPropertiesRealm.getiMaxServiceForCustomer()+"");
            mQueueLength.setText(calendarPropertiesRealm.getiPeriodInWeeksForMaxServices()+"");
        }
        else {
            limitNumQueuesYN.change_status(false);
            mDairyL.setVisibility(LinearLayout.GONE);
            py = !py;
        }
    }


    //=========================
    public class ListViewAdapter extends ArrayAdapter<String> {
        private Context context_;
        private ArrayList<String> strings;

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
            } else {
            }

            String str = strings.get(position);
            TextView name = (TextView) convertView.findViewById(R.id.type_text);
            name.setText(str);
            return convertView;
        }
    }
}





