package webit.bthereapp.CustemViews;

import android.content.Context;
import android.graphics.Typeface;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import webit.bthereapp.R;

/**
 * Created by User on 07/04/2016.
 */
public class CustomDatePicker extends RelativeLayout implements AbsListView.OnScrollListener, View.OnTouchListener {
    private Context mContext;
    private ListView years_lv, months_lv, days_lv;
    private ArrayList<String> years, months, days;
    private int positionSelectedY = 0, positionParentY = 0;
    private int positionSelectedM = 0, positionParentM = 0;
    private int positionSelectedD = 0, positionParentD = 0;
    private boolean scrollPerformed = false;
    CustomDatePickerAdapter adapterY, adapterM, adapterD;
    Typeface font, font1;

    public CustomDatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(mContext, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        final View view = View.inflate(context, R.layout.custom_date_picker, this);
        years_lv = (ListView) view.findViewById(R.id.year);
        months_lv = (ListView) view.findViewById(R.id.month);
        days_lv = (ListView) view.findViewById(R.id.day);
        font = Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/OpenSansHebrew-Bold.ttf");
        font1 = Typeface.createFromAsset(context.getAssets(), "OpenSansHebrew/OpenSansHebrew-Light.ttf");
        initList();
    }

    public void initList() {
        years = new ArrayList<>();
        months = new ArrayList<>();
        days = new ArrayList<>();

        for (int i = 1900; i < 3001; i++) {
            years.add(String.valueOf(i));
        }

        months.add(getResources().getString(R.string.m1));
        months.add(getResources().getString(R.string.m2));
        months.add(getResources().getString(R.string.m3));
        months.add(getResources().getString(R.string.m4));
        months.add(getResources().getString(R.string.m5));
        months.add(getResources().getString(R.string.m6));
        months.add(getResources().getString(R.string.m7));
        months.add(getResources().getString(R.string.m8));
        months.add(getResources().getString(R.string.m9));
        months.add(getResources().getString(R.string.m10));
        months.add(getResources().getString(R.string.m11));
        months.add(getResources().getString(R.string.m12));

        for (int i = 1; i <= 30; i++) {
            days.add(String.valueOf(i));
        }

        adapterY = new CustomDatePickerAdapter(mContext, R.layout.custom_date_item, years);
        years_lv.setAdapter(adapterY);
        years_lv.setSelectionFromTop(adapterY.MIDDLE, 0);
        adapterM = new CustomDatePickerAdapter(mContext, R.layout.custom_date_item, months);
        months_lv.setAdapter(adapterM);
        months_lv.setSelectionFromTop(adapterM.MIDDLE, 0);
        adapterD = new CustomDatePickerAdapter(mContext, R.layout.custom_date_item, days);
        days_lv.setAdapter(adapterD);
        days_lv.setSelectionFromTop(adapterD.MIDDLE, 0);

        years_lv.setOnScrollListener(this);
        months_lv.setOnScrollListener(this);
        days_lv.setOnScrollListener(this);
        months_lv.setOnTouchListener(this);
        days_lv.setOnTouchListener(this);
        years_lv.setOnTouchListener(this);
    }

    private void updateView(int index, ListView lv) {
        View v = lv.getChildAt(index -
                lv.getFirstVisiblePosition());

        if (v == null)
            return;

        TextView t1 = (TextView) v.findViewById(R.id.txt);
        t1.setTextColor(getResources().getColor(R.color.color4));
        t1.setTextSize(21);
        t1.setTypeface(font);
    }

    private void updateView2(int index, ListView lv) {
        View v = lv.getChildAt(index -
                lv.getFirstVisiblePosition());
        if (v == null)
            return;
        TextView t1 = (TextView) v.findViewById(R.id.txt);
        t1.setTextColor(getResources().getColor(R.color.color_white));
        t1.setTextSize((float) 18.5);
        t1.setTypeface(font1);
    }

    private void updateViewParent(int index, ListView lv) {
        View v = lv.getChildAt(index -
                lv.getFirstVisiblePosition());
        if (v == null)
            return;

        TextView t1 = (TextView) v.findViewById(R.id.txt);
        t1.setTextColor(getResources().getColor(R.color.color_white));
        t1.setTextSize((float) 13.5);
        t1.setTypeface(font1);
    }

    private void initListDays(int year, int month) {
        days.clear();
        Calendar mycal = new GregorianCalendar(year, month - 1, 1);
        int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= daysInMonth; i++) {
            days.add(String.valueOf(i));
        }
        adapterD.notifyDataSetChanged();
    }

    public void getPosition() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        for (int i = 0; i < years.size(); i++) {
            if (years.get(i).equals(today.year))
                years_lv.setSelection(i);
        }
        for (int i = 0; i < months.size(); i++) {
            if (months.get(i).equals(today.month))
                months_lv.setSelection(i);
        }

        initListDays(today.year, today.month);

        for (int i = 0; i < days.size(); i++) {
            if (days.get(i).equals(today.monthDay))
                days_lv.setSelection(i);
        }
        adapterM.notifyDataSetChanged();
        adapterY.notifyDataSetChanged();
        adapterD.notifyDataSetChanged();
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            switch (view.getId()) {
                case R.id.year: {
                    updateViewParent(positionSelectedY - 2, days_lv);
                    updateViewParent(positionSelectedY + 2, days_lv);
                    updateView(positionSelectedY, years_lv);
                    updateView2(positionSelectedY - 1, years_lv);
                    updateView2(positionSelectedY + 1, years_lv);
                    break;
                }
                case R.id.month: {
                    initListDays(positionSelectedY, positionSelectedM);
                    updateViewParent(positionSelectedM - 2, days_lv);
                    updateViewParent(positionSelectedM + 2, days_lv);
                    updateView(positionSelectedM, months_lv);
                    updateView2(positionSelectedM - 1, months_lv);
                    updateView2(positionSelectedM + 1, months_lv);

                    break;
                }
                case R.id.day: {
                    updateViewParent(positionSelectedD - 2, days_lv);
                    updateViewParent(positionSelectedD + 2, days_lv);
                    updateView(positionSelectedD, days_lv);
                    updateView2(positionSelectedD - 1, days_lv);
                    updateView2(positionSelectedD + 1, days_lv);

                    break;
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        visibleItemCount = visibleItemCount - 1;
        int half = visibleItemCount / 2;
        if (visibleItemCount % 2 != 0) {
            half = half + 1;
        }

        switch (view.getId()) {
            case R.id.year: {
                scrollPerformed = true;
                positionSelectedY = firstVisibleItem + half;
                break;
            }
            case R.id.month: {
                scrollPerformed = true;
                positionSelectedM = firstVisibleItem + half;
                break;
            }
            case R.id.day: {
                scrollPerformed = true;
                positionSelectedD = firstVisibleItem + half;
                break;
            }
        }
    }

    public String getYe() {
        return ((CustomDatePickerAdapter) years_lv.getAdapter()).getSelected(positionSelectedY);
    }

    public String getMo() {
        return ((CustomDatePickerAdapter) months_lv.getAdapter()).getSelected(positionSelectedM);
    }

    public String getDa() {
        return ((CustomDatePickerAdapter) days_lv.getAdapter()).getSelected(positionSelectedD);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.requestFocus();
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    @Override
    public String toString() {
        return getDa() + " " + getMo() + " " + getYe();
    }
}