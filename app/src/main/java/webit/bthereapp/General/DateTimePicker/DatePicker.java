package webit.bthereapp.General.DateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import webit.bthereapp.R;


public class DatePicker extends FrameLayout {

    private Context mContext;
    private NumberPicker mDayPicker;
    private NumberPicker mMonthPicker;
    private NumberPicker mYearPicker;
    private Calendar mCalendar;
    private String[] mMonthDisplay;

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mCalendar = Calendar.getInstance();
        initMonthDisplay();
        ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.date_picker, this, true);
        mDayPicker = (NumberPicker) findViewById(R.id.date_day);
        mMonthPicker = (NumberPicker) findViewById(R.id.date_month);
        mYearPicker = (NumberPicker) findViewById(R.id.date_year);

        mDayPicker.setMinValue(1);
        mDayPicker.setMaxValue(31);
        mDayPicker.setValue(20);
        mDayPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);

        mMonthPicker.setMinValue(0);
        mMonthPicker.setMaxValue(11);
        mMonthPicker.setDisplayedValues(mMonthDisplay);
        mMonthPicker.setValue(mCalendar.get(Calendar.MONTH));

        mYearPicker.setMinValue(1950);
        mYearPicker.setMaxValue(mCalendar.get(Calendar.YEAR));
        mYearPicker.setValue(mCalendar.get(Calendar.YEAR));

        mMonthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCalendar.set(Calendar.MONTH, newVal);
                updateDate();
            }
        });
        mDayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCalendar.set(Calendar.DATE, newVal);
                updateDate();
            }
        });
        mYearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCalendar.set(Calendar.YEAR, newVal);
                updateDate();
            }
        });

        updateDate();

    }

//    private void initMonthDisplay() {
//        mMonthDisplay = new String[12];
//        mMonthDisplay[0] = "Jan";
//        mMonthDisplay[1] = "Feb";
//        mMonthDisplay[2] = "Mar";
//        mMonthDisplay[3] = "Apr";
//        mMonthDisplay[4] = "May";
//        mMonthDisplay[5] = "Jun";
//        mMonthDisplay[6] = "Jul";
//        mMonthDisplay[7] = "Aug";
//        mMonthDisplay[8] = "Sep";
//        mMonthDisplay[9] = "Oct";
//        mMonthDisplay[10] = "Nov";
//        mMonthDisplay[11] = "Dec";
//    }

    private void initMonthDisplay() {
        mMonthDisplay = new String[12];
        mMonthDisplay[0] = getResources().getString(R.string.m1);
        mMonthDisplay[1] = getResources().getString(R.string.m2);
        mMonthDisplay[2] = getResources().getString(R.string.m3);
        mMonthDisplay[3] = getResources().getString(R.string.m4);
        mMonthDisplay[4] = getResources().getString(R.string.m5);
        mMonthDisplay[5] = getResources().getString(R.string.m6);
        mMonthDisplay[6] = getResources().getString(R.string.m7);
        mMonthDisplay[7] = getResources().getString(R.string.m8);
        mMonthDisplay[8] = getResources().getString(R.string.m9);
        mMonthDisplay[9] = getResources().getString(R.string.m10);
        mMonthDisplay[10] = getResources().getString(R.string.m11);
        mMonthDisplay[11] = getResources().getString(R.string.m12);
    }

    private void updateDate() {
        System.out.println("Month: " + mCalendar.get(Calendar.MONTH) + " Max: " + mCalendar.getActualMaximum(Calendar.DATE));
        mDayPicker.setMinValue(mCalendar.getActualMinimum(Calendar.DATE));
        mDayPicker.setMaxValue(mCalendar.getActualMaximum(Calendar.DATE));
        mDayPicker.setValue(mCalendar.get(Calendar.DATE));
        mMonthPicker.setValue(mCalendar.get(Calendar.MONTH));
        mYearPicker.setValue(mCalendar.get(Calendar.YEAR));
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public String getDate() {
        SimpleDateFormat format3 = new SimpleDateFormat("dd/MM/yyyy");
        String date = mYearPicker.getValue() + "/" + (mMonthPicker.getValue() + 1) + "/" + mDayPicker.getValue();
        try {
            return String.valueOf(format3.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public int getDay() {
        return mCalendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMonth() {
        return mCalendar.get(Calendar.MONTH);
    }

    public int getYear() {
        return mCalendar.get(Calendar.YEAR);
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
        updateDate();
    }

}
