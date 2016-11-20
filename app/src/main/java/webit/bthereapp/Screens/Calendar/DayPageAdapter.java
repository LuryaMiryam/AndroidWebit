package webit.bthereapp.Screens.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import webit.bthereapp.Screens.Customer.OrderServiceFragment;

/**
 * Created by user on 20/03/2016.
 */
public class DayPageAdapter extends FragmentStatePagerAdapter {
    private TextView mShowTv, mLittleDate;
    public Fragment fragment;
    public Calendar currentCalendar, sendCalender;
    Context context;
    Fragment getFr;

    public Calendar getSentCalender() {
        return sentCalender;
    }

    public void setSentCalender(Calendar sentCalender) {
        this.sentCalender = sentCalender;
    }

    public Calendar sentCalender;


    public DayPageAdapter(Context context, FragmentManager fm, TextView showTv, TextView dateLittleTV, Fragment getFr) {

        super(fm);
        mShowTv = showTv;
        this.context = context;
        mLittleDate = dateLittleTV;
        this.getFr = getFr;
    }



    @Override
    public Fragment getItem(int position) {
        fragment = null;
        if (position == 200) {
            Calendar nameCalendar;
            SimpleDateFormat day_format = new SimpleDateFormat("EEEE");
            nameCalendar = Calendar.getInstance();
            if (sentCalender != null)
                nameCalendar.set(sentCalender.get(Calendar.YEAR), sentCalender.get(Calendar.MONTH), sentCalender.get(Calendar.DAY_OF_MONTH));
            String day_name = day_format.format(nameCalendar.getTime());
            mShowTv.setText(day_name);
            SimpleDateFormat format = new SimpleDateFormat("d MMMM  yyyy");
            String dateString = format.format(nameCalendar.getTime());
            mLittleDate.setText(dateString);
        }
        //start to set from a spesific date when click on a day in month or week
        currentCalendar = Calendar.getInstance();
        if (sentCalender != null)
            currentCalendar.set(sentCalender.get(Calendar.YEAR), sentCalender.get(Calendar.MONTH), sentCalender.get(Calendar.DAY_OF_MONTH));
        int dayOfMonth = position - 200;
        currentCalendar.add(Calendar.DAY_OF_MONTH, dayOfMonth);
        sendCalender = Calendar.getInstance();
        //needs to go to the day before to send the day that is displayed
        sendCalender.set(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH) - 1);

        if (getFr instanceof NextFragment)
            ((NextFragment) getFr).setDayForWeek(sendCalender);
        else if (getFr instanceof OrderServiceFragment)
            ((OrderServiceFragment) getFr).setDayForWeek(sendCalender);

        fragment = CalanderDisplayDay.newInstance(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DATE));
        return fragment;

    }


    @Override
    public int getCount() {
        return 400;
    }
}
