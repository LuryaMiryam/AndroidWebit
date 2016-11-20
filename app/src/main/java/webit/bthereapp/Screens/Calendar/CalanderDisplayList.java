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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import webit.bthereapp.Adapters.CalanderListTogetherAdapter;
import webit.bthereapp.Connection.ConnectionUtils;
import webit.bthereapp.Entities.OrderDetailsObj;
import webit.bthereapp.Entities.TasksCalander;
import webit.bthereapp.R;
import webit.bthereapp.Screens.Supplier.ExistsSuplierActivity;
import webit.bthereapp.Utils.CalendarUtil;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CalanderDisplayList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalanderDisplayList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CalanderDisplayList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalanderDisplayList.
     */
    // TODO: Rename and change types and number of parameters
    public static CalanderDisplayList newInstance(String param1, String param2) {
        CalanderDisplayList fragment = new CalanderDisplayList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private List<TasksCalander> tasksCalanderList;
    private List<OrderDetailsObj> orderList;
    private Object objects[];
    private int c;

    private int count;
    private int location;
    private int listHeight;
    private View view;
    private LinearLayout.LayoutParams params;
    private LinearLayout mLinearLayout;
    private boolean flagColor;
    private Calendar calendar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public static CalanderDisplayList instance;

    public static CalanderDisplayList getInstance() {
        if (instance == null)
            instance = new CalanderDisplayList();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_calander_display_list, container, false);
        calendar = Calendar.getInstance();
        if (!CalendarUtil.mEyeShow || !mayRequestCalendar())
            tasksCalanderList = new ArrayList<>();
        else {
            tasksCalanderList = (CalendarUtil.GetListTaskList(getActivity(), calendar));
            sortList();
        }

        if ((!(CalendarUtil.is_calendar_of_provider)) && CalendarUtil.OrderDetailsObj_ != null)
            orderList = CalendarUtil.GetOrdersList(getActivity(), calendar);
        else
            orderList = new ArrayList<>();

        sortListOrders();
        mLinearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutMAin);
        c = tasksCalanderList.size() + orderList.size();
        objects = new Object[c];

        // count the days in the list
        count = together();
        int dpHeightTytle = 110;
        listHeight = (int) (dpHeightTytle * 2.5);
        if (getActivity() instanceof ExistsSuplierActivity)
            flagColor = true;
        addRelativeTogether(dpHeightTytle);
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
                tasksCalanderList = (CalendarUtil.GetListTaskList(getActivity(), calendar));
                sortList();
            }
        }
    }

    public void addRelativeTogether(int dpHeight) {
        location = 0;
        TasksCalander tasksCalander;
        OrderDetailsObj order;
        Calendar calendarCurrent = Calendar.getInstance();


        for (int i = 0; i < count; i++) {
            if (objects.length > location) {
                CustemTyltleCalcnderDisplayList custemTyltleCalcnderDisplayList = new CustemTyltleCalcnderDisplayList(getActivity());
                custemTyltleCalcnderDisplayList.setId(i + 1 * 100);
                RelativeLayout.LayoutParams paramsView = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpHeight);

                if (objects[location] instanceof TasksCalander) {
                    tasksCalander = new TasksCalander((TasksCalander) objects[location]);
                    custemTyltleCalcnderDisplayList.setTitleTextDay(tasksCalander.getmCalendarStart().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + ",");
                    custemTyltleCalcnderDisplayList.setTitleTextDate(tasksCalander.getmCalendarStart().get(Calendar.DAY_OF_MONTH) + " " + (new SimpleDateFormat("MMM").format(tasksCalander.getmCalendarStart().getTime())) + " " + tasksCalander.getmCalendarStart().get(Calendar.YEAR));

                    if (tasksCalanderList.size() > location && calendarCurrent.get(Calendar.DAY_OF_MONTH) == tasksCalanderList.get(location).getmCalendarStart().get(Calendar.DAY_OF_MONTH) &&
                            calendarCurrent.get(Calendar.MONTH) == tasksCalanderList.get(location).getmCalendarStart().get(Calendar.MONTH)
                            && calendarCurrent.get(Calendar.YEAR) == tasksCalanderList.get(location).getmCalendarStart().get(Calendar.YEAR)) {
                        custemTyltleCalcnderDisplayList.setBockGroundCircleblack(true);
                    }

                } else {
                    order = new OrderDetailsObj((OrderDetailsObj) objects[location]);
                    Date date = ConnectionUtils.JsonDateToDate(order.getDtDateOrder());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    custemTyltleCalcnderDisplayList.setTitleTextDay(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault()) + ",");
                    custemTyltleCalcnderDisplayList.setTitleTextDate(calendar.get(Calendar.DAY_OF_MONTH) + " " + (new SimpleDateFormat("MMM").format(calendar.getTime())) + " " + calendar.get(Calendar.YEAR));

                    if (orderList.size() > location) {
                        date = ConnectionUtils.JsonDateToDate(orderList.get(location).getDtDateOrder());
                        calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        if (calendarCurrent.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                                calendarCurrent.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)
                                && calendarCurrent.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
                            custemTyltleCalcnderDisplayList.setBockGroundCircleblack(true);
                        }
                    }

                }


                custemTyltleCalcnderDisplayList.setBackGroundMain(flagColor);
                mLinearLayout.addView(custemTyltleCalcnderDisplayList, paramsView);
                ListView listView = new ListView(getActivity());
                listView.setId(i + 1);

                CalanderListTogetherAdapter mCalanderListAdapter1 = new CalanderListTogetherAdapter(getActivity(), objects, location, listHeight);

                View listItem = mCalanderListAdapter1.getView(0, null, listView);
                listItem.measure(0, 0);
                listView.setAdapter(mCalanderListAdapter1);
                location += mCalanderListAdapter1.getcount() == 1 ? 1 : mCalanderListAdapter1.getcount();
                //todo return
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ((listItem.getMeasuredHeight()) * mCalanderListAdapter1.getcount()));
                mLinearLayout.addView(listView, params);
            }
        }
    }

    //enter the values to the list and return the num days
    public int together() {
        int x = 0, y = 0;
        int num_d = 0;
        Calendar calendar_p = Calendar.getInstance();
        if (tasksCalanderList.size() > 0 && orderList.size() > 0) {
            Calendar c1 = tasksCalanderList.get(0).getmCalendarStart();

            Date date = ConnectionUtils.JsonDateToDate(orderList.get(0).getDtDateOrder());
            Calendar c2 = Calendar.getInstance();
            c2.setTime(date);

            if (c1.after(c2)) {
                calendar_p.setTime(c2.getTime());

                //put the first order in the big list
                objects[0] = new OrderDetailsObj();
                objects[0] = orderList.get(y);

                y = 1;
                num_d = 1;

            } else {
                calendar_p.setTime(c1.getTime());

                //put the first task in the big list
                objects[0] = new TasksCalander();
                objects[0] = tasksCalanderList.get(x);

                num_d = 1;
                x = 1;

            }
        } else if (tasksCalanderList.size() > 0) {
            calendar_p = tasksCalanderList.get(0).getmCalendarStart();

            //put the first task in the big list
            objects[0] = new TasksCalander();
            objects[0] = tasksCalanderList.get(x);

            num_d = 1;
            x = 1;

        } else if (orderList.size() > 0) {
            Date date = ConnectionUtils.JsonDateToDate(orderList.get(0).getDtDateOrder());
            calendar_p.setTime(date);

            //put the first order in the big list
            objects[0] = new OrderDetailsObj();
            objects[0] = orderList.get(y);

            y = 1;
            num_d = 1;
        }

        Calendar calendar = Calendar.getInstance();
        Date date = new Date();

        for (int i = 1; i < c; i++) {
            if (orderList.size() > y && tasksCalanderList.size() > x) {
                date = ConnectionUtils.JsonDateToDate(orderList.get(y).getDtDateOrder());
                calendar.setTime(date);

                if ((tasksCalanderList.get(x).getmCalendarStart().getTime().before(calendar.getTime()))) {
                    if (tasksCalanderList.get(x).getmCalendarStart().getTime().getDate() != calendar_p.getTime().getDate()) {
                        // count the days in the list
                        num_d++;
                    }
                    calendar_p = tasksCalanderList.get(x).getmCalendarStart();
                    objects[i] = new TasksCalander();
                    objects[i] = tasksCalanderList.get(x);
                    x++;
                } else if (calendar.getTime().before(tasksCalanderList.get(x).getmCalendarStart().getTime())) {
                    if (calendar.getTime().getDate() != calendar_p.getTime().getDate()) {
                        // count the days in the list
                        num_d++;
                    }
                    calendar_p = calendar;
                    objects[i] = new OrderDetailsObj();
                    objects[i] = orderList.get(y);
                    y++;
                } else if (tasksCalanderList.get(x).getmCalendarStart().getTime().equals(calendar.getTime())) {
                    if (calendar.getTime().getDate() != calendar_p.getTime().getDate()) {
                        // count the days in the list
                        num_d++;
                    }
                    calendar_p = calendar;
                    date = ConnectionUtils.JsonDateToDate(orderList.get(y).getDtDateOrder());
                    calendar.setTime(date);
                    if (tasksCalanderList.get(x).getmCalendarStart().getTime().equals(calendar.getTime())) {
                        objects[i] = new TasksCalander();
                        objects[i] = tasksCalanderList.get(x);
                        x++;
                        i++;
                        objects[i] = new OrderDetailsObj();
                        objects[i] = orderList.get(y);
                        y++;
                    }
                    if (tasksCalanderList.get(x).getmCalendarStart().getTime().before(calendar.getTime())) {
                        objects[i] = new TasksCalander();
                        objects[i] = tasksCalanderList.get(x);
                        x++;
                    }
                    if (calendar.getTime().before(calendar.getTime())) {
                        objects[i] = new OrderDetailsObj();
                        objects[i] = orderList.get(y);
                        y++;
                    }

                }
            } else {
                if (orderList.size() > y) {
                    date = ConnectionUtils.JsonDateToDate(orderList.get(y).getDtDateOrder());
                    calendar.setTime(date);
                    if (calendar.getTime().getDate() != calendar_p.getTime().getDate()) {
                        num_d++;
                    }
                    calendar_p.setTime(date);
                    objects[i] = new OrderDetailsObj();
                    objects[i] = orderList.get(y);
                    y++;

                } else {
                    if (tasksCalanderList.size() > x) {

                        if (tasksCalanderList.get(x).getmCalendarStart().getTime().getDate() != calendar_p.getTime().getDate()) {
                            num_d++;
                        }
                        calendar_p = tasksCalanderList.get(x).getmCalendarStart();
                        objects[i] = new OrderDetailsObj();
                        objects[i] = tasksCalanderList.get(x);
                        x++;
                    }
                }

            }

        }

        return num_d;

    }

    public void sortList() {
        if (tasksCalanderList != null) {
            Collections.sort(tasksCalanderList, new Comparator<TasksCalander>() {
                @Override
                public int compare(TasksCalander lhs, TasksCalander rhs) {
                    return lhs.getmStartTime().compareTo(rhs.getmStartTime());
                }
            });
        }
    }

    public void sortListOrders() {
        if (orderList != null) {
            Collections.sort(orderList, new Comparator<OrderDetailsObj>() {
                @Override
                public int compare(OrderDetailsObj lhs, OrderDetailsObj rhs) {
                    return lhs.getDtDateOrder().compareTo(rhs.getDtDateOrder());
                }
            });
        }
    }


}
