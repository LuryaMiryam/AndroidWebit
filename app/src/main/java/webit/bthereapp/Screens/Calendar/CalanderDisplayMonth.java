package webit.bthereapp.Screens.Calendar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.CustemViews.Fonts.CustemCalanderDisplayMonth;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderServiceFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


public class CalanderDisplayMonth extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mYear;
    private int mMonth;
    private View view;
    private View mViewCalander;
    LinearLayout.LayoutParams params;
    LinearLayout.LayoutParams paramTextView;
    LinearLayout.LayoutParams paramsView;
    public int firstDaysOfWeek;
    public int numDayOfFirstWeek;
    public int maxDays;
    public int numWeek;
    public int numDaysEndWeek;
    long currentDate;
    LinearLayout mLinearLayoutMain;
    private int num = 1;
    private LinearLayout mTitleL;
    boolean flag;
    Fragment getFr;
    List<TasksCalander> taskCalanderList;
    List<ProviderFreeDaysObj> tasksCalanderfree_arr = new ArrayList<>();
    List<OrderDetailsObj> orderList = new ArrayList<>();
    private Calendar calander;




    public CalanderDisplayMonth(int mYear, int mMonth, Fragment getFr) {
        this.mYear = mYear;
        this.mMonth = mMonth;
        this.getFr = getFr;
    }
    public CalanderDisplayMonth() {
        // Required empty public constructor
    }
    public static void removeInstance() {
        instance = null;
    }
    public static CalanderDisplayMonth newInstance(int param1, int param2) {
        CalanderDisplayMonth fragment = new CalanderDisplayMonth();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYear = getArguments().getInt(ARG_PARAM1);
            mMonth = getArguments().getInt(ARG_PARAM2);
        }
    }

    public static CalanderDisplayMonth instance;

    public static CalanderDisplayMonth getInstance() {
        if (instance == null)
            instance = new CalanderDisplayMonth();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calander_display_month, container, false);
        mTitleL = (LinearLayout) view.findViewById(R.id.title);
        if (getActivity() instanceof ExistsSuplierActivity)
            ((ExistsSuplierActivity) getActivity()).changeColor(mTitleL);
        addTextViewTitle();
        mLinearLayoutMain = (LinearLayout) view.findViewById(R.id.linearLayoutMainCalander);
        mViewCalander = (View) view.findViewById(R.id.viewLinear);
        calander = Calendar.getInstance();
        calander.set(mYear, mMonth, 1);
            orderList = CalendarUtil.GetOrdersMonth(getActivity(), calander);
        tasksCalanderfree_arr = CalendarUtil.GetListTaskMonthFree(getActivity(), calander);
        if (!CalendarUtil.mEyeShow||!mayRequestCalendar()) {
            taskCalanderList=new ArrayList<>();
        }else{
            taskCalanderList = CalendarUtil.GetListTaskMonth(getActivity(), calander);
        }
        currentDate = getCurrentDateLong(calander.getTime());
        firstDaysOfWeek = calander.get(Calendar.DAY_OF_WEEK);

        maxDays = calander.getActualMaximum(Calendar.DAY_OF_MONTH);

        numDayOfFirstWeek = 7 - (firstDaysOfWeek - 1);
        int days = maxDays - numDayOfFirstWeek;
        int numDays = maxDays - numDayOfFirstWeek;

        numWeek = numDays % 7 == 0 ? (numDays / 7) + 1 : (numDays / 7) + 2;

        numDaysEndWeek = days - ((numWeek - 2) * 7);

        if (getActivity() instanceof ExistsSuplierActivity) {
            mTitleL.setBackgroundColor(getResources().getColor(R.color.light_blue));
            flag = true;
        } else
            mTitleL.setBackgroundColor(getResources().getColor(R.color.color3));
        params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 0, 1f);
        paramTextView = new LinearLayout.LayoutParams(0, AbsListView.LayoutParams.MATCH_PARENT, 1f);
        paramsView = new LinearLayout.LayoutParams(mViewCalander.getLayoutParams());
        mLinearLayoutMain.setWeightSum(numWeek);
        addLinearLayoutWeek(numWeek);
        return view;

    }


    private static final int REQUEST_READ_CALENDAR = 0;

    private boolean mayRequestCalendar() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
            try {
                Snackbar.make(view.findViewById(android.R.id.content), "", Snackbar.LENGTH_INDEFINITE)
                        .setAction(android.R.string.ok, new View.OnClickListener() {
                            @Override
                            @TargetApi(Build.VERSION_CODES.M)
                            public void onClick(View v) {
                                requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, REQUEST_READ_CALENDAR);
                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }

        } else {
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, REQUEST_READ_CALENDAR);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CALENDAR) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted , Access contacts here or do whatever you need.
                taskCalanderList = CalendarUtil.GetListTaskMonth(getActivity(), calander);
            }
        }
    }

    public void addDays(int numOFweek, LinearLayout linearLayout) {
        Calendar calendar = Calendar.getInstance();
        CustemCalanderDisplayMonth mCustemCalanderDisplayMonth;
        int numOfDays = numOFweek * 7;
        int max = 8;
        if (numOFweek == 0) {
            LinearLayout LinearLayoutView = new LinearLayout(getActivity());
            LinearLayoutView.setId(Integer.valueOf(10000));
            LinearLayoutView.setVisibility(View.VISIBLE);
            max = numDayOfFirstWeek + 1;
            int ff = 7 - max + 1;
            float ft = ff;
            LinearLayout.LayoutParams paramsl = new LinearLayout.LayoutParams(0, AbsListView.LayoutParams.MATCH_PARENT, ft);
            LinearLayoutView.setBackgroundColor(getResources().getColor(R.color.transparent));
            linearLayout.addView(LinearLayoutView, paramsl);
        }
        if (numOFweek == numWeek - 1)
            max = numDaysEndWeek + 1;
        for (int i = 1; i < max; i++) {
            mCustemCalanderDisplayMonth = new CustemCalanderDisplayMonth(getActivity());
            mCustemCalanderDisplayMonth.setTitleText(String.valueOf(num));
            mCustemCalanderDisplayMonth.setId(100 + numOfDays + i);
            linearLayout.addView(mCustemCalanderDisplayMonth, paramTextView);

            Calendar c = Calendar.getInstance();
            if (num == currentDate&& mMonth==c.get(Calendar.MONTH)) {
                mCustemCalanderDisplayMonth.setBockGroundCircleblack(true);
                mCustemCalanderDisplayMonth.setColorTextView(getResources().getColor(R.color.black));
            } else if (num > currentDate||num == currentDate)
                mCustemCalanderDisplayMonth.setColorTextView(getResources().getColor(R.color.black));
            else {
                mCustemCalanderDisplayMonth.setColorTextView(getResources().getColor(R.color.dark_gray_6));
            }
            boolean numEnd = numDaysEndWeek == 7 ? true : false;
            if (i == max - 1 && numOFweek != numWeek - 1 || i == max - 1 && numEnd)
                mCustemCalanderDisplayMonth.setColor(flag);

            //tasksCalanderfree
            int b = 0;
            calendar = Calendar.getInstance();
            for (ProviderFreeDaysObj tasksCalanderfree : tasksCalanderfree_arr) {
                calendar.setTime(ConnectionUtils.convertJsonDate_(tasksCalanderfree.getDtDate()));
                if (calendar.get(Calendar.YEAR) == mYear && calendar.get(Calendar.MONTH) == mMonth && calendar.get(Calendar.DAY_OF_MONTH) == num) {
                    b = 1;

                }
            }
            if (b == 1)
                mCustemCalanderDisplayMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.square));

            //orderDetailsObj
           int a = 0;
            calendar = Calendar.getInstance();
            for (OrderDetailsObj orderDetailsObj : orderList) {
                calendar.setTime(ConnectionUtils.convertJsonDate_(orderDetailsObj.getDtDateOrder()));
                if (calendar.get(Calendar.YEAR) == mYear && calendar.get(Calendar.MONTH) == mMonth && calendar.get(Calendar.DAY_OF_MONTH) == num) {
                    a = 1;
                }
            }
            if (a == 1) {
                mCustemCalanderDisplayMonth.setmCircleOrange(true);
            }
            a = 0;
            for (TasksCalander tasksCalander : taskCalanderList)
                if (tasksCalander.getmCalendarStart().get(Calendar.YEAR) == mYear && tasksCalander.getmCalendarStart().get(Calendar.MONTH) == mMonth && tasksCalander.getmCalendarStart().get(Calendar.DAY_OF_MONTH) == num) {
                if(tasksCalander.getmTitle()==null||!tasksCalander.getmTitle().equals("BThere"))
                    a = 1;
                }

            if (a == 1)
                mCustemCalanderDisplayMonth.setUnderline(true);
            final int finalB = b;
            mCustemCalanderDisplayMonth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CustemCalanderDisplayMonth custemCalanderDisplayMonth = (CustemCalanderDisplayMonth) v;
                    int pressedDay = Integer.valueOf(custemCalanderDisplayMonth.getTitleTextView());
                    //check that the date pressed day did not pass in the calender of service
                    if (pressedDay >= currentDate || (getFr instanceof NextFragment && getActivity() instanceof NavigetionLayout)) {
                        //go to the day that was pressed
                        Calendar calendars = Calendar.getInstance();
                        calendars.set(mYear, mMonth, Integer.valueOf(custemCalanderDisplayMonth.getTitleTextView()));
                        if (getFr instanceof NextFragment)
//                       ||getFr instanceof OrderServiceFragmen
                            ((NextFragment) getFr).setDisplayDay(calendars);
                        else if (getFr instanceof OrderServiceFragment)
                            if(finalB ==1)
                            ((OrderServiceFragment) getFr).setDisplayDay(calendars);
                    }

                }
            });
            num++;
        }

    }

    public void addLinearLayoutWeek(int weeks) {
        LinearLayout mLinearLayout;
        View mView;
        for (int i = 0; i < weeks; i++) {
            mLinearLayout = new LinearLayout(getActivity());
            mLinearLayout.setId(Integer.valueOf(i));
            mLinearLayoutMain.addView(mLinearLayout, params);
            mLinearLayout.setWeightSum(7f);
            mLinearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            mView = new View(getActivity());
            mView.setId(Integer.valueOf(i) + 1000);
            mView.setBackgroundColor(getResources().getColor(R.color.dark_gray_6));
            mLinearLayoutMain.addView(mView, paramsView);
            addDays(i, mLinearLayout);
        }
        num = 1;
    }

    //put the text of the days
    public void addTextViewTitle() {
        TextView mTextView;
        String weekdays[] = new DateFormatSymbols().getShortWeekdays();
        for (int i = 0; i < 7; i++) {
            String nameTitle = "textViewTitle" + String.valueOf(i + 1000);
            int textView = this.getResources().getIdentifier(nameTitle, "id", getActivity().getPackageName());
            mTextView = (TextView) view.findViewById(textView);
            String stringWeek = weekdays[i + 1];

            if (weekdays[i + 1].contains("שבת"))
                stringWeek = "ש";
            else if (weekdays[i + 1].contains("א"))
                stringWeek = "א";
            else if (weekdays[i + 1].contains("ב"))
                stringWeek = "ב";
            else if (weekdays[i + 1].contains("ג"))
                stringWeek = "ג";
            else if (weekdays[i + 1].contains("ד"))
                stringWeek = "ד";
            else if (weekdays[i + 1].contains("ה"))
                stringWeek = "ה";
            else if (weekdays[i + 1].contains("ו"))
                stringWeek = "ו";
            mTextView.setText(stringWeek);
            mTextView.setGravity(Gravity.CENTER);
        }
    }

    public static long getCurrentDateLong(Date lastCheckDate) {
        Date today = new Date();
        long different;
        different = today.getTime() - lastCheckDate.getTime();
        different = (different / 1000 / 60 / 60 / 24) + 1;
        return different;
    }

}
