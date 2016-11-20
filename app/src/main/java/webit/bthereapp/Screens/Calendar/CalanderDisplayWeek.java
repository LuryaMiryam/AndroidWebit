package webit.bthereapp.Screens.Calendar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import webit.bthereapp.Application.NavigetionLayout;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Customer.OrderServiceFragment;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


public class CalanderDisplayWeek extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";
    // TODO: Rename and change types of parameters
    private int mYar;
    private int mMonth;
    private int mDay;
    private static Fragment getFr_;
    private boolean startOfMonth;
    View view;
    LinearLayout mlinearLayoutTime;
    float heightBottom;
    float dpwidth;
    View mView;
    Calendar calander;
    int firstofweek, saveFirstofweek = 2;
    boolean flag;
    List<TasksCalander> taskCalanderList;
    List<ProviderFreeDaysObj> tasksCalanderListfree;
    List<OrderDetailsObj> orderList;


    public static CalanderDisplayWeek instance;
    public CalanderDisplayWeek() {
        // Required empty public constructor
    }


    public void put_fr(Fragment getFr_) {
        this.getFr_ = getFr_;
    }
    public void put_fr_and_calendar(int year, int month, int day, Fragment getFr_) {
        mYar = year;
        mMonth = month;
        mDay = day;
        this.getFr_ = getFr_;
    }


    public static CalanderDisplayWeek newInstance(int param1, int param2, int param3) {
        CalanderDisplayWeek fragment = new CalanderDisplayWeek();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putInt(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    public static CalanderDisplayWeek getInstance() {
        if (instance == null) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            instance = CalanderDisplayWeek.newInstance(year, month, day);
        }
        return instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYar = getArguments().getInt(ARG_PARAM1);
            mMonth = getArguments().getInt(ARG_PARAM2);
            mDay = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calander_display_week, container, false);
        calander = Calendar.getInstance();
        calander.set(mYar, mMonth, mDay);
        // the day of today??
        int dayOfweek = calander.get(Calendar.DAY_OF_WEEK);
        //  int dayOfweek= calander.get(Calendar.DAY_OF_WEEK );
        firstofweek = mDay - dayOfweek + 1;
        if (firstofweek < 1) {
            calander.set(mYar, mMonth - 1, mDay);
            int maxDaysInMonth = calander.getActualMaximum(Calendar.DAY_OF_MONTH);
            calander.set(Calendar.DATE, maxDaysInMonth);
            dayOfweek = calander.get(Calendar.DAY_OF_WEEK);
            saveFirstofweek = firstofweek;
            firstofweek = calander.getActualMaximum(Calendar.DAY_OF_MONTH) - dayOfweek + 1;
        }
        calander.set(Calendar.DATE, firstofweek);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        dpwidth = display.getWidth();// deprecate
        int dpHeight = display.getHeight();
        int heightTop = (int) dpHeight / 32 * 3;
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 69, r.getDisplayMetrics());
        heightBottom = (float) (dpHeight - heightTop - px * 4.2);
        LinearLayout mlinearLayoutTop = (LinearLayout) view.findViewById(R.id.linearLayoutTitle);
        if (getActivity() instanceof ExistsSuplierActivity) {
            mlinearLayoutTop.setBackgroundColor(getResources().getColor(R.color.light_blue));
            flag = true;
        } else mlinearLayoutTop.setBackgroundColor(getResources().getColor(R.color.color3));
        ScrollView mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) heightBottom));
        mlinearLayoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) heightTop));
        mlinearLayoutTime = (LinearLayout) view.findViewById(R.id.linearLayoutMainCalander);
        mlinearLayoutTime.setOrientation(LinearLayout.HORIZONTAL);
        mlinearLayoutTime.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        mView = (View) view.findViewById(R.id.View1);
        addTextViewTitle();
        heightBottom = (float) (dpHeight - heightTop - px);
        CalendarUtil.addNameHours(getActivity(), heightBottom, dpwidth, mlinearLayoutTime);

        tasksCalanderListfree = CalendarUtil.GetListTaskWeekFree(getActivity(), calander);
        if (!CalendarUtil.mEyeShow || !mayRequestCalendar()) {
            taskCalanderList = new ArrayList<>();
        } else {
            taskCalanderList = CalendarUtil.GetListTaskWeek(getActivity(), calander);
        }
        orderList = CalendarUtil.GetOrdersWeek(getActivity(), calander);

        CalendarUtil.setMlinearLayoutDays(getActivity(), dpwidth, heightBottom, mView, 1, mlinearLayoutTime, taskCalanderList, tasksCalanderListfree, orderList, 7, null);
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
            } catch (Exception e) {
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
                taskCalanderList = CalendarUtil.GetListTaskWeek(getActivity(), calander);
            }
        }
    }

    public void addTextViewTitle() {
        final Calendar secondCalander = Calendar.getInstance();
        final List<Calendar> saveCalenderForDays = new ArrayList<Calendar>();

//        // do we need to do mm-1??
//        secondCalander.set(mYar, mMonth-1, firstofweek - 1);
        //if the end of the month it's ok to do mMonth-1,probably this is good for all the cases, check....!!!
        secondCalander.set(calander.get(Calendar.YEAR), calander.get(Calendar.MONTH), firstofweek - 1);

        int maxDayOFMonth = calander.getActualMaximum(Calendar.DAY_OF_MONTH);//31
        int count = 1;
        CustemCalanderDisplayWeek mTextViewName;
        String weekdays[] = new DateFormatSymbols().getShortWeekdays();
        for (int i = 0; i < 7; i++) {
            secondCalander.add(Calendar.DATE, 1);
            String nameTitle = "textViewTitle" + String.valueOf(i + 100);
            int textView = this.getResources().getIdentifier(nameTitle, "id", getActivity().getPackageName());
            mTextViewName = (CustemCalanderDisplayWeek) view.findViewById(textView);
            if (i + firstofweek > maxDayOFMonth) {
                //if at the start of the month
                startOfMonth = true;
                mTextViewName.setTitleTextNum(String.valueOf(count));
                count++;
            } else {
                startOfMonth = false;
                mTextViewName.setTitleTextNum(String.valueOf(firstofweek + i));
            }
            String nameWeek = weekdays[i + 1];
            if (weekdays[i + 1].contains("שבת"))
                nameWeek = "ש";
            else if (weekdays[i + 1].contains("א"))
                nameWeek = "א";
            else if (weekdays[i + 1].contains("ב"))
                nameWeek = "ב";
            else if (weekdays[i + 1].contains("ג"))
                nameWeek = "ג";
            else if (weekdays[i + 1].contains("ד"))
                nameWeek = "ד";
            else if (weekdays[i + 1].contains("ה"))
                nameWeek = "ה";
            else if (weekdays[i + 1].contains("ו"))
                nameWeek = "ו";
            mTextViewName.setTitleTextName(nameWeek);
            mTextViewName.setGravity(Gravity.CENTER);
            mTextViewName.setBackGrounColor(flag);
            final Calendar calendarCurrent = Calendar.getInstance();
            if (calendarCurrent.get(Calendar.DAY_OF_MONTH) == secondCalander.get(Calendar.DAY_OF_MONTH) &&
                    calendarCurrent.get(Calendar.MONTH) == secondCalander.get(Calendar.MONTH)
                    && calendarCurrent.get(Calendar.YEAR) == secondCalander.get(Calendar.YEAR)) {
                mTextViewName.setBockGroundCircleblack(true);
            }
            // create a calender of the day that is created and save it in a list for passing after to day
            Calendar sC = Calendar.getInstance();
            sC.set(secondCalander.get(Calendar.YEAR), secondCalander.get(Calendar.MONTH), secondCalander.get(Calendar.DAY_OF_MONTH));
            sC.clear(Calendar.HOUR);
            saveCalenderForDays.add(sC);
            mTextViewName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get the day that was pressed to get the right day from the list
                    CustemCalanderDisplayWeek custemCalanderDisplayWeek = (CustemCalanderDisplayWeek) v;
                    int day = 1;
                    if (custemCalanderDisplayWeek.getTitleTextName().contains("א") || custemCalanderDisplayWeek.getTitleTextName().contains("Sun"))
                        day = 1;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ב") || custemCalanderDisplayWeek.getTitleTextName().contains("Mon"))
                        day = 2;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ג") || custemCalanderDisplayWeek.getTitleTextName().contains("Tue"))
                        day = 3;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ד") || custemCalanderDisplayWeek.getTitleTextName().contains("Wed"))
                        day = 4;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ה") || custemCalanderDisplayWeek.getTitleTextName().contains("Thu"))
                        day = 5;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ו") || custemCalanderDisplayWeek.getTitleTextName().contains("Fri"))
                        day = 6;
                    else if (custemCalanderDisplayWeek.getTitleTextName().contains("ש") || custemCalanderDisplayWeek.getTitleTextName().contains("Sat"))
                        day = 7;
                    //get the current date without the time
                    Calendar cal = Calendar.getInstance();
                    cal.set(Calendar.HOUR_OF_DAY, 0);
                    cal.set(Calendar.MINUTE, 0);
                    cal.set(Calendar.SECOND, 0);
                    cal.set(Calendar.MILLISECOND, 0);
                    //check if the date that's pressed is after today and if needed not let to pass to the day
//                    if (saveCalenderForDays.get(day - 1).getTimeInMillis() >= cal.getTimeInMillis() || (CalanderDisplayWeek.getInstance().getFr instanceof NextFragment && getActivity() instanceof NavigetionLayout)) {
                        //go to the day that was pressed
                        if (getFr_ instanceof NextFragment) {
                            //get the date that was pressed from a list of calendars that was made at displaying the week
                            ((NextFragment)getFr_).setDisplayDay(saveCalenderForDays.get(day - 1));
                        } else {
                            if (getFr_ instanceof OrderServiceFragment)
                                ((OrderServiceFragment)getFr_).setDisplayDay(saveCalenderForDays.get(day - 1));
                        }
                }
            });

        }
    }


}
