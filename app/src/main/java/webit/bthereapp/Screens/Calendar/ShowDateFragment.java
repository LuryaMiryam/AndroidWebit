package webit.bthereapp.Screens.Calendar;

import android.os.Bundle;
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
import webit.bthereapp.CustemViews.popupListOfGivingService;
import webit.bthereapp.Entities.ProviderDetailsObj;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Calendar.MonthPagerAdapter;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


public class ShowDateFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String sentFragment = "param1";
    private static final String ARG_PARAM2 = "param2";
    RelativeLayout mBackIB, mNextIB;
    ImageView mBackIBiv, mNextIBiv;
    public ViewPager mViewPager;
    public int year, month;
    MonthPagerAdapter mMonthPagerAdapter;
    int currentPosition;
    TextView mShowDateTv, mShowLittleDate;
    int maxInt = Integer.MAX_VALUE;
    int i = 0;
    ImageButton mEyeIB, mEyeIB2;
    private popupListOfGivingService spinner;
    Fragment getFr;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public Fragment sentF;


    public ShowDateFragment() {
        // Required empty public constructor
    }


    public static ShowDateFragment instance;

    public static ShowDateFragment getInstance() {
        if (instance == null)
            instance = new ShowDateFragment();
        return instance;
    }
    public static void removeInstance() {
        instance = null;
    }
    public static ShowDateFragment newInstance(Fragment fr) {
        ShowDateFragment fragment = new ShowDateFragment();
        Bundle args = new Bundle();
        args.putString(sentFragment, new Gson().toJson(fr));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("onstop","oncreate_in_sdf");
        if (getArguments() != null) {
                     try {
                getFr = new Gson().fromJson(getArguments().getString(sentFragment, ""), Fragment.class);
                         }
                     catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_date, container, false);
        spinner = (popupListOfGivingService) view.findViewById(R.id.spinner);
        spinner.setVisibility(View.GONE);
        if (getActivity() instanceof ExistsSuplierActivity)
            if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders() != null)
                if (ProviderDetailsObj.getInstance().getObjProviderGeneralDetails().getServiceProviders().size() > 0)
                    spinner.setVisibility(View.VISIBLE);

        mEyeIB = (ImageButton) view.findViewById(R.id.eye);
        mEyeIB.setOnClickListener(this);
        mEyeIB2 = (ImageButton) view.findViewById(R.id.eye2);
        mEyeIB2.setOnClickListener(this);
        mShowDateTv = (TextView) view.findViewById(R.id.show_date);
        mShowLittleDate = (TextView) view.findViewById(R.id.show_little_date);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
        mShowLittleDate.setText(df.format(c.getTime()));
        mBackIBiv= (ImageView) view.findViewById(R.id.back_buttonIv);
        mNextIBiv= (ImageView) view.findViewById(R.id.next_buttonIv);
        mBackIB = (RelativeLayout) view.findViewById(R.id.back_button);
        mNextIB = (RelativeLayout) view.findViewById(R.id.next_button);
        mBackIB.setOnClickListener(this);
        mNextIB.setOnClickListener(this);
        if (getActivity() instanceof ExistsSuplierActivity)
        {
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
        // currentPosition = 200;
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        // mMonthPagerAdapter.isRight = 0;
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPosition = position;
            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mMonthPagerAdapter =
                new MonthPagerAdapter(  getActivity().getSupportFragmentManager(), mViewPager, mShowDateTv, sentF);
        mViewPager.setAdapter(mMonthPagerAdapter);
        mViewPager.setCurrentItem(200);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2) {
                    SimpleDateFormat montD = new SimpleDateFormat("MMMM");
                    Calendar nameCalendar = Calendar.getInstance();
                    int numMonth = mViewPager.getCurrentItem() - 200;
                    nameCalendar.add(Calendar.MONTH, numMonth);
                    String month_name = montD.format(nameCalendar.getTime());
                    mShowDateTv.setText(month_name + "  " + nameCalendar.get(Calendar.YEAR) + "");
                }
            }
        });

        return view;
    }


    public void put_fr(Fragment fr) {
        sentF = fr;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //to the next month
            case R.id.next_button: {
                mViewPager.setCurrentItem(getItem(+1), true);
            }
            break;
            //to the back month
            case R.id.back_button: {
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
                if (getActivity() instanceof NavigetionLayout)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander,ShowDateFragment.getInstance()).commit();
                mViewPager.setAdapter(mMonthPagerAdapter);
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
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container_calander,ShowDateFragment.getInstance()).commit();
                mViewPager.setAdapter(mMonthPagerAdapter);
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
