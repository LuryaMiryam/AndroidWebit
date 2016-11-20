package webit.bthereapp.Screens.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderDetailsFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ShowDateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowWeekFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout mBackIB, mNextIB;
    ImageView mBackIBiv, mNextIBiv;
    public ViewPager mViewPager;
    public int year, month, date;
    WeekPageAdapter mWeekPageAdapter;
    public static final float EPSILON = 0.001f;
    int currentPosition;
    TextView mShowDateTv, mExsistedUser, mLittleDate;
    int maxInt = Integer.MAX_VALUE;
    int halfMaxInt = maxInt / 2;
    int i = 0;
    Boolean isEyeClicked = false;
    ImageButton mEyeIB, mEyeIB2;
    RelativeLayout.LayoutParams params;
    Fragment getFr;
    Calendar getCalFormDay;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ShowWeekFragment() {
        // Required empty public constructor
    }

    public static ShowWeekFragment newInstance(String param1, String param2) {
        ShowWeekFragment fragment = new ShowWeekFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ShowWeekFragment instance;

    public static ShowWeekFragment getInstance() {
        if (instance == null)
            instance = new ShowWeekFragment();
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
        mLittleDate = (TextView) view.findViewById(R.id.show_little_date);
        mBackIBiv = (ImageView) view.findViewById(R.id.back_buttonIv);
        mNextIBiv = (ImageView) view.findViewById(R.id.next_buttonIv);
        mBackIB = (RelativeLayout) view.findViewById(R.id.back_button);
        mNextIB = (RelativeLayout) view.findViewById(R.id.next_button);
        mBackIB.setOnClickListener(this);
        mNextIB.setOnClickListener(this);
        if (getActivity() instanceof ExistsSuplierActivity) {
            mNextIBiv.setImageResource(R.drawable.blue_arror_back);
            mBackIBiv.setImageResource(R.drawable.blue_arror);
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
                currentPosition = position; // Update current position
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    Calendar nameCalendar = Calendar.getInstance();
                    if (getCalFormDay != null)
                        nameCalendar.set(getCalFormDay.get(Calendar.YEAR), getCalFormDay.get(Calendar.MONTH), getCalFormDay.get(Calendar.DAY_OF_MONTH));
                    int numWeek = mViewPager.getCurrentItem() - 200;
                    nameCalendar.add(Calendar.WEEK_OF_MONTH, numWeek);

                    int dayOfweek = nameCalendar.get(Calendar.DAY_OF_WEEK);
                    int firstofweek = nameCalendar.get(Calendar.DATE) - dayOfweek + 1;
                    if (firstofweek < 1) {
                        nameCalendar.add(Calendar.MONTH, -1);
                        int maxDaysInMonth = nameCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                        //last day of the ending month --
                        nameCalendar.set(Calendar.DATE, maxDaysInMonth);
                        dayOfweek = nameCalendar.get(Calendar.DAY_OF_WEEK);
                        // the first day of the week --
                        firstofweek = nameCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfweek + 1;
                    }
                    nameCalendar.set(Calendar.DATE, firstofweek);

                    Calendar helpCalendar = Calendar.getInstance();
                    helpCalendar.set(Calendar.YEAR, nameCalendar.get(Calendar.YEAR));
                    helpCalendar.set(Calendar.MONTH, nameCalendar.get(Calendar.MONTH));
                    helpCalendar.set(Calendar.DAY_OF_MONTH, nameCalendar.get(Calendar.DAY_OF_MONTH));
                    helpCalendar.add(Calendar.DAY_OF_MONTH, +6);
                    mShowDateTv.setText(nameCalendar.get(Calendar.DAY_OF_MONTH) + "-" + helpCalendar.get(Calendar.DAY_OF_MONTH) + "");
                    SimpleDateFormat format = new SimpleDateFormat("MMMM  yyyy");
                    String dateString = format.format(nameCalendar.getTime());
                    mLittleDate.setText(dateString);
                }
            }
        });
        if (getCalFormDay == null)
            mWeekPageAdapter = new WeekPageAdapter(getActivity().getSupportFragmentManager(), mViewPager, mShowDateTv, mLittleDate, getFr);
        else
            mWeekPageAdapter = new WeekPageAdapter(getActivity().getSupportFragmentManager(), mViewPager, mShowDateTv, mLittleDate, getFr, getCalFormDay);

        mViewPager.setAdapter(mWeekPageAdapter);
        mViewPager.setCurrentItem(200);

        return view;
    }

    public void put_cal_and_fr(Calendar cal, Fragment fr) {
        getCalFormDay = cal;
        getFr = fr;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_button: {
                mViewPager.setCurrentItem(getItem(+1), true);
            }
            break;
            case R.id.back_button: {
                mViewPager.setCurrentItem(getItem(-1), true);
            }
            break;
            // replace eyes and async
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

                if (getActivity() instanceof NavigetionLayout) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowWeekFragment.getInstance()).commit();
                }
                mViewPager.setAdapter(mWeekPageAdapter);
                mViewPager.setCurrentItem(currentPosition);
            }
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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander, ShowWeekFragment.getInstance()).commit();
                mViewPager.setAdapter(mWeekPageAdapter);
                mViewPager.setCurrentItem(currentPosition);
            }
            break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    //refresh the data
    public void notify_data() {
        if (mViewPager != null) {
            getCalFormDay = OrderDetailsFragment.getInstance().getCalendar();
            mViewPager.setAdapter(mWeekPageAdapter);
            mViewPager.setCurrentItem(200);
            if (getCalFormDay != null) {
                mWeekPageAdapter.getCalFromDate = getCalFormDay;
                mWeekPageAdapter.notifyDataSetChanged();
            }
        }
    }


    private int getItem(int i) {
        return mViewPager.getCurrentItem() + i;
    }
}
