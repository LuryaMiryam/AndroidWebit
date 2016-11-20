package webit.bthereapp.Screens.Calendar;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.DayPageAdapter;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


public class ShowDayFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageButton mEyeIB, mEyeIB2;
    RelativeLayout mBackIB, mNextIB;
    ImageView mBackIBiv, mNextIBiv;
    public ViewPager mViewPager;
    public int year, month;
    DayPageAdapter mDayPageAdapter;
    int currentPosition;
    TextView mShowDateTv, mShowDateLittleTv;
    Calendar getCalender;
    Fragment getFr;

    public Calendar getDay() {
        return day;
    }

    public void setDay(Calendar day) {
        this.day = day;
    }

    Calendar day;

    private String mParam1;
    private String mParam2;

    public ShowDayFragment() {
    }

    public static ShowDayFragment newInstance(Calendar cal, Fragment fr) {
        ShowDayFragment fragment = new ShowDayFragment();
        Bundle args = new Bundle();
        args.putString("cal", new Gson().toJson(cal));
        args.putParcelable("fr", (Parcelable) fr);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowDayFragment instance;

    public static ShowDayFragment getInstance() {
        if (instance == null)
            instance = new ShowDayFragment();
        return instance;
    }

    public static void removeInstance() {
        instance = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_date, container, false);

        mEyeIB = (ImageButton) view.findViewById(R.id.eye);
        mEyeIB.setOnClickListener(this);
        mEyeIB2 = (ImageButton) view.findViewById(R.id.eye2);
        mEyeIB2.setOnClickListener(this);
        mShowDateTv = (TextView) view.findViewById(R.id.show_date);
        mShowDateLittleTv = (TextView) view.findViewById(R.id.show_little_date);
        mBackIBiv = (ImageView) view.findViewById(R.id.back_buttonIv);
        mNextIBiv = (ImageView) view.findViewById(R.id.next_buttonIv);
        mBackIB = (RelativeLayout) view.findViewById(R.id.back_button);
        mNextIB = (RelativeLayout) view.findViewById(R.id.next_button);
        mBackIB.setOnClickListener(this);
        mNextIB.setOnClickListener(this);
        if (getActivity() instanceof ExistsSuplierActivity) {
            mNextIBiv.setImageResource(R.drawable.blue_arror_back);
            mBackIBiv.setImageResource(R.drawable.blue_arror);
        } else {
        }

        if (!CalendarUtil.mEyeShow) {
            mEyeIB2.setVisibility(View.VISIBLE);
            mEyeIB.setVisibility(View.GONE);
        } else {
            mEyeIB.setVisibility(View.VISIBLE);
            mEyeIB2.setVisibility(View.GONE);
        }
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == 2) {

                    SimpleDateFormat day_format = new SimpleDateFormat("EEEE");
                    Calendar nameCalendar;
                    nameCalendar = Calendar.getInstance();
                    if (getCalender != null)
                        nameCalendar.set(getCalender.get(Calendar.YEAR), getCalender.get(Calendar.MONTH), getCalender.get(Calendar.DAY_OF_MONTH));
                    int numDay = mViewPager.getCurrentItem() - 200;
                    nameCalendar.add(Calendar.DAY_OF_MONTH, numDay);
                    String day_name = day_format.format(nameCalendar.getTime());
                    SimpleDateFormat format = new SimpleDateFormat("d MMMM  yyyy");
                    String dateString = format.format(nameCalendar.getTime());
                    mShowDateTv.setText(day_name);
                    mShowDateLittleTv.setText(dateString);

                }
                if (mDayPageAdapter != null) {
                    day = mDayPageAdapter.currentCalendar;
                }
            }
        });
        mDayPageAdapter = new DayPageAdapter(getActivity(), getActivity().getSupportFragmentManager(), mShowDateTv, mShowDateLittleTv, getFr);
        mViewPager.setAdapter(mDayPageAdapter);
        mViewPager.setCurrentItem(200);
        if (getCalender != null) {
            mDayPageAdapter.setSentCalender(getCalender);
            mDayPageAdapter.notifyDataSetChanged();
        }
        return view;
    }

//refresh the data
    public void notify_data(){
        if(mViewPager!=null) {
            getCalender = mDayPageAdapter.sendCalender;
            mViewPager.setAdapter(mDayPageAdapter);
            mViewPager.setCurrentItem(200);
            if (getCalender != null) {
                mDayPageAdapter.setSentCalender(getCalender);
                mDayPageAdapter.notifyDataSetChanged();
            }
        }
    }
    public void put_cal_and_fr(Calendar cal, Fragment fr) {
        getCalender = cal;
        getFr = fr;
    }

    public void put_fr(Fragment fr) {
        getFr = fr;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button: {
                //go to the next day
                mViewPager.setCurrentItem(getItem(+1), true);
            }
            break;
            case R.id.back_button: {
                //go to the back day
                mViewPager.setCurrentItem(getItem(-1), true);
            }
            break;
            case R.id.eye: {
                //refresh with/without synchronization and change the eye image
                if (!CalendarUtil.mEyeShow) {
                    mEyeIB.setVisibility(View.VISIBLE);
                    mEyeIB2.setVisibility(View.GONE);
                } else {
                    mEyeIB.setVisibility(View.GONE);
                    mEyeIB2.setVisibility(View.VISIBLE);
                }
                CalendarUtil.mEyeShow = !CalendarUtil.mEyeShow;
            }
            if (getActivity() instanceof NavigetionLayout)
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
            mViewPager.setAdapter(mDayPageAdapter);
            mViewPager.setCurrentItem(currentPosition);
            break;
            case R.id.eye2: {
                //refresh with/without synchronization and change the eye image
                if (!CalendarUtil.mEyeShow) {
                    mEyeIB.setVisibility(View.VISIBLE);
                    mEyeIB2.setVisibility(View.GONE);
                } else {
                    mEyeIB.setVisibility(View.GONE);
                    mEyeIB2.setVisibility(View.VISIBLE);
                }
                CalendarUtil.mEyeShow = !CalendarUtil.mEyeShow;
                if (getActivity() instanceof NavigetionLayout)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowDayFragment.getInstance()).commit();
                mViewPager.setAdapter(mDayPageAdapter);
                mViewPager.setCurrentItem(currentPosition);
            }
            break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }

}
