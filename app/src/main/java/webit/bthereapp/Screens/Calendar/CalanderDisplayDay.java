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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.ProviderFreeDaysObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


public class CalanderDisplayDay extends Fragment implements View.OnTouchListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "year";
    private static final String ARG_PARAM2 = "month";
    private static final String ARG_PARAM3 = "day";
    // TODO: Rename and change types of parameters
    private int mYar;
    private int mMonth;
    private int mDay;
    private Fragment getFr;


    public CalanderDisplayDay() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CalanderDisplayDay newInstance(int year, int month, int day) {
        CalanderDisplayDay fragment = new CalanderDisplayDay();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, year);
        args.putInt(ARG_PARAM2, month);
        args.putInt(ARG_PARAM3, day);
        fragment.setArguments(args);
        return fragment;
    }

    View view;
    LinearLayout mlinearLayoutTime, mlinearLayoutTop;
    float heightBottom;
    float dpwidth;
    View mView;
    View mViewLine;
    Calendar calander = Calendar.getInstance();


    List<TasksCalander> taskCalanderList;
    List<ProviderFreeDaysObj> providerFreeDaysList;
    List<OrderDetailsObj> orderList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mYar = getArguments().getInt(ARG_PARAM1);
            mMonth = getArguments().getInt(ARG_PARAM2);
            mDay = getArguments().getInt(ARG_PARAM3);
        }
    }

    public static CalanderDisplayDay instance;

    public static CalanderDisplayDay getInstance() {
        if (instance == null) {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            instance = CalanderDisplayDay.newInstance(year, month, day);
        }

        return instance;
    }
    public static void removeInstance() {
       instance = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calander_display_day, container, false);
        calander = Calendar.getInstance();
        calander.set(mYar, mMonth, mDay);
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        dpwidth = display.getWidth();// deprecate
        int dpHeight = display.getHeight();
        int heightTop = dpHeight / 32 * 3;
        Resources r = getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 69, r.getDisplayMetrics());
        heightBottom = (float) (dpHeight - heightTop - px * 4.2);
        mlinearLayoutTop = (LinearLayout) view.findViewById(R.id.linearLayoutTitle);
        if (getActivity() instanceof ExistsSuplierActivity)
            mlinearLayoutTop.setBackgroundColor(getResources().getColor(R.color.light_blue));
        else mlinearLayoutTop.setBackgroundColor(getResources().getColor(R.color.color3));
        ScrollView mScrollView = (ScrollView) view.findViewById(R.id.scrollView);
        mScrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) heightBottom));
        mlinearLayoutTop.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) heightTop));
        mlinearLayoutTime = (LinearLayout) view.findViewById(R.id.linearLayoutMainCalander);
        mlinearLayoutTime.setOrientation(LinearLayout.HORIZONTAL);
        mlinearLayoutTime.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        mlinearLayoutTime.setOnTouchListener(this);

        mView = view.findViewById(R.id.View1);
        //put the text of the day
        addTextViewTitle();
        heightBottom = (dpHeight - heightTop - px);
        CalendarUtil.addNameHours(getActivity(), heightBottom, dpwidth, mlinearLayoutTime);
        providerFreeDaysList = CalendarUtil.GetListTaskDayFree(getActivity(), calander);

        if (!CalendarUtil.mEyeShow || !mayRequestCalendar()) {
            taskCalanderList = new ArrayList<>();
        } else {
            taskCalanderList = CalendarUtil.GetListTaskDay(getActivity(), calander);
        }
        orderList = CalendarUtil.GetOrdersDay(getActivity(), calander);
        mViewLine = view.findViewById(R.id.viewLine);
        CalendarUtil.setMlinearLayoutDays(getActivity(), dpwidth, heightBottom, mView, 1, mlinearLayoutTime, taskCalanderList, providerFreeDaysList, orderList, 1, mViewLine);

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
                taskCalanderList = CalendarUtil.GetListTaskDay(getActivity(), calander);
            }
        }
    }

    public void addTextViewTitle() {
        CustemCalanderDisplayWeek mTextViewName;

        String weekdays[] = new DateFormatSymbols().getShortWeekdays();

        String nameTitle = "textViewTitle" + String.valueOf(100);
        int textView = this.getResources().getIdentifier(nameTitle, "id", getActivity().getPackageName());
        mTextViewName = (CustemCalanderDisplayWeek) view.findViewById(textView);
        mTextViewName.setTitleTextNum(String.valueOf(mDay));

        String nameDay = weekdays[calander.get(Calendar.DAY_OF_WEEK)];
        if (nameDay.contains("שבת"))
            nameDay = "ש";
        else if (nameDay.contains("א"))
            nameDay = "א";
        else if (nameDay.contains("ב"))
            nameDay = "ב";
        else if (nameDay.contains("ג"))
            nameDay = "ג";
        else if (nameDay.contains("ד"))
            nameDay = "ד";
        else if (nameDay.contains("ה"))
            nameDay = "ה";
        else if (nameDay.contains("ו"))
            nameDay = "ו";
        mTextViewName.setTitleTextName(nameDay);
        mTextViewName.setGravity(Gravity.CENTER);
        if (getActivity() instanceof ExistsSuplierActivity)
            mTextViewName.setBackGrounColor(true);
        else mTextViewName.setBackGrounColor(false);
        if (calander.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR) &&
                calander.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) &&
                calander.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            mTextViewName.setBockGroundCircleblack(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
            return true;
    }

}




