package webit.bthereapp.Screens.Calendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import webit.bthereapp.Screens.Calendar.CalanderDisplayWeek;

/**
 * Created by user on 20/03/2016.
 */
public class WeekPageAdapter extends FragmentStatePagerAdapter implements ViewPager.PageTransformer {
    public int year, month, date;
    ViewPager mViewPager;
    TextView mShowDate, mLittleDate;
    public Calendar currentCalendar;
    DepthPageTransformer depthPageTransformer;
    public Fragment fragment, getFr;
    Calendar getCalFromDate;

    public WeekPageAdapter(FragmentManager fm, ViewPager viewPager, TextView dateTV, TextView littleDate, Fragment getFr) {
        super(fm);
        mViewPager = viewPager;
        mShowDate = dateTV;
        mLittleDate = littleDate;
        this.getFr = getFr;
        depthPageTransformer = new DepthPageTransformer();
        mViewPager.setPageTransformer(true, depthPageTransformer);
    }

    public WeekPageAdapter(FragmentManager fm, ViewPager viewPager, TextView dateTV, TextView littleDate, Fragment getFr, Calendar getCalFromDate) {
        super(fm);
        mViewPager = viewPager;
        mShowDate = dateTV;
        mLittleDate = littleDate;
        this.getFr = getFr;
        this.getCalFromDate = getCalFromDate;
        depthPageTransformer = new DepthPageTransformer();
        mViewPager.setPageTransformer(true, depthPageTransformer);

    }


    @Override
    public Fragment getItem(int position) {
        fragment = null;
        if (position == 200) {
            Calendar nameCalendar = Calendar.getInstance();
            if (getCalFromDate != null) {
                nameCalendar.set(getCalFromDate.get(Calendar.YEAR), getCalFromDate.get(Calendar.MONTH), getCalFromDate.get(Calendar.DAY_OF_MONTH));
                int week = nameCalendar.get(Calendar.WEEK_OF_YEAR);
                int year = nameCalendar.get(Calendar.YEAR);
                // get the first day of the week of the day
                nameCalendar.clear();
                nameCalendar.set(Calendar.WEEK_OF_YEAR, week);
                nameCalendar.set(Calendar.YEAR, year);

                  // Now get the first day of week.
//                Date date = calendar.getTime();
            }
            Calendar helpCalendar = Calendar.getInstance();
            helpCalendar.set(nameCalendar.get(Calendar.YEAR), nameCalendar.get(Calendar.MONTH), nameCalendar.get(Calendar.DAY_OF_MONTH));
            int dayOfweek = nameCalendar.get(Calendar.DAY_OF_WEEK);
            int firstofweek = nameCalendar.get(Calendar.DATE) - dayOfweek + 1;
            if (firstofweek < 1) {
                nameCalendar.add(Calendar.MONTH, -1);
                int maxDaysInMonth = nameCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                nameCalendar.set(Calendar.DATE, maxDaysInMonth);
                dayOfweek = nameCalendar.get(Calendar.DAY_OF_WEEK);
                firstofweek = nameCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfweek + 1;
            }
            nameCalendar.set(Calendar.DATE, firstofweek);
            //nameCalendar.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
            helpCalendar.add(Calendar.DAY_OF_MONTH, +6);
            mShowDate.setText(nameCalendar.get(Calendar.DAY_OF_MONTH) + "-" + helpCalendar.get(Calendar.DAY_OF_MONTH) + "");
            SimpleDateFormat format = new SimpleDateFormat("MMMM  yyyy");
            String dateString = format.format(nameCalendar.getTime());
            mLittleDate.setText(dateString);

        }

        currentCalendar = Calendar.getInstance();
        if (getCalFromDate != null) {
            //kkk
            currentCalendar.set(getCalFromDate.get(Calendar.YEAR), getCalFromDate.get(Calendar.MONTH), getCalFromDate.get(Calendar.DAY_OF_MONTH));

        }
        int numWeek = position - 200;

        currentCalendar.add(Calendar.WEEK_OF_MONTH, numWeek);
        fragment = CalanderDisplayWeek.newInstance(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DATE));
        CalanderDisplayWeek.getInstance().put_fr(getFr);
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
