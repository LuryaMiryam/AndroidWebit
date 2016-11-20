package webit.bthereapp.Screens.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import webit.bthereapp.Screens.Calendar.CalanderDisplayMonth;

/**
 * Created by user on 16/03/2016.
 */
public class MonthPagerAdapter extends FragmentStatePagerAdapter implements ViewPager.PageTransformer {
    public Calendar mCalendar = Calendar.getInstance();
    public int year, month;
    ViewPager mViewPager;
    TextView mShowDate;
    DepthPageTransformer depthPageTransformer;
    Fragment getFr;

    public MonthPagerAdapter(FragmentManager fm, ViewPager viewPager, TextView date, Fragment getFr) {
        super(fm);
        mCalendar = Calendar.getInstance();
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH);
        mViewPager = viewPager;
        mShowDate = date;
        this.getFr=getFr;
        depthPageTransformer = new DepthPageTransformer();
        mViewPager.setPageTransformer(true, depthPageTransformer);

    }

    @Override
    public Fragment getItem(int position) {
        if(position==200) {
            SimpleDateFormat montD = new SimpleDateFormat("MMMM");
            Calendar nameCalendar = Calendar.getInstance();
            String month_name = montD.format(nameCalendar.getTime());
            mShowDate.setText(month_name + "  " + nameCalendar.get(Calendar.YEAR) + "");
        }
        Calendar CurrentCalendar = Calendar.getInstance();
        int numMonth = position - 200;
        CurrentCalendar.add(Calendar.MONTH, numMonth);
        CalanderDisplayMonth fragment = new CalanderDisplayMonth(CurrentCalendar.get(Calendar.YEAR), CurrentCalendar.get(Calendar.MONTH),getFr);
        return fragment;
    }

    @Override
    public int getCount() {
        return 400;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }

    @Override
    public void transformPage(View page, float position) {

    }


    public class DepthPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.75f;
        int side = 2;


        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the right.
                side = 1;
                view.setAlpha(0);


            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1);
                view.setTranslationX(0);
                view.setScaleX(1);
                view.setScaleY(1);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the left.
                side = 0;
                view.setAlpha(0);
            }
        }
    }
}